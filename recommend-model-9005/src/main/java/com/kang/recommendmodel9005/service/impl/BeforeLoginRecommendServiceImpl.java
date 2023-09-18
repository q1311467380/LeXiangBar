package com.kang.recommendmodel9005.service.impl;

import com.kang.beanmodel.bean.Bar;
import com.kang.beanmodel.bean.Post;
import com.kang.beanmodel.util.ResponseData;
import com.kang.recommendmodel9005.mapper.AfterLoginRecommendMapper;
import com.kang.recommendmodel9005.openFeign.PostServiceFeign;
import com.kang.recommendmodel9005.service.BeforeLoginRecommendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

import java.util.*;

/**
 * 登录前的推荐相对潦草，过后再进行优化，目前重点在登录后的推荐
 *
 * 先按帖子的浏览量，就是zset中的score
 *再判断是否是一天内发布的（有个问题：浏览量总是以前的吧多的可能性大，要从头找3个，需要的判断次数有点多！那就写两个，一个是一天过期的一个是永久保存的
 * 首页这种需要时效性，所以推荐的）
 * 所以不用判断时间了
 * 最后获取帖子的数据
 */
@Service
@Slf4j
public class BeforeLoginRecommendServiceImpl implements BeforeLoginRecommendService {
    /**
     * 获取当天浏览量最高的3个帖子
     *   “总分“ ：--- 浏览量按百分比*0.6 + 评论数百分比* 0.4
     */
//    @Autowired
//    AfterLoginRecommendMapper afterLoginRecommendMapper;
    @Autowired
    JedisPool jedisPool;
    @Autowired
    PostServiceFeign postServiceFeign;
    @Override
    public List<ResponseData<Post>> beforeLoginRecommend() {
        Jedis resource = jedisPool.getResource();
        Map<String,Double> recommendMap = new HashMap<>();//存储推荐帖子的分数，以浏览量为主
        //计算浏览量总数，并把要比较的10个帖子放在map集合中赋初值为浏览量计算出来的值
        Set<Tuple> postViewNumber = resource.zrangeWithScores("postViewNumber", 0, 9);
        double totalPostView = 0.0;//浏览量最高的10个帖子的浏览量总和
        for (Tuple tuple : postViewNumber) {
            String postName = tuple.getElement();
            recommendMap.put(postName,0.0);
            totalPostView += tuple.getScore();
        }
        //赋推荐分（仅含浏览量部分）
        for (Tuple tuple : postViewNumber) {
            recommendMap.put(tuple.getElement(),(tuple.getScore()/totalPostView)*0.6);
        }

        Set<String> tenRecommend = recommendMap.keySet();
        Set<Tuple> commentNumber = resource.zrangeWithScores("commentNumber", 0, 9);
        double totalComment = 0.0;//评论量最高的10个帖子的评论数总和
        //判断是否是浏览量前10的帖子，不是就删除
        for (Tuple tuple : commentNumber) {
            boolean flag = false;
            String commentPostName = tuple.getElement();
            for (String postName : tenRecommend) {
                //存在于需要统计的10个帖子中
                if (commentPostName.equals(postName)){
                 totalComment += tuple.getScore();//记录同样存在浏览量前10的帖子中的帖子的评论总数
                 flag = true;
                 break;
                }
            }
            //没有该帖,就从评论数最高的10个帖子中删除
            if (!flag){
                commentNumber.remove(tuple);//注意可能有错
            }
        }
        
        /*
        加权 赋分
        先算占比
        然后加权
        再进行对应帖子的赋分
         */
        for (Map.Entry<String, Double> post : recommendMap.entrySet()) {
            for (Tuple tuple : commentNumber) {
                if (tuple.getElement().equals(post.getKey())){
                   double commentScore = (tuple.getScore() / totalComment)*0.4;
                   log.info("commentScore" + commentScore);
                   double finalScore = post.getValue()  + commentScore;
                   log.info("final:" + finalScore);
                   post.setValue(finalScore);
                   log.info("帖子的最终推荐分数：" +  post.getValue());
                }
            }
        }

        List<Map.Entry<String, Double>> entries = new ArrayList<>(recommendMap.entrySet());

        // 对辅助集合进行排序，按值的大小进行降序排序
        Collections.sort(entries, (a, b) -> b.getValue().compareTo(a.getValue()));

        // 获取前三个键
        List<String> topThreePost = new ArrayList<>();
        for (int i = 0; i < Math.min(3, entries.size()); i++) {
            topThreePost.add(entries.get(i).getKey());
        }
        log.info("前三个帖子：" + topThreePost);
        
       //根据上面求得的推荐帖子集合获取相关的帖子信息

        List<ResponseData<Post>> recommends = new ArrayList<>();
        List<Post> postList = postServiceFeign.getRecommendPosts(topThreePost);//使用openfeign远程调用post模块
        for (Post post : postList) {
            ResponseData<Post> postResponseData = new ResponseData<>();
            postResponseData.setData(post);
            String filePath = post.getFilePath();
            //获得文件的后缀名
            String[] parts = filePath.split("\\.");
            log.info("文件后缀名：" + parts[parts.length-1]);
            String type = parts[parts.length-1];
            //判断文件类型
            if (type.equalsIgnoreCase("mp4") || type.equalsIgnoreCase("avi")|| type.equalsIgnoreCase("mov")) {
                // 文件是视频
                postResponseData.setMessage("video");
                log.info("video");
            } else if (type.equalsIgnoreCase("jpg") || type.equalsIgnoreCase("png")|| type.equalsIgnoreCase("gif")) {
                // 文件是图片
                postResponseData.setMessage("picture");
                log.info("picture");
            } else {
                // 其他文件类型,其它类型不显示，但仍然提供下载
                postResponseData.setMessage("other");
                log.info("other");
            }
            recommends.add(postResponseData);
        }
        resource.close();
        return recommends;
    }
}

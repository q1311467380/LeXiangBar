package com.kang.postmodel9002.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.kang.beanmodel.bean.Bar;
import com.kang.beanmodel.bean.Post;
import com.kang.beanmodel.bean.User;
import com.kang.postmodel9002.mapper.PostMapper;
import com.kang.postmodel9002.service.PublishService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.WebUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;

@Service
@Slf4j
public class PublishServiceImpl implements PublishService {
    /**
     * 发帖
     * 先写入本地磁盘--文件放哪呢？桌面 post文件夹
     * 再写入数据库
     * 写完写redis缓存
     *
     * @param upLoadFile
     * @param title
     * @param content
     */
    @Autowired
    PostMapper postMapper;
    @Autowired
    JedisPool jedisPool;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Override
    public void newPost(MultipartFile uploadFile, String title, String content, HttpServletRequest req) {
//        String userInfo = req.getHeader("userInfo");
//        String barInfo = req.getHeader("barInfo");
//        log.info("title:" + title);
//        log.info("content:" + content);
//        User user = JSONObject.parseObject(userInfo, User.class);
//        Bar bar = JSONObject.parseObject(barInfo, Bar.class);
//        String username = user.getUsername();
//        log.info("username:" + username);
//        String barName = bar.getBarName();
//        log.info("barName:" + barName);
        String username = req.getHeader("username");
        String barName = req.getHeader("barName");
        //判断用户是否写过该帖名的帖子,不允许一个用户有相同帖名的两篇帖子

        List<String> postNameList = postMapper.selectPostNameByUsername(username);
        for (String s : postNameList) {
            if (s.equals(title)){
                return;
            }
        }
        Timestamp createTime = new Timestamp(System.currentTimeMillis());
        //写入本地
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            String realName = uploadFile.getOriginalFilename();//文件的名字
            String name = System.currentTimeMillis() + "-" + realName;
            String realPath = "C:/Users/86157/Desktop/post/" + name;
            inputStream = uploadFile.getInputStream();
            fileOutputStream = new FileOutputStream(realPath);
            IOUtils.copy(inputStream, fileOutputStream);
            //附件的的地址登记在表中，因为是访问该文件时是服务器访问本地的文件，不行，所以要为该路径设置一个虚拟路径
            String path = "/kang/" + name;
            //写入数据库
            //有个小问题，帖子需要绑定作者和所属的吧的信息（这里用id），又不能让用户自己输入，该怎么携带过来
            //解决：使用cookie进行数据的传递，因为多模块项目本质是多个服务器的，会有不同的session，而cookie是客户端所有的所有服务端共用一个cookie

            Post post0 = new Post(title,username,barName,content, path,createTime);
            log.info("post:" + post0);
            postMapper.insertNewPost(post0);
            Integer postId = post0.getId();
            log.info("postId: " + postId);
            //redis缓存的写入,写入该帖子的表中的数据
            //该使用id区分redis中的post
            Jedis resource = jedisPool.getResource();
            Post post = postMapper.selectPostByName(title);
            resource.set("post-" + postId,JSONObject.toJSONString(post));


            //写入记录帖子评论数的zSet集合并设置过期时间
            // 添加成员到 zSet
            resource.zadd("postViewNumber",0,title);
            resource.zadd("commentNumber",0,title);
            resource.zadd("lastCommentTime-" + barName, System.currentTimeMillis(),post.getPostName());//记录各个贴吧中帖子的最后回复时间，在帖子刚开始创建时创建时间就是最后回复时间
            log.info("浏览：" + resource.zrange("postViewNumber",0,5));

            //存储成员的过期时间戳
            long currentTimeMillis = System.currentTimeMillis() + 864000000;//帖子设置就行了，目前仅考虑帖子的时间
            resource.hset("commentTime",title, String.valueOf(currentTimeMillis));
            log.info("time:" + resource.hget("commentTime",title));
            resource.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null){
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fileOutputStream != null){
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

package com.kang.recommendmodel9005.controller;

import com.kang.beanmodel.bean.Bar;
import com.kang.beanmodel.bean.Post;
import com.kang.beanmodel.util.ResponseData;
import com.kang.recommendmodel9005.service.BeforeLoginRecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 登录前的推荐算法
 *这里简单用浏览量、评论量和时间三个因素使用加权写一个基础的推荐算法
 * 时间上限定为一天内，然后浏览量和评论量是7，3加权
 *
 *
 * 这里是根据前端传来的序号进行推荐，序号为一，就推荐推荐分第二的帖子
 *
 * 到这里需要考虑两个问题：
 *      帖子分数的实时计算和实时更新
 *      返回什么给前端---考虑，前端能接什么 和 前端需要什么
 *      需要：吧名 吧地址 帖子名 帖子地址 帖子最热评论
 *      假如现在已经做出了推荐，服务端可以有：吧名，帖子名，帖子最热评论
 *      吧地址查不到，只能通过用该吧名为uri的接口方法查询该吧的相应内容，在跳转到吧的视图进行渲染 借鉴mvc那个流程图
 *      用jsp 动态页面设计的话一个页面的内容的动态显示只需要一次异步请求 麻烦在于我的html页面需要改成jsp页面
 *      那就用thymeleft 不用改文件格式
 *
 *    】【怕使用单纯的前端三剑客也行，但是考虑到文件的上传还是使用thymeleft比较熟悉
 */

/**
 * 返回的内容包括： 贴吧名 帖子名 帖子内容
 * 返回List<ResponseData<Post>> 帖子内容类别怎么办--在message中表明
 */

@Controller
public class BeforeLoginRecommendController {
    @Autowired
    BeforeLoginRecommendService beforeLoginRecommendService;
    @RequestMapping("/beforeLoginRecommendPost")
    @ResponseBody
    @CrossOrigin
    public List<ResponseData<Post>> beforeLoginRecommendPost(){

        return beforeLoginRecommendService.beforeLoginRecommend();
    }
}

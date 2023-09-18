package com.kang.postmodel9002.controller;

import com.kang.beanmodel.bean.Post;
import com.kang.beanmodel.bean.User;
import com.kang.beanmodel.util.ResponseData;
import com.kang.postmodel9002.service.GetPostInfoService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 进入帖子页面前获得该帖子的相关信息
 * 然后到达帖子页面的时候前端再取用展示页面内容
 *
 * 获取post（指定贴吧）页面的内容
 * 返回自定义响应体
 * code--暂无
 * message--告知前端该帖子的内容是纯文本、文本加视频，还是文本加图片--暂时就这三种
 */
@Controller
public class GetPostInfoController {
    @Autowired
    GetPostInfoService getPostInfoService;
    @RequestMapping("/getPostInfo")
    @ResponseBody
    @CrossOrigin
    public ResponseData<User> getPostInfo(@RequestParam String postName,HttpServletRequest req){
        return getPostInfoService.getPost(postName,req);
    }
}

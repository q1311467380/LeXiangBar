package com.kang.postmodel9002.controller;



import com.kang.postmodel9002.service.PublishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * 发布帖子的服务端逻辑
 * 文件的上传
 * MySQL表的插入
 * redis缓存的写入
 * 数据
 */
@Controller
@Slf4j
public class PublishPostController {
    @Autowired
    PublishService publishService;
    @RequestMapping( "/publishPost")
    @ResponseBody
    @CrossOrigin
    public String publishPost(@RequestParam("uploadFile") MultipartFile uploadFile, @RequestParam("title") String title, @RequestParam("content") String content, HttpServletRequest req) throws IOException {
//        BufferedReader reader = req.getReader();
//        StringBuilder stringBuilder = new StringBuilder();
//        String temp = "";
//        while ((temp = reader.readLine()) != null) {
//            stringBuilder.append(temp);
//        }
//        log.info(stringBuilder.toString());

        log.info("附件名：" + uploadFile.getOriginalFilename());
        publishService.newPost(uploadFile,title,content,req);

        return "1";//如果发布成功就跳转到该帖子的页面
    }
}

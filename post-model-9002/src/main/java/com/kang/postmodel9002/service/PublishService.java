package com.kang.postmodel9002.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface PublishService {
    void newPost(MultipartFile uploadFile, String title, String content, HttpServletRequest req);
}

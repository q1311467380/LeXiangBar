package com.kang.recommendmodel9005.controller;

import com.kang.recommendmodel9005.service.PreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */
@Controller
public class PreferenceController {
    @Autowired
    PreferenceService preferenceService;
    @RequestMapping("/setPreference")
    @ResponseBody
    @CrossOrigin
    public String setPreference(@RequestBody String[] selectedPreferences, HttpServletRequest req){
        preferenceService.setPreference(selectedPreferences,req);
        return "ok";//需不需要返回用户偏好信息等等，看后面逻辑的需求
    }
}

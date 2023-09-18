package com.kang.recommendmodel9005.service;

import javax.servlet.http.HttpServletRequest;

public interface PreferenceService {
    void setPreference(String[] selectedPreferences,HttpServletRequest req);
}

package com.kang.recommendmodel9005.service;

import javax.servlet.http.HttpServletRequest;

public interface RegularSetPreferenceService {
    void setExpireTime(HttpServletRequest req);

    Long getExpireTime(HttpServletRequest req);
}

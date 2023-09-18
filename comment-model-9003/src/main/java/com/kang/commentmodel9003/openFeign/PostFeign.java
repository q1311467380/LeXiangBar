package com.kang.commentmodel9003.openFeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 *
 */
@FeignClient("post-server")
@Component
public interface PostFeign {

}

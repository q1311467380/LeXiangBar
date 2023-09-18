package com.kang.recommendmodel9005.openFeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * 关于bar模块的远程调用
 */
@FeignClient("bar-server")
@Component
public interface BarServiceFeign {
}

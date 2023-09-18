package com.kang.usermodel9000.interceptor;

import com.kang.usermodel9000.annotation.CheckToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 判断是否是正常用户操作的代码
 * 多处用到所以提取到拦截器使用
 * 因为第一次登录的接口方法不用这个所以加个注解标明一下，没标注解的接口方法就都进行判断，不是正常用户操作就阻止操作
 * 要注入到ioc中，不然没办法用@Autowired进行注入
 */
@Component
public class CheckTokenInterceptor implements HandlerInterceptor {
    @Autowired
    JedisPool jedisPool;
    /**
     * 在正要调用接口方法前拦截
     * @param request
     * @param response
     * @param handler -- 拦截的对象(反射动态创建的Class对象)
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断是接口方法
        if (handler instanceof HandlerMethod){
            HandlerMethod interceptorMethod = (HandlerMethod) handler;
            CheckToken methodAnnotation = interceptorMethod.getMethodAnnotation(CheckToken.class);
            //判断是否是被拦截的方法，有被注解就是，所以判断非空
            if (methodAnnotation != null){
                return true;
            }
            String token = request.getHeader("loginSalt");//获取存储ip地址的钥匙-uuid
            String realToken = token.replace("\"", "");
            String remoteAddr = request.getRemoteAddr();
            Jedis resource = jedisPool.getResource();
            String realIp = resource.get(realToken);
            if (!(remoteAddr.equals(realIp))){
                response.getWriter().write("异常操作");
                resource.close();
                return false;
            }
            resource.close();
        }
        return true;
    }
}

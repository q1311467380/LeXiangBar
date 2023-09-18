package com.kang.usermodel9000;

import com.kang.usermodel9000.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 登录逻辑服务端测试
 */
@SpringBootTest
@Slf4j
public class LoginTest {
    @Autowired
    UserMapper userMapper;
    @Test
    void checkUserInfo(){
        log.info("password: " + userMapper.selectPasswordByUsername("kang"));

    }

}

package com.kang.usermodel9000;

import com.kang.beanmodel.bean.User;
import com.kang.usermodel9000.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *关于注册逻辑的测试
 */
@SpringBootTest
@Slf4j
public class SignUpTest {
    @Autowired
    UserMapper userMapper;
    @Test
    void insertTest(){
        User kk = new User("kk", "321");
        boolean b = userMapper.insertUser(kk);
        log.info("result:" + b);
    }
}

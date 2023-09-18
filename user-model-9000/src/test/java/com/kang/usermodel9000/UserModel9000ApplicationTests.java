package com.kang.usermodel9000;

import com.kang.usermodel9000.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

@SpringBootTest
class UserModel9000ApplicationTests {

    @Test
    void contextLoads() {
    }

    /**
     * 测试用户名错误的查询数据库返回的值是什么
     * null
     */
    @Autowired
    UserMapper userMapper;
    @Test
    void test1(){
        String s = userMapper.selectUsernameById(2);
        System.out.println("username: " + s);
    }

}

package com.kang.usermodel9000;

import com.kang.beanmodel.bean.User;
import com.kang.usermodel9000.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class InfoTest {
    @Autowired
    UserMapper userMapper;
    @Test
    public void getInfo(){
        User kang = userMapper.getUserByUsername("kang");
        log.info("kang" + kang);
    }
    @Test
    public void getInfo2(){

    }
}

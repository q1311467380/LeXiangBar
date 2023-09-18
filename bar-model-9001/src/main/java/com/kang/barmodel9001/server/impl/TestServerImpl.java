package com.kang.barmodel9001.server.impl;

import com.kang.barmodel9001.bean.TestBean;
import com.kang.barmodel9001.mapper.TestMapper;
import com.kang.barmodel9001.server.TestServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServerImpl implements TestServer {
    @Autowired
    TestMapper testMapper;
    @Override
    public String getBarName(Integer id) {
        TestBean barNameById = testMapper.getBarNameById(id);
        String s = barNameById.toString();
        return s;
    }
}

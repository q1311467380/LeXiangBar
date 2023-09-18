package com.kang.barmodel9001.mapper;

import com.kang.barmodel9001.bean.TestBean;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public interface TestMapper {
    TestBean getBarNameById(Integer id);
}

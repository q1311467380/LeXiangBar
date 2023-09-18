package com.kang.usermodel9000.mapper;

import com.kang.beanmodel.bean.User;
import org.springframework.stereotype.Repository;

import javax.servlet.http.Cookie;

/**
 *关于用户的持久层操作
 */
@Repository
public interface UserMapper {
    /**
     * 测试用例
     * @param id
     * @return
     */
    String selectUsernameById (Integer id);


    String selectPasswordByUsername(String username);

    boolean insertUser(User user);

    Integer getUserIdByUsername(String username);

    User getUserByUsername(String username);

    User getUserAllByUsername(String username);
}

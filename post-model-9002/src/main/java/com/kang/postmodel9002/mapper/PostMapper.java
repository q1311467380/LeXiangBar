package com.kang.postmodel9002.mapper;

import com.kang.beanmodel.bean.Post;
import org.springframework.stereotype.Repository;

import javax.servlet.http.Cookie;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 */
@Repository
public interface PostMapper {
    boolean insertNewPost(Post post);

    Post selectPostByName(String title);

    List<Post> getRecentlyPostByUsername(String username);

    Post getPostByUsernameAndPostName(String username, String postName);

    List<String> selectPostNameByUsername(String username);
}

package com.kang.commentmodel9003;

import com.kang.beanmodel.bean.Comment;
import com.kang.commentmodel9003.mapper.CommentMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
@Slf4j
public class GetCommentTest {
    @Autowired
    CommentMapper commentMapper;
    @Test
    public void getAllCommentTest(){
        List<Comment> allCommentByPostName = commentMapper.getAllCommentByPostName("newPost1");
        for (Comment comment : allCommentByPostName) {
            log.info("评论：" + comment);
        }
    }
    @Test
    public void test2(){
//        System.out.println(System.currentTimeMillis());
        long currentTimeMillis = System.currentTimeMillis();
        System.out.println(currentTimeMillis  + 86400000);
    }
    @Test
    public void test3(){
        Map<String, Double> myMap = new HashMap<>();
        myMap.put("apple", 10.0);
        myMap.put("banana", 5.0);
        myMap.put("cherry", 8.0);
        myMap.put("date", 15.0);
        myMap.put("elderberry", 3.0);

        List<Map.Entry<String, Double>> entries = new ArrayList<>(myMap.entrySet());

        // 对辅助集合进行排序，按值的大小进行降序排序
        Collections.sort(entries, (a, b) -> b.getValue().compareTo(a.getValue()));

        // 获取前三个键
        List<String> topThreeKeys = new ArrayList<>();
        for (int i = 0; i < Math.min(3, entries.size()); i++) {
            topThreeKeys.add(entries.get(i).getKey());
        }

        System.out.println(topThreeKeys);
    }
}

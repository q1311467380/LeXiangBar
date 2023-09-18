package com.kang.attentionmodel9004.mapper;

import com.kang.beanmodel.bean.Fans;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 关于粉丝的持久化操作接口
 */
@Repository
public interface FansMapper {

    boolean insertNewFans(Fans fans);

    List<String> selectBarNameByUsername(String username);

    void deleteAttentionByUsernameAndBarName(String username, String barName);
}

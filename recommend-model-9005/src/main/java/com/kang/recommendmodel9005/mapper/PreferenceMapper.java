package com.kang.recommendmodel9005.mapper;

import com.kang.beanmodel.bean.Preference;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

/**
 *
 */
@Repository
public interface PreferenceMapper {
    Preference selectPreferenceByUsernameAndKindName(String username, String selectedPreference);

    void insertPreference(String username, String selectedPreference);

    void deletePreferenceByUsername(String username);

    Timestamp getTime(String postName);
}

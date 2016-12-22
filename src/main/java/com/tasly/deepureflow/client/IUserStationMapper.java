package com.tasly.deepureflow.client;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tasly.deepureflow.domain.user.UserStationExample;
import com.tasly.deepureflow.domain.user.UserStationKey;

public interface IUserStationMapper {
    int countByExample(UserStationExample example);

    int deleteByExample(UserStationExample example);

    int deleteByPrimaryKey(UserStationKey key);

    int insert(UserStationKey record);

    int insertSelective(UserStationKey record);

    List<UserStationKey> selectByExample(UserStationExample example);

    int updateByExampleSelective(@Param("record") UserStationKey record, @Param("example") UserStationExample example);

    int updateByExample(@Param("record") UserStationKey record, @Param("example") UserStationExample example);
}
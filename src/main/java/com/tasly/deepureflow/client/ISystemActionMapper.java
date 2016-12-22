package com.tasly.deepureflow.client;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tasly.deepureflow.domain.system.SystemAction;
import com.tasly.deepureflow.domain.system.SystemActionExample;

public interface ISystemActionMapper {
    int countByExample(SystemActionExample example);

    int deleteByExample(SystemActionExample example);

    int deleteByPrimaryKey(Integer actionId);

    int insert(SystemAction record);

    int insertSelective(SystemAction record);

    List<SystemAction> selectByExample(SystemActionExample example);

    SystemAction selectByPrimaryKey(Integer actionId);

    int updateByExampleSelective(@Param("record") SystemAction record, @Param("example") SystemActionExample example);

    int updateByExample(@Param("record") SystemAction record, @Param("example") SystemActionExample example);

    int updateByPrimaryKeySelective(SystemAction record);

    int updateByPrimaryKey(SystemAction record);
}
package com.tasly.deepureflow.client;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tasly.deepureflow.domain.system.ZonePlan;
import com.tasly.deepureflow.domain.system.ZonePlanExample;

public interface IZonePlanMapper {
    int countByExample(ZonePlanExample example);

    int deleteByExample(ZonePlanExample example);

    int insert(ZonePlan record);

    int insertSelective(ZonePlan record);

    List<ZonePlan> selectByExample(ZonePlanExample example);

    int updateByExampleSelective(@Param("record") ZonePlan record, @Param("example") ZonePlanExample example);

    int updateByExample(@Param("record") ZonePlan record, @Param("example") ZonePlanExample example);
}
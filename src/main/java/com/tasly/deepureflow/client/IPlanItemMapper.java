package com.tasly.deepureflow.client;

import com.tasly.deepureflow.domain.deepureflow.PlanItem;
import com.tasly.deepureflow.domain.deepureflow.PlanItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IPlanItemMapper {
    int countByExample(PlanItemExample example);

    int deleteByExample(PlanItemExample example);

    int deleteByPrimaryKey(String salePlanItemId);

    int insert(PlanItem record);

    int insertSelective(PlanItem record);

    List<PlanItem> selectByExample(PlanItemExample example);

    PlanItem selectByPrimaryKey(String salePlanItemId);

    int updateByExampleSelective(@Param("record") PlanItem record, @Param("example") PlanItemExample example);

    int updateByExample(@Param("record") PlanItem record, @Param("example") PlanItemExample example);

    int updateByPrimaryKeySelective(PlanItem record);

    int updateByPrimaryKey(PlanItem record);
}
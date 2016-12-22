package com.tasly.deepureflow.client;

import com.tasly.deepureflow.domain.deepureflow.SalePlan;
import com.tasly.deepureflow.domain.deepureflow.SalePlanExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ISalePlanMapper {
    int countByExample(SalePlanExample example);

    int deleteByExample(SalePlanExample example);

    int deleteByPrimaryKey(String salePlanId);

    int insert(SalePlan record);

    int insertSelective(SalePlan record);

    List<SalePlan> selectByExample(SalePlanExample example);

    SalePlan selectByPrimaryKey(String salePlanId);

    int updateByExampleSelective(@Param("record") SalePlan record, @Param("example") SalePlanExample example);

    int updateByExample(@Param("record") SalePlan record, @Param("example") SalePlanExample example);

    int updateByPrimaryKeySelective(SalePlan record);

    int updateByPrimaryKey(SalePlan record);
}
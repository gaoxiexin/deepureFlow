package com.tasly.deepureflow.client;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tasly.deepureflow.domain.system.Hierarchy;
import com.tasly.deepureflow.domain.system.HierarchyExample;

public interface IHierarchyMapper {
    int countByExample(HierarchyExample example);

    int deleteByExample(HierarchyExample example);

    int deleteByPrimaryKey(String hierarchyId);

    int insert(Hierarchy record);

    int insertSelective(Hierarchy record);

    List<Hierarchy> selectByExample(HierarchyExample example);

    Hierarchy selectByPrimaryKey(Integer i);

    int updateByExampleSelective(@Param("record") Hierarchy record, @Param("example") HierarchyExample example);

    int updateByExample(@Param("record") Hierarchy record, @Param("example") HierarchyExample example);

    int updateByPrimaryKeySelective(Hierarchy record);

    int updateByPrimaryKey(Hierarchy record);
    
    List<Hierarchy> queryHierarchyByName(Map<String,Object> map);
}
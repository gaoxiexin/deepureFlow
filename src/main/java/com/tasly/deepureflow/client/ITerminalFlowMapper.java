package com.tasly.deepureflow.client;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tasly.deepureflow.domain.deepureflow.TerminalFlow;
import com.tasly.deepureflow.domain.deepureflow.TerminalFlowExample;


public interface ITerminalFlowMapper {
    int countByExample(TerminalFlowExample example);

    int deleteByExample(TerminalFlowExample example);

    int deleteByPrimaryKey(String terminalFlowId);

    int insert(TerminalFlow record);

    int insertSelective(TerminalFlow record);

    List<TerminalFlow> selectByExample(TerminalFlowExample example);

    TerminalFlow selectByPrimaryKey(String terminalFlowId);

    int updateByExampleSelective(@Param("record") TerminalFlow record, @Param("example") TerminalFlowExample example);

    int updateByExample(@Param("record") TerminalFlow record, @Param("example") TerminalFlowExample example);

    int updateByPrimaryKeySelective(TerminalFlow record);

    int updateByPrimaryKey(TerminalFlow record);
}
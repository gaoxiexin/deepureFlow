package com.tasly.deepureflow.client;

import com.tasly.deepureflow.domain.deepureflow.TerminalFlowItem;
import com.tasly.deepureflow.domain.deepureflow.TerminalFlowItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ITerminalFlowItemMapper {
    int countByExample(TerminalFlowItemExample example);

    int deleteByExample(TerminalFlowItemExample example);

    int deleteByPrimaryKey(String terminalFlowItemId);

    int insert(TerminalFlowItem record);

    int insertSelective(TerminalFlowItem record);

    List<TerminalFlowItem> selectByExample(TerminalFlowItemExample example);

    TerminalFlowItem selectByPrimaryKey(String terminalFlowItemId);

    int updateByExampleSelective(@Param("record") TerminalFlowItem record, @Param("example") TerminalFlowItemExample example);

    int updateByExample(@Param("record") TerminalFlowItem record, @Param("example") TerminalFlowItemExample example);

    int updateByPrimaryKeySelective(TerminalFlowItem record);

    int updateByPrimaryKey(TerminalFlowItem record);
}
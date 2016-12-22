package com.tasly.deepureflow.client;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tasly.deepureflow.domain.user.Terminal;
import com.tasly.deepureflow.domain.user.TerminalExample;

public interface ITerminalMapper {
    int countByExample(TerminalExample example);

    int deleteByExample(TerminalExample example);

    int deleteByPrimaryKey(String terminalId);

    int insert(Terminal record);

    int insertSelective(Terminal record);

    List<Terminal> selectByExample(TerminalExample example);

    Terminal selectByPrimaryKey(String terminalId);

    int updateByExampleSelective(@Param("record") Terminal record, @Param("example") TerminalExample example);

    int updateByExample(@Param("record") Terminal record, @Param("example") TerminalExample example);

    int updateByPrimaryKeySelective(Terminal record);

    int updateByPrimaryKey(Terminal record);
}
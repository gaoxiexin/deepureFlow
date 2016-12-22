package com.tasly.deepureflow.client;

import com.tasly.deepureflow.domain.system.Zone;
import com.tasly.deepureflow.domain.system.ZoneExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IZoneMapper {
    int countByExample(ZoneExample example);

    int deleteByExample(ZoneExample example);

    int deleteByPrimaryKey(Integer zoneId);

    int insert(Zone record);

    int insertSelective(Zone record);

    List<Zone> selectByExample(ZoneExample example);

    Zone selectByPrimaryKey(Integer zoneId);

    int updateByExampleSelective(@Param("record") Zone record, @Param("example") ZoneExample example);

    int updateByExample(@Param("record") Zone record, @Param("example") ZoneExample example);

    int updateByPrimaryKeySelective(Zone record);

    int updateByPrimaryKey(Zone record);
}
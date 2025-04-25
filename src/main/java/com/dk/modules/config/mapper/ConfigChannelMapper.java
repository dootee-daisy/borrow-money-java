package com.dk.modules.config.mapper;

import com.dk.modules.config.po.ConfigChannel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ConfigChannelMapper {
    int deleteByPrimaryKey(String channelCode);

    int insert(ConfigChannel record);

    int insertSelective(ConfigChannel record);

    ConfigChannel selectByPrimaryKey(String channelCode);

    int updateByPrimaryKeySelective(ConfigChannel record);

    int updateByPrimaryKey(ConfigChannel record);

    List<ConfigChannel> selectAll();
}
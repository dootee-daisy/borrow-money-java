package com.dk.modules.config.mapper;

import com.dk.modules.config.po.ConfigSpread;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ConfigSpreadMapper {
    int deleteByPrimaryKey(String id);

    int insert(ConfigSpread record);

    int insertSelective(ConfigSpread record);

    ConfigSpread selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ConfigSpread record);

    int updateByPrimaryKey(ConfigSpread record);
}
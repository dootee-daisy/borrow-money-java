package com.dk.modules.config.mapper;

import com.dk.modules.config.po.ConfigOrderWords;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ConfigOrderWordsMapper {
    int deleteByPrimaryKey(String tipsId);

    int insert(ConfigOrderWords record);

    int insertSelective(ConfigOrderWords record);

    ConfigOrderWords selectByPrimaryKey(String tipsId);

    List<ConfigOrderWords> selectWords();

    int updateByPrimaryKeySelective(ConfigOrderWords record);

    int updateByPrimaryKey(ConfigOrderWords record);
}
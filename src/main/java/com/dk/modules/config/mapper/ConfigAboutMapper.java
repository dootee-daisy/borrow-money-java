package com.dk.modules.config.mapper;

import com.dk.modules.config.po.ConfigAbout;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ConfigAboutMapper {
    int deleteByPrimaryKey(String aboutId);

    int insert(ConfigAbout record);

    int insertSelective(ConfigAbout record);

    ConfigAbout selectByPrimaryKey(String aboutId);

    int updateByPrimaryKeySelective(ConfigAbout record);

    int updateByPrimaryKeyWithBLOBs(ConfigAbout record);

    List<ConfigAbout> selectByType(@Param("aboutType") int aboutType);
}
package com.dk.modules.config.mapper;

import com.dk.modules.config.po.ConfigAgreement;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ConfigAgreementMapper {
    int deleteByPrimaryKey(String aId);

    int insert(ConfigAgreement record);

    int insertSelective(ConfigAgreement record);

    ConfigAgreement selectByPrimaryKey(String aId);

    int updateByPrimaryKeySelective(ConfigAgreement record);

    int updateByPrimaryKeyWithBLOBs(ConfigAgreement record);

    int updateByPrimaryKey(ConfigAgreement record);

    List<ConfigAgreement> selectAll();
}
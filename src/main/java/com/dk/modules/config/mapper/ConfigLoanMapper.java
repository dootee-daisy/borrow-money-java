package com.dk.modules.config.mapper;

import com.dk.modules.config.po.ConfigLoan;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ConfigLoanMapper {
    int deleteByPrimaryKey(String lId);

    int insert(ConfigLoan record);

    int insertSelective(ConfigLoan record);

    ConfigLoan selectByPrimaryKey(String lId);

    int updateByPrimaryKeySelective(ConfigLoan record);

    int updateByPrimaryKey(ConfigLoan record);
}
package com.dk.modules.member.mapper;

import com.dk.modules.member.po.MemberInfoDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberInfoDetailMapper {
    int deleteByPrimaryKey(String id);

    int insert(MemberInfoDetail record);

    int insertSelective(MemberInfoDetail record);

    MemberInfoDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MemberInfoDetail record);

    int updateByPrimaryKey(MemberInfoDetail record);

    int selectRealnameByDay();
}
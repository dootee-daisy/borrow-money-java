package com.dk.modules.member.mapper;

import com.dk.modules.member.po.MemberInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(MemberInfo record);

    int insertSelective(MemberInfo record);

    MemberInfo selectByPrimaryKey(String id);

    List<MemberInfo> selectMemberAll(@Param("phone")String phone,@Param("idInfo") Integer idInfo);

    int updateByPrimaryKeySelective(MemberInfo record);

    int updateByPrimaryKey(MemberInfo record);


    int selectRegisterByDay();
}
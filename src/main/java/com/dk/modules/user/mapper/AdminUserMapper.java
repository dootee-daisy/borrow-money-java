package com.dk.modules.user.mapper;

import com.dk.modules.user.po.AdminUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(AdminUser record);

    int insertSelective(AdminUser record);

    AdminUser selectByPrimaryKey(String id);

    List<AdminUser> selectConfigAdminAll(@Param("phone")String phone);

    int updateByPrimaryKeySelective(AdminUser record);

    int updateByPrimaryKey(AdminUser record);


    AdminUser selectByAccountAndPwd(@Param("account") String account, @Param("password")String password);
}
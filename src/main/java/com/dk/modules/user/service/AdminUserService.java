package com.dk.modules.user.service;

import com.dk.common.exception.MyServiceException;
import com.dk.common.util.IDGenerator;
import com.dk.modules.user.mapper.AdminUserMapper;
import com.dk.modules.user.po.AdminUser;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUserService {

    @Autowired
    private AdminUserMapper configAdminMapper;

    //Truy vấn quản trị viên trang web
    public List<AdminUser> findAdminAllUser(String phone,int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return configAdminMapper.selectConfigAdminAll(phone);
    }

    public AdminUser findById(String id){
        return configAdminMapper.selectByPrimaryKey(id);
    }

    //Thêm quản trị viên trang web
    public void addAdminUser(AdminUser adminUser) {
        adminUser.setId(IDGenerator.createUserId());
        configAdminMapper.insertSelective(adminUser);
    }

    //Chỉnh sửa quản trị viên trang web
    public void updateAdminUser(AdminUser adminUser) {
        //adminUser.setType(null);
        configAdminMapper.updateByPrimaryKeySelective(adminUser);
    }

    //Xóa quản trị viên trang web
    public void deleteConfigAdmin(String id) {
        AdminUser adminUser = new AdminUser();
        adminUser.setId(id);
        adminUser.setDeleted(new Integer(1));
        configAdminMapper.updateByPrimaryKeySelective(adminUser);
    }

    public AdminUser login(AdminUser user)throws MyServiceException {
        AdminUser u = configAdminMapper.selectByAccountAndPwd(user.getAccount(),user.getPassword());
        if (u == null){
            throw new MyServiceException("用户名或密码错误");
        }else {
            if (u.getStatus().endsWith("1")){
                throw new MyServiceException("用户已注销");
            }
        }
        return u;
    }
}

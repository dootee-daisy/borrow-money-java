package com.dk.modules.user.controller;


import com.alibaba.fastjson.JSONObject;
import com.dk.common.constant.Constant;
import com.dk.common.exception.MyServiceException;
import com.dk.common.http.DataResult;
import com.dk.common.http.PageRequest;
import com.dk.common.http.PageResult;
import com.dk.common.http.Result;
import com.dk.common.redis.RedisKeyPrefix;
import com.dk.common.redis.RedisService;
import com.dk.interceptor.AuthorityHelper;
import com.dk.modules.user.po.AdminUser;
import com.dk.modules.user.service.AdminUserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/dk/admin/user")
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private AuthorityHelper authorityHelper;
    @Autowired
    private RedisService redisService;
    //查询网站管理员
    @ResponseBody
    @PostMapping("/list")
    public PageResult queryAdminAll(@RequestBody PageRequest pageRequest){
        String phone = null;
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        if (null != pageRequest.getParam()){
            phone = pageRequest.getParam().getString("phone");
        }
        List<AdminUser> adminUsers= adminUserService.findAdminAllUser(phone,pageNum,pageSize);
        PageInfo pageInfo = new PageInfo(adminUsers);
        PageResult result = PageResult.init();
        result.setAllCount(pageInfo.getTotal());
        result.setPageCount(pageInfo.getPages());
        result.setPageNum(pageInfo.getPageNum());
        result.setPageSize(pageInfo.getPageSize());
        result.setData(pageInfo);
        return  result;
    }

    //添加网站管理员
    @PostMapping("/add")
    public Result addAdmin(@RequestBody AdminUser adminUser){
        adminUserService.addAdminUser(adminUser);
        return Result.init();
    }

    //修改网站管理员
    @PostMapping("/update")
    public Result updateWebAdmin(@RequestBody AdminUser adminUser){
        adminUserService.updateAdminUser(adminUser);
        return Result.init();
    }

    //删除网站管理员
    @PostMapping("/delete")
    public Result deleteWebAdmin(@RequestBody JSONObject param){
        String id = param.getString("id");
        adminUserService.deleteConfigAdmin(id);
        return Result.init();
    }

    //删除网站管理员
    @PostMapping("/login")
    public DataResult login(HttpServletResponse response, @RequestBody AdminUser user) throws MyServiceException {
        AdminUser u = adminUserService.login(user);
        JSONObject object = new JSONObject();
        object.put(Constant.HTTP_HEADER_ID,u.getId());
        object.put(Constant.HTTP_HEADER_TYPE,2);
        String token = UUID.randomUUID().toString().replace("-","");
        object.put("token",token);
        redisService.addDefaultAgeValue(RedisKeyPrefix.GLOBAL_LOGIN_TOKEN+token,object);
        authorityHelper.login(response,object);
        return DataResult.init().buildData(object);
//        return Result.init();
    }

    @PostMapping("/logout")
    public Result logout(HttpServletRequest request) throws MyServiceException {
        String token = request.getHeader(Constant.HTTP_HEADER_TOKEN);
        authorityHelper.logout(token);
        return Result.init();
    }

}

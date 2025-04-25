package com.dk.modules.config.controller;


import com.alibaba.fastjson.JSONObject;
import com.dk.common.constant.Constant;
import com.dk.common.http.DataResult;
import com.dk.common.http.Result;
import com.dk.modules.config.po.ConfigAbout;
import com.dk.modules.config.service.ConfigAboutService;
import com.dk.modules.user.po.AdminUser;
import com.dk.modules.user.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class AboutConfigController {

    @Autowired
    private ConfigAboutService configAboutService;

    @PostMapping("/dk/config/about")
    public DataResult findAbout(){
        return DataResult.init().buildData(configAboutService.findById("1"));
    }

    @PostMapping("/dk/config/banner")
    public DataResult findBanner(){
        return DataResult.init().buildData(configAboutService.findById("2"));
    }

    @PostMapping("/dk/config/guide")
    public DataResult findGuide(){
        return DataResult.init().buildData(configAboutService.findType(3));
    }

    @PostMapping("/dk/admin/config/about/update")
    public Result update(@RequestBody ConfigAbout about){
        configAboutService.update(about);
        return Result.init();
    }


    @Autowired
    private AdminUserService userService;

    //菜单权限
    @GetMapping("/dk/config/menu")
    public JSONObject queryMenu(HttpServletRequest request){
        String id = request.getHeader(Constant.HTTP_HEADER_ID);
        AdminUser user = userService.findById(id);
        List<JSONObject> menus = createMenu(user.getType());

        JSONObject result = new JSONObject();
        result.put("code",0);
        result.put("data",menus);
        return result;
    }

    private List<JSONObject> createMenu(int userType){
        List<JSONObject> list = new ArrayList<>();
        JSONObject o = new JSONObject();
        o.put("title","控制台");
        o.put("icon","layui-icon-home");
        o.put("jump","/index");
        if (userType==1){
            list.add(o);
            o = new JSONObject();
            o.put("name","sysset");
            o.put("title","系统设置");
            o.put("icon","layui-icon-set");
            List<JSONObject> children = new ArrayList<>();
            o.put("list",children);
            list.add(o);
            JSONObject child = new JSONObject();
            child.put("name","daikuan");
            child.put("title","贷款设置");
            child.put("jump","sysset/daikuanshezhi");
            children.add(child);
            child = new JSONObject();
            child.put("name","jiekuanxieyi");
            child.put("title","借款协议");
            child.put("jump","sysset/xieyi/jiekuanxieyi");
            children.add(child);
            child = new JSONObject();
            child.put("name","fuwuxieyi");
            child.put("title","服务协议");
            child.put("jump","sysset/xieyi/fuwuxieyi");
            children.add(child);
            child = new JSONObject();
            child.put("name","orderstatus");
            child.put("title","订单状态");
            child.put("jump","sysset/orderstatus/osList");
            children.add(child);
            child = new JSONObject();
            child.put("name","channel");
            child.put("title","推广渠道");
            child.put("jump","sysset/tgqd/tgqdList");
            children.add(child);
            child = new JSONObject();
            child.put("name","area");
            child.put("title","区域设置");
            child.put("jump","sysset/area/area");
            children.add(child);

            o = new JSONObject();
            o.put("name","user");
            o.put("title","用户管理");
            o.put("icon","layui-icon-user");
            o.put("jump","user/user/list");
            list.add(o);
        }
        o = new JSONObject();
        o.put("name","member");
        o.put("title","会员管理");
        o.put("icon","layui-icon-face-smile");
        o.put("jump","member/memberList");
        list.add(o);
        o = new JSONObject();
        o.put("name","order");
        o.put("title","订单管理");
        o.put("icon","layui-icon-app");
        o.put("jump","order/orderList");
        list.add(o);

        return list;
    }
}

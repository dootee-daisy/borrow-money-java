package com.dk.modules.member.controller;


import com.alibaba.fastjson.JSONObject;
import com.dk.common.exception.MyServiceException;
import com.dk.common.http.DataResult;
import com.dk.common.http.PageRequest;
import com.dk.common.http.PageResult;
import com.dk.common.http.Result;
import com.dk.modules.member.po.*;
import com.dk.modules.member.service.*;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dk/admin/member")
public class AdminMemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberInfoDetailService memberInfoDetailService;

    //查询用户列表
    @ResponseBody
    @PostMapping("/list")
    public PageResult queryMemberAll(@RequestBody PageRequest pageRequest){
        String phone = null;
        String idInfo = null;
        Integer intIdInfo = null;
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();

        if (null != pageRequest.getParam()){
            phone = pageRequest.getParam().getString("phone");
            idInfo = pageRequest.getParam().getString("idInfo");
        }
        if (null!=idInfo && (idInfo.equals("0") || idInfo.equals("1"))){
            intIdInfo = new Integer(idInfo);
        }
        List<MemberInfo> members = memberService.findMemberAll(phone,intIdInfo,pageSize,pageNum);
        PageInfo page = new PageInfo(members);
        PageResult result = PageResult.init();
        result.setAllCount(page.getTotal());
        result.setPageCount(page.getPages());
        result.setPageNum(page.getPageNum());
        result.setPageSize(page.getPageSize());
        result.setData(page.getList());
        return result;
    }


    //删除用户
    @PostMapping("/delete")
    public Result deleteUserById(@RequestBody JSONObject param){
        String id = param.getString("id");
        memberService.deleteMember(id);
        return Result.init();
    }

    //修改用户密码
    @PostMapping("/update")
    public Result updateMember(@RequestBody MemberInfo memberInfo){
        memberService.updateMember(memberInfo);
        return Result.init();
    }

    //添加用户
    @PostMapping("/add")
    public Result insertUser(@RequestBody MemberInfo memberInfo) throws MyServiceException {
        memberService.addMember(memberInfo);
        return Result.init();
    }

    //查看用户资料
    @PostMapping("/detail/info")
    public DataResult queryMemberDetail(@RequestBody JSONObject param) {
        String id = param.getString("id");
        MemberInfoDetail memberDetail = memberInfoDetailService.findMemberDetail(id);
        return DataResult.init().buildData(memberDetail);
    }

    //修改用户资料
    @PostMapping("/detail/update")
    public Result updateMemberDetailInfo(@RequestBody MemberInfoDetail memberInfoDetail){
         memberInfoDetailService.updateMemberDetail(memberInfoDetail);
         return Result.init();
    }

    @PostMapping("/bank/update")
    public Result updateMemberBankInfo(@RequestBody MemberInfoDetail memberInfoDetail){
        memberInfoDetailService.updateMemberDetail(memberInfoDetail);
        return Result.init();
    }



    @PostMapping("/number")
    public DataResult queryRegister(){
        int registerNumber = memberService.findRegisterByDay();  //今日注册数量
        int realnameNumber = memberService.findRealnameByDay();  //今日实名数量
        int applyNumber = memberService.findApplyByDay();        //今日申请数量
        int withdrawNumber = memberService.findWithdrawByDay();  //今日提现数量
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("registerNumber",registerNumber);
        jsonObject.put("realnameNumber",realnameNumber);
        jsonObject.put("applyNumber",applyNumber);
        jsonObject.put("withdrawNumber",withdrawNumber);
        return DataResult.init().buildData(jsonObject);
    }

}

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

    //Query user list
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


    //Delete user
    @PostMapping("/delete")
    public Result deleteUserById(@RequestBody JSONObject param){
        String id = param.getString("id");
        memberService.deleteMember(id);
        return Result.init();
    }

    //Modify user password
    @PostMapping("/update")
    public Result updateMember(@RequestBody MemberInfo memberInfo){
        memberService.updateMember(memberInfo);
        return Result.init();
    }

    //Add user
    @PostMapping("/add")
    public Result insertUser(@RequestBody MemberInfo memberInfo) throws MyServiceException {
        memberService.addMember(memberInfo);
        return Result.init();
    }

    //View user profile
    @PostMapping("/detail/info")
    public DataResult queryMemberDetail(@RequestBody JSONObject param) {
        String id = param.getString("id");
        MemberInfoDetail memberDetail = memberInfoDetailService.findMemberDetail(id);
        return DataResult.init().buildData(memberDetail);
    }

    //Modify user profile
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
        int registerNumber = memberService.findRegisterByDay();  //Today's registration count
        int realnameNumber = memberService.findRealnameByDay();  //Today's real-name verification count
        int applyNumber = memberService.findApplyByDay();        //Today's application count
        int withdrawNumber = memberService.findWithdrawByDay();  //Today's withdrawal count
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("registerNumber",registerNumber);
        jsonObject.put("realnameNumber",realnameNumber);
        jsonObject.put("applyNumber",applyNumber);
        jsonObject.put("withdrawNumber",withdrawNumber);
        return DataResult.init().buildData(jsonObject);
    }

}
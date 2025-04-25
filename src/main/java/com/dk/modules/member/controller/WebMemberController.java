package com.dk.modules.member.controller;


import com.alibaba.fastjson.JSONObject;
import com.dk.common.constant.Constant;
import com.dk.common.exception.MyServiceException;
import com.dk.common.http.DataResult;
import com.dk.common.http.Result;
import com.dk.common.redis.RedisKeyPrefix;
import com.dk.common.redis.RedisService;
import com.dk.modules.member.po.MemberInfo;
import com.dk.modules.member.po.MemberInfoDetail;
import com.dk.modules.member.service.MemberInfoDetailService;
import com.dk.modules.member.service.MemberService;
import com.dk.modules.order.po.OrderInfo;
import com.dk.modules.order.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/dk/web/member")
public class WebMemberController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberInfoDetailService detailService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private OrderInfoService orderService;


    @PostMapping("/register")
    public DataResult register(@RequestBody JSONObject param,HttpServletRequest request, HttpServletResponse response) throws MyServiceException {

        String channelCode = param.getString("channelCode");
       MemberInfo memberInfo = memberService.register(param,channelCode);
        JSONObject object = new JSONObject();
        object.put(Constant.HTTP_HEADER_ID,memberInfo.getId());
        object.put(Constant.HTTP_HEADER_TYPE,1);
        String token = UUID.randomUUID().toString().replace("-","");
        object.put("token",token);
        redisService.addDefaultAgeValue(RedisKeyPrefix.GLOBAL_LOGIN_TOKEN+token,object);
        return DataResult.init().buildData(object);
    }

    @PostMapping("/register/phoneCode")
    public DataResult registerPhoneCode(@RequestBody JSONObject object) throws MyServiceException {
        memberService.registerPhoneCode(object.getString("phone"));
        return DataResult.init().buildData("888888");
    }

    @PostMapping("/login")
    public DataResult login(@RequestBody JSONObject param, HttpServletResponse response) throws MyServiceException {

        MemberInfo memberInfo = memberService.login(param);
        JSONObject object = new JSONObject();
        object.put(Constant.HTTP_HEADER_ID,memberInfo.getId());
        object.put(Constant.HTTP_HEADER_TYPE,1);
        String token = UUID.randomUUID().toString().replace("-","");
        object.put("token",token);
        redisService.addDefaultAgeValue(RedisKeyPrefix.GLOBAL_LOGIN_TOKEN+token,object);
        return DataResult.init().buildData(object);
    }

    @PostMapping("/logout")
    public Result logout(HttpServletRequest request){
        String token = request.getHeader(Constant.HTTP_HEADER_TOKEN);
        if (!StringUtils.isEmpty(token)){
            redisService.deleteKey(RedisKeyPrefix.GLOBAL_LOGIN_TOKEN+token);
        }
        return Result.init();
    }

    @PostMapping("/login/phoneCode")
    public DataResult loginPhoneCode(@RequestBody JSONObject object) throws MyServiceException {
        memberService.loginPhoneCode(object.getString("phone"));
        return DataResult.init().buildData("888888");
    }

    @PostMapping("/info")
    public DataResult info(HttpServletRequest request) throws MyServiceException {
        String id = request.getHeader(Constant.HTTP_HEADER_ID);
        MemberInfo info = memberService.findById(id);
        if (info == null){
            DataResult result = new DataResult();
            result.setCode(HttpStatus.UNAUTHORIZED.value());
            result.setMsg("请先登录");
            return result;
        }
        info.setPassword(null);
        MemberInfoDetail detail = detailService.findMemberDetail(id);
        JSONObject o = new JSONObject();
        o.put("info",info);
        o.put("detail",detail);
        o.put("withdrawalStatus",null);
        List<OrderInfo> list = orderService.findByMemberId(id);
        if (null != list && !list.isEmpty()){
            OrderInfo order = list.get(0);
            JSONObject withdrawalStatus = new JSONObject();
            withdrawalStatus.put("statusName",order.getStatusName());
            withdrawalStatus.put("statusContent",order.getStatusContent());
            o.put("withdrawalStatus",withdrawalStatus);
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return DataResult.init().buildData(o);
    }

//    @PostMapping("/detail")
//    public DataResult detail(HttpServletRequest request) throws MyServiceException {
//        String id = request.getHeader(Constant.HTTP_HEADER_ID);
//        MemberInfoDetail detail = detailService.findMemberDetail(id);
//        return DataResult.init().buildData(detail);
//    }


    @PostMapping("/detail/id/add")
    public Result updateIdInfo(@RequestBody MemberInfoDetail detail, HttpServletRequest request){
        String id = request.getHeader(Constant.HTTP_HEADER_ID);
        detail.setId(id);
        detail.setCreateDate(new Date());
        detailService.addIdInfo(detail);
        return Result.init();
    }

    @PostMapping("/detail/basic/add")
    public Result updateBasicInfo(@RequestBody MemberInfoDetail detail, HttpServletRequest request){
        String id = request.getHeader(Constant.HTTP_HEADER_ID);
        detail.setId(id);
        detailService.addBasicInfo(detail);
        return Result.init();
    }

    @PostMapping("/detail/bank/add")
    public Result updateBankInfo(@RequestBody MemberInfoDetail detail, HttpServletRequest request){
        String id = request.getHeader(Constant.HTTP_HEADER_ID);
        detail.setId(id);
        detailService.addBankInfo(detail);
        return Result.init();
    }

    @PostMapping("/repayment/list")
    public DataResult repaymentList(HttpServletRequest request){
        String id = request.getHeader(Constant.HTTP_HEADER_ID);
        List<OrderInfo> orderList = orderService.findByMemberId(id);
        List<JSONObject> repaymentList = new ArrayList<>();
        if (null!=orderList && !orderList.isEmpty()){
            for (OrderInfo order : orderList){
                String orderStatus = order.getStatus();
                if (orderStatus.equals("1") || orderStatus.equals("2") || orderStatus.equals("4")){
                    continue;
                }else {
                    JSONObject o = new JSONObject();
                    o.put("repaymentAmount",order.getRepaymentAmount());
                    o.put("monthlyPayments",order.getMonthlyPayments());
                    o.put("monthPayDay",order.getMonthPayDay());
                    o.put("loanDeadline",order.getLoanDeadline());
                    repaymentList.add(o);
                }
            }
        }
        return DataResult.init().buildData(repaymentList);
    }
}

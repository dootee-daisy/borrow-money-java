package com.dk.modules.order.controller;


import com.alibaba.fastjson.JSONObject;
import com.dk.common.exception.MyServiceException;
import com.dk.common.http.PageRequest;
import com.dk.common.http.PageResult;
import com.dk.common.http.Result;
import com.dk.modules.order.po.OrderInfo;
import com.dk.modules.order.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
@RequestMapping("/dk/admin/order")
public class AdminOrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

    //Truy vấn danh sách vay
    @ResponseBody
    @PostMapping("/list")
    public PageResult queryLoanAll(@RequestBody PageRequest pageRequest){
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        String phone = null;
        Date startTime = null;
        Date endTime = null;
        if(null != pageRequest.getParam()){
            phone = pageRequest.getParam().getString("phone");
            startTime = pageRequest.getParam().getDate("startTime");
            endTime = pageRequest.getParam().getDate("endTime");
        }
        PageResult loanAll = orderInfoService.findLoanAll(phone, startTime,endTime,pageNum, pageSize);
        return loanAll;
    }

    @PostMapping("/updateBankCard")
    public Result updateBankCard(@RequestBody JSONObject param) throws MyServiceException {
        String orderId = param.getString("orderId");
        String bankCard = param.getString("bankCard");
        orderInfoService.updateBankCard(orderId,bankCard);
        return Result.init();
    }


    @PostMapping("/updateAmount")
    public Result updateAmount(@RequestBody JSONObject param) throws MyServiceException {
        String orderId = param.getString("orderId");
        int sumLoanAdd = param.getIntValue("sumLoanAdd");
        int sumLoanDel = param.getIntValue("sumLoanDel");
        orderInfoService.updateAmount(orderId,sumLoanAdd,sumLoanDel);
        return Result.init();
    }

    @PostMapping("/update")
    public Result update(@RequestBody OrderInfo orderInfo) throws MyServiceException {
        orderInfoService.update(orderInfo);
        return Result.init();
    }

    @PostMapping("/delete")
    public Result delete(@RequestBody OrderInfo orderInfo) throws MyServiceException {
        orderInfo.setDeleted(new Integer(1));
        orderInfoService.update(orderInfo);
        return Result.init();
    }

    @PostMapping("/updateStatus")
    public Result updateStatus(@RequestBody OrderInfo orderInfo) throws MyServiceException {
        orderInfoService.updateStatus(orderInfo);
        return Result.init();
    }

}

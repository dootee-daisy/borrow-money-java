package com.dk.modules.order.controller;


import com.alibaba.fastjson.JSONObject;
import com.dk.common.constant.Constant;
import com.dk.common.exception.MyServiceException;
import com.dk.common.http.DataResult;
import com.dk.common.http.Result;
import com.dk.modules.order.mapper.OrderContractMapper;
import com.dk.modules.order.po.OrderContractWithBLOBs;
import com.dk.modules.order.po.OrderInfo;
import com.dk.modules.order.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/dk/web/order")
public class WebOrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private OrderContractMapper contractMapper;

    //Gửi đơn đặt hàng
    @PostMapping("/add")
    public DataResult addOrder(HttpServletRequest request, @RequestBody OrderInfo orderInfo) throws Exception {
        String memberId = request.getHeader(Constant.HTTP_HEADER_ID);
        orderInfo.setPhone(memberId);
        String  orderId = orderInfoService.addOrder(orderInfo);
        JSONObject o = new JSONObject();
        o.put("orderId",orderId);
        return DataResult.init().buildData(o);
    }
    @PostMapping("/list")
    public DataResult list(HttpServletRequest request){
        String memberId = request.getHeader(Constant.HTTP_HEADER_ID);
        List<OrderInfo> list = orderInfoService.findByMemberId(memberId);
        return DataResult.init().buildData(list);
    }

    @PostMapping("/detail")
    public DataResult list(@RequestBody OrderInfo orderInfo){
        OrderInfo order =  orderInfoService.findDetail(orderInfo.getOrderId());
        return DataResult.init().buildData(order);
    }

    @PostMapping("/withdrawal")
    public Result withdrawal(HttpServletRequest request) throws MyServiceException {
        String id = request.getHeader(Constant.HTTP_HEADER_ID);
        orderInfoService.withdrawal(id);
        return Result.init();
    }

    //hợp đồng
    @PostMapping("/contract/info")
    public DataResult contractInfo(@RequestBody OrderContractWithBLOBs orderContract){
        OrderContractWithBLOBs contract = contractMapper.selectByPrimaryKey(orderContract.getOrderId());
        return DataResult.init().buildData(contract);
    }

    @PostMapping("/contract/sign")
    public Result contractSign(@RequestBody OrderContractWithBLOBs contract){
        contract.setStatus(1);
        contractMapper.updateByPrimaryKeySelective(contract);
        return Result.init();
    }

}

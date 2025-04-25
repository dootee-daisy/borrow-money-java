package com.dk.modules.order.mapper;

import com.dk.modules.order.po.OrderContract;
import com.dk.modules.order.po.OrderContractWithBLOBs;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderContractMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(OrderContractWithBLOBs record);

    int insertSelective(OrderContractWithBLOBs record);

    OrderContractWithBLOBs selectByPrimaryKey(String orderId);

    OrderContractWithBLOBs selectAgreementByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(OrderContractWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(OrderContractWithBLOBs record);

    int updateByPrimaryKey(OrderContract record);
}
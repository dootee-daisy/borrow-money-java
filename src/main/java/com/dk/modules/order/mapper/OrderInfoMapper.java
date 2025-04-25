package com.dk.modules.order.mapper;

import com.dk.modules.order.po.OrderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface OrderInfoMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(OrderInfo record);

    int insertSelective(OrderInfo record);

    OrderInfo selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(OrderInfo record);

    int updateByPrimaryKey(OrderInfo record);

    List<OrderInfo> selectByMemberId(@Param("phone") String phone);
    List<OrderInfo> selectLoanAll(@Param("phone") String phone, @Param("startTime")Date startTime, @Param("endTime")Date endTime, @Param("pageNum")int pageNum, @Param("pageSize")int pageSize);

    int selectApplyByDay();
}
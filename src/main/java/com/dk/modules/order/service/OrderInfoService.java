package com.dk.modules.order.service;

import com.dk.common.constant.Constant;
import com.dk.common.exception.MyServiceException;
import com.dk.common.http.PageResult;
import com.dk.common.util.DateUtil;
import com.dk.common.util.IDGenerator;
import com.dk.common.util.NumberToCN;
import com.dk.common.util.SmsUtil;
import com.dk.modules.config.po.ConfigAgreement;
import com.dk.modules.config.po.ConfigChannel;
import com.dk.modules.config.po.ConfigLoan;
import com.dk.modules.config.po.ConfigOrderWords;
import com.dk.modules.config.service.ConfigAgreementService;
import com.dk.modules.config.service.ConfigChannelService;
import com.dk.modules.config.service.ConfigLoanService;
import com.dk.modules.config.service.ConfigOrderWordsService;
import com.dk.modules.member.po.MemberInfo;
import com.dk.modules.member.po.MemberInfoDetail;
import com.dk.modules.member.service.MemberInfoDetailService;
import com.dk.modules.member.service.MemberService;
import com.dk.modules.order.mapper.OrderContractMapper;
import com.dk.modules.order.mapper.OrderInfoMapper;
import com.dk.modules.order.po.OrderContract;
import com.dk.modules.order.po.OrderContractWithBLOBs;
import com.dk.modules.order.po.OrderInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderInfoService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private MemberService memberService;

    @Autowired
    private ConfigOrderWordsService wordsService;
    @Autowired
    private ConfigAgreementService agreementService;
    @Autowired
    private OrderContractMapper contractMapper;
    @Autowired
    private MemberInfoDetailService memberInfoDetailService;
    @Autowired
    private ConfigLoanService loanService;
    @Autowired
    private ConfigChannelService channelService;


    public void updateAmount(String orderId, int sumLoanAdd,int sumLoanDel) {
        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(orderId);
        String sumLoan = orderInfo.getSumLoan();
        if (StringUtils.isEmpty(sumLoan)){
            sumLoan = "0";
        }
        int intSumLoan = Integer.valueOf(sumLoan);
        int s = intSumLoan+sumLoanAdd-sumLoanDel;
        orderInfo.setSumLoanUpdate(new Integer(1));
        orderInfo.setSumLoan(String.valueOf(s));

        orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
        //修改账户余额
        String orderStatus = orderInfo.getStatus();
        MemberInfo member = memberService.findById(orderInfo.getPhone());
        if (null != member){
            if (orderStatus.equals("1") || orderStatus.equals("2")){
                member.setBalance(new BigDecimal(0));
                member.setWithdrawal(new BigDecimal(0));
            }else if (orderStatus.equals("3")){
                member.setBalance(new BigDecimal(orderInfo.getSumLoan()));
                member.setWithdrawal(new BigDecimal(0));
            }else if (orderStatus.equals("4")){
                member.setBalance(new BigDecimal(0));
                member.setWithdrawal(new BigDecimal(0));
            } else if (orderStatus.equals("5")){
                member.setBalance(new BigDecimal(orderInfo.getSumLoan()));
                member.setWithdrawal(new BigDecimal(0));
            }else {
                member.setBalance(new BigDecimal(0));
                member.setWithdrawal(new BigDecimal(orderInfo.getSumLoan()));
            }
            memberService.updateMember(member);
        }
    }

    @Transactional
    public void updateBankCard(String orderId,String bankCard) throws MyServiceException{
        OrderInfo order = orderInfoMapper.selectByPrimaryKey(orderId);
        if (null!=order){
            String orderBankCard = order.getBankCard();
            if (bankCard.equals(orderBankCard)){
                throw new MyServiceException("修改卡号与原卡号相同");
            }
            ConfigOrderWords words = wordsService.findById("17");
            if (null != words){
                if(words.getLoanOrderStatus() ==1 && !StringUtils.isEmpty(words.getLoanOrderContent())){
                    order.setStatus(words.getTipsId());
                    order.setStatusName(words.getTipsName());
                    order.setStatusContent(words.getLoanOrderContent());
                }
                if (words.getOrderMessageStatus() == 1 && !StringUtils.isEmpty(words.getOrderMessageContent())){
                    //发送短信
                }
            }
            order.setBankCard(bankCard);
            orderInfoMapper.updateByPrimaryKeySelective(order);
            MemberInfoDetail detail = new MemberInfoDetail();
            detail.setId(order.getPhone());
            detail.setBankCard(bankCard);
            memberInfoDetailService.updateMemberDetail(detail);

            //改合同卡号
            OrderContractWithBLOBs contract = contractMapper.selectAgreementByPrimaryKey(orderId);
            if (null != contract && !StringUtils.isEmpty(contract.getAgreement())){
                contract.setAgreement(contract.getAgreement().replace(orderBankCard,bankCard));
                contractMapper.updateByPrimaryKeySelective(contract);
            }
        }
    }

    //查询借款列表
    public PageResult findLoanAll(String phone,Date startTime,Date endTime,int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<OrderInfo> orderInfos = orderInfoMapper.selectLoanAll(phone,startTime,endTime,pageNum,pageSize);
        PageInfo pageInfo = new PageInfo(orderInfos);
        PageResult result = PageResult.init();
        result.setAllCount(pageInfo.getTotal());
        result.setPageCount(pageInfo.getPages());
        result.setPageNum(pageInfo.getPageNum());
        result.setPageSize(pageInfo.getPageSize());
        result.setData(pageInfo.getList());
        return result;
    }

    @Transactional
    public String addOrder(OrderInfo orderInfo) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        MemberInfo memberInfo = memberService.findById(orderInfo.getPhone());
        MemberInfoDetail memberDetail = memberInfoDetailService.findMemberDetail(orderInfo.getPhone());
        if (null == memberInfo || memberInfo.getStatus() != 0 || memberInfo.getDeleted() != 0){
            throw new MyServiceException("账户异常，请联系客服！");
        }
        if (memberInfo.getIdInfo() ==0 || memberInfo.getBasicInfo() == 0 || memberInfo.getBankInfo()==0){
            throw new MyServiceException("请先完善资料！");
        }
        List<OrderInfo> list = orderInfoMapper.selectByMemberId(orderInfo.getPhone());
        if (null != list && !list.isEmpty()){
            throw new MyServiceException("您还有订单未处理完，不能重复申请！");
        }
        BigDecimal numberOfMoney = new BigDecimal(orderInfo.getSumLoan());

        orderInfo.setOrderId(IDGenerator.createOrderId());
        orderInfo.setName(memberInfo.getName());
        orderInfo.setPhone(memberInfo.getId());
        orderInfo.setSumLoanInit(orderInfo.getSumLoan());
        orderInfo.setBankName(memberDetail.getBankName());
        orderInfo.setBankCard(memberDetail.getBankCard());
        orderInfo.setBankCardInit(memberDetail.getBankCard());
        orderInfo.setSumLoanUpdate(new Integer(0));
        orderInfo.setBankCardUpdate(new Integer(0));
        orderInfo.setCreateDate(new Date());
        orderInfo.setWallet(new BigDecimal(0));
        orderInfo.setDrawCode("456789");
        Date repaymentDate = DateUtil.addMonth(orderInfo.getCreateDate(), Integer.parseInt(orderInfo.getLoanDeadline()));//还款日期
        orderInfo.setRepaymentDate(repaymentDate);
        orderInfo.setLoanRate("0.6");

        ConfigLoan loan = loanService.findInfo();
        if (null!=loan && !StringUtils.isEmpty(loan.getRate())){
            orderInfo.setLoanRate(loan.getRate());
            orderInfo.setMonthPayDay(loan.getRepaymentDay());
            //利率
            BigDecimal rate = new BigDecimal(0.06);
            try {
                rate = new BigDecimal(loan.getRate());
                rate  = rate.divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP);
            }catch (Exception e){

            }
            //借款
            BigDecimal sumLoan = new BigDecimal(orderInfo.getSumLoan());
            //月息
            BigDecimal yuexi = sumLoan.multiply(rate);
            //总期限
            BigDecimal zongqixian = new BigDecimal(orderInfo.getLoanDeadline());
            //总利息
            BigDecimal zonglixi = yuexi.multiply(zongqixian);
            //总还款
            BigDecimal zongHunKuang = sumLoan.add(zonglixi);
            //每月还款
            BigDecimal meiyuehuankuan = zongHunKuang.divide(zongqixian,2,BigDecimal.ROUND_HALF_UP);
            orderInfo.setRepaymentAmount(String.valueOf(zongHunKuang));
            orderInfo.setMonthlyPayments(String.valueOf(meiyuehuankuan));
        }

        orderInfo.setStatus("1");
        //设置状态
        ConfigOrderWords words = wordsService.findById(orderInfo.getStatus());
        if (null != words){
            orderInfo.setStatusName(words.getTipsName());
            orderInfo.setStatusContent(words.getLoanOrderContent());
            if (words.getOrderMessageStatus() == 1 && !StringUtils.isEmpty(words.getOrderMessageContent())){
                //发送消息
//                SmsUtil.sendSMS(orderInfo.getPhone(),words.getOrderMessageContent());
            }
        }
        //记录渠道订单量
        String channelCode = memberInfo.getChannelCode();
        if (!StringUtils.isEmpty(channelCode)){
            ConfigChannel channel = channelService.findById(channelCode);
            if (null != channel){
                channel.setOrderCount(channel.getOrderCount()+1);
                channelService.update(channel);
                orderInfo.setChannelCode(channelCode);
                orderInfo.setChannelName(channel.getChannelName());
            }
        }
        orderInfoMapper.insertSelective(orderInfo);
        //生成合同
        ConfigAgreement agreement = agreementService.findById(Constant.AGREEMENT_JK);
        if (null != agreement){

            String con = agreement.getaContent();
            String numberOfCN = NumberToCN.number2CNMontrayUnit(numberOfMoney);
            String sDate = format.format(orderInfo.getCreateDate());
            String sfDate = format.format(repaymentDate);
            String key = "【】";
            con = con.replaceFirst(key, "【"+orderInfo.getName()+"】");//借款人
            con = con.replaceFirst(key, "【"+orderInfo.getSumLoan()+"】");//借款本金
            con = con.replaceFirst(key, "【"+numberOfCN+"】");//大写本金
            con = con.replaceFirst(key, "【"+sDate.substring(0, 4)+"】")
                    .replaceFirst(key, "【"+sDate.substring(5, 7)+"】")
                    .replaceFirst(key, "【"+sDate.substring(8, 10)+"】")
                    .replaceFirst(key, "【"+sfDate.substring(0, 4)+"】")
                    .replaceFirst(key, "【"+sfDate.substring(5, 7)+"】")
                    .replaceFirst(key, "【"+sfDate.substring(8, 10)+"】")
                    .replaceFirst(key, "【"+orderInfo.getLoanDeadline()+"】")
                    .replaceFirst(key, "【"+memberDetail.getName()+"】")
                    .replaceFirst(key, "【"+memberDetail.getBankCard()+"】")
                    .replaceFirst(key, "【"+memberDetail.getBankName()+"】");

            OrderContractWithBLOBs orderContractWithBLOBs = new OrderContractWithBLOBs();
            orderContractWithBLOBs.setAgreement(con);
            orderContractWithBLOBs.setOrderId(orderInfo.getOrderId());
            orderContractWithBLOBs.setStatus(0);
            contractMapper.insertSelective(orderContractWithBLOBs);
        }

        return orderInfo.getOrderId();
    }


    public List<OrderInfo> findByMemberId(String memberId){
        return orderInfoMapper.selectByMemberId(memberId);
    }

    public OrderInfo findDetail(String orderId) {
        return orderInfoMapper.selectByPrimaryKey(orderId);
    }

    public void update(OrderInfo orderInfo) {
        orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
    }

    public void updateStatus(OrderInfo orderInfo) {
        String orderStatus = orderInfo.getStatus();
        OrderInfo order = orderInfoMapper.selectByPrimaryKey(orderInfo.getOrderId());
        ConfigOrderWords words = wordsService.findById(orderInfo.getStatus());
        if (null != words){
            order.setStatus(orderStatus);
            order.setStatusName(words.getTipsName());
            order.setStatusContent(words.getLoanOrderContent());
            if (words.getOrderMessageStatus() == 1 || !StringUtils.isEmpty(words.getOrderMessageContent())){
                //发送消息
//                SmsUtil.sendSMS(order.getPhone(),words.getOrderMessageContent());
            }
        }
        MemberInfo member = memberService.findById(order.getPhone());
        if (null != member){
            if (orderStatus.equals("1") || orderStatus.equals("2")){
                member.setBalance(new BigDecimal(0));
                member.setWithdrawal(new BigDecimal(0));
            }else if (orderStatus.equals("3")){
                member.setBalance(new BigDecimal(order.getSumLoan()));
                member.setWithdrawal(new BigDecimal(0));
            }else if (orderStatus.equals("4")){
                member.setBalance(new BigDecimal(0));
                member.setWithdrawal(new BigDecimal(0));
            } else if (orderStatus.equals("5")){
                member.setBalance(new BigDecimal(order.getSumLoan()));
                member.setWithdrawal(new BigDecimal(0));
            }else {
                member.setBalance(new BigDecimal(0));
                member.setWithdrawal(new BigDecimal(order.getSumLoan()));
            }
            memberService.updateMember(member);
        }
        orderInfoMapper.updateByPrimaryKeySelective(order);
    }

    @Transactional
    public void withdrawal(String id) throws MyServiceException{
        MemberInfo memberInfo = memberService.findById(id);
        if (null==memberInfo ||memberInfo.getStatus() == 1 || memberInfo.getDeleted() ==1 || memberInfo.getBalance().intValue() == 0){
            throw new MyServiceException("账户暂时不能提现");
        }
        memberInfo.setWithdrawal(memberInfo.getBalance().add(memberInfo.getWithdrawal()));
        memberInfo.setBalance(new BigDecimal(0));
        memberService.updateMember(memberInfo);

        List<OrderInfo> list = orderInfoMapper.selectByMemberId(id);
        if (null != list && !list.isEmpty()){
            ConfigOrderWords words = wordsService.findById("6");
            if (null != words){
                for (OrderInfo order : list){
                    order.setStatus(words.getTipsId());
                    order.setStatusName(words.getTipsName());
                    order.setStatusContent(words.getLoanOrderContent());
                    orderInfoMapper.updateByPrimaryKeySelective(order);
                }
            }
        }
    }
}

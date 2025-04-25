package com.dk.modules.member.service;

import com.alibaba.fastjson.JSONObject;
import com.dk.common.exception.MyServiceException;
import com.dk.common.redis.RedisKeyPrefix;
import com.dk.common.redis.RedisService;
import com.dk.common.util.SmsUtil;
import com.dk.modules.config.po.ConfigChannel;
import com.dk.modules.config.service.ConfigChannelService;
import com.dk.modules.member.mapper.MemberInfoDetailMapper;
import com.dk.modules.member.mapper.MemberInfoMapper;
import com.dk.modules.member.po.MemberInfo;
import com.dk.modules.member.po.MemberInfoDetail;
import com.dk.modules.order.mapper.OrderInfoMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.fastjson.JSONObject;
import com.dk.modules.member.mapper.MemberInfoMapper;
import com.dk.modules.member.po.MemberInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class MemberService {

    @Autowired
    private MemberInfoMapper memberInfoMapper;

    @Autowired
    private MemberInfoDetailMapper memberInfoDetailMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private ConfigChannelService channelService;


    @Transactional
    public MemberInfo register(JSONObject object,String channelCode) throws MyServiceException{
        String phone = object.getString("phone");
        String phoneCode = object.getString("phoneCode");
        String password = object.getString("password");
        if (StringUtils.isEmpty(phone)){
            throw new MyServiceException("请输入手机号码");
        }
        if (StringUtils.isEmpty(phoneCode)){
            throw new MyServiceException("请输入验证码");
        }
        if (StringUtils.isEmpty(password)){
            password = "123456";
        }
        JSONObject o = redisService.getValue(RedisKeyPrefix.GLOBAL_PHONE_CODE +phone,JSONObject.class);
        if (null == o || StringUtils.isEmpty(o.getString("phoneCode")) || !o.getString("phoneCode").equals(phoneCode)){
            throw new MyServiceException("验证码不正确");
        }

        MemberInfo memberInfo = memberInfoMapper.selectByPrimaryKey(phone);
        if (null!=memberInfo){
            throw new MyServiceException("手机号已注册，请登录");
        }

        memberInfo = new MemberInfo();
        memberInfo.setId(phone);
        memberInfo.setPassword(password);
        memberInfo.setAddTime(new Date());
        memberInfo.setUpdateTime(memberInfo.getAddTime());
        memberInfo.setLevel(new Integer(1));
        memberInfo.setStatus(new Integer(0));

        //记录注册量
        if (!StringUtils.isEmpty(channelCode)){
            ConfigChannel channel = channelService.findById(channelCode);
            if (null != channel){
                channel.setOrderCount(channel.getOrderCount()+1);
                channelService.update(channel);
                memberInfo.setChannelCode(channelCode);
                memberInfo.setChannelName(channel.getChannelName());
            }
        }


        memberInfoMapper.insertSelective(memberInfo);
        MemberInfoDetail detail = new MemberInfoDetail();
        detail.setId(phone);
        memberInfoDetailMapper.insertSelective(detail);


        return memberInfo;
    }

    public MemberInfo login(JSONObject object) throws MyServiceException {
        int type = object.getIntValue("type");
        String phone = object.getString("phone");
        MemberInfo memberInfo = null;
        if (type == 0){
            //密码登录
            String password = object.getString("password");
            if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)){
                throw new MyServiceException("用户名和密码不能为空");
            }
            memberInfo = memberInfoMapper.selectByPrimaryKey(phone);
            if (null == memberInfo){
                throw new MyServiceException("用户未注册");
            }else if (!memberInfo.getPassword().equals(password)){
                throw new MyServiceException("密码错误");
            }else if (memberInfo.getStatus()==1 || memberInfo.getDeleted() ==1){
                throw new MyServiceException("您的账户已注销");
            }
        }else{
            //验证码登录
            String phoneCode = object.getString("phoneCode");
            if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(phoneCode)){
                throw new MyServiceException("用户名和验证码不能为空");
            }
            memberInfo = memberInfoMapper.selectByPrimaryKey(phone);
            if (null == memberInfo){
                throw new MyServiceException("用户未注册");
            }else if (memberInfo.getStatus()==1 || memberInfo.getDeleted() ==1){
                throw new MyServiceException("您的账户已注销");
            }
            JSONObject o = redisService.getValue(RedisKeyPrefix.GLOBAL_PHONE_CODE +phone,JSONObject.class);
            if (null == o || StringUtils.isEmpty(o.getString("phoneCode")) || !o.getString("phoneCode").equals(phoneCode)){
                throw new MyServiceException("验证码不正确");
            }
        }
        return memberInfo;
    }

    public void loginPhoneCode(String phone) throws MyServiceException {
        if (StringUtils.isEmpty(phone)){
            throw new MyServiceException("请输入手机号码");
        }
        String phoneCode = String.valueOf(new Random().nextInt(9999));
        JSONObject o = new JSONObject();
//        o.put("phoneCode",phoneCode);
        o.put("phoneCode","888888");
        redisService.addValue(RedisKeyPrefix.GLOBAL_PHONE_CODE+phone,o,15L);
//        SmsUtil.sendSMS(phone,"你的短信验证码为 "+phoneCode+" 请务透露给其他人。");
    }

    public void registerPhoneCode(String phone) throws MyServiceException{
        MemberInfo memberInfo = memberInfoMapper.selectByPrimaryKey(phone);
        if (null != memberInfo){
            throw new MyServiceException("手机号已注册，请登录");
        }
        String phoneCode = String.valueOf(new Random().nextInt(9999));
        JSONObject o = new JSONObject();
//        o.put("phoneCode",phoneCode);
        o.put("phoneCode","888888");
        redisService.addValue(RedisKeyPrefix.GLOBAL_PHONE_CODE+phone,o,15L);
//        SmsUtil.sendSMS(phone,"你的短信验证码为 "+phoneCode+" 请务透露给其他人。");
    }


    //查询用户列表
    public List<MemberInfo> findMemberAll(String phone,Integer idInfo,int pageSize,int pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<MemberInfo> memberInfos = memberInfoMapper.selectMemberAll(phone,idInfo);
        return memberInfos;
    }

    //删除用户
    public void deleteMember(String id) {
        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setId(id);
        memberInfo.setDeleted(new Integer(1));
        memberInfoMapper.updateByPrimaryKeySelective(memberInfo);
    }



    //编辑用户
    public void updateMember(MemberInfo memberInfo) {
        memberInfoMapper.updateByPrimaryKeySelective(memberInfo);
    }

    //添加用户
    public void addMember(MemberInfo memberInfo)throws MyServiceException {
        MemberInfo member = memberInfoMapper.selectByPrimaryKey(memberInfo.getId());
        if (null != member){
            throw new MyServiceException("手机号码已注册");
        }
        memberInfoMapper.insertSelective(memberInfo);
    }

    public MemberInfo findById(String phone) {
        return memberInfoMapper.selectByPrimaryKey(phone);
    }



    //今日实名数量
    public int findRegisterByDay() {
        int number = memberInfoMapper.selectRegisterByDay();
        return number;
    }

    //今日实名数量
    public int findRealnameByDay() {
        int number = memberInfoDetailMapper.selectRealnameByDay();
        return number;
    }

    //今日申请数量
    public int findApplyByDay() {
        int number = orderInfoMapper.selectApplyByDay();
        return number;
    }

    //今日提现数量
    public int findWithdrawByDay() {
        return 0;
    }
}

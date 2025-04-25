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
  public MemberInfo register(JSONObject object, String channelCode) throws MyServiceException {
    String phone = object.getString("phone");
    String phoneCode = object.getString("phoneCode");
    String password = object.getString("password");
    if (StringUtils.isEmpty(phone)) {
      throw new MyServiceException("Please enter phone number");
    }
    if (StringUtils.isEmpty(phoneCode)) {
      throw new MyServiceException("Please enter verification code");
    }
    if (StringUtils.isEmpty(password)) {
      password = "123456";
    }
    JSONObject o = redisService.getValue(RedisKeyPrefix.GLOBAL_PHONE_CODE + phone,
        JSONObject.class);
    if (null == o || StringUtils.isEmpty(o.getString("phoneCode")) || !o.getString("phoneCode")
        .equals(phoneCode)) {
      throw new MyServiceException("Verification code is incorrect");
    }

    MemberInfo memberInfo = memberInfoMapper.selectByPrimaryKey(phone);
    if (null != memberInfo) {
      throw new MyServiceException("Phone number already registered, please login");
    }

    memberInfo = new MemberInfo();
    memberInfo.setId(phone);
    memberInfo.setPassword(password);
    memberInfo.setAddTime(new Date());
    memberInfo.setUpdateTime(memberInfo.getAddTime());
    memberInfo.setLevel(new Integer(1));
    memberInfo.setStatus(new Integer(0));

    //Record registration count
    if (!StringUtils.isEmpty(channelCode)) {
      ConfigChannel channel = channelService.findById(channelCode);
      if (null != channel) {
        channel.setOrderCount(channel.getOrderCount() + 1);
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
    if (type == 0) {
      //Password login
      String password = object.getString("password");
      if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)) {
        throw new MyServiceException("Username and password cannot be empty");
      }
      memberInfo = memberInfoMapper.selectByPrimaryKey(phone);
      if (null == memberInfo) {
        throw new MyServiceException("User not registered");
      } else if (!memberInfo.getPassword().equals(password)) {
        throw new MyServiceException("Password error");
      } else if (memberInfo.getStatus() == 1 || memberInfo.getDeleted() == 1) {
        throw new MyServiceException("Your account has been deactivated");
      }
    } else {
      //Verification code login
      String phoneCode = object.getString("phoneCode");
      if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(phoneCode)) {
        throw new MyServiceException("Username and verification code cannot be empty");
      }
      memberInfo = memberInfoMapper.selectByPrimaryKey(phone);
      if (null == memberInfo) {
        throw new MyServiceException("User not registered");
      } else if (memberInfo.getStatus() == 1 || memberInfo.getDeleted() == 1) {
        throw new MyServiceException("Your account has been deactivated");
      }
      JSONObject o = redisService.getValue(RedisKeyPrefix.GLOBAL_PHONE_CODE + phone,
          JSONObject.class);
      if (null == o || StringUtils.isEmpty(o.getString("phoneCode")) || !o.getString("phoneCode")
          .equals(phoneCode)) {
        throw new MyServiceException("Verification code is incorrect");
      }
    }
    return memberInfo;
  }

  public void loginPhoneCode(String phone) throws MyServiceException {
    if (StringUtils.isEmpty(phone)) {
      throw new MyServiceException("Please enter phone number");
    }
    String phoneCode = String.valueOf(new Random().nextInt(9999));
    JSONObject o = new JSONObject();
//        o.put("phoneCode",phoneCode);
    o.put("phoneCode", "888888");
    redisService.addValue(RedisKeyPrefix.GLOBAL_PHONE_CODE + phone, o, 15L);
//        SmsUtil.sendSMS(phone,"Your SMS verification code is "+phoneCode+" Please do not disclose it to others.");
  }

  public void registerPhoneCode(String phone) throws MyServiceException {
    MemberInfo memberInfo = memberInfoMapper.selectByPrimaryKey(phone);
    if (null != memberInfo) {
      throw new MyServiceException("Phone number already registered, please login");
    }
    String phoneCode = String.valueOf(new Random().nextInt(9999));
    JSONObject o = new JSONObject();
//        o.put("phoneCode",phoneCode);
    o.put("phoneCode", "888888");
    redisService.addValue(RedisKeyPrefix.GLOBAL_PHONE_CODE + phone, o, 15L);
//        SmsUtil.sendSMS(phone,"Your SMS verification code is "+phoneCode+" Please do not disclose it to others.");
  }


  //Query user list
  public List<MemberInfo> findMemberAll(String phone, Integer idInfo, int pageSize, int pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    List<MemberInfo> memberInfos = memberInfoMapper.selectMemberAll(phone, idInfo);
    return memberInfos;
  }

  //Delete user
  public void deleteMember(String id) {
    MemberInfo memberInfo = new MemberInfo();
    memberInfo.setId(id);
    memberInfo.setDeleted(new Integer(1));
    memberInfoMapper.updateByPrimaryKeySelective(memberInfo);
  }


  //Edit user
  public void updateMember(MemberInfo memberInfo) {
    memberInfoMapper.updateByPrimaryKeySelective(memberInfo);
  }

  //Add user
  public void addMember(MemberInfo memberInfo) throws MyServiceException {
    MemberInfo member = memberInfoMapper.selectByPrimaryKey(memberInfo.getId());
    if (null != member) {
      throw new MyServiceException("Phone number already registered");
    }
    memberInfoMapper.insertSelective(memberInfo);
  }

  public MemberInfo findById(String phone) {
    return memberInfoMapper.selectByPrimaryKey(phone);
  }


  //Today's registration count
  public int findRegisterByDay() {
    int number = memberInfoMapper.selectRegisterByDay();
    return number;
  }

  //Today's real-name verification count
  public int findRealnameByDay() {
    int number = memberInfoDetailMapper.selectRealnameByDay();
    return number;
  }

  //Today's application count
  public int findApplyByDay() {
    int number = orderInfoMapper.selectApplyByDay();
    return number;
  }

  //Today's withdrawal count
  public int findWithdrawByDay() {
    return 0;
  }
}
package com.dk.modules.member.service;

import com.dk.common.exception.MyServiceException;
import com.dk.common.http.Result;
import com.dk.modules.member.mapper.MemberInfoDetailMapper;
import com.dk.modules.member.po.MemberInfo;
import com.dk.modules.member.po.MemberInfoDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberInfoDetailService {

    @Autowired
    private MemberInfoDetailMapper memberInfoDetailMapper;
    @Autowired
    private MemberService memberService;


    //View user details
    public MemberInfoDetail findMemberDetail(String id)  {
        return memberInfoDetailMapper.selectByPrimaryKey(id);
    }

    //Edit user
    public void updateMemberDetail(MemberInfoDetail memberInfoDetail) {
        memberInfoDetailMapper.updateByPrimaryKeySelective(memberInfoDetail);
    }

    public void addIdInfo(MemberInfoDetail detail){
        memberInfoDetailMapper.updateByPrimaryKeySelective(detail);
        MemberInfo member = new MemberInfo();
        member.setId(detail.getId());
        member.setName(detail.getName());
        member.setIdcard(detail.getIdcard());
        member.setIdcardBack(detail.getIdcardBack());
        member.setIdcardFront(detail.getIdcardFront());
        member.setIdcardHand(detail.getIdcardHand());
        member.setIdInfo(new Integer(1));
        member.setUpdateTime(detail.getCreateDate());
        memberService.updateMember(member);
    }


    public void addBasicInfo(MemberInfoDetail detail) {
        memberInfoDetailMapper.updateByPrimaryKeySelective(detail);
        MemberInfo member = new MemberInfo();
        member.setId(detail.getId());
        member.setBasicInfo(new Integer(1));
        memberService.updateMember(member);
    }

    public void addBankInfo(MemberInfoDetail detail) {
        memberInfoDetailMapper.updateByPrimaryKeySelective(detail);
        MemberInfo member = new MemberInfo();
        member.setId(detail.getId());
        member.setBankInfo(new Integer(1));
        memberService.updateMember(member);
    }
}
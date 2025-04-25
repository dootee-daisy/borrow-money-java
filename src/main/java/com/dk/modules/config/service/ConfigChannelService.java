package com.dk.modules.config.service;

import com.dk.common.exception.MyServiceException;
import com.dk.common.http.PageResult;
import com.dk.common.util.IDGenerator;
import com.dk.modules.config.mapper.ConfigChannelMapper;
import com.dk.modules.config.po.ConfigChannel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class ConfigChannelService {

    @Autowired
    private ConfigChannelMapper channelMapper;

    public PageResult list(int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<ConfigChannel> list =  channelMapper.selectAll();
        PageInfo pageInfo = new PageInfo(list);
        PageResult result = PageResult.init();
        result.setAllCount(pageInfo.getTotal());
        result.setPageCount(pageInfo.getPages());
        result.setPageNum(pageInfo.getPageNum());
        result.setPageSize(pageInfo.getPageSize());
        result.setData(pageInfo.getList());

        return result;
    }

    public void add(ConfigChannel channel) throws MyServiceException {
        channel.setChannelCode(IDGenerator.getRandomNumbersAndUppercaseString(8));
        channel.setChannelPath(channel.getChannelPath()+"?channelCode="+channel.getChannelCode());
        channel.setDeleted(new Integer(0));
        channel.setAddTime(new Date());
        channel.setUpdateTime(channel.getAddTime());
        channelMapper.insertSelective(channel);
    }
    public void update(ConfigChannel channel) throws MyServiceException {
        channelMapper.updateByPrimaryKeySelective(channel);
    }

    public void delete(String channelCode) throws MyServiceException {
        ConfigChannel channel = new ConfigChannel();
        channel.setChannelCode(channelCode);
        channel.setDeleted(new Integer(1));
        channelMapper.updateByPrimaryKeySelective(channel);
    }

    public ConfigChannel findById(String channelCode){
        return channelMapper.selectByPrimaryKey(channelCode);
    }
}

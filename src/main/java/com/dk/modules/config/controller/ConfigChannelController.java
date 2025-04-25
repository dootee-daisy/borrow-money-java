package com.dk.modules.config.controller;

import com.alibaba.fastjson.JSONObject;
import com.dk.common.exception.MyServiceException;
import com.dk.common.http.DataResult;
import com.dk.common.http.PageRequest;
import com.dk.common.http.PageResult;
import com.dk.common.http.Result;
import com.dk.common.util.IpAddressUtil;
import com.dk.modules.config.mapper.ConfigSpreadMapper;
import com.dk.modules.config.po.ConfigChannel;
import com.dk.modules.config.po.ConfigSpread;
import com.dk.modules.config.service.ConfigChannelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController()
public class ConfigChannelController {

    @Autowired
    private ConfigChannelService channelService;
    @Autowired
    private ConfigSpreadMapper spreadMapper;
    @Autowired
    private IpAddressUtil ipAddressUtil;

    private static final Logger log = LoggerFactory.getLogger(ConfigChannelController.class);

    @PostMapping("/dk/config/channel/list")
    public PageResult list(@RequestBody PageRequest pageRequest){
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        return channelService.list(pageNum,pageSize);
    }

    @PostMapping("/dk/config/channel/add")
    public Result add(@RequestBody ConfigChannel channel) throws MyServiceException {
        channelService.add(channel);
        return Result.init();
    }

    @PostMapping("/dk/config/channel/update")
    public Result update(@RequestBody ConfigChannel channel) throws MyServiceException {
        channelService.update(channel);
        return Result.init();
    }


    @PostMapping("/dk/config/channel/delete")
    public Result deleted(@RequestBody ConfigChannel channel) throws MyServiceException {
        channelService.delete(channel.getChannelCode());
        return Result.init();
    }

    @PostMapping("/dk/config/channel/check")
    public DataResult check(HttpServletRequest request) throws MyServiceException {
        boolean flag = true;
        JSONObject o = new JSONObject();
        String channelCode = request.getParameter("channelCode");
        if (StringUtils.isEmpty(channelCode)){
            throw new MyServiceException("服务器异常，请稍后重试！");
        }
        ConfigChannel channel = channelService.findById(channelCode);
        if (null == channel || channel.getDeleted() ==1){
            throw new MyServiceException("通道已关闭，请与管理员联系！");
        }
        ConfigSpread spread = spreadMapper.selectByPrimaryKey("1");
        if (null!=spread && spread.getStatus() == 0 && !StringUtils.isEmpty(spread.getAreas())){
            String areas = spread.getAreas();
            String region = null;
            try {
                String ip = ipAddressUtil.getRemoteHost(request);
                region = ipAddressUtil.getAddressFromTaobao(ip);
            }catch (Exception e){
                region = null;
            }
            if (!StringUtils.isEmpty(region) && areas.contains(region)){
                Date now = new Date();
                Date startTime = spread.getStartTime();
                Date endTime = spread.getEndTime();
                if (startTime != null && endTime ==null && startTime.before(now)){
                    //有效
                }else if (startTime == null && endTime !=null && endTime.after(now)){
                    //有效
                }else if (startTime != null && endTime !=null && startTime.before(now) && endTime.after(now)){
                    //有效
                }else {
                    //无效
                    flag = false;
                }
            }else {
                flag =false;
            }
        }else {
            flag = false;
        }
        if (flag){
            channel.setUvCount(channel.getUvCount()+1);
            channelService.update(channel);
            o.put("type",0);
            o.put("channelCode",channelCode);
            o.put("url",spread.getInsidePath());
        }else {
            o.put("type",1);
            o.put("url",spread.getOutsidePath());
        }
        return DataResult.init().buildData(o);
    }

}

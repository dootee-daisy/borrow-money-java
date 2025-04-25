package com.dk.modules.config.controller;

import com.alibaba.fastjson.JSONObject;
import com.dk.common.http.DataResult;
import com.dk.common.http.Result;
import com.dk.modules.config.mapper.ConfigSpreadMapper;
import com.dk.modules.config.po.ConfigSpread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigSpreadController {

    @Autowired
    private ConfigSpreadMapper spreadMapper;

    @PostMapping("/dk/config/spread/info")
    public DataResult info(){
        ConfigSpread area = spreadMapper.selectByPrimaryKey("1");
        return DataResult.init().buildData(area);
    }

    @PostMapping("/dk/config/spread/update")
    public Result update(@RequestBody ConfigSpread area){
        area.setId("1");
        spreadMapper.updateByPrimaryKeySelective(area);
        return Result.init();
    }

    @PostMapping("/dk/config/spread/appPath")
    public DataResult appPath(@RequestBody JSONObject param){
        int type = param.getIntValue("appType");
        ConfigSpread spread = spreadMapper.selectByPrimaryKey("1");
        JSONObject o = new JSONObject();
        if (type == 0){
            o.put("appPath",spread.getAndroidPath());
        }else {
            o.put("appPath",spread.getIosPath());
        }
        return DataResult.init().buildData(o);
    }
}


package com.dk.modules.config.controller;

import com.dk.common.http.DataResult;
import com.dk.common.http.Result;
import com.dk.modules.config.mapper.ConfigAgreementMapper;
import com.dk.modules.config.po.ConfigAgreement;
import com.dk.modules.config.service.ConfigAgreementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ConfigAgreementController {

    @Autowired
    private ConfigAgreementService agreementService;
    @Autowired
    private ConfigAgreementMapper agreementMapper;

    @ResponseBody
    @PostMapping("/dk/admin/config/agreement/list")
    public DataResult list(){
        List<ConfigAgreement> list = agreementService.findAll();
        return DataResult.init().buildData(list);
    }

    @ResponseBody
    @PostMapping("/dk/admin/config/agreement/update")
    public Result update(@RequestBody ConfigAgreement agreement){
        agreementService.update(agreement);
        return Result.init();
    }

    @PostMapping("/dk/config/agreement/jkxy")
    public DataResult jkxy() {
        ConfigAgreement agreement = agreementMapper.selectByPrimaryKey("1");
        return DataResult.init().buildData(agreement);
    }
    @PostMapping("/dk/config/agreement/fwxy")
    public DataResult fwxy() {
        ConfigAgreement agreement = agreementMapper.selectByPrimaryKey("2");
        return DataResult.init().buildData(agreement);
    }
    @PostMapping("/dk/config/agreement/sqxy")
    public DataResult sqxy() {
        ConfigAgreement agreement = agreementMapper.selectByPrimaryKey("3");
        return DataResult.init().buildData(agreement);
    }


}

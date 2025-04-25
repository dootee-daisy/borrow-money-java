package com.dk.modules.config.controller;

import com.dk.common.http.DataResult;
import com.dk.common.http.Result;
import com.dk.modules.config.po.ConfigLoan;
import com.dk.modules.config.service.ConfigLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigLoanController {

    @Autowired
    private ConfigLoanService configLoanService;

    @PostMapping("/dk/config/loan/info")
    public DataResult info(){
        return DataResult.init().buildData(configLoanService.findInfo());
    }

    @PostMapping("/dk/admin/config/loan/update")
    public Result update(@RequestBody ConfigLoan loan){
        configLoanService.update(loan);
        return Result.init();
    }
}

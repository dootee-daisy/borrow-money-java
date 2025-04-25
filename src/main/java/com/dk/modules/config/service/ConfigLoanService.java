package com.dk.modules.config.service;

import com.dk.modules.config.mapper.ConfigLoanMapper;
import com.dk.modules.config.po.ConfigLoan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigLoanService {


    @Autowired
    private ConfigLoanMapper loanMapper;

    public ConfigLoan findInfo() {
        return loanMapper.selectByPrimaryKey("1");
    }

    public void update(ConfigLoan loan) {
        loanMapper.updateByPrimaryKeySelective(loan);
    }
}

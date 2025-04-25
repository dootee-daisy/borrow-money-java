package com.dk.modules.config.service;

import com.dk.modules.config.mapper.ConfigAgreementMapper;
import com.dk.modules.config.po.ConfigAgreement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigAgreementService {

    @Autowired
    private ConfigAgreementMapper configAgreementMapper;

    public List<ConfigAgreement> findAll(){
        return configAgreementMapper.selectAll();
    }

    public void update(ConfigAgreement agreement){
        configAgreementMapper.updateByPrimaryKeyWithBLOBs(agreement);
    }

    public ConfigAgreement findById(String id){
        return configAgreementMapper.selectByPrimaryKey(id);
    }

}

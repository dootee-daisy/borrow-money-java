package com.dk.modules.config.service;

import com.dk.modules.config.mapper.ConfigAboutMapper;
import com.dk.modules.config.po.ConfigAbout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigAboutService {

    @Autowired
    private ConfigAboutMapper aboutMapper;

    public ConfigAbout findById(String id){
        return aboutMapper.selectByPrimaryKey(id);
    }

    public void update(ConfigAbout configAbout){
        aboutMapper.updateByPrimaryKeyWithBLOBs(configAbout);
    }

    public List<ConfigAbout> findType(int type) {
        return aboutMapper.selectByType(type);
    }
}

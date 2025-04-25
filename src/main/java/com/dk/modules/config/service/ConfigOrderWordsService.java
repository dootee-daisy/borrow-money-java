package com.dk.modules.config.service;

import com.dk.common.util.IDGenerator;
import com.dk.modules.config.mapper.ConfigOrderWordsMapper;
import com.dk.modules.config.po.ConfigOrderWords;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ConfigOrderWordsService {

    @Autowired
    private ConfigOrderWordsMapper configOrderWordsMapper;

    //查询贷款订单文字
    public List<ConfigOrderWords> findWords(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return configOrderWordsMapper.selectWords();
    }

    //修改贷款订单文字
    public void updateWords(ConfigOrderWords configOrderWord) {
        configOrderWordsMapper.updateByPrimaryKeySelective(configOrderWord);
    }

    //删除贷款订单文字
    public void deleteWords(String id) {
        ConfigOrderWords words = new ConfigOrderWords();
        words.setTipsId(id);
        words.setDeleted(new Integer(1));
        configOrderWordsMapper.updateByPrimaryKeySelective(words);
    }


    public void addWords(ConfigOrderWords configOrderWord) {
        configOrderWord.setTipsId(IDGenerator.createSysId());
        configOrderWordsMapper.insertSelective(configOrderWord);
    }

    public List<ConfigOrderWords> findAllWords() {
        return configOrderWordsMapper.selectWords();
    }

    public ConfigOrderWords findById(String id) {
        return configOrderWordsMapper.selectByPrimaryKey(id);
    }
}

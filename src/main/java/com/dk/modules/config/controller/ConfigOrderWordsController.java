package com.dk.modules.config.controller;

import com.alibaba.fastjson.JSONObject;
import com.dk.common.constant.Constant;
import com.dk.common.exception.MyServiceException;
import com.dk.common.http.DataResult;
import com.dk.common.http.PageRequest;
import com.dk.common.http.PageResult;
import com.dk.common.http.Result;
import com.dk.modules.config.po.ConfigOrderWords;
import com.dk.modules.config.service.ConfigOrderWordsService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dk/admin/config")
public class ConfigOrderWordsController {

    @Autowired
    private ConfigOrderWordsService configOrderWordsService;

    // Query loan order texts
    @ResponseBody
    @PostMapping("/words/list")
    public PageResult queryWords(@RequestBody PageRequest pageRequest) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        List<ConfigOrderWords> words = configOrderWordsService.findWords(pageNum, pageSize);
        PageInfo page = new PageInfo(words);
        PageResult result = PageResult.init();
        result.setAllCount(page.getTotal());
        result.setPageCount(page.getPages());
        result.setPageNum(page.getPageNum());
        result.setPageSize(page.getPageSize());
        result.setData(page.getList());
        return result;
    }

    @PostMapping("/words/all")
    public DataResult queryAllWords(@RequestBody PageRequest pageRequest) {
        List<ConfigOrderWords> words = configOrderWordsService.findAllWords();
        return DataResult.init().buildData(words);
    }

    // Update loan order texts
    @PostMapping("/words/update")
    public Result updateWords(@RequestBody ConfigOrderWords configOrderWord) {
        configOrderWordsService.updateWords(configOrderWord);
        return Result.init();
    }

    @PostMapping("/words/add")
    public Result addWords(@RequestBody ConfigOrderWords configOrderWord) {
        configOrderWordsService.addWords(configOrderWord);
        return Result.init();
    }

    // Delete loan order texts
    @PostMapping("/words/delete")
    public Result deleteWords(@RequestBody JSONObject param) throws MyServiceException {
        String id = param.getString("id");
//        if (Constant.ORDER_STATUS_TJCG.equals(id) || Constant.ORDER_STATUS_HETG.equals(id) || Constant.ORDER_STATUS_TIZ.equals(id) || Constant.ORDER_STATUS_KHCW.equals(id)) {
//            throw new MyServiceException("System default data, cannot be deleted");
//        }
        configOrderWordsService.deleteWords(id);
        return Result.init();
    }
}
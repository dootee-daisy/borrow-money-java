package com.dk.common;

import com.alibaba.fastjson.JSONObject;
import com.dk.common.exception.MyServiceException;
import com.dk.common.http.DataResult;
import com.dk.common.http.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/dk/common")
public class CommonController {

//    private String folder = "C:/work/xiangmu/daikuan/workspace/site/files/";

    @Value("${dk.file.folder}")
    private String folder;

    @PostMapping("/image/upload")
    public DataResult upload(@RequestParam MultipartFile file) throws Exception {
        if(file.isEmpty()) {
            throw new MyServiceException("Please select the file to upload");
        }
        File destFile = new File(folder);
        if (!destFile.exists()) {
            destFile = new File("");
        }
        // concatenate sub-path
        SimpleDateFormat sf_ = new SimpleDateFormat("yyyyMMdd");
        String times = sf_.format(new Date());
        File upload = new File(destFile.getAbsolutePath(), times);
        // if the target folder does not exist, then create it
        if (!upload.exists()) {
            upload.mkdirs();
        }
        // Prepare a byte array based on file size
        byte[] bytes = file.getBytes();
        // concatenate upload path
        // Through the project path, concatenate the upload path
        String fileName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        fileName = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
        fileName = uuid+fileName;
        Path path = Paths.get(upload.getAbsolutePath() + "/" + fileName);
        //** Start writing the source file to the target address
        Files.write(path, bytes);
        String folder1 = upload.getAbsolutePath() + "/" + fileName;
        folder1 = folder1.substring(folder.length(),folder1.length());
        folder1 = "/files/"+folder1;
        JSONObject o = new JSONObject();
        o.put("path",folder1);
        return DataResult.init().buildData(o);
    }

    @PostMapping("/msg/send")
    public Result sendMsg(@RequestBody JSONObject o){
        String phone = o.getString("phone");
        String content = o.getString("content");
        return Result.init();
    }
}

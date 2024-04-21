package com.ab.puzzle.controller.admin;


import com.ab.result.Result;
import com.ab.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@Slf4j
@Api(tags = "通用接口")
public class CommonCtroller {

    @Autowired
    private AliOssUtil aliOssUtil;
    @PostMapping("/common/upload")
    @ApiOperation("上传图片")
    public Result<String[]> upload( MultipartFile[] files,Integer count){
        String[] uploadfiles=new String[count];
        int i=0;
        for (MultipartFile file : files) {
            try {
                String originalFilename = file.getOriginalFilename();
                String regex = originalFilename.substring(originalFilename.lastIndexOf("."));
                String newfilename = UUID.randomUUID().toString() + regex;
                uploadfiles[i++] =aliOssUtil.upload(file.getBytes(), newfilename);

            } catch (IOException e) {
                log.error("错误信息:{}",e.getMessage());
            }
        }
        return Result.success(uploadfiles);

    }
}

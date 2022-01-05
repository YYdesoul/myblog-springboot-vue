package com.soul.blog.controller;

import com.soul.blog.utils.QiniuUtils;
import com.soul.blog.vo.Result;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("upload")
public class UploadController {

  @Autowired
  private QiniuUtils qiniuUtils;

  @PostMapping
  public Result upload(@RequestParam("image") MultipartFile file) {
    // 原始文件名称 比如 aa.png
    String originalFilename = file.getOriginalFilename();
    // 唯一的文件名称
    String fileName =
        UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(originalFilename, ".");
    // 上传文件，上传到七牛云。与服务器按量付费，速度快，把图片放到离用户最近的服务器里
    // 降低我们自身应用服务器的贷款消耗
    boolean upload = qiniuUtils.upload(file, fileName);
    return null;
  }
}
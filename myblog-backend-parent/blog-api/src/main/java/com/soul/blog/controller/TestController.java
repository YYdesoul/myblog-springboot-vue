package com.soul.blog.controller;

import com.soul.blog.dao.pojo.SysUser;
import com.soul.blog.utils.UserThreadLocal;
import com.soul.blog.vo.Result;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
@Log4j2
public class TestController {

    @GetMapping
    public Result test(){
        SysUser sysUser = UserThreadLocal.get();
        log.info("sysUser is: " + sysUser);
        return Result.success(null);
    }
}

package com.soul.blog.controller;

import com.soul.blog.service.LoginService;
import com.soul.blog.vo.Result;
import com.soul.blog.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for Login
 */
@RestController
@RequestMapping("login")
public class LoginController {

  @Autowired
  private LoginService loginService;

  @PostMapping
  public Result login(@RequestBody LoginParam loginParam) {
    // 登录 验证用户 访问用户
    return loginService.login(loginParam);
  }
}

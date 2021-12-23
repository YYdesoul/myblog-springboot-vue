package com.soul.blog.service;

import com.soul.blog.dao.pojo.SysUser;
import com.soul.blog.vo.Result;
import com.soul.blog.vo.params.LoginParam;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface LoginService {

  Result login(LoginParam loginParam);

  SysUser checkToken(String token);

  Result logout(String token);

  Result register(LoginParam loginParam);
}

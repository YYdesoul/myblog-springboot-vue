package com.soul.blog.service;

import com.soul.blog.dao.pojo.SysUser;
import com.soul.blog.vo.Result;

public interface SysUserService {

  SysUser findUserById(Long id);

  SysUser findUser(String account, String password);

  /**
   * find user information by token
   * @param token
   * @return
   */
  Result findUserByToken(String token);

  SysUser findUserByAccount(String account);

  void save(SysUser sysUser);
}

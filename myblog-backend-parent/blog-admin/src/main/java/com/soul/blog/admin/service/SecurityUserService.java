package com.soul.blog.admin.service;

import com.soul.blog.admin.pojo.Admin;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserService implements UserDetailsService {

  @Autowired
  private AdminService adminService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    /**
     * 登录的时候，会把username传递到这里
     * 通过username查询admin表，如果admin存在，将密码告诉spring security
     * 如果不存在 返回null 认证失败
     */
    Admin admin = this.adminService.findAdmnByUsername(username);
    if (admin == null) {
      // 登录失败
      return null;
    }

    UserDetails userDetails = new User(username, admin.getPassword(), new ArrayList<>());
    return userDetails;
  }
}

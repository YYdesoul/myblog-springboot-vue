package com.soul.blog.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.soul.blog.admin.mapper.AdminMapper;
import com.soul.blog.admin.pojo.Admin;
import com.soul.blog.admin.pojo.Permission;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

  @Autowired
  private AdminMapper adminMapper;

  public Admin findAdmnByUsername(String username) {
    LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(Admin::getUsername, username);
    queryWrapper.last("limit 1");
    Admin admin = adminMapper.selectOne(queryWrapper);
    return admin;
  }

  public List<Permission> findPermissionByAdminId(Long adminId) {
    return adminMapper.findPermissionByAdminId(adminId);
  }
}

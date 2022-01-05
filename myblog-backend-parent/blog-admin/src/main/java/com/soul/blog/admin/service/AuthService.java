package com.soul.blog.admin.service;

import com.soul.blog.admin.pojo.Admin;
import com.soul.blog.admin.pojo.Permission;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  @Autowired
  private AdminService adminService;

  public boolean auth(HttpServletRequest request, Authentication authentication) {
    // 权限认证
    // 请求路径
    String requestURI = request.getRequestURI();
    Object principal = authentication.getPrincipal();
    if (principal == null || "anonymousUser".equals(principal)) {
      // 未登录
      return false;
    }
    UserDetails userDetails = (UserDetails) principal;
    String username = userDetails.getUsername();
    Admin admin = adminService.findAdmnByUsername(username);
    if (admin == null) {
      return false;
    }
    if (admin.getId() == 1) {
      // 超级管理员
      return true;
    }

    Long id = admin.getId();
    List<Permission> permissionList = this.adminService.findPermissionByAdminId(id);
    requestURI = StringUtils.split(requestURI, '?')[0];
    for (Permission permission : permissionList) {
      if (requestURI.equals(permission.getPath())) {
        return true;
      }
    }
    return false;
  }
}

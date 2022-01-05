package com.soul.blog.admin.controller;


import com.soul.blog.admin.model.params.PageParam;
import com.soul.blog.admin.pojo.Permission;
import com.soul.blog.admin.service.PermissionService;
import com.soul.blog.admin.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
public class AdminController {

  @Autowired
  private PermissionService permissionService;

  @PostMapping("permission/permissionList")
  public Result listPermission(@RequestBody PageParam pageParam) {
    return permissionService.listPermission(pageParam);
  }

  @PostMapping("permission/add")
  public Result add(@RequestBody Permission permission){
    return permissionService.add(permission);
  }

  @PostMapping("permission/update")
  public Result update(@RequestBody Permission permission){
    return permissionService.update(permission);
  }

  @GetMapping("permission/delete/{id}")
  public Result delete(@PathVariable("id") Long id){
    return permissionService.delete(id);
  }

}

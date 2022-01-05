package com.soul.blog.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soul.blog.admin.pojo.Admin;
import com.soul.blog.admin.pojo.Permission;
import java.util.List;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminMapper extends BaseMapper<Admin> {

    @Select("SELECT * FROM ms_permission where id in (select permission_id from ms_admin_permission where admin_id=#{adminId})")
    List<Permission> findPermissionByAdminId(Long adminId);
}

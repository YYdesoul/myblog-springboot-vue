package com.soul.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.soul.blog.dao.mapper.SysUserMapper;
import com.soul.blog.dao.pojo.SysUser;
import com.soul.blog.service.LoginService;
import com.soul.blog.service.SysUserService;
import com.soul.blog.vo.ErrorCode;
import com.soul.blog.vo.LoginUserVo;
import com.soul.blog.vo.Result;
import com.soul.blog.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysUserServiceImpl implements SysUserService {

  @Autowired
  private SysUserMapper sysUserMapper;

  @Autowired
  private LoginService loginService;

  @Override
  public UserVo findUserVoById(Long id) {
    SysUser sysUser = sysUserMapper.selectById(id);
    UserVo userVo = new UserVo();

    if (sysUser == null) {
      return UserVo.builder().nickname("de_soul").avatar("null").build();
    }

    BeanUtils.copyProperties(sysUser, userVo);
    userVo.setId(String.valueOf(sysUser.getId()));

    return userVo;
  }

  @Override
  public SysUser findUserById(Long id) {
    SysUser sysUser = sysUserMapper.selectById(id);

    if (sysUser == null) {
      return SysUser.builder().nickname("de_soul").build();
    }

    return sysUser;
  }

  @Override
  public SysUser findUser(String account, String password) {
    LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(SysUser::getAccount, account);
    queryWrapper.eq(SysUser::getPassword, password);
    queryWrapper.select(SysUser::getAccount, SysUser::getId, SysUser::getAvatar, SysUser::getNickname);
    queryWrapper.last("limit 1");

    return sysUserMapper.selectOne(queryWrapper);
  }

  @Override
  public Result findUserByToken(String token) {
    /**
     * 1. token 合法性校验
     *    - 是否为空，解析是否成功，redis是否存在
     * 2. 如果校验失败，返回错误
     * 3. 如果成功，反对对应的结果 LoginUserVo
     */
    SysUser sysUser = loginService.checkToken(token);
    if (sysUser == null) {
      return Result.fail(ErrorCode.TOKEN_ERROR.getCode(), ErrorCode.TOKEN_ERROR.getMsg());
    }

    // build LoginUserVo by sysUser
    LoginUserVo loginUserVo = LoginUserVo.builder()
        .id(String.valueOf(sysUser.getId()))
        .nickname(sysUser.getNickname())
        .avatar(sysUser.getAvatar())
        .account(sysUser.getAccount())
        .build();

    return Result.success(loginUserVo);
  }

  @Override
  public SysUser findUserByAccount(String account) {
    LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(SysUser::getAccount, account);
    queryWrapper.last("limit 1");
    sysUserMapper.selectOne(queryWrapper);
    return sysUserMapper.selectOne(queryWrapper);
  }

  @Override
  public void save(SysUser sysUser) {
    // 保存用户 id会自动生成
    // 这个地方 默认生成的id是 分布式id
    sysUserMapper.insert(sysUser);
  }
}

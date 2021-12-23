package com.soul.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.soul.blog.dao.pojo.SysUser;
import com.soul.blog.service.LoginService;
import com.soul.blog.service.SysUserService;
import com.soul.blog.utils.JWTUtils;
import com.soul.blog.vo.ErrorCode;
import com.soul.blog.vo.Result;
import com.soul.blog.vo.params.LoginParam;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

@Service
//@Transactional should add it in interface
public class LoginServiceImpl implements LoginService {

  private SysUserService sysUserService;

  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  private static final String salt = "mszlu!@#"; // 加密盐

  @Autowired
  public LoginServiceImpl(@Lazy SysUserService sysUserService) {  // 懒注入，消除bean循环依赖
    this.sysUserService = sysUserService;
  }

  @Override
  public Result login(LoginParam loginParam) {
    /**
     * 1. 检查参数是否合法
     * 2. 根据用户名和密码去user表中查询是否存在
     * 3. 如果不存在 登录失败
     * 4. 如果存在，使用jwt 生成token 返回给前端
     * 5. token放入redis当中，redis token: user信息 设置过期时间
     * (登录认证的时候 先认证token字符串是否合法，去redis认证是否存在)
     */
    String account = loginParam.getAccount();
    String password = loginParam.getPassword();
    if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
      return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
    }
    password = DigestUtils.md5Hex(password + salt);
    SysUser sysUser = sysUserService.findUser(account, password);
    if (sysUser == null) {
      return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
    }
    String token = JWTUtils.createToken(sysUser.getId());
    redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser), 1,  TimeUnit.DAYS);
    return Result.success(token);
  }

  @Override
  public SysUser checkToken(String token) {
    if (StringUtils.isBlank(token)) {
      return null;
    }

    Map<String, Object> parsedTokenMap = JWTUtils.checkToken(token); // parse token
    if (parsedTokenMap == null) {
      return null;  // parse failed
    }
    String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);  // get user object string in redis by token
    if (StringUtils.isBlank(userJson)) {
      return null;  // no this token in redis
    }
    SysUser sysUser = JSON.parseObject(userJson, SysUser.class);  // fastjson parse userJson from String to SysUser Object

    return sysUser;
  }

  /**
   * logout
   * @param token
   * @return
   */
  @Override
  public Result logout(String token) {
    redisTemplate.delete("TOKEN_" + token);
    return Result.success(null);
  }

  @Override
  public Result register(LoginParam loginParam) {
    /**
     * 1. 判断参数 是否合法
     * 2. 判断账户是否存在，存在 返回账户已经被注册
     * 3. 如果账户不存在就注册用户
     * 4. 生成token
     * 5. 存入redis 并返回
     * 6. 注意加上事务， 一旦中间的任何过程出现问题，注册的用户 需要回滚
     */
    String account = loginParam.getAccount();
    String password = loginParam.getPassword();
    String nickname = loginParam.getNickname();
    if (StringUtils.isBlank(account)
        || StringUtils.isBlank(password)
        || StringUtils.isBlank(nickname)) {
      return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
    }

    SysUser sysUser = sysUserService.findUserByAccount(account);
    if (sysUser != null) {
      return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(), ErrorCode.ACCOUNT_EXIST.getMsg());
    }

    sysUser = SysUser.builder()
        .nickname(nickname)
        .account(account)
        .password(DigestUtils.md5Hex(password+salt))
        .createDate(System.currentTimeMillis())
        .lastLogin(System.currentTimeMillis())
        .avatar("/static/img/logo.b3a48c0.png")
        .admin(1) // 1 为true
        .deleted(0) // 0 为false
        .salt("")
        .status("")
        .email("")
        .build();

    sysUserService.save(sysUser);

    String token = JWTUtils.createToken(sysUser.getId());
    redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);

    return Result.success(token);
  }
}

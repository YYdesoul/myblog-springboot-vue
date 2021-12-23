package com.soul.blog.handler;

import com.alibaba.fastjson.JSON;
import com.soul.blog.dao.pojo.SysUser;
import com.soul.blog.service.LoginService;
import com.soul.blog.utils.UserThreadLocal;
import com.soul.blog.vo.ErrorCode;
import com.soul.blog.vo.Result;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Log4j2
public class LoginInterceptor implements HandlerInterceptor {

  @Autowired
  private LoginService loginService;

  /**
   * 在执行controller方法(handler)之前进行执行
   * @param request
   * @param response
   * @param handler
   * @return
   * @throws Exception
   */
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    /**
     * 1. 需要判断 请求的接口路径 是否为HandlerMethod(controller方法)
     * 2. 判断token是否为空，如果为空-> 未登录
     * 3. 如果token 不为空-> 登录验证 loginService checkToken
     * 4. 如果认证成功-> 放行即可
     */
    if (!(handler instanceof HandlerMethod)) {
      // handler 可能是 RequestResourceHandler。springboot程序访问静态资源时，默认去classpath下的static目录去查询
      return true;
    }

    String token = request.getHeader("Authorization");

    log.info("=================request start===========================");
    String requestURI = request.getRequestURI();
    log.info("request uri:{}",requestURI);
    log.info("request method:{}",request.getMethod());
    log.info("token:{}", token);
    log.info("=================request end===========================");


    if (StringUtils.isBlank(token)) {
      printNoLogin(response);
      return false;
    }

    SysUser sysUser = loginService.checkToken(token);
    if (sysUser == null) { // if no this token in redis
      printNoLogin(response);
      return false;
    }
    // 我们希望在Controller中 直接获取用户的信息 -> 使用TreadLocal
    UserThreadLocal.put(sysUser);
    return true;  // 放行
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {
    // 如果不删除ThreadLocal中用完的信息，会有内存泄漏的风险
    UserThreadLocal.remove();
  }

  private void printNoLogin(HttpServletResponse response) throws IOException {
    Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
    response.setContentType("text/json;charset=utf-8");
    response.getWriter().print(JSON.toJSONString(result));  // 把result写入response
  }


}

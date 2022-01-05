package com.soul.blog.common.aop;

import com.alibaba.fastjson.JSON;
import com.soul.blog.utils.HttpContextUtils;
import com.soul.blog.utils.IpUtils;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component
@Aspect // 切面 定义了通知和切点的关系
@Log4j2
public class LogAspect {

  @Pointcut("@annotation(com.soul.blog.common.aop.LogAnnotation)")  // 定义切点
  public void pt() {

  }

  // 环绕通知
  @Around("pt()")
  public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
    long beginTime = System.currentTimeMillis();
    // 执行方法
    Object result = joinPoint.proceed(); // 执行原方法
    // 执行时长(毫秒)
    long time = System.currentTimeMillis() - beginTime;
    // 保存日志
    recordLog(joinPoint, time);
    return result;
  }

  private void recordLog(ProceedingJoinPoint joinPoint, long time) {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();
    LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
    log.info("=====================log start================================");
    log.info("module:{}",logAnnotation.module());
    log.info("operation:{}",logAnnotation.operator());

    //请求的方法名
    String className = joinPoint.getTarget().getClass().getName();
    String methodName = signature.getName();
    log.info("request method:{}",className + "." + methodName + "()");

//        //请求的参数
    Object[] args = joinPoint.getArgs();
    String params = JSON.toJSONString(args[0]);
    log.info("params:{}",params);

    //获取request 设置IP地址
    HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
    log.info("ip:{}", IpUtils.getIpAddr(request));


    log.info("excute time : {} ms",time);
    log.info("=====================log end================================");
  }


}

package com.soul.blog.common.cache;

import com.alibaba.fastjson.JSON;
import com.soul.blog.vo.Result;
import java.lang.reflect.Method;
import java.time.Duration;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log4j2
public class CacheAspect {

  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  @Pointcut("@annotation(com.soul.blog.common.cache.Cache)")
  public void pt(){}

  @Around("pt()")
  public Object around(ProceedingJoinPoint pjp) {
    try{
      Signature signature = pjp.getSignature();
      // 类名
      String className = pjp.getTarget().getClass().getSimpleName();
      // 调用的方法名
      String methodName = signature.getName();

      Class[] parameterTypes = new Class[pjp.getArgs().length];
      Object[] args = pjp.getArgs();
      // 参数
      String params = "";
      for (int i = 0; i < args.length; i++) {
        if (args[i] != null) {
          params += JSON.toJSONString(args[i]);
          parameterTypes[i] = args[i].getClass();
        } else {
          parameterTypes[i] = null;
        }
      }
      if (StringUtils.isNotEmpty(params)) {
        // 加密 以防出现key过长以及字符转义获取不到的情况
        params = DigestUtils.md5Hex(params);
      }
      Method method = pjp.getSignature().getDeclaringType().getMethod(methodName, parameterTypes);
      //获取Cache 注解
      Cache annotation = method.getAnnotation(Cache.class);
      long expire = annotation.expire();
      String name = annotation.name();
      // 先从redis获取
      String redisKey = name + "::" + className + "::" + methodName + "::" + params;
      String redisValue = redisTemplate.opsForValue().get(redisKey);
      if (StringUtils.isNotEmpty(redisValue)) {
        log.info("走了缓存~~~, {}, {}", className, methodName);
        Result result = JSON.parseObject(redisValue, Result.class);
        return result;
      }
      Object proceed = pjp.proceed(); // 执行原方法
      redisTemplate.opsForValue().set(redisKey, JSON.toJSONString(proceed), Duration.ofMillis(expire));
      log.info("存入缓存~~~ {}, {}", className, methodName);
      return proceed;
    } catch (Throwable throwable) {
      throwable.printStackTrace();
    }

    return Result.fail(-999, "系统错误");
  }
}

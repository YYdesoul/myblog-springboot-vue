package com.soul.blog.handler;

import com.soul.blog.vo.Result;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 对加了@Controller注解的方法进行拦截处理 AOP实现
 */

@ControllerAdvice
@Log4j2
public class AllExceptionHandler {

  // 进行异常处理，处理Exception.class的异常
//  @ExceptionHandler(Exception.class)
//  @ResponseBody // 返回Json数据
//  public Result doException(Exception e) {
//    log.error(e.getMessage());
//    return Result.fail(500, "Server Internal Error");
//  }

}

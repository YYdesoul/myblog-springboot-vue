package com.soul.blog.common.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD}) //Type 代表可以放在类上面 Method 代表可以放在方法上
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

  String module() default "";
  String operator() default "";

}

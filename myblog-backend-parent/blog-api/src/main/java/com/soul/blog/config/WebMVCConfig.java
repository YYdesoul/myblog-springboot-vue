package com.soul.blog.config;

import com.soul.blog.handler.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

  @Autowired
  private LoginInterceptor loginInterceptor;

  /**
   * Cors (跨域) conf.
   * @param registry
   */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**").allowedOrigins("*");
//    registry.addMapping("/**").allowedOrigins("http://85.214.77.110:81");
//    registry.addMapping("/**").allowedOrigins("http://localhost:81");
  }

  /**
   * inject interceptor to SpringMVC
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(loginInterceptor)
        .addPathPatterns("/test")
        .addPathPatterns("/comments/create/change") // 拦截test接口
        .addPathPatterns("/articles/publish")
        ;

  }
}

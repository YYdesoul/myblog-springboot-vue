package com.soul.blog.utils;

import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JWTUtilsTest {

  @Test
  public void testToken() {
    long userId = 100L;
    String token = JWTUtils.createToken(userId);
    System.out.println(token);
    Map<String, Object> map = JWTUtils.checkToken(token);
    System.out.println(map.get("userId"));
    Assertions.assertEquals((int) userId,  (int) map.get("userId"));
  }
}

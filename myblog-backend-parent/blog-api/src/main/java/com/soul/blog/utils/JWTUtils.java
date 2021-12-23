package com.soul.blog.utils;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtils {

  private static final String jwtToken = "123456Myblog!@#$$"; // part C

  public static String createToken(Long userId) {
    HashMap<String, Object> claims = new HashMap<>();
    claims.put("userId", userId);
    JwtBuilder jwtBuilder = Jwts.builder()
        .signWith(SignatureAlgorithm.HS256, jwtToken) // 签发算法，秘钥为jwtToken
        .setClaims(claims)  // body，要唯一，自行设置
        .setIssuedAt(new Date()) // 设置签发时间，这样每次生成的jwt就不一样
        .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 60 * 1000)); // 一天的有效期
    String token = jwtBuilder.compact();
    return token;
  }

  public static Map<String, Object> checkToken(String token) {
    try {
      Jwt parse = Jwts.parser().setSigningKey(jwtToken).parse(token);
      return (Map<String, Object>) parse.getBody();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }
}

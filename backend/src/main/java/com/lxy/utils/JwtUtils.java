package com.lxy.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtils {

    private static String signKey;
    private static Long expire;

    @Value("${jwt.sign-key:YourSecretKeyAtLeast256BitsLongForHS256!}")
    private String configuredSignKey;

    @Value("${jwt.expire:43200000}")
    private Long configuredExpire;

    @PostConstruct
    public void init() {
        JwtUtils.signKey = configuredSignKey;
        JwtUtils.expire = configuredExpire;
    }

    /**
     * 生成JWT令牌
     */
    public static String generateJwt(Map<String, Object> claims) {
        return Jwts.builder()
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256, signKey)
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .compact();
    }

    /**
     * 解析JWT令牌
     * @param jwt JWT令牌
     * @return JWT第二部分负载中存储的内容
     */
    public static Claims parseJWT(String jwt) {
        return Jwts.parser()
                .setSigningKey(signKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

    /**
     * 解析Token（parseJWT的别名，供Interceptor调用）
     */
    public static Claims parseToken(String token) {
        return parseJWT(token);
    }
}


package com.example.rabbitmq_stomp.global.jwt;

import com.example.rabbitmq_stomp.global.config.AppConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Service
public class JwtUtil {
    private static final String SECRET_KEY = AppConfig.getJwtSecretKey();

    private static Date ACCESSTOKEN_EXPIRATION;
    private static Date REFRESHTOKEN_EXPIRATION;

    public static SecretKey getSecretKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public static Cookie createCookie(String tokenName, Map<Object, Object> data) {
        String token = encode(tokenName, data);
        Cookie cookie  = new Cookie(tokenName, token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

    public static Cookie createCookie(String tokenName){
        return createCookie(tokenName, null);
    }

    private static String encode(String tokenName, Map<Object, Object> data) {
        Claims claims = Jwts.claims()
                .add("type", tokenName)
                .add("data", data)
                .build();

        Date now = Date.from(Instant.now());

        return Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(getTokenExpiration(tokenName))
                .signWith(getSecretKey(), Jwts.SIG.HS256)
                .compact();
    }

    private static Date getTokenExpiration(String tokenName){
        Instant nowIn = Instant.now();
        Date token = null;
        if(tokenName.equals("accessToken")){
            if(ACCESSTOKEN_EXPIRATION == null){
                Duration duration = Duration.ofHours(12);
                ACCESSTOKEN_EXPIRATION = Date.from(nowIn.plus(duration));
            }
            token = ACCESSTOKEN_EXPIRATION;
        }

        if(tokenName.equals("refreshToken")){
            if(REFRESHTOKEN_EXPIRATION == null){
                Duration duration = Duration.ofDays(365);
                REFRESHTOKEN_EXPIRATION = Date.from(nowIn.plus(duration));
            }
            token = REFRESHTOKEN_EXPIRATION;
        }

        return token;
    }

}

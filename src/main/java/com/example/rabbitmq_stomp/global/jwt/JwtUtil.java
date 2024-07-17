package com.example.rabbitmq_stomp.global.jwt;

import com.example.rabbitmq_stomp.global.config.AppConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
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

    private static Duration ACCESSTOKEN_EXPIRATION = Duration.ofHours(12);
    private static Duration REFRESHTOKEN_EXPIRATION = Duration.ofDays(365);

    public static SecretKey getSecretKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public static Cookie createCookie(String tokenName, Map<String, Object> data) {
        String token = encode(tokenName, data);
        Cookie cookie  = new Cookie(tokenName, token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

    public static Cookie createCookie(String tokenName){
        return createCookie(tokenName, null);
    }

    private static String encode(String tokenName, Map<String, Object> data) {
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
            token = Date.from(nowIn.plus(ACCESSTOKEN_EXPIRATION));
        }

        if(tokenName.equals("refreshToken")){
            token = Date.from(nowIn.plus(REFRESHTOKEN_EXPIRATION));
        }

        return token;
    }

    public static boolean validateToken(String accessToken) {
        return !isExpired(accessToken);
    }

    private static boolean isExpired(String token) {
        try{
            Date ex = getClaims(token)
                    .getExpiration();
            return ex != null && ex.before(new Date());
        }catch(ExpiredJwtException | SignatureException e){
            return true;
        }
    }

    public static Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public static Map<String, Object> getDataFrom(String tokenValue) {
        Map<String, Object> payloadData = (Map<String, Object>) getClaims(tokenValue).get("data");

        return payloadData;
    }
}

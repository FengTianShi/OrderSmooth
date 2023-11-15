package com.nobody.OrderSmoothAPI.common;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtils {

        private static String secret = "affe506d-dcff-4444-825d-63d4d3e9c7d0";

        public static String generateToken(String subject, Object ContentClass, long expire) {
                Map<String, Object> claims = new HashMap<>();
                claims.put("subject", subject);
                try {
                        claims.put("content", new ObjectMapper().writeValueAsString(ContentClass));
                } catch (JsonProcessingException e) {
                        e.printStackTrace();
                }

                Date now = new Date();
                Date expiration = new Date(now.getTime() + expire * 1000);

                return Jwts.builder()
                                .setHeaderParam("type", "JWT")
                                .setClaims(claims)
                                .setIssuedAt(now)
                                .setExpiration(expiration)
                                .signWith(SignatureAlgorithm.HS512, secret)
                                .compact();
        }

        public static Claims getBody(String token) {
                return Jwts.parser()
                                .setSigningKey(secret)
                                .parseClaimsJws(token)
                                .getBody();
        }

        public static String getSubject(String token) {
                return (String) Jwts.parser()
                                .setSigningKey(secret)
                                .parseClaimsJws(token)
                                .getBody()
                                .get("subject");
        }

        public static String getContent(String token) {
                return (String) Jwts.parser()
                                .setSigningKey(secret)
                                .parseClaimsJws(token)
                                .getBody()
                                .get("content");
        }

        public static <T> T getContent(String token, Class<T> clazz) {
                try {
                        return new ObjectMapper().readValue(
                                        (String) Jwts.parser()
                                                        .setSigningKey(secret)
                                                        .parseClaimsJws(token)
                                                        .getBody()
                                                        .get("content"),
                                        clazz);
                } catch (JsonProcessingException e) {
                        e.printStackTrace();
                        return null;
                }
        }

}

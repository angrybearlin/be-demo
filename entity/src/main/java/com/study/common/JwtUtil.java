package com.study.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class JwtUtil {
    private static String KEY = "lkx";
    public static String create(String id) {
        return JWT.create().withClaim("userId", id)
                .sign(Algorithm.HMAC256(KEY));
    }

    public static String decode(String token) {
        return JWT.require(Algorithm.HMAC256(KEY)).build().verify(token).getClaim("userId").asString();
    }
}

package com.study.test;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class Test {
    public static void main(String[] args) {
        String token = JWT.create().withClaim("userId", "123456")
                .sign(Algorithm.HMAC256("lkx"));
        System.out.println(token);
    }
}

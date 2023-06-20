package com.study.common;

import lombok.Getter;

@Getter
public enum ResultCode {
    SUCCESS(200),
    FAILED(500),
    UNLOGIN(401),
    FORBID(403);
    Integer code;

    ResultCode(Integer code) {
        this.code = code;
    }
}

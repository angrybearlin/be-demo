package com.study.common;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResultJson<T> {
    private Integer code;
    private T content;
    private String msg;

    private ResultJson(Integer code, T content, String msg) {
        this.code = code;
        this.content = content;
        this.msg = msg;
    }
    public static <T> ResultJson getInstance(ResultCode code, T content, String msg) {
        return new ResultJson(code.getCode(), content, msg);
    }
    public static <T> ResultJson<T> success(T content, String msg) {
        return getInstance(ResultCode.SUCCESS, content, msg);
    }

    public static <T> ResultJson<T> success(T content) {
        return success(content, null);
    }
    public static <T> ResultJson<T> failed(T content, String msg) {
        return getInstance(ResultCode.FAILED, content, msg);
    }
    public static <T> ResultJson<T> failed(String msg) {
        return failed(null, msg);
    }

    public static <T> ResultJson<T> unlogin(String msg) {
        return getInstance(ResultCode.UNLOGIN, null, msg);
    }

    public static <T> ResultJson<T> forbid() {
        return getInstance(ResultCode.FORBID, null, "该用户无此操作权限");
    }

}

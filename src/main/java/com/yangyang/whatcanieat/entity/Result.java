package com.yangyang.whatcanieat.entity;

public class Result<T> {

    public int code;
    public String msg;
    public T data;

    public static <T> Result<T> ok(String msg) {
        Result<T> r = new Result<>();
        r.code = 0;
        r.msg = msg;
        r.data = null;
        return r;
    }

    public static <T> Result<T> ok(T data) {
        Result<T> r = new Result<>();
        r.code = 0;
        r.msg = "ok";
        r.data = data;
        return r;
    }

    public static <T> Result<T> ok(T data,String msg) {
        Result<T> r = new Result<>();
        r.code = 0;
        r.msg = msg;
        r.data = data;
        return r;
    }

    public static <T> Result<T> loginSuccess(T token) {
        Result<T> r = new Result<>();
        r.code = 0;
        r.msg = "登录成功";
        r.data = token;
        return r;
    }

    public static <T> Result<T> fail(String msg) {
        Result<T> r = new Result<>();
        r.code = -1;
        r.msg = msg;
        r.data = null;
        return r;
    }
}

package com.yue.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 进行统一结果集封装,用于前后端分离
 */
@Data
public class Result implements Serializable {

    private int code;   // 200是正常，非200表示异常
    private String msg;
    private Object data;

    public static Result succ(String msg,Object data){
        Result result = new Result();
        result.setCode(200);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static Result succ(String msg){
        Result result = new Result();
        result.setCode(200);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    public static Result succ(Object data){
        Result result = new Result();
        result.setCode(200);
        result.setMsg("操作成功");
        result.setData(data);
        return result;
    }

    public static Result fail(String msg) {
        return fail(400, msg, null);
    }

    public static Result fail(String msg, Object data) {
        return fail(400, msg, data);
    }

    public static Result fail(int code, String msg) {
        return fail(code, msg,null);
    }

    public static Result fail(int code, String msg, Object data) {
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }
}

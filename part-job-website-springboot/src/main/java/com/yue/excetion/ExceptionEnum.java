package com.yue.excetion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {

    //用户相关异常
    USERNAME_NOT_FOUND(002, "用户名不存在"),
    PASSWORD_NOT_EXISTED(002, "用户名或密码错误"),
    OLD_PASSWORD_INPUT_ERROR(002, "用户名或密码错误"),
    NEW_PASSWORD_INPUT_ERROR(002, "两次输入的密码不一致"),
    LOGIN_USER_ERROR(002, "用户登陆失败"),
    USER_HAS_EXISTED(002,"用户名已存在"),
    NICKNAME_HAS_EXISTED(002,"昵称重复"),
    USER_CODE_ERROR(002,"验证码错误"),
    SAVE_USER_ERROR(002, "注册用户失败"),

    //分类相关异常
    GET_CATEGORY_ERROR(002, "分类查询异常"),
    GET_CATEGORY_NOT_FOUND(002, "分类查询为空"),

    //收藏相关异常
    SAVE_COLLECT_ERROR(002, "收藏失败"),
    SAVE_COLLECT_REUSE(002, "已收藏，请勿重复收藏"),
    GET_COLLECT_NOT_FOUND(002, "尚无商品收藏"),
    GET_COLLECT_ERROR(002, "获取收藏失败"),
    DELETE_COLLECT_ERROR(002, "删除收藏失败"),

    //兼职相关异常
    PART_JOB_EDIT_ERROR(002, "兼职操作异常"),
    PART_JOB_DELETE_ERROR(002, "兼职删除异常"),
    PART_JOB_SEARCH_ERROR(002, "没有查询到相关兼职"),
    PART_JOB_CATEGORY_ERROR(002, "当前分类下暂无兼职"),
    USERID_ERROR(002,"没有权限修改其他人创建的兼职"),


    //会话相关异常
    SESSION_LIST_CREATE_ERROR(002, "会话建立异常"),
    SESSION_LIST_EDIT_ERROR(002, "发送消息异常"),
    SESSION_LIST_GET_ERROR(002, "会话查询异常"),
    MESSAGE_SEND_ERROR(002,"没有权限修改其他人创建的兼职");



    private int code;
    private String msg;

}

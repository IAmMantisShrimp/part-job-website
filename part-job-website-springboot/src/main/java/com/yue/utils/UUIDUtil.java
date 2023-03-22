package com.yue.utils;

import java.util.UUID;

/**
 * 用于生成主键
 */
public class UUIDUtil {
    public static String getUUID(){
        String id = UUID.randomUUID().toString();
        String uid = id.replaceAll("-", "");

        return uid;
    }
}

package com.yue.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeUtil {
    public static LocalDateTime getCurrentLocalDateTime(){
        String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime currentTime = LocalDateTime.parse(format, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return currentTime;
    }
}

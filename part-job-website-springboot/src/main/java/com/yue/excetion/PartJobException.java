package com.yue.excetion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 自定义异常
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PartJobException extends RuntimeException{
    private ExceptionEnum exceptionEnum;
}

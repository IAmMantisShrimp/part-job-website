package com.yue.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用于页面显示用户信息的vo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserVO {
    private String userId;
    private String avatar;
    private String nickname;
}

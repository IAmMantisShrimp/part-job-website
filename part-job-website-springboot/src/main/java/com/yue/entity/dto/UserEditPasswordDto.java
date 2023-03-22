package com.yue.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserEditPasswordDto {
    private String userId;
    private String password;
    private String newPassword1;
    private String newPassword2;
}

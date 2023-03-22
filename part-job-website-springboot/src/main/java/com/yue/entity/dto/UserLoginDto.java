package com.yue.entity.dto;

import com.yue.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserLoginDto extends User {

    private String code;

}

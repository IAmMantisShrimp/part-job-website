package com.yue.mapper;

import com.yue.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author liuy
 * @since 2022-03-23
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    User getUserByUsername(String username);
    User getUserByNickname(String nickname);
}

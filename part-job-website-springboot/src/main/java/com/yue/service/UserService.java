package com.yue.service;

import com.yue.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yue.entity.dto.UserEditPasswordDto;
import com.yue.entity.vo.UserVO;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author liuy
 * @since 2022-03-23
 */
public interface UserService extends IService<User> {
    void register(User user);
    void editUser(User user);
    void editPassword(UserEditPasswordDto user);
    User getUserByUsername(String username);
    UserVO findUserVoById(String authorId);
}

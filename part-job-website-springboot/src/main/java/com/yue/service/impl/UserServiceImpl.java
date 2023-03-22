package com.yue.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.yue.entity.User;
import com.yue.entity.dto.UserEditPasswordDto;
import com.yue.entity.vo.UserVO;
import com.yue.excetion.ExceptionEnum;
import com.yue.excetion.PartJobException;
import com.yue.mapper.UserMapper;
import com.yue.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yue.utils.SaltUtil;
import com.yue.utils.UUIDUtil;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author liuy
 * @since 2022-03-23
 */
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 注册用户
     * @param user
     */
    public void register(User user){
        System.out.println("user: "+user);
        //判断用户名是否被注册
        User userTemp = userMapper.getUserByUsername(user.getUsername());
        if(ObjectUtil.isNotEmpty(userTemp)){
            throw new PartJobException(ExceptionEnum.USER_HAS_EXISTED);
        }
        //判断昵称是否重复
        User userNickname = userMapper.getUserByNickname(user.getNickname());
        if(ObjectUtil.isNotEmpty(userNickname)){
            throw new PartJobException(ExceptionEnum.NICKNAME_HAS_EXISTED);
        }
        //
        user.setDelFlag(false);
        user.setUserId(UUIDUtil.getUUID());
        //对密码进行md5加随机盐加密
        String salt = SaltUtil.getSalt(8);
        user.setSalt(salt);
        Md5Hash md5Hash = new Md5Hash(user.getPassword(),salt,1024);
        user.setPassword(md5Hash.toHex());
        try {
            userMapper.insert(user);
        }catch (Exception e){
            e.printStackTrace();
            throw new PartJobException(ExceptionEnum.SAVE_USER_ERROR);
        }
    }

    @Override
    public void editUser(User user) {
        User oldUser = this.userMapper.selectById(user.getUserId());
        if(null != user.getAvatar()){
            oldUser.setAvatar(user.getAvatar());
        }
        if(null != user.getNickname()){
            oldUser.setNickname(user.getNickname());
        }
        if(null != user.getPhone()){
            oldUser.setPhone(user.getPhone());
        }
        if(null != user.getEmail()){
            oldUser.setEmail(user.getEmail());
        }
        if(null != user.getEnrollmentYear()){
            oldUser.setEnrollmentYear(user.getEnrollmentYear());
        }
        if(null != user.getSex()){
            oldUser.setSex(user.getSex());
        }
        this.userMapper.updateById(user);
    }

    /**
     * 修改用户密码
     * @param user
     */
    @Override
    public void editPassword(UserEditPasswordDto user) {
        if(!user.getNewPassword1().equals(user.getNewPassword2())){
            throw new PartJobException(ExceptionEnum.NEW_PASSWORD_INPUT_ERROR);
        }
        User userTemp = this.userMapper.selectById(user.getUserId());
        //校验输入的旧密码是否正确
        Md5Hash md5Hash = new Md5Hash(user.getPassword(),userTemp.getSalt(),1024);
        if(!md5Hash.toHex().equals(userTemp.getPassword())){
            throw new PartJobException(ExceptionEnum.OLD_PASSWORD_INPUT_ERROR);
        }
        //修改密码
        Md5Hash newPasswordMd5Hash = new Md5Hash(user.getNewPassword1(),userTemp.getSalt(),1024);
        userTemp.setPassword(newPasswordMd5Hash.toHex());
        this.userMapper.updateById(userTemp);
    }

    @Override
    public User getUserByUsername(String username) {
        return this.userMapper.getUserByUsername(username);
    }

    @Override
    public UserVO findUserVoById(String userId) {
        User user = this.userMapper.selectById(userId);
        UserVO userVo = new UserVO();
        userVo.setAvatar(user.getAvatar());
        userVo.setNickname(user.getNickname());
        userVo.setUserId(user.getUserId());
        return userVo;
    }
}

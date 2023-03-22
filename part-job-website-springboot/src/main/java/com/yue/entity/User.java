package com.yue.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author liuy
 * @since 2022-03-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID=1L;

      /**
     * 主键
     */
      @TableId(value = "user_id")
        private String userId;

      /**
     * 用户名
     */
      private String username;

      /**
     * 密码
     */
      private String password;

      /**
     * 昵称
     */
      private String nickname;

      /**
     * 电话号码
     */
      private String phone;

      /**
     * 电子邮箱
     */
      private String email;

      /**
     * 性别
     */
      private String sex;

      /**
     * 入学年份
     */
      private String enrollmentYear;

      /**
       * 随机盐
       */
      private String salt;

      /**
       * 用户头像链接
       */
      private String avatar;

      /**
     * 删除标记
     */
    @TableLogic(value="0",delval="1")
    @TableField("del_flag")
    private Boolean delFlag;


}

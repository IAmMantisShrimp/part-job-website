package com.yue.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 会话列表
 * </p>
 *
 * @author liuy
 * @since 2022-03-28
 */
@Data
  @EqualsAndHashCode(callSuper = false)
  @Accessors(chain = true)
public class SessionList implements Serializable {

    private static final long serialVersionUID=1L;

      /**
     * id
     */
        private String id;

      /**
     * 所属用户
     */
      private String userId;

      /**
     * 到用户
     */
      private String toUserId;

      /**
     * 到用户
     */
      private String toUserAvatar;

      /**
     * 会话名称
     */
      private String listName;

      /**
     * 未读消息数
     */
      private Integer unReadCount;


      /**
     * 备注
     */
      private String remark;


}

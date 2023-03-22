package com.yue.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 消息表
 * </p>
 *
 * @author liuy
 * @since 2022-03-28
 */
@Data
  @EqualsAndHashCode(callSuper = false)
  @Accessors(chain = true)
public class MsgInfo implements Serializable {

    private static final long serialVersionUID=1L;

      /**
     * 消息id
     */
        private String id;

      /**
     * 消息发送者id
     */
      private String fromUserId;

      /**
     * 消息发送者名称
     */
      private String fromUserName;

      /**
     * 消息接收者id
     */
      private String toUserId;

      /**
     * 消息接收者名称
     */
      private String toUserName;

      /**
     * 消息内容
     */
      private String content;

      /**
     * 消息发送时间
     */
      private LocalDateTime createTime;

      /**
     * 是否已读（1 已读）
     */
      private Boolean unReadFlag;

      /**
     * 消息类型(1文本 2图片)
     */
      private String msgType;


}

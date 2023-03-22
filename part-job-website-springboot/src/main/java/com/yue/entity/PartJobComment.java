package com.yue.entity;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 兼职评论表
 * </p>
 *
 * @author liuy
 * @since 2022-04-02
 */
@Data
  @EqualsAndHashCode(callSuper = false)
  @Accessors(chain = true)
public class PartJobComment implements Serializable {

    private static final long serialVersionUID=1L;

      /**
     * 主键
     */
        private String partJobCommentId;

      /**
     * 用户id
     */
      private String userId;

      /**
     * 兼职id
     */
      private String partJobId;

      /**
     * 评论内容
     */
      private String content;

      /**
     * 创建时间
     */
      private LocalDateTime createTime;

      /**
     * 删除标记
     */
      @TableField("DEL_FLAG")
    private Boolean delFlag;

      /**
     * 上级评论id
     */
      private String parentId;

      /**
     * 上级评论的用户id
     */
      private String toUid;

      /**
     * 是否有子评论
     */
      private Integer level;


}

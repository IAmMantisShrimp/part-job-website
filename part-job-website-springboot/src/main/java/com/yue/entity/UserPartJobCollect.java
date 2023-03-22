package com.yue.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户兼职收藏关系表
 * </p>
 *
 * @author liuy
 * @since 2022-03-23
 */
@Data
  @EqualsAndHashCode(callSuper = false)
  @Accessors(chain = true)
public class UserPartJobCollect implements Serializable {

    private static final long serialVersionUID=1L;

      /**
     * 主键
     */
      @TableId(value = "user_part_job_collect_id")
        private String userPartJobCollectId;

      /**
     * 用户id
     */
      private String userId;

      /**
     * 兼职id
     */
      private String partJobId;

      /**
     * 收藏时间
     */
      private LocalDateTime collectTime;


}

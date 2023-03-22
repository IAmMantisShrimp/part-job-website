package com.yue.entity;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 兼职信息表
 * </p>
 *
 * @author liuy
 * @since 2022-03-23
 */
@Data
  @EqualsAndHashCode(callSuper = false)
  @Accessors(chain = true)
public class PartJob implements Serializable {

    private static final long serialVersionUID=1L;

      /**
     * 主键
     */
      @TableId(value = "part_job_id")
      private String partJobId;

      /**
     * 标题
     */
      private String title;

      /**
     * 描述
     */
      private String description;

      /**
     * 兼职类别id
     */
      private Integer categoryId;

      /**
       * 创建用户的id
       */
      private String userId;

      /**
     * 薪酬
     */
      private Integer salary;

      /**
     * 薪酬类别(日结、月结)
     */
      private String salaryCategory;

      /**
     * 校内，校外
     */
      private String isInsideSchool;

      /**
     * 创建时间
     */
      private LocalDateTime createTime;


      /**
       * 评论数
       */
      private int commentCount;

      /**
     * 删除标记
     */
      @TableLogic(value="0",delval="1")
      @TableField("del_flag")
    private Boolean delFlag;


}

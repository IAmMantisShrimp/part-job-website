package com.yue.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 兼职类别表
 * </p>
 *
 * @author liuy
 * @since 2022-03-23
 */
@Data
  @EqualsAndHashCode(callSuper = false)
  @Accessors(chain = true)
public class PartJobCategory implements Serializable {

    private static final long serialVersionUID=1L;

      /**
     * 主键
     */
        @TableId(value = "category_id")
      private String categoryId;

      /**
     * 类别名称
     */
      private String categoryName;


}

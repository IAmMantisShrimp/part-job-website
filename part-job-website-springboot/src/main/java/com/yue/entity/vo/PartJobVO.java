package com.yue.entity.vo;

import com.yue.entity.PartJob;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PartJobVO extends PartJob {
    private String categoryName;
}

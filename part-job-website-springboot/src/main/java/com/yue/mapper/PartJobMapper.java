package com.yue.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yue.entity.PartJob;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yue.entity.vo.PartJobVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 兼职信息表 Mapper 接口
 * </p>
 *
 * @author liuy
 * @since 2022-03-23
 */
@Mapper
public interface PartJobMapper extends BaseMapper<PartJob> {
    Page<PartJobVO> searchPartJobByWord(@Param("page") Page<PartJobVO> page,@Param("word") String word);
    Page<PartJobVO> searchPartJob(@Param("page") Page<PartJobVO> page);
    Page<PartJobVO> searchPartJobByCategory(@Param("page") Page<PartJobVO> page,@Param("categoryId") String categoryId);
}

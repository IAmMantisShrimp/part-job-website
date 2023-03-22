package com.yue.mapper;

import com.yue.entity.PartJob;
import com.yue.entity.UserPartJobCollect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yue.entity.vo.PartJobVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 用户兼职收藏关系表 Mapper 接口
 * </p>
 *
 * @author liuy
 * @since 2022-03-23
 */
@Mapper
public interface UserPartJobCollectMapper extends BaseMapper<UserPartJobCollect> {

    List<PartJobVO> getCollect(String userId);

}

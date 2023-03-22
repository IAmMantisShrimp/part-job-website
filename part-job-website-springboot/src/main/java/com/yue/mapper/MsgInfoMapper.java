package com.yue.mapper;

import com.yue.entity.MsgInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 消息表 Mapper 接口
 * </p>
 *
 * @author liuy
 * @since 2022-03-28
 */
@Mapper
public interface MsgInfoMapper extends BaseMapper<MsgInfo> {
    List<MsgInfo> selectMsgInfoListByUserId(@Param("toUserId") String toUserId,@Param("fromUserId") String fromUserId);
    void updateMsgInfoRead(@Param("toUserId") String toUserId,@Param("fromUserId") String fromUserId);
}

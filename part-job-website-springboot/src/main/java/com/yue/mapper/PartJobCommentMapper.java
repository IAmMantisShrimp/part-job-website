package com.yue.mapper;

import com.yue.entity.PartJobComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yue.entity.vo.PartJobCommentVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 兼职评论表 Mapper 接口
 * </p>
 *
 * @author liuy
 * @since 2022-04-02
 */
@Mapper
public interface PartJobCommentMapper extends BaseMapper<PartJobComment> {
    PartJobCommentVO selectVoByCommentId(String commentId);
}

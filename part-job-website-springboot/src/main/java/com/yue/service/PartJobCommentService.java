package com.yue.service;

import com.yue.entity.PartJobComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yue.entity.vo.PartJobCommentVO;

import java.util.List;

/**
 * <p>
 * 兼职评论表 服务类
 * </p>
 *
 * @author liuy
 * @since 2022-04-02
 */
public interface PartJobCommentService extends IService<PartJobComment> {
    List<PartJobCommentVO> getCommentListByPartJobId(String partJobId);
    void addComment(PartJobComment partJobComment);
}

package com.yue.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yue.entity.PartJobComment;
import com.yue.entity.vo.PartJobCommentVO;
import com.yue.entity.vo.UserVO;
import com.yue.mapper.PartJobCommentMapper;
import com.yue.service.PartJobCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yue.service.PartJobService;
import com.yue.service.UserService;
import com.yue.utils.LocalDateTimeUtil;
import com.yue.utils.UUIDUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 兼职评论表 服务实现类
 * </p>
 *
 * @author liuy
 * @since 2022-04-02
 */
@Service
public class PartJobCommentServiceImpl extends ServiceImpl<PartJobCommentMapper, PartJobComment> implements PartJobCommentService {

    @Autowired
    private PartJobCommentMapper partJobCommentMapper;
    @Autowired
    private PartJobService partJobService;
    @Autowired
    private UserService userService;

    /**
     * 创建评论
     * @param partJobComment
     * @return
     */
    @Override
    public void addComment(PartJobComment partJobComment) {
        partJobComment.setPartJobCommentId(UUIDUtil.getUUID());
        partJobComment.setCreateTime(LocalDateTimeUtil.getCurrentLocalDateTime());
        partJobComment.setDelFlag(false);
        if(ObjectUtil.isEmpty(partJobComment.getParentId()) || StrUtil.isBlank(partJobComment.getParentId())){
            partJobComment.setLevel(1);
        }else{
            partJobComment.setLevel(2);
        }
        this.partJobService.addPartJobCommentCount(partJobComment.getPartJobId());
        this.partJobCommentMapper.insert(partJobComment);
    }

    /**
     * 根据兼职id获取所有评论
     * @param partJobId
     * @return
     */
    @Override
    public List<PartJobCommentVO> getCommentListByPartJobId(String partJobId) {
        QueryWrapper<PartJobComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("part_job_id", partJobId);
        queryWrapper.eq("level", 1);
        List<PartJobComment> partJobCommentList = this.partJobCommentMapper.selectList(queryWrapper);
        return copyList(partJobCommentList);
    }

    /**
     * 将评论对象转换为VO对象，方便页面显示
     * @param comment
     * @return
     */
    public PartJobCommentVO copy(PartJobComment comment){
        PartJobCommentVO commentVo = new PartJobCommentVO();
        BeanUtils.copyProperties(comment,commentVo);
        //时间格式化
        //commentVo.setCreateDate(new DateTime(comment.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        String authorId = comment.getUserId();
        UserVO userVo = userService.findUserVoById(authorId);
        commentVo.setAuthor(userVo);
        //评论的评论
        List<PartJobCommentVO> commentVoList = findCommentsByParentId(comment.getPartJobCommentId());
        commentVo.setChildComment(commentVoList);
        if (comment.getLevel() > 1) {
            UserVO toUserVo = userService.findUserVoById(comment.getToUid());
            commentVo.setToUser(toUserVo);
        }
        return commentVo;
    }

    /**
     * 寻找当前评论的子评论
     * @param id    //当前评论id，如果一条评论父id指向当前id
     * @return
     */
    private List<PartJobCommentVO> findCommentsByParentId(String id) {
        LambdaQueryWrapper<PartJobComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PartJobComment::getParentId,id);
        queryWrapper.eq(PartJobComment::getLevel,2);
        List<PartJobComment> comments = this.partJobCommentMapper.selectList(queryWrapper);
        return copyList(comments);
    }

    public List<PartJobCommentVO> copyList(List<PartJobComment> commentList){
        List<PartJobCommentVO> commentVoList = new ArrayList<>();
        for (PartJobComment comment : commentList) {
            commentVoList.add(copy(comment));
        }
        return commentVoList;
    }
}

package com.yue.controller;


import com.yue.common.Result;
import com.yue.entity.PartJobComment;
import com.yue.entity.vo.PartJobCommentVO;
import com.yue.service.PartJobCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 兼职评论表 前端控制器
 * </p>
 *
 * @author liuy
 * @since 2022-04-02
 */
@Slf4j
@RestController
@RequestMapping("/partJobComment")
public class PartJobCommentController {

    @Autowired
    PartJobCommentService partJobCommentService;

    @GetMapping("/listByPartJob/{partJobId}")
    public Result getCommentListByPartJobId(@PathVariable String partJobId){
        List<PartJobCommentVO> partJobComments = this.partJobCommentService.getCommentListByPartJobId(partJobId);
        return Result.succ("获取成功",partJobComments);
    }

    @GetMapping("/listByCommentId/{commentId}")
    public Result getCommentListByCommentId(@PathVariable String commentId){
        PartJobComment partJobComment = this.partJobCommentService.getById(commentId);
        return Result.succ("获取成功",partJobComment);
    }

    @PostMapping("/add")
    public Result addComment(@RequestBody PartJobComment partJobComment){
        this.partJobCommentService.addComment(partJobComment);
        return Result.succ("创建成功");
    }

    @DeleteMapping("/delete/{commentId}")
    public Result delComment(@PathVariable String commentId){
        this.partJobCommentService.removeById(commentId);
        return Result.succ("删除成功");
    }

}


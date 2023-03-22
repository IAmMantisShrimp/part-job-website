package com.yue.controller;


import com.yue.common.Result;
import com.yue.entity.SessionList;
import com.yue.service.SessionListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 会话列表 前端控制器
 * </p>
 *
 * @author liuy
 * @since 2022-03-28
 */
@Slf4j
@RestController
@RequestMapping("/sessionList")
public class SessionListController {

    @Autowired
    SessionListService sessionListService;

    /**
     * 创建会话对象，在用户点击其他用户头像开始私信时，创建该对象
     * @param fromUserId
     * @param toUserId
     * @return
     */
    @GetMapping("/add/{fromUserId}/{toUserId}")
    public Result createSessionList(@PathVariable String fromUserId,@PathVariable String toUserId){
        log.info("执行 SessionListController.createSessionList: fromUserId: " + fromUserId + "toUserId: " + toUserId);
        SessionList sessionList = sessionListService.createSessionList(fromUserId, toUserId);
        return Result.succ("创建成功",sessionList);
    }

    @GetMapping("/getHistory/{userId}")
    public Result getSessionListHistory(@PathVariable String userId){
        log.info("执行 SessionListController.getSessionListHistory: userId: " + userId);
        List<SessionList> sessionListHistory = this.sessionListService.getSessionListHistory(userId);
        return Result.succ("获取成功",sessionListHistory);
    }

    @DeleteMapping("/del/{sessionListId}")
    public Result delSessionList(@PathVariable String sessionListId){
        log.info("执行 SessionListController.delSessionList: sessionListId: " + sessionListId);
        this.sessionListService.removeById(sessionListId);
        return Result.succ("删除成功");
    }

    @GetMapping("/edit/{sessionListId}/{remark}")
    public Result editSessionListRemark(@PathVariable String sessionListId,@PathVariable String remark){
        log.info("执行 SessionListController.editSessionListRemark: sessionListId: " + sessionListId);
        this.sessionListService.editSessionListRemark(sessionListId,remark);
        return Result.succ("修改成功");
    }

}


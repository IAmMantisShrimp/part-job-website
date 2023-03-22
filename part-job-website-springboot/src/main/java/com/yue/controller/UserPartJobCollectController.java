package com.yue.controller;


import com.yue.common.Result;
import com.yue.entity.PartJob;
import com.yue.entity.vo.PartJobVO;
import com.yue.service.UserPartJobCollectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 用户兼职收藏关系表 前端控制器
 * </p>
 *
 * @author liuy
 * @since 2022-03-23
 */
@Slf4j
@RestController
@RequestMapping("/collect")
public class UserPartJobCollectController {

    @Autowired
    private UserPartJobCollectService userPartJobCollectService;

    @GetMapping("/list/{userId}")
    public Result listCollect(@PathVariable String userId) {
        log.info("执行 UserPartJobCollectController.listCollect: userId:"+userId);
        List<PartJobVO> partJobs = this.userPartJobCollectService.getCollect(userId);
        return Result.succ("获取成功",partJobs);
    }

    @GetMapping("/add/{userId}/{partJobId}")
    public Result addCollect(@PathVariable String userId,@PathVariable String partJobId){
        log.info("执行 UserPartJobCollectController.addCollect: userId"+userId+"partJobId: "+partJobId);
        this.userPartJobCollectService.addCollect(userId,partJobId);
        return Result.succ("收藏成功");
    }

    @DeleteMapping("/del/{userId}/{partJobId}")
    public Result deleteCollect(@PathVariable String userId,@PathVariable String partJobId){
        log.info("执行 UserPartJobCollectController.deleteCollect: userId"+userId+"partJobId: "+partJobId);
        this.userPartJobCollectService.deleteCollect(userId,partJobId);
        return Result.succ("取消收藏成功");
    }

    @GetMapping("/check/{userId}/{partJobId}")
    public Result checkIsCollected(@PathVariable String userId,@PathVariable String partJobId){
        log.info("执行 UserPartJobCollectController.checkIsCollected: userId"+userId+"partJobId: "+partJobId);
        boolean isCollected = this.userPartJobCollectService.checkIsCollected(userId, partJobId);
        return Result.succ("收藏成功",isCollected);
    }

}


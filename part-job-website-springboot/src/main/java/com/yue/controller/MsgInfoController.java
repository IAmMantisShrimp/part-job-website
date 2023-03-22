package com.yue.controller;


import com.yue.common.Result;
import com.yue.entity.MsgInfo;
import com.yue.service.MsgInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 消息表 前端控制器
 * </p>
 *
 * @author liuy
 * @since 2022-03-28
 */
@Slf4j
@RestController
@RequestMapping("/msgInfo")
public class MsgInfoController {

    @Autowired
    private MsgInfoService msgInfoService;

    @GetMapping("/list/{sessionListId}")
    public Result getMsgInfoList(@PathVariable String sessionListId){
        log.info("执行 MsgInfoController.getMsgInfoList: sessionListId: " + sessionListId);
        List<MsgInfo> msgInfos = this.msgInfoService.getMsgInfoList(sessionListId);
        return Result.succ("获取成功",msgInfos);
    }

}


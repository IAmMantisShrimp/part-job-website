package com.yue.service;

import com.yue.entity.MsgInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yue.entity.SessionList;

import java.util.List;

/**
 * <p>
 * 消息表 服务类
 * </p>
 *
 * @author liuy
 * @since 2022-03-28
 */
public interface MsgInfoService extends IService<MsgInfo> {
    MsgInfo createMsgInfoBymessage(String msgType, String message, SessionList sessionList);
    List<MsgInfo> getMsgInfoList(String sessionListId);
}

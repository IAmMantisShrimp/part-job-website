package com.yue.service;

import com.yue.entity.SessionList;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.websocket.Session;
import java.util.List;

/**
 * <p>
 * 会话列表 服务类
 * </p>
 *
 * @author liuy
 * @since 2022-03-28
 */
public interface SessionListService extends IService<SessionList> {
    SessionList createSessionList(String fromUserId,String toUserId);
    List<SessionList> getSessionListHistory(String userId);
    void addUnReadCount(String sessionListId);
    SessionList getSessionListByUser(String fromUserId,String toUserId);
    SessionList createReverseSessionList(SessionList sessionList,boolean isAddUnReadCount);
    void emptySessionListUnReadCount(SessionList sessionList);
    void editSessionListRemark(String sessionListId,String remark);
}

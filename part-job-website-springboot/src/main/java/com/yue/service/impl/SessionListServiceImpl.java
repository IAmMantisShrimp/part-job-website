package com.yue.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yue.entity.SessionList;
import com.yue.entity.User;
import com.yue.excetion.ExceptionEnum;
import com.yue.excetion.PartJobException;
import com.yue.mapper.SessionListMapper;
import com.yue.service.SessionListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yue.service.UserService;
import com.yue.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 会话列表 服务实现类
 * </p>
 *
 * @author liuy
 * @since 2022-03-28
 */
@Service
public class SessionListServiceImpl extends ServiceImpl<SessionListMapper, SessionList> implements SessionListService {

    @Autowired
    private SessionListMapper sessionListMapper;
    @Autowired
    private UserService userService;

    /**
     * 创建会话对象
     * @param fromUserId    发起会话的用户id
     * @param toUserId  目标用户的id
     * @return
     */
    @Override
    public SessionList createSessionList(String fromUserId, String toUserId) {
        //判断会话对象是否已经存在
        SessionList sessionListTemp = this.getSessionListByUser(fromUserId, toUserId);
        if(ObjectUtil.isNotEmpty(sessionListTemp)){
            return sessionListTemp;
        }
        User toUser = userService.getById(toUserId);
        if(ObjectUtil.isEmpty(toUser)){
            throw new PartJobException(ExceptionEnum.SESSION_LIST_CREATE_ERROR);
        }
        SessionList sessionList = new SessionList();
        sessionList.setId(UUIDUtil.getUUID());
        sessionList.setUserId(fromUserId);
        sessionList.setToUserId(toUserId);
        sessionList.setToUserAvatar(toUser.getAvatar());
        sessionList.setUnReadCount(0);
        sessionList.setListName(toUser.getNickname());

        sessionListMapper.insert(sessionList);

        return sessionList;
    }

    /**
     * 获取当前登录用户的历史会话
     * @param userId    当前登录的用户ID
     * @return
     */
    @Override
    public List<SessionList> getSessionListHistory(String userId) {
        QueryWrapper<SessionList> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<SessionList> sessionLists = this.sessionListMapper.selectList(queryWrapper);
        return sessionLists;
    }

    /**
     * 当发送消息时，接受消息的用户不在当前聊天窗口时，增加未读消息数
     * @param sessionListId
     */
    @Override
    public void addUnReadCount(String sessionListId) {
        SessionList sessionList = this.sessionListMapper.selectById(sessionListId);
        sessionList.setUnReadCount(sessionList.getUnReadCount() + 1);
        try {
            this.sessionListMapper.updateById(sessionList);
        }catch (PartJobException e){
            throw new PartJobException(ExceptionEnum.SESSION_LIST_EDIT_ERROR);
        }
    }

    /**
     * 查询符合条件的sessionList对象
     * @param fromUserId
     * @param toUserId
     * @return
     */
    @Override
    public SessionList getSessionListByUser(String fromUserId, String toUserId) {
        QueryWrapper<SessionList> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", fromUserId);
        queryWrapper.eq("to_user_id", toUserId);
        SessionList sessionList = this.sessionListMapper.selectOne(queryWrapper);
        return sessionList;
    }

    /**
     * 根据正向的SessionList 创建反向的SessionList
     * @param sessionList
     * @return
     */
    @Override
    public SessionList createReverseSessionList(SessionList sessionList,boolean isAddUnReadCount) {
        User user = userService.getById(sessionList.getUserId());

        SessionList tmpSessionList = new SessionList();
        tmpSessionList.setId(UUIDUtil.getUUID());
        tmpSessionList.setUserId(sessionList.getToUserId());
        tmpSessionList.setToUserId(sessionList.getUserId());
        tmpSessionList.setListName(user.getNickname());
        tmpSessionList.setToUserAvatar(user.getAvatar());
        if(isAddUnReadCount){
            tmpSessionList.setUnReadCount(1);
        }else{
            tmpSessionList.setUnReadCount(0);
        }

        this.sessionListMapper.insert(tmpSessionList);
        return tmpSessionList;
    }

    /**
     * 将sessionList的未读消息数置零
     * @param sessionList
     */
    @Override
    public void emptySessionListUnReadCount(SessionList sessionList) {
        sessionList.setUnReadCount(0);
        this.sessionListMapper.updateById(sessionList);
    }

    /**
     * 修改当前会话的备注
     * @param sessionListId
     * @param remark
     */
    @Override
    public void editSessionListRemark(String sessionListId, String remark) {
        SessionList sessionList = this.sessionListMapper.selectById(sessionListId);
        sessionList.setRemark(remark);
        this.sessionListMapper.updateById(sessionList);
    }

}

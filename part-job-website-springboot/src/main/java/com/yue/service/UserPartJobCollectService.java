package com.yue.service;

import com.yue.entity.PartJob;
import com.yue.entity.UserPartJobCollect;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yue.entity.vo.PartJobVO;

import java.util.List;

/**
 * <p>
 * 用户兼职收藏关系表 服务类
 * </p>
 *
 * @author liuy
 * @since 2022-03-23
 */
public interface UserPartJobCollectService extends IService<UserPartJobCollect> {
    List<PartJobVO> getCollect(String userId);
    void addCollect(String userId,String partJobId);
    void deleteCollect(String userId,String partJobId);
    boolean checkIsCollected(String userId,String partJobId);
}

package com.yue.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yue.entity.PartJob;
import com.yue.entity.UserPartJobCollect;
import com.yue.entity.vo.PartJobVO;
import com.yue.excetion.ExceptionEnum;
import com.yue.excetion.PartJobException;
import com.yue.mapper.UserPartJobCollectMapper;
import com.yue.service.UserPartJobCollectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yue.utils.LocalDateTimeUtil;
import com.yue.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 用户兼职收藏关系表 服务实现类
 * </p>
 *
 * @author liuy
 * @since 2022-03-23
 */
@Service
public class UserPartJobCollectServiceImpl extends ServiceImpl<UserPartJobCollectMapper, UserPartJobCollect> implements UserPartJobCollectService {

    @Autowired
    UserPartJobCollectMapper userPartJobCollectMapper;

    /**
     * 获取当前用户的收藏列表
     * @param userId
     * @return
     */
    @Override
    public List<PartJobVO> getCollect(String userId) {
        List<PartJobVO> partJobs = this.userPartJobCollectMapper.getCollect(userId);
        if (null == partJobs || partJobs.size() == 0){
            throw new PartJobException(ExceptionEnum.GET_COLLECT_NOT_FOUND);
        }
        return partJobs;
    }

    /**
     * 添加收藏
     * @param userId
     * @param partJobId
     */
    @Override
    public void addCollect(String userId, String partJobId) {
        //判断是否已经被收藏
        QueryWrapper<UserPartJobCollect> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId)
                .eq("part_job_id", partJobId);
        UserPartJobCollect collectTemp = this.userPartJobCollectMapper.selectOne(wrapper);
        if(ObjectUtil.isNotEmpty(collectTemp)){
            throw new PartJobException(ExceptionEnum.SAVE_COLLECT_REUSE);
        }
        //没有收藏则开始添加
        UserPartJobCollect collect = new UserPartJobCollect();
        collect.setUserId(userId);
        collect.setPartJobId(partJobId);
        collect.setUserPartJobCollectId(UUIDUtil.getUUID());
        collect.setCollectTime(LocalDateTimeUtil.getCurrentLocalDateTime());
        this.userPartJobCollectMapper.insert(collect);
    }

    /**
     * 取消收藏
     * @param userId
     * @param partJobId
     */
    @Override
    public void deleteCollect(String userId, String partJobId) {
        QueryWrapper<UserPartJobCollect> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId)
                .eq("part_job_id", partJobId);
        int count = this.userPartJobCollectMapper.delete(wrapper);
        if(count != 1){
            throw new PartJobException(ExceptionEnum.DELETE_COLLECT_ERROR);
        }
    }

    /**
     * 判断当前兼职是否被登录用户收藏
     * @param userId
     * @param partJobId
     * @return
     */
    @Override
    public boolean checkIsCollected(String userId, String partJobId) {
        QueryWrapper<UserPartJobCollect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("part_job_id", partJobId);

        UserPartJobCollect userPartJobCollect = this.userPartJobCollectMapper.selectOne(queryWrapper);
        if(ObjectUtil.isEmpty(userPartJobCollect)){
            return false;
        }
        return true;
    }
}

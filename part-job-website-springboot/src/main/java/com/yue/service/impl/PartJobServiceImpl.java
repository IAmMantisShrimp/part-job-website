package com.yue.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yue.entity.vo.PartJobVO;
import com.yue.entity.PartJob;
import com.yue.entity.User;
import com.yue.excetion.ExceptionEnum;
import com.yue.excetion.PartJobException;
import com.yue.mapper.PartJobMapper;
import com.yue.service.PartJobService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yue.service.UserService;
import com.yue.utils.LocalDateTimeUtil;
import com.yue.utils.UUIDUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 兼职信息表 服务实现类
 * </p>
 *
 * @author liuy
 * @since 2022-03-23
 */
@Service
public class PartJobServiceImpl extends ServiceImpl<PartJobMapper, PartJob> implements PartJobService {

    @Autowired
    PartJobMapper partJobMapper;
    @Autowired
    UserService userService;

    /**
     * 保存或者修改兼职
     * @param partJob
     */
    @Override
    public void saveOrEditPartJob(PartJob partJob) {
        //获取当前登录的用户信息
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        User user = userService.getUserByUsername(username);
        //判断是新增还是修改
        if(StrUtil.isBlank(partJob.getPartJobId())){
            partJob.setUserId(user.getUserId());
            partJob.setPartJobId(UUIDUtil.getUUID());
            partJob.setDelFlag(false);
            partJob.setCommentCount(0);
            partJob.setCreateTime(LocalDateTimeUtil.getCurrentLocalDateTime());
        }else{
            //确保当前修改这篇兼职的人是创建者
            if(!user.getUserId().equals(partJob.getUserId())){
                throw new PartJobException(ExceptionEnum.PASSWORD_NOT_EXISTED);
            }
        }
        try{
            this.saveOrUpdate(partJob);
        }catch (Exception e){
            e.printStackTrace();
            throw new PartJobException(ExceptionEnum.PART_JOB_EDIT_ERROR);
        }
    }

    /**
     * 根据关键词搜索兼职（title和description对应即可）
     * @param word
     * @return
     */
    @Override
    public Page<PartJobVO> searchPartJobByWord(Page<PartJobVO> page,String word) {
        return this.partJobMapper.searchPartJobByWord(page,word);
    }

    @Override
    public Page<PartJobVO> searchPartJobVO(Page<PartJobVO> page) {
        return this.partJobMapper.searchPartJob(page);
    }

    @Override
    public Page<PartJobVO> searchPartJobByCategory(Page<PartJobVO> page, String categoryId) {
        return this.partJobMapper.searchPartJobByCategory(page,categoryId);
    }

    /**
     * 将兼职的评论id加一
     * @param partJobId
     */
    @Override
    public void addPartJobCommentCount(String partJobId) {
        PartJob partJob = this.partJobMapper.selectById(partJobId);
        if(ObjectUtil.isEmpty(partJob.getCommentCount())){
            partJob.setCommentCount(0);
        }
        partJob.setCommentCount(partJob.getCommentCount()+1);
        this.partJobMapper.updateById(partJob);
    }


}

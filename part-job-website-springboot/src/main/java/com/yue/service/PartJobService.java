package com.yue.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yue.entity.vo.PartJobVO;
import com.yue.entity.PartJob;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 兼职信息表 服务类
 * </p>
 *
 * @author liuy
 * @since 2022-03-23
 */
public interface PartJobService extends IService<PartJob> {
    void saveOrEditPartJob(PartJob partJob);
    Page<PartJobVO> searchPartJobByWord(Page<PartJobVO> page, String word);
    Page<PartJobVO> searchPartJobVO(Page<PartJobVO> page);
    Page<PartJobVO> searchPartJobByCategory(Page<PartJobVO> page,String categoryId);
    void addPartJobCommentCount(String partJobId);
}

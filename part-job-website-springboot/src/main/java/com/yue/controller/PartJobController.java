package com.yue.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yue.common.Result;
import com.yue.entity.vo.PartJobVO;
import com.yue.entity.PartJob;
import com.yue.entity.PartJobCategory;
import com.yue.excetion.ExceptionEnum;
import com.yue.excetion.PartJobException;
import com.yue.service.PartJobCategoryService;
import com.yue.service.PartJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 兼职信息表 前端控制器
 * </p>
 *
 * @author liuy
 * @since 2022-03-23
 */
@Slf4j
@RestController
@RequestMapping("/partJob")
public class PartJobController {
    @Autowired
    private PartJobService partJobService;
    @Autowired
    private PartJobCategoryService partJobCategoryService;

    @GetMapping("/list/{currentPage}")
    public Result listPartJob(@PathVariable Integer currentPage){
        log.info("执行 PartJobController.listPartJob: "+currentPage);
        Page page = new Page(currentPage,5);
        //IPage pageData = partJobService.page(page, new QueryWrapper<PartJob>().orderByDesc("create_time"));
        IPage pageData = partJobService.searchPartJobVO(page);
        return Result.succ(pageData);
    }

    @PostMapping("/edit")
    public Result editPartJob(@RequestBody PartJob partJob){
        log.info("执行 PartJobController.editPartJob: "+partJob);
        this.partJobService.saveOrEditPartJob(partJob);
        return Result.succ("操作成功");
    }

    @GetMapping("/category")
    public Result getPartJobCategory(){
        log.info("执行 PartJobController.getPartJobCategory: ");
        List<PartJobCategory> categories = this.partJobCategoryService.list();
        if(null == categories || categories.size() == 0){
            throw new PartJobException(ExceptionEnum.GET_CATEGORY_NOT_FOUND);
        }
        return Result.succ("获取成功",categories);
    }

    @GetMapping("/{partJobId}")
    public Result getPartJob(@PathVariable String partJobId){
        log.info("执行 PartJobController.getPartJob: "+partJobId);
        PartJob partJob = this.partJobService.getById(partJobId);
        PartJobVO partJobVO = new PartJobVO();
        BeanUtil.copyProperties(partJob,partJobVO);
        partJobVO.setCategoryName(partJobCategoryService.getById(partJob.getCategoryId()).getCategoryName());
        return Result.succ("获取成功",partJobVO);
    }

    @GetMapping("/search/{keyword}/{currentPage}")
    public Result searchPartJob(@PathVariable Integer currentPage,@PathVariable String keyword){
        log.info("执行 PartJobController.searchPartJob: "+keyword);
        Page page = new Page(currentPage,5);
        IPage pageData = this.partJobService.searchPartJobByWord(page,keyword);
        if (pageData.getTotal() == 0){
            throw new PartJobException(ExceptionEnum.PART_JOB_SEARCH_ERROR);
        }
        return Result.succ("获取成功",pageData);
    }

    @GetMapping("/searchByCategory/{categoryId}/{currentPage}")
    public Result searchPartJobByCategory(@PathVariable String categoryId,@PathVariable Integer currentPage){
        log.info("执行 PartJobController.searchPartJobByCategory: categoryId"+categoryId+"currentPage: "+currentPage);
        Page page = new Page(currentPage,5);
        IPage pageData = this.partJobService.searchPartJobByCategory(page,categoryId);
        if (pageData.getTotal() == 0){
            throw new PartJobException(ExceptionEnum.PART_JOB_CATEGORY_ERROR);
        }
        return Result.succ("获取成功",pageData);
    }

    @DeleteMapping("/{partJobId}")
    public Result deletePartJob(@PathVariable String partJobId){
        log.info("执行 PartJobController.deletePartJob: "+partJobId);
        boolean b = this.partJobService.removeById(partJobId);
        return Result.succ("删除成功");
    }

}


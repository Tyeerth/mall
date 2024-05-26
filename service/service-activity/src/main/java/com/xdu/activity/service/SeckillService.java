package com.xdu.activity.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xdu.model.activity.Seckill;
import com.xdu.vo.activity.SeckillQueryVo;

public interface SeckillService extends IService<Seckill> {

    IPage<Seckill> selectPage(Page<Seckill> pageParam, SeckillQueryVo seckillQueryVo);

    void updateStatus(Long id, Integer status);
}
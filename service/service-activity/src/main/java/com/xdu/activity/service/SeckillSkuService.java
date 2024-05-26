package com.xdu.activity.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xdu.model.activity.SeckillSku;
import com.xdu.vo.activity.SeckillSkuQueryVo;
import com.xdu.vo.activity.SeckillSkuVo;
import com.xdu.vo.product.SkuStockLockVo;

import java.util.List;

public interface SeckillSkuService extends IService<SeckillSku> {

    IPage<SeckillSku> selectPage(Page<SeckillSku> pageParam, SeckillSkuQueryVo seckillSkuQueryVo);

    void save(List<SeckillSku> seckillSkuList);

    /**
     * 将某日的秒杀列表加载到缓存
     * @param date
     */
    void saveSeckillSkuListToCache(String date);

    /**
     * 从缓存中读取秒杀sku信息
     * @param timeName 场次名称
     */
    List<SeckillSkuVo> findSeckillSkuListFromCache(String timeName);

    /**
     * 根据skuId获取秒杀sku信息
     * @param skuId
     * @return
     */
    SeckillSkuVo getSeckillSkuVo(Long skuId);

    Boolean checkAndMinusStock(List<SkuStockLockVo> skuStockLockVoList, String orderToken);

    void rollBackStock(String orderNo);
}
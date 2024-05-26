package com.xdu.activity.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xdu.model.activity.CouponInfo;
import com.xdu.model.order.CartInfo;
import com.xdu.vo.activity.CouponRuleVo;

import java.util.List;
import java.util.Map;

public interface CouponInfoService extends IService<CouponInfo> {
    //更新优惠卷使用状态
    void updateCouponInfoUseStatus(Long couponId, Long userId, Long orderId);
    //获取购物车对应优惠卷
    CouponInfo findRangeSkuIdList(List<CartInfo> cartInfoList, Long couponId);
    //3 获取购物车可以使用优惠卷列表
    List<CouponInfo> findCartCouponInfo(List<CartInfo> cartInfoList, Long userId);
    //优惠卷分页查询
    IPage<CouponInfo> selectPage(Page<CouponInfo> pageParam);

    //根据id获取优惠券
    CouponInfo getCouponInfo(String id);

    //根据优惠卷id获取优惠券规则列表
    Map<String, Object> findCouponRuleList(Long couponId);

    //新增优惠券规则
    void saveCouponRule(CouponRuleVo couponRuleVo);

    //根据关键字获取sku列表，活动使用
    List<CouponInfo> findCouponByKeyword(String keyword);
    //2 根据skuId+userId查询优惠卷信息
    List<CouponInfo> findCouponInfoList(Long skuId, Long userId);

}
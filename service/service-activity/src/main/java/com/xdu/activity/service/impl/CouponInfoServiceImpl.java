package com.xdu.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xdu.activity.mapper.CouponInfoMapper;
import com.xdu.activity.mapper.CouponRangeMapper;
import com.xdu.activity.mapper.CouponUseMapper;
import com.xdu.activity.service.CouponInfoService;
import com.xdu.client.product.SkuFeignClient;
import com.xdu.enums.CouponRangeType;
import com.xdu.enums.CouponStatus;
import com.xdu.model.activity.CouponInfo;
import com.xdu.model.activity.CouponRange;
import com.xdu.model.activity.CouponUse;
import com.xdu.model.order.CartInfo;
import com.xdu.model.product.SkuInfo;
import com.xdu.vo.activity.CouponRuleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CouponInfoServiceImpl extends ServiceImpl<CouponInfoMapper, CouponInfo>
        implements CouponInfoService {

    @Autowired
    private CouponInfoMapper couponInfoMapper;

    @Autowired
    private CouponRangeMapper couponRangeMapper;

    @Autowired
    private CouponUseMapper couponUseMapper;

    @Autowired
    private SkuFeignClient productFeignClient;

    //更新优惠卷使用状态
    @Override
    public void updateCouponInfoUseStatus(Long couponId, Long userId, Long orderId) {
        //根据couponId查询优惠卷信息
        CouponUse couponUse = couponUseMapper.selectOne(
                new LambdaQueryWrapper<CouponUse>()
                        .eq(CouponUse::getCouponId, couponId)
                        .eq(CouponUse::getUserId, userId)
                        .eq(CouponUse::getOrderId, orderId)
        );

        //设置修改值
        couponUse.setCouponStatus(CouponStatus.USED);

        //调用方法修改
        couponUseMapper.updateById(couponUse);
    }
    //获取购物车对应优惠卷
    @Override
    public CouponInfo findRangeSkuIdList(List<CartInfo> cartInfoList,
                                         Long couponId) {
        //根据优惠卷id基本信息查询
        CouponInfo couponInfo = baseMapper.selectById(couponId);
        if(couponInfo == null) {
            return null;
        }
        //根据couponId查询对应CouponRange数据
        List<CouponRange> couponRangeList = couponRangeMapper.selectList(
                new LambdaQueryWrapper<CouponRange>()
                        .eq(CouponRange::getCouponId, couponId)
        );
        //对应sku信息
        Map<Long, List<Long>> couponIdToSkuIdMap = this.findCouponIdToSkuIdMap(cartInfoList, couponRangeList);
        //遍历map，得到value值，封装到couponInfo对象
        List<Long> skuIdList =
                couponIdToSkuIdMap.entrySet().iterator().next().getValue();
        couponInfo.setSkuIdList(skuIdList);
        return couponInfo;
    }

    //2 根据skuId+userId查询优惠卷信息
    @Override
    public List<CouponInfo> findCouponInfoList(Long skuId, Long userId) {
        //远程调用：根据skuId获取skuInfo
        SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);

        //根据条件查询：skuId + 分类id + userId
        List<CouponInfo> couponInfoList = baseMapper.selectCouponInfoList(skuInfo.getId(),
                skuInfo.getCategoryId(),userId);

        return couponInfoList;
    }

    @Override
    public List<CouponInfo> findCartCouponInfo(List<CartInfo> cartInfoList, Long userId) {
        return null;
    }

    //4 获取优惠卷id 对应skuId列表
    //优惠卷id进行分组，得到map集合
    private Map<Long, List<Long>> findCouponIdToSkuIdMap(List<CartInfo> cartInfoList,
                                                         List<CouponRange> couponRangeList) {
        Map<Long, List<Long>> couponIdToSkuIdMap = new HashMap<>();

        //couponRangeList数据处理，根据优惠卷id分组
        Map<Long, List<CouponRange>> couponRangeToRangeListMap = couponRangeList.stream()
                .collect(
                        Collectors.groupingBy(couponRange -> couponRange.getCouponId())
                );

        //遍历map集合
        Iterator<Map.Entry<Long, List<CouponRange>>> iterator =
                couponRangeToRangeListMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Long, List<CouponRange>> entry = iterator.next();
            Long couponId = entry.getKey();
            List<CouponRange> rangeList = entry.getValue();

            //创建集合 set
            Set<Long> skuIdSet = new HashSet<>();
            for (CartInfo cartInfo:cartInfoList) {
                for (CouponRange couponRange:rangeList) {
                    //判断
                    if(couponRange.getRangeType() == CouponRangeType.SKU
                            && couponRange.getRangeId().longValue() == cartInfo.getSkuId().longValue()) {
                        skuIdSet.add(cartInfo.getSkuId());
                    } else if(couponRange.getRangeType() == CouponRangeType.CATEGORY
                            && couponRange.getRangeId().longValue() == cartInfo.getCategoryId().longValue()) {
                        skuIdSet.add(cartInfo.getSkuId());
                    } else {

                    }
                }
            }
            couponIdToSkuIdMap.put(couponId,new ArrayList<>(skuIdSet));
        }
        return couponIdToSkuIdMap;
    }
    //优惠卷分页查询
    @Override
    public IPage selectPage(Page<CouponInfo> pageParam) {
        //  构造排序条件
        QueryWrapper<CouponInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        IPage<CouponInfo> page = couponInfoMapper.selectPage(pageParam, queryWrapper);
        page.getRecords().stream().forEach(item -> {
            item.setCouponTypeString(item.getCouponType().getComment());
            if(null != item.getRangeType()) {
                item.setRangeTypeString(item.getRangeType().getComment());
            }
        });
        //  返回数据集合
        return page;
    }

    //根据id获取优惠券
    @Override
    public CouponInfo getCouponInfo(String id) {
        CouponInfo couponInfo = this.getById(id);
        couponInfo.setCouponTypeString(couponInfo.getCouponType().getComment());
        if(null != couponInfo.getRangeType()) {
            couponInfo.setRangeTypeString(couponInfo.getRangeType().getComment());
        }
        return couponInfo;
    }

    //根据优惠卷id获取优惠券规则列表
    @Override
    public Map<String, Object> findCouponRuleList(Long couponId) {
        Map<String, Object> result = new HashMap<>();
//        CouponInfo couponInfo = this.getById(couponId);
//
//        QueryWrapper couponRangeQueryWrapper = new QueryWrapper<CouponRange>();
//        couponRangeQueryWrapper.eq("coupon_id",couponId);
//        List<CouponRange> activitySkuList = couponRangeMapper.selectList(couponRangeQueryWrapper);
//
//        List<Long> rangeIdList = activitySkuList.stream().map(CouponRange::getRangeId).collect(Collectors.toList());
//
//        if(!CollectionUtils.isEmpty(rangeIdList)) {
//            if(couponInfo.getRangeType() == CouponRangeType.SKU) {
//                List<SkuInfo> skuInfoList = productFeignClient.findSkuInfoList(rangeIdList);
//                result.put("skuInfoList", skuInfoList);
//
//            } else if (couponInfo.getRangeType() == CouponRangeType.CATEGORY) {
//                List<Category> categoryList = productFeignClient.findCategoryList(rangeIdList);
//                result.put("categoryList", categoryList);
//
//            } else {
//                //通用
//            }
//        }
        return result;
    }

    //新增优惠券规则
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveCouponRule(CouponRuleVo couponRuleVo) {
        /*
        优惠券couponInfo 与 couponRange 要一起操作：先删除couponRange ，更新couponInfo ，再新增couponRange ！
         */
        QueryWrapper<CouponRange> couponRangeQueryWrapper = new QueryWrapper<>();
        couponRangeQueryWrapper.eq("coupon_id",couponRuleVo.getCouponId());
        couponRangeMapper.delete(couponRangeQueryWrapper);

        //  更新数据
        CouponInfo couponInfo = this.getById(couponRuleVo.getCouponId());
        // couponInfo.setCouponType();
        couponInfo.setRangeType(couponRuleVo.getRangeType());
        couponInfo.setConditionAmount(couponRuleVo.getConditionAmount());
        couponInfo.setAmount(couponRuleVo.getAmount());
        couponInfo.setConditionAmount(couponRuleVo.getConditionAmount());
        couponInfo.setRangeDesc(couponRuleVo.getRangeDesc());

        couponInfoMapper.updateById(couponInfo);

        //  插入优惠券的规则 couponRangeList
        List<CouponRange> couponRangeList = couponRuleVo.getCouponRangeList();
        for (CouponRange couponRange : couponRangeList) {
            couponRange.setCouponId(couponRuleVo.getCouponId());
            //  插入数据
            couponRangeMapper.insert(couponRange);
        }
    }

    //根据关键字获取sku列表，活动使用
    @Override
    public List<CouponInfo> findCouponByKeyword(String keyword) {
        //  模糊查询
        QueryWrapper<CouponInfo> couponInfoQueryWrapper = new QueryWrapper<>();
        couponInfoQueryWrapper.like("coupon_name",keyword);
        return couponInfoMapper.selectList(couponInfoQueryWrapper);
    }
}
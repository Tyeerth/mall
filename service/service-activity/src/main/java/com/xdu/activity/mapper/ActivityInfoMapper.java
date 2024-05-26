package com.xdu.activity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xdu.model.activity.ActivityInfo;
import com.xdu.model.activity.ActivityRule;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityInfoMapper extends BaseMapper<ActivityInfo> {

    List<Long> selectExistSkuIdList(@Param("skuIdList")List<Long> skuIdList);

    List<ActivityRule> selectActivityRuleList(@Param("skuId")Long skuId);

    List<ActivityRule> findActivityRule(Long skuId);
}
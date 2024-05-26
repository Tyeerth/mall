package com.xdu.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xdu.activity.mapper.SeckillMapper;
import com.xdu.activity.service.SeckillService;
import com.xdu.model.activity.Seckill;
import com.xdu.vo.activity.SeckillQueryVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class SeckillServiceImpl extends ServiceImpl<SeckillMapper, Seckill> implements SeckillService {

	@Resource
	private SeckillMapper seckillMapper;

	@Override
	public IPage<Seckill> selectPage(Page<Seckill> pageParam, SeckillQueryVo seckillQueryVo) {
		Integer status = seckillQueryVo.getStatus();
		String title = seckillQueryVo.getTitle();
		LambdaQueryWrapper<Seckill> wrapper = new LambdaQueryWrapper<>();
		if(!StringUtils.isEmpty(String.valueOf(status))) {
			wrapper.eq(Seckill::getStatus,status);
		}
		if(!StringUtils.isEmpty(title)) {
			wrapper.like(Seckill::getTitle,title);
		}
		IPage<Seckill> seckillPage = baseMapper.selectPage(pageParam, wrapper);
		return seckillPage;
	}

    @Override
	public void updateStatus(Long id, Integer status) {
		Seckill seckill = new Seckill();
		seckill.setStatus(status);
		seckill.setId(id);
		this.updateById(seckill);
	}

}
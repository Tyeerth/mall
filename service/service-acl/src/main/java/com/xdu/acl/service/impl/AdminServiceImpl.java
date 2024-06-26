package com.xdu.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xdu.acl.mapper.AdminMapper;
import com.xdu.acl.service.AdminService;
import com.xdu.acl.service.RoleService;
import com.xdu.model.acl.Admin;
import com.xdu.vo.acl.AdminQueryVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户角色服务实现类
 * </p>
 */
@Service
@Transactional(  )
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
								implements AdminService {

	@Autowired
	private AdminMapper userMapper;

	@Autowired
	private RoleService roleService;

	@Override
	public IPage<Admin> selectPage(Page<Admin> pageParam, AdminQueryVo userQueryVo) {
		//获取用户名称条件值
		String name = userQueryVo.getName();
		//创建条件构造器
		LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
		if(!StringUtils.isEmpty(name)) {
			//封装条件
			wrapper.like(Admin::getName,name);
		}
		//调用mapper方法
		IPage<Admin> pageModel = baseMapper.selectPage(pageParam,wrapper);
		return pageModel;
	}
}
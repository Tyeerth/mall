package com.xdu.acl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xdu.acl.mapper.AdminRoleMapper;
import com.xdu.acl.service.AdminRoleService;
import com.xdu.model.acl.AdminRole;
import org.springframework.stereotype.Service;

/**
 * 用户角色服务实现类
 */
@Service
public class AdminRoleServiceImpl extends ServiceImpl<AdminRoleMapper, AdminRole>
										implements AdminRoleService {

}
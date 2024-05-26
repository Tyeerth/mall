package com.xdu.acl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xdu.model.acl.Permission;
import org.springframework.stereotype.Repository;

/**
 * 菜单Mpper接口
 */
@Repository
public interface PermissionMapper extends BaseMapper<Permission> {

}
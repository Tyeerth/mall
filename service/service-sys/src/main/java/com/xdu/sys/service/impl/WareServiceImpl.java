package com.xdu.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xdu.model.sys.Ware;
import com.xdu.sys.mapper.WareMapper;
import com.xdu.sys.service.WareService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 仓库表 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2023-04-03
 */
@Service
public class WareServiceImpl extends ServiceImpl<WareMapper, Ware> implements WareService {

}

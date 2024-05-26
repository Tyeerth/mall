package com.xdu.sys.controller;


import com.xdu.common.result.Result;
import com.xdu.model.sys.Ware;
import com.xdu.sys.service.RegionWareService;
import com.xdu.sys.service.WareService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 仓库表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-04-03
 */
@RestController
@RequestMapping("/admin/sys/ware")
//@CrossOrigin
public class WareController {

    @Autowired
    private WareService wareService;

    @Autowired
    private RegionWareService regionWareService;
    //查询所有仓库列表
//    url: `${api_name}/findAllList`,
//    method: 'get'
    @ApiOperation("查询所有仓库列表")
    @GetMapping("findAllList")
    public Result findAllList() {
        List<Ware> list = wareService.list();
        Deque<Integer> path = new LinkedList<>();
        return Result.ok(list);
    }
    @ApiOperation(value = "取消开通区域")
    @PostMapping("updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable Long id,@PathVariable Integer status) {
        regionWareService.updateStatus(id, status);
        return Result.ok();
    }
}


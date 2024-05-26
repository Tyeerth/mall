package com.xdu.product.controller;

import com.xdu.model.product.Category;
import com.xdu.model.product.SkuInfo;
import com.xdu.product.service.CategoryService;
import com.xdu.product.service.SkuInfoService;
import com.xdu.vo.product.SkuStockLockVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 营销活动管理接口
 */
@RestController
@RequestMapping("api/product")
public class ProductInnnerController {
    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private CategoryService categoryService;
//    @ApiOperation(value = "获取分类信息")
//    @GetMapping("inner/findAllCategoryList")
//    public List<Category> findAllCategoryList() {
//        return categoryService.findAllList();
//    }
//
//    @ApiOperation(value = "获取新人专享")
//    @GetMapping("inner/findNewPersonSkuInfoList")
//    public List<SkuInfo> findNewPersonSkuInfoList() {
//        return skuInfoService.findNewPersonList();
//    }

    @ApiOperation(value = "批量获取sku信息")
    @PostMapping("inner/findSkuInfoList")
    public List<SkuInfo> findSkuInfoList(@RequestBody List<Long> skuIdList) {
        return skuInfoService.findSkuInfoList(skuIdList);
    }

    @ApiOperation(value = "根据关键字获取sku列表")
    @GetMapping("inner/findSkuInfoByKeyword/{keyword}")
    public List<SkuInfo> findSkuInfoByKeyword(@PathVariable("keyword") String keyword) {
        return skuInfoService.findSkuInfoByKeyword(keyword);
    }
    @ApiOperation(value = "批量获取分类信息")
    @PostMapping("inner/findCategoryList")
    public List<Category> findCategoryList(@RequestBody List<Long> categoryIdList) {
        return categoryService.findCategoryList(categoryIdList);
    }
    @ApiOperation(value = "锁定库存")
    @PostMapping("inner/checkAndLock/{orderNo}")
    public Boolean checkAndLock(@RequestBody List<SkuStockLockVo> skuStockLockVoList, @PathVariable String orderNo) {
        return skuInfoService.checkAndLock(skuStockLockVoList, orderNo);
    }
}

package com.xdu.service.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xdu.common.auth.AuthContextHolder;
import com.xdu.common.result.Result;
import com.xdu.model.order.OrderInfo;
import com.xdu.service.order.service.OrderInfoService;
import com.xdu.vo.order.OrderSubmitVo;
import com.xdu.vo.order.OrderUserQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Api(value = "Order管理", tags = "Order管理")
@RestController
@RequestMapping(value="/api/order")
public class OrderApiController {
	
	@Resource
	private OrderInfoService orderService;

	@ApiOperation("确认订单")
	@GetMapping("auth/confirmOrder")
	public Result confirm() {
		return Result.ok(orderService.confirmOrder());
	}

	@ApiOperation("生成订单")
	@PostMapping("auth/submitOrder")
	public Result submitOrder(@RequestBody OrderSubmitVo orderParamVo, HttpServletRequest request) {
		// 获取到用户Id
		Long userId = AuthContextHolder.getUserId();
		return Result.ok(orderService.submitOrder(orderParamVo));
	}

	@ApiOperation("获取订单详情")
	@GetMapping("auth/getOrderInfoById/{orderId}")
	public Result getOrderInfoById(@PathVariable("orderId") Long orderId){
		return Result.ok(orderService.getOrderInfoById(orderId));
	}
	@ApiOperation(value = "获取用户订单分页列表")
	@GetMapping("auth/findUserOrderPage/{page}/{limit}")
	public Result findUserOrderPage(
			@ApiParam(name = "page", value = "当前页码", required = true)
			@PathVariable Long page,

			@ApiParam(name = "limit", value = "每页记录数", required = true)
			@PathVariable Long limit,

			@ApiParam(name = "orderVo", value = "查询对象", required = false)
			OrderUserQueryVo orderUserQueryVo) {
		Long userId = AuthContextHolder.getUserId();
		orderUserQueryVo.setUserId(userId);
		Page<OrderInfo> pageParam = new Page<>(page, limit);
		IPage<OrderInfo> pageModel = orderService.findUserOrderPage(pageParam, orderUserQueryVo);
		return Result.ok(pageModel);
	}
}
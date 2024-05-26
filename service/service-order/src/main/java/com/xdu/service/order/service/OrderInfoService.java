package com.xdu.service.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xdu.model.order.OrderInfo;
import com.xdu.vo.order.OrderConfirmVo;
import com.xdu.vo.order.OrderSubmitVo;
import com.xdu.vo.order.OrderUserQueryVo;

public interface OrderInfoService extends IService<OrderInfo> {
    /**
     * 确认订单
     */
    OrderConfirmVo confirmOrder();

    //生成订单
    Long submitOrder(OrderSubmitVo orderParamVo);

    //订单详情
    OrderInfo getOrderInfoById(Long orderId);

    void orderPay(String orderNo);

    IPage<OrderInfo> findUserOrderPage(Page<OrderInfo> pageParam, OrderUserQueryVo orderUserQueryVo);
}
package com.xdu.cart.receiver;

import com.rabbitmq.client.Channel;
import com.xdu.cart.service.CartInfoService;
import com.xdu.rabbitmq.constant.MqConst;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CartReceiver {

    //TODO 监听消息RabbitMQ
    @Autowired
    private CartInfoService cartInfoService;

    /**
     * 删除购物车选项
     * @param userId
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConst.QUEUE_DELETE_CART,durable = "true"),
            exchange = @Exchange(value = MqConst.EXCHANGE_ORDER_DIRECT),
            key = {MqConst.ROUTING_DELETE_CART}
    ))
    public void deleteCart(Long userId, Message message, Channel channel) throws IOException {
        if(userId != null) {
            cartInfoService.deleteCartChecked(userId);
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
}

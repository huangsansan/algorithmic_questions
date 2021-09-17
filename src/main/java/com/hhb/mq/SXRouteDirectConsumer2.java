package com.hhb.mq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @Author: huangsan
 * @Date: 2021/9/16 下午5:45
 */
public class SXRouteDirectConsumer2 {
    public static void main(String[] args) throws IOException {
        Connection connect = RabbitUtils.getConnect();

        Channel channel = connect.createChannel();

        channel.basicConsume("dingshi_zhen", true, new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("延时队列：" + new String(body));
                System.out.println("消息过期了：" + new Date());
            }
        });
    }
}

package com.hhb.mq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Date;

/**
 * @description:
 * @Author: huangsan
 * @Date: 2021/9/16 下午5:23
 */
public class SXRouteDirectProvider {
    public static void main(String[] args) throws IOException {

        Connection connect = RabbitUtils.getConnect();

        Channel channel = connect.createChannel();


        String msg = "这时一个定时程序，我想定时10秒之后发送";

        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .deliveryMode(2)
                .contentEncoding("UTF-8")
                .expiration("10000")
                .build();

        channel.basicPublish("dingshi", "route.dlx", properties, msg.getBytes());

        System.out.println("消息有效期10秒：" + new Date());
        RabbitUtils.closeConnect(channel, connect);
    }
}

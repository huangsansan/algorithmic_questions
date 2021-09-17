package com.hhb.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * 发布-订阅模式 fanout 扇出
 */
public class mqFanoutProvider {
    public static void main(String[] args) throws IOException {
        Connection connect = RabbitUtils.getConnect();
        Channel channel = connect.createChannel();

        //创建路由
        channel.exchangeDeclare("exA", "fanout", true);

        //send
        channel.basicPublish("exA", "", null, "hello fanout".getBytes());
    }
}

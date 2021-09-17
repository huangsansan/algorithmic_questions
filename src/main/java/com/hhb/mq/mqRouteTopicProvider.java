package com.hhb.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @description:
 * @Author: huangsan
 * @Date: 2021/9/15 下午5:42
 */
public class mqRouteTopicProvider {
    public static void main(String[] args) throws IOException {
        Connection connect = RabbitUtils.getConnect();

        Channel channel = connect.createChannel();

        channel.exchangeDeclare("exC", "topic", true);

        //发送消息
        channel.basicPublish("exC", "info_d.123*.qqwe", null, "hello rabbit for hhb ".getBytes());

        RabbitUtils.closeConnect(channel, connect);
    }
}

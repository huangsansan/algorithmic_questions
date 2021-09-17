package com.hhb.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @description:
 * @Author: huangsan
 * @Date: 2021/9/15 下午5:26
 */
public class mqRouteDirectProvider {

    public static void main(String[] args) throws IOException {
        Connection connect = RabbitUtils.getConnect();

        Channel channel = connect.createChannel();

        channel.exchangeDeclare("exB", "direct", true);

        //发送消息
        for (int i = 0; i < 10; i++) {
            channel.basicPublish("exB", "info_c", null, ("hello rabbit for hhb " + i).getBytes());
        }
        RabbitUtils.closeConnect(channel, connect);
    }
}

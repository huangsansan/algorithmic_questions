package com.hhb.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

/**
 * 消息生产者
 */
public class mqProvider {
    public static void main(String[] args) throws IOException {

        Connection connect = RabbitUtils.getConnect();

        Channel channel = connect.createChannel();

        //创建队列
//        channel.queueDeclare("xxx", true, false, false, null);

        //发送消息
        for (int i = 0; i < 10; i++) {
            channel.basicPublish("", "xxx", null, ("hello rabbit for hhb " + i).getBytes());
        }
        RabbitUtils.closeConnect(channel, connect);
    }
}

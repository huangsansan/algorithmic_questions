package com.hhb.mq;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @description:
 * @Author: huangsan
 * @Date: 2021/9/15 下午5:26
 */
public class mqRouteDirectConsumer {
    public static void main(String[] args) throws IOException {
        Connection connect = RabbitUtils.getConnect();

        Channel channel = connect.createChannel();

        String queue = channel.queueDeclare().getQueue();

        channel.queueBind(queue, "exB", "info_b");

        channel.basicConsume(queue, true, new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                System.out.println("我消费了info_b:" + new String(body));
            }
        });
    }
}

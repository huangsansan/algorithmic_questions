package com.hhb.mq;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @description:
 * @Author: huangsan
 * @Date: 2021/9/15 上午11:12
 */
public class mqConsumer2 {

    public static void main(String[] args) throws IOException {
        Connection connect = RabbitUtils.getConnect();

        Channel channel = connect.createChannel();

//        channel.queueDeclare("xxx", false, false, false, null);

        channel.basicQos(1);
        channel.basicConsume("xxx", false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者2消费了消息：" + new String(body));
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }
}

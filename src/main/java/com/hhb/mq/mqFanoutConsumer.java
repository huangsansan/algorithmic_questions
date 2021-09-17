package com.hhb.mq;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @description:
 * @Author: huangsan
 * @Date: 2021/9/15 下午3:19
 */
public class mqFanoutConsumer {
    public static void main(String[] args) throws IOException {
        Connection connect = RabbitUtils.getConnect();
        Channel channel = connect.createChannel();

        //当我们不向queueDeclare()提供参数时， 我们会创建一个具有生成名称的非持久、独占、自动删除队列
        String queue = channel.queueDeclare().getQueue();

        channel.queueBind(queue, "exA", "");

        channel.basicConsume(queue, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1消费了消息：" + new String(body));
//                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }
}

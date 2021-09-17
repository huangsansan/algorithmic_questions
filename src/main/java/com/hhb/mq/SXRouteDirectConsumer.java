package com.hhb.mq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @Author: huangsan
 * @Date: 2021/9/16 下午5:23
 */
public class SXRouteDirectConsumer {

    public static void main(String[] args) throws IOException {
        Connection connect = RabbitUtils.getConnect();

        Channel channel = connect.createChannel();

        //给死信队列执行一个正常执行的交换
        Map<String, Object> agruments = new HashMap<String, Object>();
        agruments.put("x-dead-letter-exchange", "dlx.dingshi");
        channel.queueDeclare("dingshi", true, false, false, agruments);
        channel.queueBind("dingshi","dingshi","route.dlx");

        //创建一个真正去执行任务的队列
        channel.exchangeDeclare("dlx.dingshi", "direct", true, false, null);
        channel.queueDeclare("dingshi_zhen", true, false, false, null);
        channel.queueBind("dingshi_zhen", "dlx.dingshi", "route.dlx");


        channel.basicConsume("dingshi", true, new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("我是用来测试正常消费的，其实我不应该存在。：" + new String(body));
            }
        });
    }
}

package com.hhb.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @description:
 * @Author: huangsan
 * @Date: 2021/9/15 上午10:43
 */
public class RabbitUtils {


    public static ConnectionFactory factory;

    static {
        factory = new ConnectionFactory();
        factory.setHost("172.17.90.4");
        factory.setPort(5672);
        factory.setVirtualHost("/ems");
        factory.setUsername("hhb");
        factory.setPassword("123");
    }

    public static Connection getConnect() {
        try {
            Connection connection = factory.newConnection();
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void closeConnect(Channel channel, Connection connection) {
        try {
            if (channel != null) channel.close();
            if (connection != null) connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

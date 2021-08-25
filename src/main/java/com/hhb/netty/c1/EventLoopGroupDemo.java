package com.hhb.netty.c1;

import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @description: EventLoopGroup详解
 * @Author: huangsan
 * @Date: 2021/8/25 下午5:37
 */
@Slf4j
public class EventLoopGroupDemo {
    public static void main(String[] args) {

        NioEventLoopGroup group = new NioEventLoopGroup(2);
        log.info("{}", group.next().parent());
        log.info("{}","执行普通任务");
        group.next().submit(() -> {
            System.out.println(111);
        });
        log.info("{}","执行定时任务,5秒开始，1秒一次");
        group.next().scheduleAtFixedRate(() -> {
            System.out.println(222);
        }, 5,1, TimeUnit.SECONDS);
    }
}

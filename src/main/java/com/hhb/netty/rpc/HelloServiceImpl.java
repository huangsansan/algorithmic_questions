package com.hhb.netty.rpc;

/**
 * @description:
 * @Author: huangsan
 * @Date: 2021/8/30 下午9:18
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String helloWord(String name) {
        return "Hello ：" + name;
    }
}

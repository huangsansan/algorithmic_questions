package com.hhb.proxy;

/**
 * @description:
 * @Author: huangsan
 * @Date: 2021/8/31 上午11:22
 */
public class UserServiceImpl implements UserService {
    @Override
    public void update() {
        System.out.println("do update is ok");
    }

    @Override
    public void select() {
        System.out.println("do select is ok");
    }
}

package com.hhb.proxy;

/**
 * JAVA静态代理 实现UserService
 * 静态代理在不改变目标对象的前提下对目标进行了功能扩展。
 * 缺点：静态代理实现了目标对象的全部方法，如果目标增加方法那么静态代理和目标都需要增加方法，增加工作。
 */
public class UserServiceProxy implements UserService {

    private UserService userService;

    public UserServiceProxy(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void update() {
        System.out.println("------start proxy update------");
        userService.update();
        System.out.println("------end proxy update------");
    }

    @Override
    public void select() {
        System.out.println("------start proxy select------");
        userService.select();
        System.out.println("------end proxy select------");
    }
}

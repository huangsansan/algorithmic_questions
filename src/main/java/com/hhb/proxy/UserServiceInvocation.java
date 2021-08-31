package com.hhb.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 *
 */
public class UserServiceInvocation implements InvocationHandler {

    private Object target;

    public UserServiceInvocation(UserService userService) {
        this.target = userService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("------start 动态代理增强------");
        Object mess = method.invoke(target, args);
        System.out.println("------end 动态代理增强------");
        return mess;
    }
}

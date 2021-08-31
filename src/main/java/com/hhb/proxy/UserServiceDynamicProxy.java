package com.hhb.proxy;

import java.lang.reflect.Proxy;

/**
 * JDK动态代理
 * 优点：1、proxy对象不需要实现对象。2、proxy使用jdk自带的proxy实现
 */
public class UserServiceDynamicProxy {
    private Object target;
    private UserServiceInvocation userServiceInvocation;

    public UserServiceDynamicProxy(Object target, UserServiceInvocation userServiceInvocation) {
        this.target = target;
        this.userServiceInvocation = userServiceInvocation;
    }

    public Object getPersonProxy() {
        Object o = Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), userServiceInvocation);
        return o;
    }
}

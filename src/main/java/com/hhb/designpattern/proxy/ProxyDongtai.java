package com.hhb.designpattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyDongtai implements InvocationHandler {

    private ProxyStudent student;

    public ProxyDongtai(ProxyStudent student) {
        this.student = student;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("动态代理执行start");
        method.invoke(student, args);
        System.out.println("动态代理执行end");
        return null;
    }
}

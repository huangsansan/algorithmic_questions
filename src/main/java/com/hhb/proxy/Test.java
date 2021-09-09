package com.hhb.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @description:
 * @Author: huangsan
 * @Date: 2021/8/31 上午11:23
 */
public class Test {
    public static void main(String[] args) {

        UserService service = new UserServiceImpl();
        System.out.println("遍历1");
        System.out.println(Arrays.toString(service.getClass().getInterfaces()));
        System.out.println(service.getClass().getInterfaces().length);
        System.out.println("遍历2 为空 不符合");
        System.out.println(Arrays.toString(UserService.class.getInterfaces()));
        System.out.println("遍历2");
        System.out.println(Arrays.toString(new Class[]{UserService.class}));
//        UserServiceProxy proxy = new UserServiceProxy(service);
//        proxy.select();

        System.out.println("方法1");
        UserServiceInvocation invocation = new UserServiceInvocation(service);
        UserService proxy = (UserService) new UserServiceDynamicProxy(service, invocation).getPersonProxy();
        proxy.select();
        System.out.println();
        System.out.println("方法2");
        UserService proxy2 = (UserService) Proxy.newProxyInstance(service.getClass().getClassLoader(), service.getClass().getInterfaces(), invocation);
        proxy2.select();
        System.out.println();
        System.out.println("方法3");
        UserService proxy3 = (UserService) Proxy.newProxyInstance(service.getClass().getClassLoader(), service.getClass().getInterfaces(), (loader, method, arg) -> {
            Object mess;
            if (method.getName().equals("update")) {
                System.out.println("------start 动态代理增强------");
                mess = method.invoke(service, arg);
                System.out.println("------end 动态代理增强------");
            } else {
                System.out.println("------start 动态代理增强2------");
                mess = method.invoke(service, arg);
                System.out.println("------end 动态代理增强2------");
            }
            return mess;
        });
        proxy3.update();
        proxy3.select();

    }
}

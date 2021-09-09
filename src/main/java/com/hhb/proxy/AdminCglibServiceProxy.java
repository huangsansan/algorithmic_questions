package com.hhb.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @description:
 * @Author: huangsan
 * @Date: 2021/8/31 下午5:04
 */
public class AdminCglibServiceProxy implements MethodInterceptor {

    private Object object;

    public AdminCglibServiceProxy(Object object) {
        this.object = object;
    }

    public Object getProxyInstance() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(object.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        System.out.println("cglib start----");
        Object invoke = method.invoke(object);
        System.out.println("cglib end----");
        return invoke;
    }
}

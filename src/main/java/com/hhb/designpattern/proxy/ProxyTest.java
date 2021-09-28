package com.hhb.designpattern.proxy;

import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.Proxy;

public class ProxyTest {
    public static void main(String[] args) {
//        jingtaidaili();
//        dongtaidaili();
//        cglib();
    }

    //cglib代理，注意代理的是ProxyStudentImpl类，而不是接口
    private static void cglib() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ProxyStudentImpl.class);
        enhancer.setCallback(new ProxyCglib());
        ProxyStudent proxyStudent = (ProxyStudent) enhancer.create();
        proxyStudent.doSome();
    }

    //动态代理
    private static void dongtaidaili() {
        ProxyStudent student = new ProxyStudentImpl();
        ProxyStudent proxyStudent = (ProxyStudent) Proxy.newProxyInstance(
                ProxyStudent.class.getClassLoader(),
                student.getClass().getInterfaces(),
                new ProxyDongtai(student));
        proxyStudent.doSome();
    }


    //静态代理
    private static void jingtaidaili() {
        ProxyJingtai jingtai = new ProxyJingtai(new ProxyStudentImpl());
        jingtai.doSome();
    }
}

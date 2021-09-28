package com.hhb.designpattern.proxy;

public class ProxyJingtai implements ProxyStudent {

    private ProxyStudent student;

    public ProxyJingtai(ProxyStudent student) {
        this.student = student;
    }

    @Override
    public void doSome() {
        System.out.println("静态代理执行start");
        student.doSome();
        System.out.println("静态代理执行end");
    }
}

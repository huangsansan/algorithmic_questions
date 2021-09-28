package com.hhb.designpattern.proxy;

public class ProxyStudentImpl implements ProxyStudent {

    @Override
    public void doSome() {
        System.out.println("我执行了学生！！！");
    }
}

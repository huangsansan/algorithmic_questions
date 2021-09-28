package com.hhb.designpattern.decorator;

public class GoodStudentImpl implements Student {
    @Override
    public void doSome() {
        System.out.println("我是一个好学生");
    }
}

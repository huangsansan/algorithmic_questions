package com.hhb.designpattern.decorator;

public class BedStudentImpl implements Student {
    @Override
    public void doSome() {
        System.out.println("我是一个坏学生");
    }
}

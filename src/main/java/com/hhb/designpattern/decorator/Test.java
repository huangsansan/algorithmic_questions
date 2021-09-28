package com.hhb.designpattern.decorator;

public class Test {
    public static void main(String[] args) {
        Student student = new BedStudentImpl();
        student.doSome();


        StudentDecorator decorator = new StudentDecoratorImpl(new BedStudentImpl());
        decorator.doSome();

        StudentDecorator decorator2 = new StudentDecoratorImpl(new GoodStudentImpl());
        decorator2.doSome();


    }
}

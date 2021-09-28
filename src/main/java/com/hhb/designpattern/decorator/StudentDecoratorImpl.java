package com.hhb.designpattern.decorator;

public class StudentDecoratorImpl extends StudentDecorator {

    public StudentDecoratorImpl(Student student) {
        super(student);
    }

    @Override
    public void doSome() {
        super.doSome();
        doOtherSome();
    }

    private void doOtherSome() {
        System.out.println("我干了点别的事情");
    }
}

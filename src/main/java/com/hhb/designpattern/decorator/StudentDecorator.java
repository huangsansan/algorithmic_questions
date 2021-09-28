package com.hhb.designpattern.decorator;

public abstract class StudentDecorator implements Student {

    protected Student student;

    public StudentDecorator(Student student) {
        this.student = student;
    }

    @Override
    public void doSome() {
        student.doSome();
    }
}

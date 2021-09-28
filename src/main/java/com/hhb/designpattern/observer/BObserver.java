package com.hhb.designpattern.observer;

public class BObserver extends Observer {

    public BObserver(Subject subject) {
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    public void update() {
        System.out.println(this.subject.getStatus());
        System.out.println("i am b , num*100=" + this.subject.getStatus() * 100);
    }
}

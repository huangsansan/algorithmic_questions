package com.hhb.designpattern.observer;

public class CObserver extends Observer {

    public CObserver(Subject subject) {
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    public void update() {
        System.out.println(this.subject.getStatus());
        System.out.println("i am c , num*1000=" + this.subject.getStatus() * 1000);
    }
}

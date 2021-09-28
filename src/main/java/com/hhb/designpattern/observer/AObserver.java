package com.hhb.designpattern.observer;

public class AObserver extends Observer {

    public AObserver(Subject subject) {
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    public void update() {
        System.out.println(this.subject.getStatus());
        System.out.println("i am a , num*10=" + this.subject.getStatus() * 10);
    }
}

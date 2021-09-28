package com.hhb.designpattern.observer;

public class Test {
    public static void main(String[] args) {
        Subject subject = new Subject();

        AObserver observer = new AObserver(subject);
        BObserver bObserver = new BObserver(subject);
        CObserver cObserver = new CObserver(subject);

        subject.setStatus(3);
    }
}

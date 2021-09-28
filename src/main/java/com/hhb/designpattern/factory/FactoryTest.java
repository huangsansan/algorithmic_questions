package com.hhb.designpattern.factory;

public class FactoryTest {
    public static void main(String[] args) {

        Human h1 = new HumanFactory().getInstance("");
        h1.doSome();
        Human h2 = new HumanFactory().getInstance("男的");
        h2.doSome();
    }
}

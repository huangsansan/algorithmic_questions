package com.hhb.designpattern.factory;

public class HumanFactory {

    public Human getInstance(String name) {

        switch (name) {
            case "男的":
                return new Man();
            default:
                return new Woman();
        }
    }
}

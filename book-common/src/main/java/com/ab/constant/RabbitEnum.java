package com.ab.constant;

public enum RabbitEnum {
    ACTIVITYQUEUE("activity.queue"),
    ACTIVITYEXCHANGE("activity.exchange"),
    ACTIVITYKEY("red");

    public String getName() {
        return name;
    }

    RabbitEnum(String name) {
        this.name = name;
    }

    private final String name;
}

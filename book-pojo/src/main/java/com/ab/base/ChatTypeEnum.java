package com.ab.base;

/**
 * @author cattleYuan
 * @date 2024/1/29
 */
public enum ChatTypeEnum {
    GroupChat("groupchat"),
    SingleChat("singlechat");
    public String getName() {
        return name;
    }

    private final String name;

    ChatTypeEnum(String name) {
        this.name = name;
    }
}

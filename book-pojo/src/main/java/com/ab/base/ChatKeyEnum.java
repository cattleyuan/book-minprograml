package com.ab.base;

import lombok.Data;

/**
 * @author cattleYuan
 * @date 2024/1/29
 */

public enum ChatKeyEnum {
    SINGLECHATBASEKEY("singlechat::");

    private final String name;

    public String getName() {
        return name;
    }

    ChatKeyEnum(String name) {
        this.name = name;
    }
}

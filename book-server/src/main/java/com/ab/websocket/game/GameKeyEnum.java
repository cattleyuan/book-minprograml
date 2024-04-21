package com.ab.websocket.game;

public enum GameKeyEnum {
    ADDTIME("addtime:"),CUTTIME("cuttime:"),INITIALIZE("playerinfo:");

    private final String name;

    public String getName() {
        return name;
    }

    GameKeyEnum(String name) {
        this.name = name;
    }
}

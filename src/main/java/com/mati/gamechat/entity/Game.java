package com.mati.gamechat.entity;

public enum Game {
    LOL("LoL");

    private final String displayValue;

    Game(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}

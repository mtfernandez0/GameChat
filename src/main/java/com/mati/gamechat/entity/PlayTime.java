package com.mati.gamechat.entity;

public enum PlayTime {
    MORNING("Morning"),
    AFTERNOON("Afternoon"),
    EVENING("Evening"),
    ANYTIME("Anytime");

    private final String displayPlayTime;

    PlayTime(String displayPlayTime) {
        this.displayPlayTime = displayPlayTime;
    }

    public String getDisplayPlayTime() {
        return displayPlayTime;
    }
}

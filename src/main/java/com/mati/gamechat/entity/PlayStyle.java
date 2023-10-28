package com.mati.gamechat.entity;

public enum PlayStyle {
    CHILLING("Chilling"),
    GAMING("Gaming"),
    CASUAL("Casual"),
    RANKED("Ranked");

    private final String displayPlayStyle;

    PlayStyle(String displayPlayStyle) {
        this.displayPlayStyle = displayPlayStyle;
    }

    public String getDisplayPlayStyle() {
        return displayPlayStyle;
    }
}

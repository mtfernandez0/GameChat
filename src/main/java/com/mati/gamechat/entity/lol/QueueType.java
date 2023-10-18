package com.mati.gamechat.entity.lol;

public enum QueueType {
    RANKED_SOLO_5x5("Ranked Solo 5x5"),
    RANKED_FLEX_SR("Ranked Flex SR"),
    RANKED_FLEX_TT("Ranked Flex TT");

    private final String queueType;

    QueueType(String queueType) {
        this.queueType = queueType;
    }

    public String getQueueType() {
        return queueType;
    }

    public static QueueType findCorrectOne(String str) throws IllegalArgumentException{
        return QueueType.valueOf(str);
    }
}

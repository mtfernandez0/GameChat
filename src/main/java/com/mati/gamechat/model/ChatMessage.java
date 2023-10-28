package com.mati.gamechat.model;

import lombok.*;

import java.time.LocalTime;

@NoArgsConstructor @AllArgsConstructor @Getter
@Setter
@Builder
public class ChatMessage {

    private String content;

    private String sender;

    private LocalTime localTime;

    private MessageType messageType;

    @Override
    public String toString() {
        return "ChatMessage{" +
                "content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                ", localTime=" + localTime +
                ", messageType=" + messageType +
                '}';
    }
}
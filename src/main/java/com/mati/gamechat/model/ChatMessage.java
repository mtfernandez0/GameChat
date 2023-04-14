package com.mati.gamechat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class ChatMessage {
    private Long id;
    private Long IdFrom;
    private String message;
}

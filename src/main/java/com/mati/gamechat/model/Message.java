package com.mati.gamechat.model;

import lombok.*;

@Data
@AllArgsConstructor @NoArgsConstructor
public class Message {
    private Long idReceiver;
    private Long idFrom;
    private String message;
}

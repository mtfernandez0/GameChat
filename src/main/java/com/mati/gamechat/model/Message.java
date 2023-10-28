package com.mati.gamechat.model;

import lombok.*;


@AllArgsConstructor @NoArgsConstructor @Getter @Setter
@Builder
public class Message {
    private Long from;
    private String text;
}

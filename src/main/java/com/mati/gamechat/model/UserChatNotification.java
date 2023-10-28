package com.mati.gamechat.model;

import lombok.*;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter
@Builder
public class UserChatNotification {

    private String username;

    private Long groupId;

    private String group_owner;

    private Integer group_size;

    private MessageType messageType;
}

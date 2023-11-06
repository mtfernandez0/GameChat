package com.mati.gamechat.dto;

import com.mati.gamechat.entity.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GroupDto {

    private Long id;

    private PlayTime playTime;

    private Game game;

    private PlayStyle playStyle;

    private UserDto owner;
}

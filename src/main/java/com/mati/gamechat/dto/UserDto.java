package com.mati.gamechat.dto;

import com.mati.gamechat.entity.Group;
import com.mati.gamechat.entity.User;
import lombok.*;

import java.util.List;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
@Builder
public class UserDto {
    private String username;
    private List<Group> groupsInvolved;

    public UserDto userToUserDto(User user){
        return UserDto.builder()
                .username(user.getUsername())
                .groupsInvolved(user.getGroupsInvolved())
                .build();
    }
}

package com.mati.gamechat.dto;

import com.mati.gamechat.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class UserDto {
    private String username;
    private String password;
    private String email;

    public User userDtoToUser(){
        return User.builder()
                .username(username)
                .password(password)
                .email(email).build();
    }
}

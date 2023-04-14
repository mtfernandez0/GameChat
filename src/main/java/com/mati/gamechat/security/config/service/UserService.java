package com.mati.gamechat.security.config.service;

import com.mati.gamechat.dto.UserDto;
import com.mati.gamechat.entity.User;

public interface UserService {
    User save(UserDto dto) throws Exception;
}

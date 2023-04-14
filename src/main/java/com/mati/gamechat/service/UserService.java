package com.mati.gamechat.service;

import com.mati.gamechat.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User findById(Long receiverId) throws Exception;

    Optional<User> findByUsername(String username);

    List<User> findAll();

    User save(User user);

    void deleteById(Long id);

}

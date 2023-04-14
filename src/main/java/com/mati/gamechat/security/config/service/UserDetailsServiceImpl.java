package com.mati.gamechat.security.config.service;

import com.mati.gamechat.dto.UserDto;
import com.mati.gamechat.entity.User;
import com.mati.gamechat.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService, UserService {
    private final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    private final PasswordEncoderServiceImpl passwordEncoder;
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(PasswordEncoderServiceImpl passwordEncoder,
                                  UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password"));

        logger.info("User {} loaded!", username);

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>()
        );
    }

    @Override
    public User save(UserDto dto) throws Exception{

        if (userRepository.findByUsername(dto.getUsername()).isPresent())
            throw new Exception("Username already exists!");

        if (userRepository.findByEmail(dto.getEmail()).isPresent())
            throw new Exception("Email already exists!");

        User user = dto.userDtoToUser();

        user.setPassword(passwordEncoder.passwordEncoder().encode(user.getPassword()));

        userRepository.save(user);

        logger.info("User {} registered successfully!", user.getUsername());

        return user;
    }
}
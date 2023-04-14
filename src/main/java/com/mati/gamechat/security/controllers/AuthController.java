package com.mati.gamechat.security.controllers;

import com.mati.gamechat.dto.UserDto;
import com.mati.gamechat.security.config.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String register() { return "register"; }

    @PostMapping("/register")
    public String validate(@ModelAttribute UserDto dto) throws Exception {
        userService.save(dto);

        return "redirect:/login";
    }
}
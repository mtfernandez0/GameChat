package com.mati.gamechat.controller;

import com.mati.gamechat.entity.User;
import com.mati.gamechat.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@CrossOrigin
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = {"", "/"})
    public String index(Model model, Principal principal){
        User user = null;

        if (principal != null) user = userService.findByUsername(principal.getName());

        model.addAttribute("user", user);
        return "index";
    }

    @GetMapping("/chat")
    public String chat(Model model){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if (username.equals("anonymousUser")){
            model.addAttribute("authenticated", false);
        } else {
            model.addAttribute("authenticated", true);
            model.addAttribute("username", username);
        }
        return "chat";
    }
}

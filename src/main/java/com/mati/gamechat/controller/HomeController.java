package com.mati.gamechat.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mati.gamechat.dto.UserDto;
import com.mati.gamechat.entity.Game;
import com.mati.gamechat.entity.User;
import com.mati.gamechat.service.GroupService;
import com.mati.gamechat.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    private final UserService userService;
    private final GroupService groupService;

    public HomeController(UserService userService,
                          GroupService groupService) {
        this.userService = userService;
        this.groupService = groupService;
    }

    @GetMapping("/")
    public String index(Model model,
                        Principal principal) throws JsonProcessingException {
        User user = userService.findByUsername(principal.getName());

        ObjectMapper mapper = new ObjectMapper();

        String userDto = mapper.writeValueAsString(new UserDto().userToUserDto(user));

        model.addAttribute("user", user);
        model.addAttribute("userDto", userDto);
        model.addAttribute("games", Game.values());
        model.addAttribute("groups", groupService.findAll());
        return "index";
    }
}

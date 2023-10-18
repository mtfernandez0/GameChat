package com.mati.gamechat.security.controller;

import com.mati.gamechat.entity.User;
import com.mati.gamechat.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String login(HttpSession session,
                        Model model) {
        if (session.getAttribute("username") != null){
            model.addAttribute("username", session.getAttribute("username"));
            session.removeAttribute("username");
        }
        return "auth/login";
    }

    @GetMapping("/register")
    public String register(@ModelAttribute("user") User user) { return "auth/register"; }

    @PostMapping("/register")
    public String validate(@Valid @ModelAttribute("user") User user,
                           BindingResult result,
                           Model model){

        userService.checkCredentialsRegistration(user, result);

        if (result.hasErrors()){
            model.addAttribute("user", user);
            return "auth/register";
        }

        userService.register(user);

        return "redirect:/login";
    }
}
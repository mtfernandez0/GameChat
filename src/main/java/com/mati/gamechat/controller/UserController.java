package com.mati.gamechat.controller;

import com.mati.gamechat.entity.Friend;
import com.mati.gamechat.entity.User;
import com.mati.gamechat.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/fetchAll")
    @ResponseBody
    public List<Long> fetchAll(){
        List<Long> ids = new ArrayList<>();
        List<User> users = userService.findAll();
        for (User u: users)
            ids.add(u.getId());

        return ids;
    }

    @PostMapping("/addUser")
    public String addUser(@RequestBody User user){
        userService.save(user);
        return "chat";
    }

    @PostMapping("/sendFriendRequest")
    @ResponseBody
    public String sendFriendRequest(@ModelAttribute Friend friend) throws Exception {
//        User userDB = userService.findByNameAndRegion(friend.getName(), friend.getRegion());

//        System.out.println(userDB);

        return "success";
    }
}

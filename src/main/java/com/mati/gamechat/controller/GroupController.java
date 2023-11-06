package com.mati.gamechat.controller;

import com.mati.gamechat.entity.*;
import com.mati.gamechat.service.GroupService;
import com.mati.gamechat.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Objects;

@Controller
@RequestMapping("/group")
public class GroupController {

    private final GroupService groupService;
    private final UserService userService;

    @Value("${maxGroupsNumberPerUser}")
    private int max_groups_number;

    public GroupController(GroupService groupService,
                           UserService userService) {
        this.groupService = groupService;
        this.userService = userService;
    }

    @GetMapping("/{groupId}")
    public String groupChat(@PathVariable Long groupId,
                       Model model,
                       Principal principal){

        Group group = groupService.findById(groupId);

        if (group == null) return "redirect:/";

        model.addAttribute("group", group);
        model.addAttribute("user", userService.findByUsername(principal.getName()));

        return "chat/index";
    }

    @GetMapping("/create")
    public String newGroup(@ModelAttribute("group") Group group,
                           Model model,
                           Principal principal){

        User user = userService.findByUsername(principal.getName());

        if (user.getGroupsCreated().size() == max_groups_number) return "redirect:/";

        if (Objects.isNull(user.getLolStats())) return "redirect:/account";

        model.addAttribute("games", Game.values());
        model.addAttribute("playtimes", PlayTime.values());
        model.addAttribute("playstyles", PlayStyle.values());

        return "group/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("group") Group group,
                         BindingResult result,
                         Model model,
                         Principal principal){

        if (result.hasErrors()){
            model.addAttribute("games", Game.values());
            model.addAttribute("playTime", PlayTime.values());
            model.addAttribute("playstyles", PlayStyle.values());
            return "group/create";
        }

        User user = userService.findByUsername(principal.getName());

        if (Objects.isNull(user.getLolStats())) return "redirect:/account";

        if (user.getGroupsCreated().size() == max_groups_number) return "redirect:/";

        group.setOwner(user);

        group.setParticipants(new ArrayList<>());
        group.getParticipants().add(user);

        groupService.save(group);

        return "redirect:/";
    }
}

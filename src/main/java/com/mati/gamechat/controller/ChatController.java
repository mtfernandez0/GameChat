package com.mati.gamechat.controller;

import com.mati.gamechat.entity.Group;
import com.mati.gamechat.entity.User;
import com.mati.gamechat.model.ChatMessage;
import com.mati.gamechat.model.MessageType;
import com.mati.gamechat.model.UserChatNotification;
import com.mati.gamechat.service.GroupService;
import com.mati.gamechat.service.UserService;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.LocalTime;

@Controller
@Log4j2
public class ChatController {

    private final GroupService groupService;
    private final UserService userService;
    private final Object lock = new Object();

    public ChatController(GroupService groupService,
                          UserService userService) {
        this.groupService = groupService;
        this.userService = userService;
    }

    @MessageMapping("/chat.sendMessage/{groupId}")
    @SendTo("/topic/public/{groupId}")
    public ChatMessage sendChat(@Payload ChatMessage message) {

        message.setLocalTime(LocalTime.now());
        log.info("[{}]: {}", message.getSender(), message.getContent());
        return message;
    }

    @MessageMapping("/chat.addUser/{groupId}")
    @SendTo("/topic/public/{groupId}")
    public ChatMessage addUser(@Payload ChatMessage message){

        message.setLocalTime(LocalTime.now());
        log.info("User {} added!", message.getSender());
        return message;
    }

    @Transactional
    @MessageMapping("/chat.join/{groupId}")
    @SendTo("/topic/public")
    public UserChatNotification joinChat(@DestinationVariable Long groupId,
                                         Principal principal) {

        User user = userService.findByUsername(principal.getName());
        Group group = groupService.findById(groupId);
        MessageType messageType = MessageType.FULL;

        if (group == null)
            throw new IllegalArgumentException("The group with id " + groupId + " does not exist");

        synchronized (lock){
            if (group.getParticipants().contains(user))
                throw new IllegalArgumentException("You already belong to this group");

            if (group.getParticipants().size() < 5){

                messageType = MessageType.JOIN;
                group.getParticipants().add(user);
                groupService.save(group);
            }
        }

        return UserChatNotification.builder()
                .username(user.getUsername())
                .groupId(groupId)
                .messageType(messageType)
                .group_size(group.getParticipants().size())
                .group_owner(group.getOwner().getUsername())
                .build();
    }

    @Transactional
    @MessageMapping("/chat.leave/{groupId}")
    @SendTo("/topic/public")
    public UserChatNotification leaveChat(@DestinationVariable Long groupId,
                                          Principal principal){

        User user = userService.findByUsername(principal.getName());
        Group group = groupService.findById(groupId);

        if (group == null)
            throw new IllegalArgumentException("The group with id " + groupId + " does not exist");

        synchronized (lock){
            if (!user.getGroupsInvolved().contains(group))
                throw new IllegalArgumentException("You don't belong to this group");

            if (group.getOwner().equals(user))
                throw new IllegalArgumentException("You are the owner of the group");

            group.getParticipants().remove(user);
            groupService.save(group);
        }

        return UserChatNotification.builder()
                .username(user.getUsername())
                .groupId(groupId)
                .messageType(MessageType.LEAVE)
                .group_size(group.getParticipants().size())
                .group_owner(group.getOwner().getUsername())
                .build();
    }
}

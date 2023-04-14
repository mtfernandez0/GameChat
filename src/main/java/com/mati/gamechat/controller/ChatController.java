package com.mati.gamechat.controller;

import com.mati.gamechat.model.ChatMessage;
import com.mati.gamechat.model.Message;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@Log4j2
public class ChatController {
    @MessageMapping("/chat/{username}")
    @SendTo("/user/{username}")
    public ChatMessage sendMessage(@DestinationVariable String username, Message message){
        log.info("Sending message to " + username);
        return new ChatMessage(
                message.getIdReceiver(),
                message.getIdFrom(),
                message.getMessage()
        );
    }
}

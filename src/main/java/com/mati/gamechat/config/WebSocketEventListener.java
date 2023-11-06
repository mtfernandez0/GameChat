package com.mati.gamechat.config;

import com.mati.gamechat.model.ChatMessage;
import com.mati.gamechat.model.MessageType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.time.LocalTime;
import java.util.Objects;

/*@Component
@RequiredArgsConstructor*/
public class WebSocketEventListener {

/*    private final SimpMessageSendingOperations messageTemplate;*/

/*    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event){

        String username = Objects.requireNonNull(event.getUser()).getName();

        if (username != null){
            ChatMessage chatMessage =
                    ChatMessage.builder()
                            .messageType(MessageType.LEAVE)
                            .sender(username)
                            .localTime(LocalTime.now())
                            .build();

            messageTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }*/
}

package com.gymohrim.controller.chat;


import com.gymohrim.entity.ChatMessage;
import com.gymohrim.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequiredArgsConstructor
public class ChatController {




    private final ChatService chatService;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(ChatMessage chatMessage) {

        chatMessage.setTimestamp(new SimpleDateFormat("HH:mm:ss").format(new Date()));


        return chatService.saveMessage(chatMessage);
    }

    @GetMapping("/chat-room")
    public String chatRoom() {
        return "chat-room";
    }
}

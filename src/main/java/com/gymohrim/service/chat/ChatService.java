package com.gymohrim.service.chat;


import com.gymohrim.entity.ChatMessage;
import com.gymohrim.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;


    public ChatMessage saveMessage(ChatMessage message) {
        log.info("Saving chat message: {}", message);
        ChatMessage savedMessage = chatMessageRepository.save(message);
        log.info("Chat message saved: {}", savedMessage);
        return savedMessage;
    }

    public List<ChatMessage> getChatHistory() {
        log.info("Retrieving chat history");
        List<ChatMessage> chatHistory = chatMessageRepository.findAllByOrderByTimestampAsc();
        log.info("Chat history retrieved, total messages: {}", chatHistory.size());
        return chatHistory;
    }
}

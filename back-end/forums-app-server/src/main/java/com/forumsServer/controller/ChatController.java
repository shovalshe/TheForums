/*
This controller provides http requests to the server to interact with the chat and chat messages services.
It defines the endpoints for the following requests:
    - GET /api/chat: get all chats of the logged-in user.
    - GET /api/chat/with/{participantId}: get chat of the logged-in user with the given participantId.
    - POST /api/chat/to/{toUserId}/message: send a message
    - GET /api/chat/message/{messageId}: get a message
    - DELETE /api/chat/message/{messageId}: delete a message
 */

package com.forumsServer.controller;

import com.forumsServer.model.Chat;
import com.forumsServer.model.ChatMessage;
import com.forumsServer.service.ChatMessageService;
import com.forumsServer.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;
    @Autowired
    private ChatMessageService chatMessageService;

    @GetMapping("")
    public List<Chat> getLoggedInUserChats() {
        return chatService.getLoggedInUserChats();
    }

    @GetMapping("/with/{participantId}")
    public Chat getChatByParticipantId(@PathVariable String participantId) {
        return chatService.findChatByParticipantId(participantId);
    }

    @GetMapping("/message/{messageId}")
    public Optional<ChatMessage> getChatMessage(@PathVariable long messageId) {
        return chatMessageService.findById(messageId);
    }

    @PostMapping("/to/{toUserId}/message")
    public ChatMessage sendMessage(@PathVariable String toUserId, @RequestBody ChatMessage chatMessage) {
        return chatMessageService.sendMessage(chatMessage, toUserId);
    }

    @DeleteMapping("/message/{messageId}")
    public void deleteMessageById(@PathVariable long messageId) {
        chatMessageService.deleteMessageById(messageId);
    }
}

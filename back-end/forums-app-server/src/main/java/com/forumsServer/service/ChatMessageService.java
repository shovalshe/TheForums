/*
This interface provides the methods to handle the business logic of the chat messages.
It provides methods to add a chat message and find a chat message by its id.
 */

package com.forumsServer.service;

import com.forumsServer.model.ChatMessage;

import javax.transaction.Transactional;
import java.util.Optional;

public interface ChatMessageService {
    @Transactional
    ChatMessage sendMessage(ChatMessage chatMessage, String toUserId);

    Optional<ChatMessage> findById(long chatMessageId);

    @Transactional
    void deleteMessageById(long messageId);
}

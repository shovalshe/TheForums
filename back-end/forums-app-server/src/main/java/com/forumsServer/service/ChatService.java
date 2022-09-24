/*
This interface provides the methods to handle the business logic of the chats between two users.
It provides methods to add a chat, find a chat by its id, find all messages of a chat, find a chat by its participants
and find all chats of the logged-in user. */

package com.forumsServer.service;

import com.forumsServer.model.Chat;
import com.forumsServer.model.ChatMessage;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ChatService {
    @Transactional
    Chat createChat(String participant1Id, String participant2Id);

    Optional<Chat> findChatById(long chatId);

    Optional<List<ChatMessage>> findChatMessagesByChatId(long chatId);

    Chat findChatByParticipantId(String participantId);

    List<Chat> getLoggedInUserChats();
}

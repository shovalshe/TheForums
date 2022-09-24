/*
This class implements the methods of the ChatMessageService interface.
It provides methods to add a chat message and find a chat message by its id.
*/

package com.forumsServer.service;

import com.forumsServer.exception.AccessDeniedException;
import com.forumsServer.exception.ResourceNotFoundException;
import com.forumsServer.model.Chat;
import com.forumsServer.model.ChatMessage;
import com.forumsServer.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.forumsServer.utils.Utils.getDateTimeAsString;
import static com.forumsServer.utils.Utils.getLoggedInUserId;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private ChatService chatService;

    /**
     * This method adds to the database a chat message from the logged-in user to toUserId.
     * It also updates the chat of the logged-in user with the toUserId.
     * If the chat does not exist, it creates a new chat.
     * @param chatMessage the chat message to be added.
     * @param toUserId the id of the user to whom the chat message is sent.
     * @return the added chat message.
     */
    @Override
    @Transactional
    public ChatMessage sendMessage(ChatMessage chatMessage, String toUserId) {
        String fromUserId = getLoggedInUserId();
        Chat chat;
        try {
            chat = chatService.findChatByParticipantId(toUserId);
        } catch (ResourceNotFoundException e) {
            chat = chatService.createChat(fromUserId, toUserId);
        }
        chatMessage.setChat(chat);
        chatMessage.setFromUserId(fromUserId);
        chatMessage.setToUserId(toUserId);
        chatMessage.setTime(getDateTimeAsString());
        return chat.addMessage(chatMessage);
    }

    /**
     * Find a chat message by its id.
     * @param chatMessageId the id of the chat message to find.
     * @return The message if it exists and the logged-in user is the author or the recipient of the message, otherwise return null.
     */
    @Override
    public Optional<ChatMessage> findById(long chatMessageId) {
        String userId = getLoggedInUserId();
        Optional<ChatMessage> chatMessage = chatMessageRepository.findById(chatMessageId);
        if (chatMessage.isPresent()) {
            if (chatMessage.get().getFromUserId().equals(userId) || chatMessage.get().getToUserId().equals(userId)) {
                return chatMessage;
            } else {
                throw new AccessDeniedException(
                        "You are not authorized to access this message since you are not one of its participants.");
            }
        } else {
            throw new ResourceNotFoundException("Chat message with id " + chatMessageId + " not found");
        }
    }

    /**
     * Delete a chat message by its id. Only the author of the message can delete it.
     * @param messageId the id of the chat message to delete.
     */
    @Override
    public void deleteMessageById(long messageId) {
        String userId = getLoggedInUserId();
        Optional<ChatMessage> chatMessage = chatMessageRepository.findById(messageId);
        if (chatMessage.isPresent() && (chatMessage.get().getFromUserId().equals(userId))) {
            chatMessageRepository.deleteById(messageId);
        } else {
            throw new AccessDeniedException(
                    "You are not authorized to delete this message since you are not one of its participants.");
        }
    }
}

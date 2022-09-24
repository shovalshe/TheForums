/*
This class implements the ChatService interface to handle the business logic of the chats between
two users.
It provides methods to add a chat, find a chat by its id, find all messages of a chat, find a chat by its participants
and find all chats of the logged-in user.
 */

package com.forumsServer.service;

import com.forumsServer.exception.AccessDeniedException;
import com.forumsServer.exception.ResourceNotFoundException;
import com.forumsServer.model.Chat;
import com.forumsServer.model.ChatMessage;
import com.forumsServer.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.forumsServer.utils.Utils.getLoggedInUserId;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository;

    /**
     * This method adds to the database a chat between the input users.
     * @param participant1Id the id of the first participant of the chat.
     * @param participant2Id the id of the second participant of the chat.
     * @return the added chat.
     */
    @Override
    @Transactional
    public Chat createChat(String participant1Id, String participant2Id) {
        Chat chat = new Chat();
        chat.setParticipant1Id(participant1Id);
        chat.setParticipant2Id(participant2Id);
        return chatRepository.save(chat);
    }

    /** This method finds a chat by its id.
     * @param chatId the id of the chat to find.
     * @return the chat if it exists, otherwise return null.
     */
    @Override
    public Optional<Chat> findChatById(long chatId) {
        Optional<Chat> chat = chatRepository.findById(chatId);
        String loggedInUserId = getLoggedInUserId();
        if (chat.isPresent()) {
            if (chat.get().isParticipant(loggedInUserId)) {
                return chat;
            } else {
                throw new AccessDeniedException(
                        "You are not authorized to access this chat since you are not one of its participants.");
            }
        } else {
            throw new ResourceNotFoundException("Chat with id " + chatId + " not found");
        }
    }

    /**
     * Find all messages of a chat.
     * @param chatId the id of the chat.
     * @return The list of messages of the chat if it exists and the logged-in user is one of the participants, otherwise return null.
     */
    @Override
    public Optional<List<ChatMessage>> findChatMessagesByChatId(long chatId) {
        Optional<Chat> chat = this.findChatById(chatId);
        String loggedInUserId = getLoggedInUserId();
        if (chat.isPresent()) {
            if (chat.get().isParticipant(loggedInUserId)) {
                return Optional.of(chat.get().getMessages());
            }
            throw new AccessDeniedException(
                    "You are not authorized to access this chat since you are not one of its participants.");
        }
        return Optional.empty();
    }

    /**
     * Find a chat of the logged-in user with another participant.
     * @param participantId The id of the chat participant.
     * @return The chat if it exists, otherwise return null.
     */
    @Override
    public Chat findChatByParticipantId(String participantId) {
        final  List<Chat> LoggedInUserChats = this.getLoggedInUserChats();
        for (Chat chat : LoggedInUserChats) {
            if (chat.isParticipant(participantId)) {
                return chat;
            }
        }
        throw new ResourceNotFoundException("Chat with participantId " + participantId + " not found");
    }

    /**
     * Find all chats of the logged-in user.
     * @return A list of the chats of the logged-in user.
     */
    public List<Chat> getLoggedInUserChats() {
         String loggedInUserId = getLoggedInUserId();
        return chatRepository.findChatsByParticipantId(loggedInUserId);
    }
}

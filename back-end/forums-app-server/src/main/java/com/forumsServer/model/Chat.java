/*
This class represents the chat (between two users) model.
It contains all chat messages that are sent between the chat's participants.
The Entity annotation is used to mark this class as a JPA entity that will be persisted to the database.
 */

package com.forumsServer.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
@Table(name = "CHATS")
public class Chat {

    private static int MAX_NUM_OF_MESSAGES = 50;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotEmpty
    private String participant1Id;

    @NotEmpty
    private String participant2Id;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ChatMessage> messages = new ArrayList<>();

    /**
     * Get id field @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id", nullable = false)
     * @return id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Set id field @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id", nullable = false)
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get participant1Id field @NotEmpty
     * @return participant1Id
     */
    public String getParticipant1Id() {
        return this.participant1Id;
    }

    /**
     * Set participant1Id field @NotEmpty
     * @param participant1Id the participant1Id to set
     */
    public void setParticipant1Id(String participant1Id) {
        this.participant1Id = participant1Id;
    }

    /**
     * Get participant2Id field @NotEmpty
     * @return participant2Id
     */
    public String getParticipant2Id() {
        return this.participant2Id;
    }

    /**
     * Set participant2Id field @NotEmpty
     * @param participant2Id the participant2Id to set
     */
    public void setParticipant2Id(String participant2Id) {
        this.participant2Id = participant2Id;
    }

    /**
     * Get messages field @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
     * @return messages of this chat
     */
    public List<ChatMessage> getMessages() {
        return this.messages;
    }

    /**
     * Add chatMessage to messages and remove the oldest message if the number of messages is greater than MAX_NUM_OF_MESSAGES
     * @param chatMessage chatMessage to add
     */
    public ChatMessage addMessage(ChatMessage chatMessage) {
        if (this.messages.size() == MAX_NUM_OF_MESSAGES) {
            Iterator<ChatMessage> it = this.messages.iterator();
            it.next();
            it.remove();
        }
        this.messages.add(chatMessage);
        return chatMessage;
    }

    /**
     * Check if the user is in the chat
     * @param userId userId to check
     * @return true if the user is in the chat, false otherwise
     */
    public boolean isParticipant(String userId) {
        return this.participant1Id.equals(userId) || this.participant2Id.equals(userId);
    }
}
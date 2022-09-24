/*
This class represents the chat message model.
This is a private message that a user can send to another user.
The Entity annotation is used to mark this class as a JPA entity that will be persisted to the database.
 */

package com.forumsServer.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "CHAT_MESSAGES")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotEmpty
    @Column(name = "content", columnDefinition = "text")
    private String content;

    @NotEmpty
    private String fromUserId;

    @NotEmpty
    private String toUserId;

    private String time;

    @JsonBackReference
    @ManyToOne
    private Chat chat;

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
     * Get content field @NotEmpty
     * @return content
     */
    public String getContent() {
        return this.content;
    }

    /**
     * Set content field @NotEmpty
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Get fromUserId field @NotEmpty
     * @return fromUserId
     */
    public String getFromUserId() {
        return this.fromUserId;
    }

    /**
     * Set fromUserId field @NotEmpty
     * @param fromUserId the fromUserId to set
     */
    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    /**
     * Get toUserId field @NotEmpty
     * @return toUserId
     */
    public String getToUserId() {
        return this.toUserId;
    }

    /**
     * Set toUserId field @NotEmpty
     * @param toUserId the toUserId to set
     */
    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    /**
     * Get time field
     * @return time
     */
    public String getTime() {
        return this.time;
    }

    /**
     * Set time field
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Get chat field @JsonBackReference @ManyToOne
     * @return chat
     */
    public Chat getChat() {
        return this.chat;
    }

    /**
     * Set chat field @JsonBackReference @ManyToOne
     * @param chat the chat to set
     */
    public void setChat(Chat chat) {
        this.chat = chat;
    }
}
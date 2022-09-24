/*
This class represents the comment model.
This is a message that a user can comment to a post message.
The Entity annotation is used to mark this class as a JPA entity that will be persisted to the database.
 */

package com.forumsServer.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "COMMENTS")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotEmpty
    @Column(name = "content", columnDefinition = "text")
    private String content;

    private String authorId;

    private String time;

    @JsonBackReference
    @ManyToOne
    private PostMessage postMessage;

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
     * Get postMessage field @JsonBackReference @ManyToOne
     * @return postMessage
     */
    public PostMessage getPostMessage() {
        return this.postMessage;
    }

    /**
     * Set postMessage field @JsonBackReference @ManyToOne
     * @param postMessage the postMessage to set
     */
    public void setPostMessage(PostMessage postMessage) {
        this.postMessage = postMessage;
    }

    /**
     * Get authorId field
     * @return authorId
     */
    public String getAuthorId() {
        return this.authorId;
    }

    /**
     * Set authorId field
     * @param authorId the authorId to set
     */
    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }
}
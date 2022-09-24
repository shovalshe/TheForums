/*
This class represents the post message model.
It is a message that a user can post in a forum.
It contains all the comments that have been made to it.
The Entity annotation is used to mark this class as a JPA entity that will be persisted to the database.
 */

package com.forumsServer.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
@Table(name = "POSTS")
public class PostMessage {

    private static int MAX_NUM_OF_COMMENTS = 50;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotEmpty
    private String title;

    @NotEmpty
    @Column(name = "content", columnDefinition = "text")
    private String content;

    private String authorId;

    private String time;

    @JsonBackReference
    @ManyToOne
    private Forum forum;

    @OneToMany(mappedBy = "postMessage", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    /**
     * Get id field @Id @GeneratedValue @Column(name = "id", nullable = false)
      * @return id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Set id field @Id @GeneratedValue @Column(name = "id", nullable = false)
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get title field @Column(name = "TITLE")
     * @return title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Set title field @Column(name = "TITLE")
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get content field @Column(name = "CONTENT")
     * @return content
     */
    public String getContent() {
        return this.content;
    }

    /**
     * Set content field @Column(name = "CONTENT")
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Get time field @Column(name = "TIME")
     * @return time
     */
    public String getTime() {
        return this.time;
    }

    /**
     * Set time field @Column(name = "TIME")
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Get forum field @JsonBackReference @ManyToOne
     * @return forum
     */
    public Forum getForum() {
        return this.forum;
    }

    /**
     * Set forum field @JsonBackReference @ManyToOne
     * @param forum the forum to set
     */
    public void setForum(Forum forum) {
        this.forum = forum;
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

    /**
     * Get comments field @OneToMany(mappedBy = "postMessage", cascade = CascadeType.ALL)
     * @return comments
     */
    public List<Comment> getComments() {
        return this.comments;
    }

    /**
     * Add a comment to comments and remove the oldest comment if the number of comments is greater than MAX_NUM_OF_COMMENTS
     * @param comment comment to add
     */
    public Comment addComment(Comment comment) {
        if (this.comments.size() == MAX_NUM_OF_COMMENTS) {
            Iterator<Comment> it = this.comments.iterator();
            it.next();
            it.remove();
        }
        this.comments.add(comment);
        return comment;
    }
}
/*
This class represents the forum model.
It contains all the post messages that were posted in a forum.
The Entity annotation is used to mark this class as a JPA entity that will be persisted to the database.
 */

package com.forumsServer.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
@Table(name = "FORUMS")
public class Forum {

    private static int MAX_NUM_OF_POSTS = 50;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotEmpty
    private String subject;

    @OneToMany(mappedBy = "forum", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PostMessage> posts = new ArrayList<>();

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
     * Get subject field @NotEmpty
     * @return subject @NotEmpty
     */
    public String getSubject() {
        return this.subject;
    }

    /**
     * Set subject field @NotEmpty
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Get field @OneToMany(mappedBy = "forum", cascade = CascadeType.ALL)
     * @return posts the posts to set
     */
    public List<PostMessage> getPosts() {
        return this.posts;
    }

    /**
     * Add postMessage to posts and remove the oldest post if the number of posts is greater than MAX_NUM_OF_POSTS
     * @param postMessage postMessage to add
     */
    public PostMessage addPost(PostMessage postMessage) {
        if (this.posts.size() == MAX_NUM_OF_POSTS) {
            Iterator<PostMessage> it = this.posts.iterator();
            it.next();
            it.remove();
        }
        this.posts.add(postMessage);
        return postMessage;
    }
}

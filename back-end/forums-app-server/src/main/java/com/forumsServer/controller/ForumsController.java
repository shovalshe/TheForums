/*
This controller provides http requests to the server to interact with the forum, post message and comment services.
It defines the endpoints for the following requests:
    - GET /api/forum: get all forums.
    - POST /api/forum: create a forum
    - GET /api/forum/{forumId}: get a forum
    - DELETE /api/forum/{forumId}: delete a forum

    - GET /api/forum/{forumId}/post: get all forum's posts
    - POST /api/forum/{forumId}/post: create a post
    - GET /api/forum/{forumId}/post/{postId}: get a post
    - DELETE /api/forum/{forumId}/post/{postId}: delete a post

    - GET /api/forum/{forumId}/post/{postId}/comment: get all post's comments
    - POST /api/forum/{forumId}/post/{postId}/comment: create a comment
    - GET /api/forum/{forumId}/post/{postId}/comment/{commentId}: get a comment
    - DELETE /api/forum/{forumId}/post/{postId}/comment/{commentId}: delete a comment
 */

package com.forumsServer.controller;

import com.forumsServer.model.Comment;
import com.forumsServer.model.Forum;
import com.forumsServer.model.PostMessage;
import com.forumsServer.service.CommentService;
import com.forumsServer.service.ForumService;
import com.forumsServer.service.PostMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/forum")
public class ForumsController {

    @Autowired
    private ForumService forumService;
    @Autowired
    private PostMessageService postMessageService;
    @Autowired
    private CommentService commentService;

    // Forum Requests:

    @GetMapping("")
    public List<Forum> getForums() {
        return forumService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Forum> getForumById(@PathVariable long id) {
        return forumService.findById(id);
    }

    @PostMapping("")
    public Forum postForum(@RequestBody Forum forum) {
        forumService.save(forum);
        return forum;
    }

    @DeleteMapping("/{id}")
    public void deleteForumById(@PathVariable long id) {
        forumService.deleteById(id);
    }

    // Post Message Requests:

    @GetMapping("/{forumId}/post")
    public List<PostMessage> getAllPostMessagesOfForum(@PathVariable long forumId) {
        return postMessageService.findAllPostsByForumId(forumId);
    }

    @PostMapping("/{forumId}/post")
    public PostMessage postPostMessage(@PathVariable long forumId, @RequestBody PostMessage postMessage) {
        return postMessageService.addPostToForum(postMessage, forumId);
    }

    @GetMapping("/{forumId}/post/{postId}")
    public Optional<PostMessage> getPostById(@PathVariable long postId) {
        return postMessageService.findById(postId);
    }

    @DeleteMapping("/{forumId}/post/{postId}")
    public void deletePostMessageById(@PathVariable long postId) {
        postMessageService.deleteById(postId);
    }

    // Comment Requests:

    @GetMapping("/{forumId}/post/{postId}/comment")
    public List<Comment> getAllCommentsOfPostMessage(@PathVariable long postId) {
        return commentService.findAllCommentsByPostId(postId);
    }

    @PostMapping("/{forumId}/post/{postId}/comment")
    public Comment postComment(@PathVariable long postId, @RequestBody Comment comment) {
        return commentService.addCommentToPostMessage(comment, postId);
    }

    @GetMapping("/{forumId}/post/{postId}/comment/{commentId}")
    public Optional<Comment> getCommentById(@PathVariable long commentId) {
        return commentService.findById(commentId);
    }

    @DeleteMapping("/{forumId}/post/{postId}/comment/{commentId}")
    public void deleteCommentById(@PathVariable long commentId) {
        commentService.deleteById(commentId);
    }
}

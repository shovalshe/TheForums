/*
This interface provides the methods to handle the business logic of the comments.
It provides methods to add a comment to a post, delete a comment by its id and find a comment by its id.
 */

package com.forumsServer.service;

import com.forumsServer.model.Comment;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface CommentService {
    @Transactional
    Comment addCommentToPostMessage(Comment comment, long postMessageId);

    @Transactional
    void deleteById(long commentId);

    List<Comment> findAllCommentsByPostId(long postId);

    Optional<Comment> findById(long commentId);
}

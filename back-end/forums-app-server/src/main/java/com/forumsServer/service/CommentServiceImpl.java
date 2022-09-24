/*
This class implements the CommentService interface to handle the business logic of the comments.
It provides methods to add a comment to a post, delete a comment by its id and find a comment by its id.
 */

package com.forumsServer.service;

import com.forumsServer.exception.ResourceNotFoundException;
import com.forumsServer.model.Comment;
import com.forumsServer.model.PostMessage;
import com.forumsServer.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.forumsServer.utils.Utils.getDateTimeAsString;
import static com.forumsServer.utils.Utils.getLoggedInUserId;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    PostMessageService postMessageService;

    /**
     * This method adds to the database a comment to a post.
     * @param postMessageId the id of the post to which the comment is added.
     * @param comment the comment to add.
     * @return the added comment.
     */
    @Override
    @Transactional
    public Comment addCommentToPostMessage(Comment comment, long postMessageId) {
        PostMessage postMessage = postMessageService.findById(postMessageId).get();
        comment.setPostMessage(postMessage);
        comment.setAuthorId(getLoggedInUserId());
        comment.setTime(getDateTimeAsString());
        return postMessage.addComment(comment);
    }

    /**
     * This method deletes a comment by its id.
     * @param commentId the id of the comment to delete.
     */
    @Override
    @Transactional
    public void deleteById(long commentId) {
        commentRepository.deleteById(commentId);
    }

    /**
     * This method finds all comments of a post.
     * @param postId the id of the post.
     * @return the comment list.
     */
    @Override
    public List<Comment> findAllCommentsByPostId(long postId) {
        return commentRepository.findAllCommentsByPostMessageId(postId);
    }

    /**
     * This method finds a comment by its id.
     * @param commentId the id of the comment to find.
     * @return the comment if it exists, otherwise return null.
     */
    @Override
    public Optional<Comment> findById(long commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isPresent()) {
            return comment;
        } else {
            throw new ResourceNotFoundException("Comment with id " + commentId + " not found");
        }
    }

}

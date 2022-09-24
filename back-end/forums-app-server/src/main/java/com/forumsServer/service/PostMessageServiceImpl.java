/*
This class implements the PostsService interface to handle the business logic of the post messages.
It provides methods to add a post to a forum, delete a post by its id, find a post by its id and find all comments of a post.
 */

package com.forumsServer.service;

import com.forumsServer.exception.ResourceNotFoundException;
import com.forumsServer.model.Comment;
import com.forumsServer.model.Forum;
import com.forumsServer.model.PostMessage;
import com.forumsServer.repository.PostMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.forumsServer.utils.Utils.getDateTimeAsString;
import static com.forumsServer.utils.Utils.getLoggedInUserId;

@Service
public class PostMessageServiceImpl implements PostMessageService {

    @Autowired
    private PostMessageRepository postMessageRepository;

    @Autowired
    ForumService forumService;

    /**
     * This method adds to the database a post in a forum.
     * @param forumId the id of the forum to which the post is added.
     * @param postMessage the post to add.
     * @return the added post.
     */
    @Override
    @Transactional
    public PostMessage addPostToForum(PostMessage postMessage, long forumId) {
        Forum forum = forumService.findById(forumId).get();
        postMessage.setForum(forum);
        postMessage.setAuthorId(getLoggedInUserId());
        postMessage.setTime(getDateTimeAsString());
        return forum.addPost(postMessage);
    }

    /**
     * This method deletes a post by its id.
     * @param postId the id of the post to delete.
     */
    @Override
    @Transactional
    public void deleteById(long postId) {
        postMessageRepository.deleteById(postId);
    }

    /**
     * This method finds a post by its id.
     * @param postId the id of the post.
     * @return the post.
     */
    @Override
    public Optional<PostMessage> findById(long postId) {
        Optional<PostMessage> postMessage = postMessageRepository.findById(postId);
        if (postMessage.isPresent()) {
            return postMessage;
        } else {
            throw new ResourceNotFoundException("Post with id " + postId + " not found");
        }
    }

    /**
     * This method finds all posts of a forum.
     * @param forumId the id of the forum.
     * @return the post list.
     */
    @Override
    public List<PostMessage> findAllPostsByForumId(long forumId) {
        return postMessageRepository.findAllPostsByForumId(forumId);
    }

    /**
     * This method finds all comments of a post.
     * @param postId the id of the post.
     * @return the comment list.
     */
    @Override
    public Optional<List<Comment>> findAllCommentsOfPost(long postId) {
        Optional<PostMessage> postMessage = this.findById(postId);
        return postMessage.map(value -> Optional.of(value.getComments())).orElse(null);
    }
}

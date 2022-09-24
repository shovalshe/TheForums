/*
This interface provides the methods to handle the business logic of the post messages.
It provides methods to add a post to a forum, delete a post by its id, find a post by its id and find all comments of a post.
 */

package com.forumsServer.service;

import com.forumsServer.model.PostMessage;
import com.forumsServer.model.Comment;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface PostMessageService {

    @Transactional
    PostMessage addPostToForum(PostMessage postMessage, long forumId);

    @Transactional
    void deleteById(long id);

    Optional<PostMessage> findById(long postId);

    List<PostMessage> findAllPostsByForumId(long forumId);

    Optional<List<Comment>> findAllCommentsOfPost(long postId);
}

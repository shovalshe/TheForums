/*
This interface provides the methods to handle the business logic of the forums.
It provides methods to add a forum, delete a forum by its id, find a forum by its id and find all posts of a forum.
 */

package com.forumsServer.service;

import com.forumsServer.model.Forum;
import com.forumsServer.model.PostMessage;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ForumService {
    @Transactional
    Forum save(Forum forum);

    @Transactional
    void deleteById(long id);

    Optional<Forum> findById(long id);

    List<Forum> findAll();

    public Optional<List<PostMessage>> findAllPostsOfForum(long forumId);
}

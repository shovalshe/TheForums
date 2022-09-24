/*
This class implements the UsersService interface to handle the business logic of the forums.
methods to handle the business logic of the forums.
It provides methods to add a forum, delete a forum by its id, find a forum by its id and find all posts of a forum.
*/

package com.forumsServer.service;

import com.forumsServer.exception.ResourceNotFoundException;
import com.forumsServer.model.Forum;
import com.forumsServer.model.PostMessage;
import com.forumsServer.repository.ForumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ForumServiceImpl implements ForumService {

    @Autowired
    private ForumRepository forumRepository;

    /**
     * This method adds to the database a forum.
     * @param forum the forum to add.
     * @return the added forum.
     */
    @Override
    @Transactional
    public Forum save(Forum forum) {
        return forumRepository.save(forum);
    }

    /**
     * This method deletes a forum by its id.
     * @param id the id of the forum to delete.
     */
    @Override
    @Transactional
    public void deleteById(long id) {
        forumRepository.deleteById(id);
    }

    /**
     * This method finds a forum by its id.
     * @param id the id of the forum to find.
     * @return the found forum.
     */
    @Override
    public Optional<Forum> findById(long id) {

        Optional<Forum> forum = forumRepository.findById(id);
        if (forum.isPresent()) {
            return forum;
        } else {
            throw new ResourceNotFoundException("Forum with id " + id + " not found");
        }
    }

    /**
     * This method finds all forums.
     * @return the forum list.
     */
    @Override
    public List<Forum> findAll() {
        return forumRepository.findAll();
    }

    /**
     * This method finds all posts of a forum.
     * @param forumId the id of the forum.
     * @return the post list.
     */
    @Override
    public Optional<List<PostMessage>> findAllPostsOfForum(long forumId) {
        Optional<Forum> forum = this.findById(forumId);
        return forum.map(value -> Optional.of(value.getPosts())).orElse(null);
    }
}

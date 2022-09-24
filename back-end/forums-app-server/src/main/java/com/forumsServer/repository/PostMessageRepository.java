/*
This interface is automatically being implemented by the Spring Data JPA generator.
It provides methods to interact with the POSTS table in the database.
 */

package com.forumsServer.repository;

import com.forumsServer.model.PostMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostMessageRepository extends JpaRepository<PostMessage, Long> {
    List<PostMessage> findAllPostsByForumId(long forumId);
}
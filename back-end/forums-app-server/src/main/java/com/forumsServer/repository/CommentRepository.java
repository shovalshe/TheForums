/*
This interface is automatically being implemented by the Spring Data JPA generator.
It provides methods to interact with the COMMENTS table in the database.
 */

package com.forumsServer.repository;

import com.forumsServer.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllCommentsByPostMessageId(long postId);
}
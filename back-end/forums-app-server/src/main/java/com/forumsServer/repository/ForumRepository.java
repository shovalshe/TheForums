/*
This interface is automatically being implemented by the Spring Data JPA generator.
It provides methods to interact with the FORUMS table in the database.
 */

package com.forumsServer.repository;

import com.forumsServer.model.Forum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForumRepository extends JpaRepository<Forum, Long> {
    // By inherit from JpaRepository, Spring auto implements this class (with @PersistenceContext EntityManager))
}
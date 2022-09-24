/*
This interface is automatically being implemented by the Spring Data JPA generator.
It provides methods to interact with the CHAT_MESSAGES table in the database.
 */

package com.forumsServer.repository;

import com.forumsServer.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
}
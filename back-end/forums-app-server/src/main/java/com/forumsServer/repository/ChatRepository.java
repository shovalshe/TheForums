/*
This interface is automatically being implemented by the Spring Data JPA generator.
It provides methods to interact with the CHATS table in the database.
 */

package com.forumsServer.repository;

import com.forumsServer.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Query(value="SELECT c FROM Chat c WHERE c.participant1Id =:participantId OR c.participant2Id =:participantId")
    public List<Chat> findChatsByParticipantId(String participantId);
}
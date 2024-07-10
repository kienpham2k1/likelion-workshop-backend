package org.example.likelion.repository;


import org.example.likelion.model.RoomChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomChatRepository extends JpaRepository<RoomChat, String> {
    Optional<RoomChat> findByUserId(String id);
}

package org.example.likelion.repository;

import org.example.likelion.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> {
    Page<Message> findAllByRoomId(String roomId, Pageable pageable);

    List<Message> findAllByRoomId(String roomId);
}

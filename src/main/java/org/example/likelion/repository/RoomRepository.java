package org.example.likelion.repository;


import org.example.likelion.model.RoomChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<RoomChat, String> {
}

package org.example.likelion.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "message")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "message_id")
    private String id;
    @Column(name = "content")
    private String content;
    private String attachment;
    private LocalDate createdDate;
    @Column(name = "user_id")
    private String userId;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private User user;
    @Column(name = "room_id")
    private String roomId;
    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "room_id", insertable = false, updatable = false)
    private RoomChat room;
}

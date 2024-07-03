package org.example.likelion.model;


import jakarta.persistence.*;
import lombok.*;
import org.example.likelion.enums.MessageType;

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
    private String id;
    @Column(name = "message_content")
    private String messageContent;
    private String attachment;
    private LocalDate createdDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
}

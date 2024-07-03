package org.example.likelion.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "room")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room {
    @Id
    @Column(name = "room_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;
    @OneToMany(mappedBy = "room")
    private Set<Message> messages;
    @OneToMany(mappedBy = "room")
    private Set<Members> members;
}

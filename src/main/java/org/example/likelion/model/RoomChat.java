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
public class RoomChat {
    @Id
    @Column(name = "user_id")
    private String id;
    @Column(name = "created_date")
    private LocalDate createdDate = LocalDate.now();
    @OneToMany(mappedBy = "room")
    private Set<Message> messages;
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
}

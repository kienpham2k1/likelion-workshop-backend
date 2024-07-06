package org.example.likelion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "members")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Members {
    @Id
    private String id;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private RoomChat room;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

package org.example.likelion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "role")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "role_id")
    private String id;
    @NotNull
    @NotBlank
    private String name;
    @OneToMany(mappedBy = "role")
    private Set<User> users;
    @OneToMany(mappedBy = "role")
    private Set<Admin> admins;
}

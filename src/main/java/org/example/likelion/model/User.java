package org.example.likelion.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.example.likelion.dto.auth.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

@Entity
@Table(name = "[user]")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends UserDetailsImpl {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private String id;
    @NotNull
    @NotBlank
    @Column(unique = true)
    private String username;
    @NotNull
    @NotBlank
    @JsonIgnore
    private String password;
    @NotNull
    @NotBlank
    private String phoneNumber;
    @NotNull
    @NotBlank
    private String addressLine;
    @Column(name = "role_id", nullable = false)
    private int roleId;
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id", insertable = false, updatable = false)
    private Role role;
    @OneToMany(mappedBy = "user")
    private Set<Order> orders;
    @OneToMany(mappedBy = "user")
    private Set<Token> tokens;
}

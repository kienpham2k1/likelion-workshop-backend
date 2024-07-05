package org.example.likelion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.example.likelion.dto.auth.Role;
import org.example.likelion.dto.auth.UserDetailsImpl;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "admin",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"username"})
        })
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Admin extends UserDetailsImpl {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @Column(name = "admin_id")
    private String id;
    @Column(unique = true)
    @NotBlank
    private String username;
    @NotNull
    @NotBlank
    private String password;
    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;
    @OneToMany(mappedBy = "admin")
    private Set<Token> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }
}

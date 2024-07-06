package org.example.likelion.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.example.likelion.dto.auth.Account;
import org.example.likelion.dto.auth.Role;
import org.example.likelion.dto.auth.UserDetailsImpl;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "[user]",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"username"})
        })
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@NamedNativeQuery(
        name = "findAccountByUsername",
        query =
                """
                        select user_id as id, username as username, password as password, "role" as role
                        from (
                            select u.user_id, u.username, u."password", u."role" from "user" u
                            union all
                            select a.admin_id, a.username, a."password", a."role" from "admin" a
                        ) as UserDetails
                            where username = :username
                        """,
        resultSetMapping = "account"
)
@SqlResultSetMapping(
        name = "account",
        classes = @ConstructorResult(
                targetClass = Account.class,
                columns = {
                        @ColumnResult(name = "id", type = String.class),
                        @ColumnResult(name = "username", type = String.class),
                        @ColumnResult(name = "password", type = String.class),
                        @ColumnResult(name = "role", type = Role.class)
                }
        )
)

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
    @NotBlank
    private String email;
    @NotNull
    private Boolean verify;
    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;
    @OneToMany(mappedBy = "user")
    private Set<Order> orders;
    @OneToMany(mappedBy = "user")
    private Set<Token> tokens;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private RoomChat roomChat;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }
}

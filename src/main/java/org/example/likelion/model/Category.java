package org.example.likelion.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "category"
        , uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name"})
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "category_id")
    private String id;
    @Column(nullable = false)
    private String name;
    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean isDeleted;
    @OneToMany(mappedBy = "category")
    private Set<Product> product;
    @OneToMany(mappedBy = "category")
    private Set<Promotion> promotions;
}

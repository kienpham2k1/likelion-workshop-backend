package org.example.likelion.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "promotion")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "promotion_id")
    private String id;
    @Column(nullable = false)
    private String name;
    @Column(name = "discount_percent", nullable = false)
    private double discountPercent;
    @Column(name = "expired_date", nullable = false)
    private LocalDate expiredDate;
    @Column(name = "started_date", nullable = false)
    private LocalDate startedDate;
    @Column(name = "is_active", nullable = false)
    private boolean active;
    @ManyToMany
    @JoinTable(
            name = "pro_cate",
            joinColumns = @JoinColumn(name = "promotion_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    Set<Category> categories;
}

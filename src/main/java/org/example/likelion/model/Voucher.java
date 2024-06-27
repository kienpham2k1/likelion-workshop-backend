package org.example.likelion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "voucher")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "voucher_id")
    private String id;
    @Column(nullable = false)
    private String name;
    @Column(name = "discount_percent", nullable = false)
    private double discountPercent;
    @Column(nullable = false)
    private LocalDate expired_date;
    @Column(nullable = false)
    private LocalDate create_date;
    @Column(nullable = false)
    @Min(0)
    private int quantity;
    @Column(nullable = false)
    private boolean isDelete;
    @Column(nullable = false)
    private boolean isActive;
    @ManyToMany(mappedBy = "vouchers")
    private Set<Order> orders;
}

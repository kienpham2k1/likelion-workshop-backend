package org.example.likelion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "voucher")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    @OneToOne(mappedBy = "voucher")
    @PrimaryKeyJoinColumn
    private Order order;
}

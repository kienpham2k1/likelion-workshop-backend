package org.example.likelion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id")
    private String id;
    @Column(nullable = false)
    private double total;
    @Column(name = "shipping_fee", nullable = false)
    private double shippingFee;
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    @Column(name = "address_line", nullable = false)
    private String addressLine;
    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;
    @OneToMany(mappedBy = "order")
    private Set<OrderDetail> orderDetails;
    @ManyToMany
    @JoinTable(
            name = "ord_voucher",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "voucher_id"))
    private Set<Voucher> vouchers;

}

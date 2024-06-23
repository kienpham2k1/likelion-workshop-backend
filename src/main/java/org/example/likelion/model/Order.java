package org.example.likelion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.example.likelion.enums.OrderStatus;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "[order]")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id")
    private String id;
    @Column(nullable = false)
    @Positive
    @Min(1)
    private double total;
    @Column(name = "shipping_fee", nullable = false)
    private double shippingFee;
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    @Column(name = "address_line", nullable = false)
    private String addressLine;
    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;
    @Column(name = "order_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.RECEIVED;
    private boolean isCancel;
    @OneToMany(mappedBy = "order")
    private Set<OrderDetail> orderDetails;
    @ManyToMany
    @JoinTable(
            name = "ord_voucher",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "voucher_id"))
    private Set<Voucher> vouchers;
    @Column(name = "user_id", nullable = false)
    private String userId;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private User user;
}

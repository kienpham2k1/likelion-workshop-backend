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
    @Column(name = "is_cancel", nullable = false)
    private boolean cancel;
    @Column(name = "is_online_payment", nullable = false)
    private boolean onlinePayment;
    @Column(name = "is_paid", nullable = false)
    private boolean paid;
    @OneToMany(mappedBy = "order")
    private Set<OrderDetail> orderDetails;
    @Column(name = "voucher_id", nullable = true)
    private String voucherId;
    @ManyToOne
    @JoinColumn(name = "voucher_id", referencedColumnName = "voucher_id", insertable = false, updatable = false, nullable = true)
    private Voucher voucher;
    @Column(name = "user_id", nullable = false)
    private String userId;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private User user;
}

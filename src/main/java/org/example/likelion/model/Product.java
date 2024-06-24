package org.example.likelion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id")
    private String id;
    @Column(nullable = false)
    @NotEmpty
    private String name;
    private String description;
    @NotEmpty
    private String color;
    @Positive
    private int size;
    @Positive
    private int quantity;
    @Column(name = "img_link")
    private String imgLink;
    @Column(nullable = false)
    private double price;
    @Column(name = "category_id", nullable = false)
    private String categoryId;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id", nullable = false, insertable = false, updatable = false)
    private Category category;
    @OneToMany(mappedBy = "product")
    private Set<OrderDetail> orderDetails;

    public Product(String name, String description, double price, int quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }
}

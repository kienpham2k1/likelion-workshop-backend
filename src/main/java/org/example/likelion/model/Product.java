package org.example.likelion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id")
    private String id;
    @Column(nullable = false)
    private String name;
    //    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private double price;
    @Column(name = "category_id")
    private String categoryId;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id", nullable = false, insertable = false, updatable = false)
    private Category category;
    @OneToOne(mappedBy = "product")
    private OrderDetail orderDetail;
    @ManyToMany(mappedBy = "products")
    private Set<Size> sizes;
}

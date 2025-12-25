package com.loc.electronics_store.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;
@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE products SET deleted = true WHERE id=?") // Tự động chuyển DELETE thành UPDATE
@SQLRestriction("deleted = false")
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;
    private Integer stockQuantity;
    private String thumbnail;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "deleted")
    private boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImage> images;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductAttribute> attributes;

    @ManyToMany(mappedBy = "products")
    private List<Promotion> promotions;
}
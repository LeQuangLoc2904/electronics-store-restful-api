package com.loc.electronics_store.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "promotions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Promotion extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String discountType;
    private Double discountValue;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean isActive;

    @ManyToMany
    @JoinTable(name = "promotion_product",
            joinColumns = @JoinColumn(name = "promotion_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

    @ManyToMany
    @JoinTable(name = "promotion_category",
            joinColumns = @JoinColumn(name = "promotion_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;
}

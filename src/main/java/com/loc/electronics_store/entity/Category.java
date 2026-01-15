package com.loc.electronics_store.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String slug;
    private String description;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    @ManyToMany
    private List<Brand> brands;

    @ManyToMany(mappedBy = "categories")
    private List<Promotion> promotions;
}
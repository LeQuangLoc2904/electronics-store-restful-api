package com.loc.electronics_store.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Entity
@Table(name = "product_attributes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String value;

    @ManyToOne @JoinColumn(name = "product_id")
    private Product product;
}
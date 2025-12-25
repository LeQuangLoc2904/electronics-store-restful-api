package com.loc.electronics_store.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Table(name = "order_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetail {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;
    private Double price;
}

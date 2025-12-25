package com.loc.electronics_store.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reviews")
@Getter
@Setter
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rating;
    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}

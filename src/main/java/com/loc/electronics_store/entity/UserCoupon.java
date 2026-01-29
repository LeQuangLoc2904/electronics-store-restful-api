package com.loc.electronics_store.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_coupons")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCoupon extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;

    private LocalDateTime usedAt;

    @Column(name = "order_id")
    private Long orderId;
}

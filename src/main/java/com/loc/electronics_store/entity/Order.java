package com.loc.electronics_store.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne @JoinColumn(name = "coupon_id")
    private Coupon coupon; // Nếu dùng mã giảm giá

    private Double totalMoney;      // Tổng tiền gốc của các SP
    private Double discountAmount;  // Số tiền được giảm (từ Coupon + Promotion)
    private Double finalAmount;     // Số tiền thực trả

    private String status; // PENDING, PROCESSING, DELIVERED, CANCELLED
    private String paymentMethod;
    private String shippingAddress;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;
}
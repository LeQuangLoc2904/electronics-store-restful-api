package com.loc.electronics_store.repository;

import com.loc.electronics_store.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Optional<Coupon> findByCode(String code);

    @Query("SELECT c FROM Coupon c WHERE " +
            "c.isActive = true " +
            "AND :now BETWEEN c.startDate AND c.endDate " +
            "AND (c.usageLimit IS NULL OR c.usedCount < c.usageLimit)")
    List<Coupon> findValidCoupons(@Param("now") LocalDateTime now);

    @Query("SELECT c FROM Coupon c WHERE " +
            "c.isActive = false " +
            "OR :now < c.startDate " +
            "OR :now > c.endDate " +
            "OR (c.usageLimit IS NOT NULL AND c.usageLimit > 0 AND c.usedCount >= c.usageLimit)")
    List<Coupon> findFullyInvalidCoupons(@Param("now") LocalDateTime now);
    boolean existsByCode(String code);
}


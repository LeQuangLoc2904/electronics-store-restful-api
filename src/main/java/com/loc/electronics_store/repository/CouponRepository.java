package com.loc.electronics_store.repository;

import com.loc.electronics_store.entity.Coupon;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Optional<Coupon> findByCode(String code);

    @Modifying
    @Transactional
    @Query("UPDATE Coupon c SET c.isActive = false " +
            "WHERE c.isActive = true " +
            "AND (:now NOT BETWEEN c.startDate AND c.endDate " +
            "OR c.usedCount >= c.usageLimit)")
    int deactivateInvalidCoupons(@Param("now") LocalDateTime now);

    List<Coupon> findByIsActiveTrue();
    List<Coupon> findByIsActiveFalse();

    boolean existsByCode(String code);
}


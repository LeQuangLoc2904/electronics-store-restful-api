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

    @Query("SELECT c FROM Coupon c WHERE :now BETWEEN c.startDate AND c.endDate")
    List<Coupon> findValidCoupons(@Param("now") LocalDateTime now);
    boolean existsByCode(String code);
}


package com.loc.electronics_store.repository;

import com.loc.electronics_store.entity.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {
    List<UserCoupon> findByUser_Id(Long userId);

    List<UserCoupon> findByCoupon_Id(Long couponId);
}


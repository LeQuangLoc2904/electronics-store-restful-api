package com.loc.electronics_store.repository;

import com.loc.electronics_store.entity.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {
    List<UserCoupon> findByUser_Id(Long userId);

    List<UserCoupon> findByCoupon_Id(Long couponId);

    Optional<UserCoupon> findByUser_IdAndCoupon_IdAndOrderIdIsNull(Long userId, Long couponId);

    List<UserCoupon> findByUser_IdAndOrderIdNull(Long userId);

    @Query("SELECT uc FROM UserCoupon uc WHERE uc.user.id = :userId " +
                                        "AND uc.coupon.id = :couponId " +
                                        "AND uc.orderId IS NULL")
    Optional<UserCoupon> findActiveCouponByUserId(@Param("userId") Long userId, @Param("couponId") Long couponId);
}


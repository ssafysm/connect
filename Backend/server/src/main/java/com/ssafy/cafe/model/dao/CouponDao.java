package com.ssafy.cafe.model.dao;

import java.util.List;

import com.ssafy.cafe.model.dto.Coupon;

public interface CouponDao {

    List<Coupon> getCoupons(String userId);

    int setInitCoupon(Coupon coupon);

    int deleteCoupon(int couponId);

    // 쿠폰 지급을 위한 메서드 추가
    int insertCoupon(Coupon coupon);
}
package com.ssafy.cafe.model.service;

import java.util.List;

import com.ssafy.cafe.model.dto.Coupon;
import com.ssafy.cafe.model.dto.CouponTemplate;

public interface CouponService {

    List<Coupon> getCoupons(String userId);

    int setInitCoupon(Coupon coupon);

    int deleteCoupon(int couponId);

    // 새로운 메서드 추가
    List<CouponTemplate> getCouponTemplates();

    int giveCouponToUser(String userId, Integer couponTemplateId);
}
package com.ssafy.cafe.model.service;

import java.util.List;

import com.ssafy.cafe.model.dto.Coupon;

public interface CouponService {
	
	List<Coupon> getCoupons(String userId);
	
	int setInitCoupon(Coupon coupon);
	
	int deleteCoupon(int couponId);

}

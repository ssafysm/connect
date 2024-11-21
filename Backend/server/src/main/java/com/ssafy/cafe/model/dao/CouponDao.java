package com.ssafy.cafe.model.dao;

import java.util.List;

import com.ssafy.cafe.model.dto.Coupon;

public interface CouponDao {
	
	List<Coupon> getCoupons(String userId);
	
	int setInitCoupon(Coupon coupon);

}

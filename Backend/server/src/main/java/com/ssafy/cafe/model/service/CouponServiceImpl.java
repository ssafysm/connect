package com.ssafy.cafe.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.cafe.model.dao.CouponDao;
import com.ssafy.cafe.model.dto.Coupon;

@Service
public class CouponServiceImpl implements CouponService {
	
	@Autowired
	CouponDao cDao;

	@Override
	public List<Coupon> getCoupons(String userId) {
		return cDao.getCoupons(userId);
	}
	
	@Override
	public int setInitCoupon(Coupon coupon) {
		return cDao.setInitCoupon(coupon);
	}

}

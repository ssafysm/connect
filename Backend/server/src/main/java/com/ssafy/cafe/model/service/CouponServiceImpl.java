package com.ssafy.cafe.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.cafe.model.dao.CouponDao;
import com.ssafy.cafe.model.dao.CouponTemplateDao;
import com.ssafy.cafe.model.dto.Coupon;
import com.ssafy.cafe.model.dto.CouponTemplate;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    CouponDao cDao;

    @Autowired
    CouponTemplateDao ctDao;

    @Override
    public List<Coupon> getCoupons(String userId) {
        return cDao.getCoupons(userId);
    }

    @Override
    public int setInitCoupon(Coupon coupon) {
        return cDao.setInitCoupon(coupon);
    }

    @Override
    public int deleteCoupon(int couponId) {
        return cDao.deleteCoupon(couponId);
    }

    @Override
    public List<CouponTemplate> getCouponTemplates() {
        return ctDao.getCouponTemplates();
    }

    @Override
    public int giveCouponToUser(String userId, Integer couponTemplateId) {
        CouponTemplate template = ctDao.selectCouponTemplateById(couponTemplateId);
        if (template == null) {
            return 0;
        }
        Coupon coupon = new Coupon(userId, template.getName(), template.getDescription(), template.getImage(), template.getMenuId(), template.getMenuCount());
        return cDao.setInitCoupon(coupon);
    }
}
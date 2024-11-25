package com.ssafy.cafe.model.dao;

import java.util.List;
import com.ssafy.cafe.model.dto.CouponTemplate;

public interface CouponTemplateDao {
    List<CouponTemplate> getCouponTemplates();
    CouponTemplate selectCouponTemplateById(Integer id);
}

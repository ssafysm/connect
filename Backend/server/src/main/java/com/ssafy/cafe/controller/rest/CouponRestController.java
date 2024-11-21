package com.ssafy.cafe.controller.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.cafe.model.dto.Coupon;
import com.ssafy.cafe.model.service.CouponService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/rest/coupon")
@CrossOrigin("*")
public class CouponRestController {

    private static final Logger log = LoggerFactory.getLogger(CouponRestController.class);

    @Autowired
    CouponService cs;
    
    @Operation(summary = "{userId}에 해당하는 사용자가 보유한 쿠폰 정보를 가지고 온다.")
    @GetMapping("/byUser")
    public ResponseEntity<?> getEvents(@RequestParam String userId) {
        List<Coupon> result = cs.getCoupons(userId);
        
        return ResponseEntity.ok(result);
    }
    
    @DeleteMapping("/{couponId}")
    public ResponseEntity<?> deleteCoupon(@PathVariable String couponId) {
    	int result = cs.deleteCoupon(Integer.parseInt(couponId));
    	
    	if (result == 1) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }

}

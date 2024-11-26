package com.ssafy.cafe.controller.rest;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.cafe.model.dto.Coupon;
import com.ssafy.cafe.model.dto.CouponTemplate;
import com.ssafy.cafe.model.dto.Alarm;
import com.ssafy.cafe.model.service.AlarmService;
import com.ssafy.cafe.model.service.CouponService;
import com.ssafy.cafe.model.service.FcmService;
import com.ssafy.cafe.model.service.FirebaseCloudMessageService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/rest/coupon")
@CrossOrigin("*")
public class CouponRestController {

    @Autowired
    CouponService cs;

    @Autowired
    private FcmService fcmService;

    @Autowired
    private FirebaseCloudMessageService firebaseCloudMessageService;

    @Autowired
    private AlarmService alarmService;

    @Operation(summary = "{userId}에 해당하는 사용자가 보유한 쿠폰 정보를 가지고 온다.")
    @GetMapping("/byUser")
    public ResponseEntity<?> getCouponsByUser(@RequestParam String userId) {
        List<Coupon> result = cs.getCoupons(userId);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "사용 가능한 모든 쿠폰 템플릿 목록을 반환한다.")
    @GetMapping("/templates")
    public ResponseEntity<?> getCouponTemplates() {
        List<CouponTemplate> templates = cs.getCouponTemplates();
        return ResponseEntity.ok(templates);
    }

    @Operation(summary = "특정 사용자에게 쿠폰을 지급한다.")
    @PostMapping("/giveCoupon")
    public ResponseEntity<?> giveCouponToUser(@RequestParam String userId, @RequestParam Integer couponTemplateId) {
        int result = cs.giveCouponToUser(userId, couponTemplateId);
        if (result == 1) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(500).body(false);
        }
    }

    @Operation(summary = "특정 사용자에게 쿠폰을 지급하고, 알림을 전송 및 기록한다.")
    @PostMapping("/giveCouponWithNotification")
    public ResponseEntity<?> giveCouponWithNotification(@RequestParam String userId, @RequestParam Integer couponTemplateId, @RequestParam String title, @RequestParam String body) {
        int result = cs.giveCouponToUser(userId, couponTemplateId);
        if (result == 1) {
            // 알림 전송 및 기록
            List<String> tokens = fcmService.getTokensByUserId(userId);
            alarmService.addAlarm(new Alarm(userId, title, body));
            int successCount = 0;
            for (String token : tokens) {
                try {
                    firebaseCloudMessageService.sendMessageTo(token, title, body);
                    successCount++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (successCount > 0) {
                return ResponseEntity.ok("쿠폰 지급 및 메시지 전송 성공: " + successCount + "건");
            } else {
                return ResponseEntity.status(500).body("쿠폰 지급은 성공했으나 메시지 전송 실패");
            }
        } else {
            return ResponseEntity.status(500).body("쿠폰 지급 실패");
        }
    }
    @Operation(summary = "특정 사용자 ID로 쿠폰 데이터를 삭제한다.")
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
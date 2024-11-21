package com.ssafy.cafe.controller.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.cafe.model.dto.Coupon;
import com.ssafy.cafe.model.dto.Grade;
import com.ssafy.cafe.model.dto.Order;
import com.ssafy.cafe.model.dto.User;
import com.ssafy.cafe.model.dto.UserInfo;
import com.ssafy.cafe.model.service.CouponService;
import com.ssafy.cafe.model.service.OrderService;
import com.ssafy.cafe.model.service.StampService;
import com.ssafy.cafe.model.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/rest/user")
@CrossOrigin("*")
@Tag(name = "user controller", description = "사용자 로그인 등 사용자 기능을 정의한다.")
public class UserRestController {

    private static final Logger log = LoggerFactory.getLogger(UserRestController.class);

    @Autowired
    UserService userService;

    @Autowired
    StampService stampService;

    @Autowired
    OrderService orderService;
    
    @Autowired
    CouponService couponService;

    @Operation(summary = "사용자 정보를 추가한다.")
    @PostMapping("")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User newUser = new User(user.getId(), user.getName(), user.getPass(), 0);
            int result = userService.join(newUser);

            if (result == 1) {
            	// 회원 가입 시 천원짜리 쿠폰 1장 주기
            	couponService.setInitCoupon(new Coupon(user.getId(), "아이스 아메리카노 1잔", "회원 가입 성공 기념 쿠폰", "americano_coupon.png", 1, 1));
                return ResponseEntity.ok(true);
            } else {
                return ResponseEntity.ok(false);
            }
        } catch (Exception e) {
            log.error("사용자 등록 오류: ", e);
            return ResponseEntity.ok(false);
        }
    }

    @Operation(summary = "사용자의 비밀번호를 변경한다.")
    @PutMapping("/password")
    public ResponseEntity<?> updatePassword(@RequestBody User user) {
    	try {
            User newUser = new User(user.getId(), user.getName(), user.getPass(), 0);
            int result = userService.updatePassword(newUser);

            if (result == 1) {
                return ResponseEntity.ok(true);
            } else {
                return ResponseEntity.ok(false);
            }
        } catch (Exception e) {
            log.error("사용자 등록 오류: ", e);
            return ResponseEntity.ok(false);
        }
    }

    @Operation(summary = "로그인 처리. 로그인 성공 시 쿠키(ssafy_id)를 내려보낸다.")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user, HttpServletResponse response) {
        User result = userService.login(user.getId(), user.getPass());
        if (result != null) {
            Cookie cookie = new Cookie("ssafy_id", user.getId());
            cookie.setMaxAge(-1);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);

            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(result);
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @Operation(summary = "사용자의 정보와 함께 사용자의 주문 내역, 사용자 등급 정보를 반환한다. 로그인된 상태여야 한다.")
    @GetMapping("/info")
    public ResponseEntity<?> getUser(
    		@RequestParam(required = false, value = "id") String id,
    		HttpServletRequest request) {
        // Check for 'ssafy_id' cookie
        String loginId = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("ssafy_id".equals(cookie.getName())) {
                    loginId = cookie.getValue();
                    break;
                }
            }
        }
        if (loginId == null) {
            return ResponseEntity.ok(null);
        }

        User nowUser = userService.selectUser(loginId);
        if (nowUser == null) {
            return ResponseEntity.ok(null);
        }
        UserInfo result = new UserInfo();
        int stamps = nowUser.getStamps();
        List<Order> nowOrders = orderService.getOrderByUser(loginId);
        Grade nowGrade = calculateGrade(stamps);

        result.setUser(nowUser);
        result.setOrder(nowOrders);
        result.setGrade(nowGrade);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "사용자의 정보와 함께 사용자의 주문 내역, 사용자 등급 정보를 반환한다.")
    @PostMapping("/info")
    public ResponseEntity<?> postUserInfo(@RequestBody User user) {
        User nowUser = userService.login(user.getId(), user.getPass());
        if (nowUser == null) {
            return ResponseEntity.ok(null);
        }
        UserInfo result = new UserInfo();
        int stamps = nowUser.getStamps();
        List<Order> nowOrders = orderService.getOrderByUser(user.getId());
        Grade nowGrade = calculateGrade(stamps);

        result.setUser(nowUser);
        result.setOrder(nowOrders);
        result.setGrade(nowGrade);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "id가 사용 중인지 확인한다.")
    @GetMapping("/isUsed")
    public ResponseEntity<?> isUsed(@RequestParam String id) {
        boolean result = userService.isUsedId(id);
        return ResponseEntity.ok(result);
    }

    private Grade calculateGrade(int stamps) {
        Grade nowGrade = new Grade();
        if (stamps < 10) {
            nowGrade.setStepMax(10);
            nowGrade.setStep(stamps);
            nowGrade.setTo(10 - stamps);
            nowGrade.setTitle("BEGINNER");
        } else if (stamps < 150) {
            nowGrade.setStepMax(150);
            nowGrade.setStep(stamps - 10);
            nowGrade.setTo(150 - stamps);
            nowGrade.setTitle("SILVER");
        } else if (stamps < 350) {
            nowGrade.setStepMax(350);
            nowGrade.setStep(stamps - 150);
            nowGrade.setTo(350 - stamps);
            nowGrade.setTitle("GOLD");
        } else {
            nowGrade.setTitle("PLATINUM");
            nowGrade.setStep(1);
            nowGrade.setStepMax(1);
            nowGrade.setTo(0);
        }
        return nowGrade;
    }
}

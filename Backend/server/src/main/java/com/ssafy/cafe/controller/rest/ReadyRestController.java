package com.ssafy.cafe.controller.rest;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ssafy.cafe.model.dto.OrderWithInfo;
import com.ssafy.cafe.model.dto.OrderDetailWithInfo;
import com.ssafy.cafe.model.dto.Ready;
import com.ssafy.cafe.model.service.FcmService;
import com.ssafy.cafe.model.service.FirebaseCloudMessageService;
import com.ssafy.cafe.model.service.OrderService;
import com.ssafy.cafe.model.service.ReadyService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/rest/ready")
@CrossOrigin("*")
public class ReadyRestController {

    @Autowired
    private ReadyService readyService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private FcmService fcmService;

    @Autowired
    private FirebaseCloudMessageService firebaseCloudMessageService;

    @Operation(summary = "새 픽업 대기 상태를 추가한다.")
    @PostMapping
    public ResponseEntity<?> addReady(@RequestBody Ready ready) {
        readyService.addReady(ready);
        return ResponseEntity.ok(true);
    }

    @Operation(summary = "특정 주문의 픽업 상태를 업데이트하고, 사용자에게 알림을 보낸다.")
    @PutMapping("/{orderId}")
    public ResponseEntity<?> updatePickUpStatus(@PathVariable Integer orderId, @RequestBody Boolean pickUp) {
        readyService.updatePickUpStatus(orderId, pickUp);

        if (pickUp) {
            // 주문 정보 가져오기
            OrderWithInfo order = orderService.getOrderWithInfo(orderId);
            String userId = order.getUserId();

            // 사용자 FCM 토큰 가져오기
            List<String> tokens = fcmService.getTokensByUserId(userId);

            if (tokens != null && !tokens.isEmpty()) {
                // 주문한 상품 목록 생성
                StringBuilder itemsList = new StringBuilder();
                for (OrderDetailWithInfo detail : order.getDetails()) {
                    if (itemsList.length() > 0) {
                        itemsList.append(", ");
                    }
                    itemsList.append(detail.getName());
                    if (detail.getQuantity() > 1) {
                        itemsList.append(" x ").append(detail.getQuantity());
                    }
                }
                String messageBody = "[" + itemsList.toString() + "]이(가) 준비되었습니다.";

                // FCM 알림 전송
                for (String token : tokens) {
                    try {
                        firebaseCloudMessageService.sendMessageTo(token, "주문 준비 완료", messageBody);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return ResponseEntity.ok(true);
    }

    @Operation(summary = "모든 픽업 대기 상태를 조회한다.")
    @GetMapping
    public ResponseEntity<?> getReadyList() {
        List<Ready> readyList = readyService.getReadyList();
        return ResponseEntity.ok(readyList);
    }

    @Operation(summary = "특정 주문의 픽업 상태를 조회한다.")
    @GetMapping("/{orderId}")
    public ResponseEntity<?> getReadyByOrderId(@PathVariable Integer orderId) {
        Ready ready = readyService.getReadyByOrderId(orderId);
        return ResponseEntity.ok(ready);
    }

    @Operation(summary = "픽업이 준비되지 않은 주문들의 상세 정보를 반환한다.")
    @GetMapping("/pendingOrders")
    public ResponseEntity<?> getPendingOrders() {
        List<OrderWithInfo> pendingOrders = readyService.getPendingOrders();
        return ResponseEntity.ok(pendingOrders);
    }
}

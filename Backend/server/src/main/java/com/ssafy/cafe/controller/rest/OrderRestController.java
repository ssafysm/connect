package com.ssafy.cafe.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.cafe.model.dto.Order;
import com.ssafy.cafe.model.dto.OrderWithInfo;
import com.ssafy.cafe.model.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/rest/order")
@CrossOrigin("*")
public class OrderRestController {

    @Autowired
    private OrderService oService;

    @Operation(summary = "주문 객체를 저장하고 추가된 Order의 id를 반환한다.")
    @PostMapping
    public ResponseEntity<?> order(@RequestBody Order order) {
        oService.makeOrder(order);
        return ResponseEntity.ok(order.getId());
    }

    @Operation(summary = "{orderId}에 해당하는 주문의 상세 내역을 반환한다.")
    @GetMapping("/{orderId}")
    public ResponseEntity<?> selectOrderDetailWithInfo(@PathVariable Integer orderId) {
        OrderWithInfo order = oService.getOrderWithInfo(orderId);
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/byUser")
    @Operation(summary = "{id}에 해당하는 사용자의 최근 1개월간 주문 내역을 반환한다.")
    public ResponseEntity<?> getLastMonthOrder(@RequestParam String id) {
        List<OrderWithInfo> result = oService.getLastMonthOrder(id); // 반환 타입 일치
        return ResponseEntity.ok(result);
    }


    @GetMapping("/byUserIn6Months")
    @Operation(summary = "최근 6개월간 주문 내역을 반환한다.")
    public ResponseEntity<?> getLast6MonthOrder(@RequestParam String id) {
        List<OrderWithInfo> result = oService.getLast6MonthOrder(id);
        return ResponseEntity.ok(result);
    }

}

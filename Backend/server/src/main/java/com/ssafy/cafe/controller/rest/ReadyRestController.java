package com.ssafy.cafe.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.cafe.model.dto.Ready;
import com.ssafy.cafe.model.service.ReadyService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/rest/ready")
@CrossOrigin("*")
public class ReadyRestController {

    @Autowired
    private ReadyService readyService;

    @Operation(summary = "새 픽업 대기 상태를 추가한다.")
    @PostMapping
    public ResponseEntity<?> addReady(@RequestBody Ready ready) {
        readyService.addReady(ready);
        return ResponseEntity.ok(true);
    }

    @Operation(summary = "특정 주문의 픽업 상태를 업데이트한다.")
    @PutMapping("/{orderId}")
    public ResponseEntity<?> updatePickUpStatus(@PathVariable Integer orderId, @RequestBody Boolean pickUp) {
        readyService.updatePickUpStatus(orderId, pickUp);
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
}

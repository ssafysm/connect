package com.ssafy.cafe.controller.rest;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ssafy.cafe.model.service.FcmService;
import com.ssafy.cafe.model.service.FirebaseCloudMessageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/rest/fcm")
@CrossOrigin("*")
@Tag(name = "FCM Controller", description = "FCM 토큰 관리를 위한 API")
public class FcmRestController {

    @Autowired
    private FcmService fcmService;

    @Autowired
    private FirebaseCloudMessageService firebaseCloudMessageService;

    @Operation(summary = "FCM 토큰을 추가한다.")
    @PostMapping("/add")
    public ResponseEntity<?> addToken(@RequestParam String userId, @RequestParam String fcmToken) {
        int result = fcmService.addToken(userId, fcmToken);
        if (result > 0) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(500).body(false);
        }
    }

    @Operation(summary = "FCM 토큰을 삭제한다.")
    @PostMapping("/remove")
    public ResponseEntity<?> removeToken(@RequestParam String userId, @RequestParam String fcmToken) {
        int result = fcmService.removeToken(userId, fcmToken);
        if (result > 0) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(500).body(false);
        }
    }

    @Operation(summary = "사용자의 FCM 토큰 리스트를 반환한다.")
    @GetMapping("/tokens")
    public ResponseEntity<?> getTokens(@RequestParam String userId) {
        List<String> tokens = fcmService.getTokensByUserId(userId);
        return ResponseEntity.ok(tokens);
    }

    @Operation(summary = "특정 토큰으로 FCM 메시지를 전송한다.")
    @PostMapping("/sendMessage")
    public ResponseEntity<?> sendMessage(@RequestParam String token, @RequestParam String title, @RequestParam String body) {
        try {
            firebaseCloudMessageService.sendMessageTo(token, title, body);
            return ResponseEntity.ok("메시지 전송 성공");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("메시지 전송 실패");
        }
    }

    @Operation(summary = "특정 유저의 모든 토큰으로 FCM 메시지를 전송한다.")
    @PostMapping("/sendMessageToUser")
    public ResponseEntity<?> sendMessageToUser(@RequestParam String userId, @RequestParam String title, @RequestParam String body) {
        List<String> tokens = fcmService.getTokensByUserId(userId);
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
            return ResponseEntity.ok("메시지 전송 성공: " + successCount + "건");
        } else {
            return ResponseEntity.status(500).body("메시지 전송 실패");
        }
    }

    @Operation(summary = "모든 토큰으로 FCM 메시지를 전송한다.")
    @PostMapping("/broadcast")
    public ResponseEntity<?> broadcast(@RequestParam String title, @RequestParam String body) {
        List<String> tokens = fcmService.getAllTokens();
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
            return ResponseEntity.ok("메시지 전송 성공: " + successCount + "건");
        } else {
            return ResponseEntity.status(500).body("메시지 전송 실패");
        }
    }

}

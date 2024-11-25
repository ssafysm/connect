package com.ssafy.cafe.controller.rest;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.cafe.model.dto.Alarm;
import com.ssafy.cafe.model.dto.Chat;
import com.ssafy.cafe.model.dto.UploadPlanRequest;
import com.ssafy.cafe.model.service.AlarmService;
import com.ssafy.cafe.model.service.ChatGptService;
import com.ssafy.cafe.model.service.ChatService;
import com.ssafy.cafe.model.service.FcmService;
import com.ssafy.cafe.model.service.FirebaseCloudMessageService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/rest/chat")
@CrossOrigin("*")
public class ChatRestController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private FcmService fcmService;

    @Autowired
    private FirebaseCloudMessageService firebaseCloudMessageService;

    @Autowired
    private ChatGptService chatGptService;

    @Autowired
    private AlarmService alarmService;

    @Operation(summary = "손글씨 이미지를 업로드하고 계획을 저장한다.")
    @PostMapping("/upload")
    public ResponseEntity<?> uploadPlan(@RequestBody UploadPlanRequest request) {
        // ChatGPT에게 이미지를 기반으로 조언 요청
        String chatGptResponse = "";
        try {
            String prompt = "다음은 이미지로 작성된 계획입니다: " + request.getBase64Image() + "\n이 계획에 대한 조언을 제공해 주세요.";
            chatGptResponse = chatGptService.getSummaryFromChatGpt(prompt);
        } catch (IOException e) {
            e.printStackTrace();
            // 오류 처리
        }

        // level에 따른 알람 주기 설정
        Integer alertIntervalMinutes = null;
        if (request.getLevel() == 1) {
            alertIntervalMinutes = 60;
        } else if (request.getLevel() == 2) {
            alertIntervalMinutes = 30;
        }

        // 다음 알람 시간 계산
        Timestamp nextAlarmTime = null;
        if (alertIntervalMinutes != null) {
            nextAlarmTime = Timestamp.valueOf(LocalDateTime.now().plusMinutes(alertIntervalMinutes));
        }

        Chat chat = new Chat();
        chat.setUserId(request.getUserId());
        chat.setLevel(request.getLevel());
        chat.setPlan(request.getBase64Image()); // Base64 문자열을 저장
        chat.setAlertIntervalMinutes(alertIntervalMinutes);
        chat.setNextAlarmTime(nextAlarmTime);

        // 기존 채팅이 존재하면 업데이트, 아니면 추가
        Chat existingChat = chatService.getChatByUserId(request.getUserId());
        if (existingChat != null) {
            chat.setId(existingChat.getId());
            chatService.updateChat(chat);
        } else {
            chatService.addChat(chat);
        }

        return ResponseEntity.ok("계획이 성공적으로 업로드되었습니다.\nChatGPT의 응답: " + chatGptResponse);
    }

    @Operation(summary = "텍스트 계획을 입력하고 저장한다.")
    @PostMapping("/text")
    public ResponseEntity<?> addTextPlan(@RequestBody Chat chatRequest) {
        // ChatGPT에게 계획에 대한 조언 요청
        String chatGptResponse = "";
        try {
            String prompt = "다음은 계획입니다: " + chatRequest.getPlan() + "\n이 계획에 대한 조언을 제공해 주세요.";
            chatGptResponse = chatGptService.getSummaryFromChatGpt(prompt);
        } catch (IOException e) {
            e.printStackTrace();
            // 오류 처리
        }

        // level에 따른 알람 주기 설정
        Integer alertIntervalMinutes = null;
        if (chatRequest.getLevel() == 1) {
            alertIntervalMinutes = 60;
        } else if (chatRequest.getLevel() == 2) {
            alertIntervalMinutes = 30;
        }

        // 다음 알람 시간 계산
        Timestamp nextAlarmTime = null;
        if (alertIntervalMinutes != null) {
            nextAlarmTime = Timestamp.valueOf(LocalDateTime.now().plusMinutes(alertIntervalMinutes));
        }

        Chat chat = new Chat();
        chat.setUserId(chatRequest.getUserId());
        chat.setLevel(chatRequest.getLevel());
        chat.setPlan(chatRequest.getPlan());
        chat.setAlertIntervalMinutes(alertIntervalMinutes);
        chat.setNextAlarmTime(nextAlarmTime);

        // 기존 채팅이 존재하면 업데이트, 아니면 추가
        Chat existingChat = chatService.getChatByUserId(chatRequest.getUserId());
        if (existingChat != null) {
            chat.setId(existingChat.getId());
            chatService.updateChat(chat);
        } else {
            chatService.addChat(chat);
        }

        return ResponseEntity.ok("계획이 성공적으로 추가되었습니다.\nChatGPT의 응답: " + chatGptResponse);
    }

    @Operation(summary = "진행 상황을 업데이트한다.")
    @PostMapping("/progress/{userId}")
    public ResponseEntity<?> updateProgress(@PathVariable String userId, @RequestBody String progress) {
        Chat chat = chatService.getChatByUserId(userId);
        if (chat == null) {
            return ResponseEntity.status(404).body("해당 유저의 계획을 찾을 수 없습니다.");
        }

        chat.setProgress(progress);

        // 초기 계획과 진행 상황을 ChatGPT에게 전달하여 조언 받기
        String advice = "";
        try {
            String prompt = "계획: " + chat.getPlan() + "\n진행 상황: " + progress + "\n계획과 진행 상황에 기반한 조언을 제공해 주세요.";
            advice = chatGptService.getSummaryFromChatGpt(prompt);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("ChatGPT로부터 조언을 받지 못했습니다.");
        }

        // FCM을 통해 사용자에게 조언 전달
        List<String> tokens = fcmService.getTokensByUserId(chat.getUserId());
        for (String token : tokens) {
            try {
                alarmService.addAlarm(new Alarm(chat.getUserId(), "계획 조언", advice));
                firebaseCloudMessageService.sendMessageTo(token, "계획 조언", advice);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 다음 알람 시간 재설정
        Integer alertIntervalMinutes = chat.getAlertIntervalMinutes();
        if (alertIntervalMinutes != null) {
            chat.setNextAlarmTime(Timestamp.valueOf(LocalDateTime.now().plusMinutes(alertIntervalMinutes)));
        }

        chatService.updateChat(chat);

        return ResponseEntity.ok("진행 상황이 업데이트되었으며, 조언이 전송되었습니다.");
    }
}

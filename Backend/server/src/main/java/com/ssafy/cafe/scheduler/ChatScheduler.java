package com.ssafy.cafe.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ssafy.cafe.model.dto.Chat;
import com.ssafy.cafe.model.service.ChatService;
import com.ssafy.cafe.model.service.FcmService;
import com.ssafy.cafe.model.service.FirebaseCloudMessageService;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Component
public class ChatScheduler {

    @Autowired
    private ChatService chatService;

    @Autowired
    private FcmService fcmService;

    @Autowired
    private FirebaseCloudMessageService firebaseCloudMessageService;

    @Scheduled(fixedRate = 60000) // 매 1분마다 실행
    public void checkPendingAlarms() {
        List<Chat> pendingChats = chatService.getPendingAlarms();
        for (Chat chat : pendingChats) {
            // 알람 메시지 생성
            String message = (chat.getProgress() == null) ? "계획은 잘 진행되고 있나요?" : "진행 상황을 업데이트 해주세요!";
            String title = "계획 알림";

            // 사용자 토큰 가져오기
            List<String> tokens = fcmService.getTokensByUserId(chat.getUserId());

            for (String token : tokens) {
                try {
                    // FCM 메시지 전송
                    firebaseCloudMessageService.sendMessageTo(token, title, message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // 다음 알람 시간 업데이트
            Integer alertIntervalMinutes = chat.getAlertIntervalMinutes();
            if (alertIntervalMinutes != null) {
                chat.setNextAlarmTime(new Timestamp(System.currentTimeMillis() + alertIntervalMinutes * 60000));
                chatService.updateChat(chat);
            }
        }
    }
}

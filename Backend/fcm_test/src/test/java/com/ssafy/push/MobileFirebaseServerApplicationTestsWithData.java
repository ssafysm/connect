package com.ssafy.push;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ssafy.push.service.FirebaseCloudMessageServiceWithData;
import com.ssafy.push.service.FirebaseCloudMessageService;

@SpringBootTest
class MobileFirebaseServerApplicationTestsWithData {

    @Autowired
    FirebaseCloudMessageService service;

    @Autowired
    FirebaseCloudMessageServiceWithData dataService;

    
    //Notification아니라 Data에 데이터 담아서 전송함. 
    @Test
    void sendDataMessage() throws IOException {
    	
        String token = "dHU5Gc1qQ1yPbfL0tvFffH:APA91bHEgrSUbPQwWul68hjPqT3iTljLG7T6Mdt-GtpM2zVO6uRR-QTzvSRM3Vk1ukucHrakQOPNQ6-h10Pj1dTqGHr-id7-1xvnTCu1HEGUsgRB6SS5uQKKgzjPFVVG9CSEKVCc27G8";
//        String token1 = "cHI9bL4QQTKQI_nJVtoUt7:APA91bF-VKnKY0NxeikBXORc0FhmS3iRHFYZgH5yBF34GNt-tYaUCvABVal1CSibhAEWkj9DDcmpZWlD4lA481JoC_UjVnykSYhyd5OHi3-91E-kVlX4YjEEK2v0fTQlqoW35yjNnlrd";

//		한건 메시지
        dataService.sendDataMessageTo(token, "from 사무국", "싸피 여러분 화이팅!!");
        
//		멀티 메시지        
//        dataService.addToken(token);
//        service.addToken(token1);
        
//        service.broadCastMessage("from 사무국1", "싸피 여러분 화이팅!!!!!!!!");
    }
}

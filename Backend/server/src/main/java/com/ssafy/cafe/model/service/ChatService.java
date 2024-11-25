package com.ssafy.cafe.model.service;

import java.util.List;

import com.ssafy.cafe.model.dto.Chat;

public interface ChatService {
    int addChat(Chat chat);
    int updateChat(Chat chat);
    Chat getChatById(Integer id);
    Chat getChatByUserId(String userId); // 추가된 메서드
    List<Chat> getAllChats();
    List<Chat> getPendingAlarms();
    int deleteChat(Integer id);
    int deleteChatByUserId(String userId); // 추가된 메서드
}

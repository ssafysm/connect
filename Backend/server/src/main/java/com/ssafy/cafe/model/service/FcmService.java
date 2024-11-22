package com.ssafy.cafe.model.service;

import java.util.List;

public interface FcmService {
    int addToken(String userId, String fcmToken);
    int removeToken(String userId, String fcmToken);
    List<String> getTokensByUserId(String userId);
    List<String> getAllTokens();
    List<String> getAllUserIds(); // 추가된 메서드
}

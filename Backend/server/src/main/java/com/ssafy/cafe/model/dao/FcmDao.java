package com.ssafy.cafe.model.dao;

import java.util.List;
import com.ssafy.cafe.model.dto.Fcm;

public interface FcmDao {
    int insert(Fcm fcm);
    int delete(Fcm fcm);
    List<String> selectTokensByUserId(String userId);
    List<String> selectAllTokens(); // 추가된 메서드
    List<String> selectAllUserIds(); // 추가된 메서드
}

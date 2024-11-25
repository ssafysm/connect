package com.ssafy.cafe.model.dao;

import java.util.List;

import com.ssafy.cafe.model.dto.Chat;

public interface ChatDao {
    int insert(Chat chat);
    int update(Chat chat);
    Chat selectById(Integer id);
    Chat selectByUserId(String userId); // 추가된 메서드
    List<Chat> selectAll();
    List<Chat> selectPendingAlarms();
    int delete(Integer id);
    int deleteByUserId(String userId); // 추가된 메서드
}

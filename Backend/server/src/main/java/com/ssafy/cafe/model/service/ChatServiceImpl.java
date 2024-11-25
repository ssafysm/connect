package com.ssafy.cafe.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.cafe.model.dao.ChatDao;
import com.ssafy.cafe.model.dto.Chat;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatDao chatDao;

    @Override
    public int addChat(Chat chat) {
        return chatDao.insert(chat);
    }

    @Override
    public int updateChat(Chat chat) {
        return chatDao.update(chat);
    }

    @Override
    public Chat getChatById(Integer id) {
        return chatDao.selectById(id);
    }

    @Override
    public Chat getChatByUserId(String userId) {
        return chatDao.selectByUserId(userId);
    }

    @Override
    public List<Chat> getAllChats() {
        return chatDao.selectAll();
    }

    @Override
    public List<Chat> getPendingAlarms() {
        return chatDao.selectPendingAlarms();
    }

    @Override
    public int deleteChat(Integer id) {
        return chatDao.delete(id);
    }

    @Override
    public int deleteChatByUserId(String userId) {
        return chatDao.deleteByUserId(userId);
    }
}

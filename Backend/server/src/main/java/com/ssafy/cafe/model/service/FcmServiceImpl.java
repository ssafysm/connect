package com.ssafy.cafe.model.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ssafy.cafe.model.dao.FcmDao;
import com.ssafy.cafe.model.dto.Fcm;

@Service
public class FcmServiceImpl implements FcmService {

    @Autowired
    private FcmDao fcmDao;

    @Override
    public int addToken(String userId, String fcmToken) {
        Fcm fcm = new Fcm(userId, fcmToken);
        return fcmDao.insert(fcm);
    }

    @Override
    public int removeToken(String userId, String fcmToken) {
        Fcm fcm = new Fcm(userId, fcmToken);
        return fcmDao.delete(fcm);
    }

    @Override
    public List<String> getTokensByUserId(String userId) {
        return fcmDao.selectTokensByUserId(userId);
    }

    @Override
    public List<String> getAllTokens() {
        return fcmDao.selectAllTokens();
    }
}

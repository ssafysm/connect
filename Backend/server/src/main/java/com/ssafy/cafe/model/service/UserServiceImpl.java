package com.ssafy.cafe.model.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.cafe.model.dao.UserDao;
import com.ssafy.cafe.model.dto.User;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public int join(User user) {
        return userDao.insert(user);
    }

    @Override
    public User login(String id, String pass) {
        return userDao.selectByUser(new User(id, "", pass, 0));
    }

    @Override
    public boolean isUsedId(String id) {
        log.info("Checking ID: {}", id);
        User user = userDao.selectById(id);
        return user != null; // 사용 중이면 true 반환
    }

    @Override
    public User selectUser(String id) {
        return userDao.selectById(id);
    }
}

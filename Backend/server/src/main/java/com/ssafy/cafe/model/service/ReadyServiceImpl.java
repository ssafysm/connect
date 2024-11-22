package com.ssafy.cafe.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.cafe.model.dao.ReadyDao;
import com.ssafy.cafe.model.dto.Ready;

@Service
public class ReadyServiceImpl implements ReadyService {

    @Autowired
    private ReadyDao readyDao;

    @Override
    public void addReady(Ready ready) {
        readyDao.insert(ready);
    }

    @Override
    public void updatePickUpStatus(Integer orderId, Boolean pickUp) {
        Ready ready = new Ready(orderId, pickUp);
        readyDao.update(ready);
    }

    @Override
    public List<Ready> getReadyList() {
        return readyDao.selectAll();
    }

    @Override
    public Ready getReadyByOrderId(Integer orderId) {
        return readyDao.selectByOrderId(orderId);
    }
}

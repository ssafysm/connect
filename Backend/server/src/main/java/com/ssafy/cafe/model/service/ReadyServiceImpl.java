package com.ssafy.cafe.model.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ssafy.cafe.model.dao.OrderDao;
import com.ssafy.cafe.model.dao.ReadyDao;
import com.ssafy.cafe.model.dto.OrderWithInfo;
import com.ssafy.cafe.model.dto.Ready;

@Service
public class ReadyServiceImpl implements ReadyService {

    @Autowired
    private ReadyDao readyDao;

    @Autowired
    private OrderDao orderDao;

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

    @Override
    public List<OrderWithInfo> getPendingOrders() {
        List<Ready> readyList = readyDao.selectByPickUp(false);
        List<OrderWithInfo> pendingOrders = new ArrayList<>();
        for (Ready ready : readyList) {
            OrderWithInfo order = orderDao.selectOrderWithInfo(ready.getOId());
            pendingOrders.add(order);
        }
        return pendingOrders;
    }
    
    @Override
    public List<OrderWithInfo> getCompletedOrders(String userId) {
        List<OrderWithInfo> userOrders = orderDao.selectByUserWithInfo(userId);
        List<OrderWithInfo> completedOrders = new ArrayList<>();

        for (OrderWithInfo order : userOrders) {
            Ready ready = readyDao.selectByOrderId(order.getId());
            if (ready != null && Boolean.TRUE.equals(ready.getPickUp())) {
                completedOrders.add(order);
            }
        }

        return completedOrders;
    }
}

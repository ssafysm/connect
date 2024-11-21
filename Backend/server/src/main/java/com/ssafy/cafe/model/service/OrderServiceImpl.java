package com.ssafy.cafe.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.cafe.model.dao.OrderDao;
import com.ssafy.cafe.model.dao.OrderDetailDao;
import com.ssafy.cafe.model.dao.StampDao;
import com.ssafy.cafe.model.dao.UserDao;
import com.ssafy.cafe.model.dto.Order;
import com.ssafy.cafe.model.dto.OrderDetail;
import com.ssafy.cafe.model.dto.OrderDetailWithInfo;
import com.ssafy.cafe.model.dto.OrderWithInfo;
import com.ssafy.cafe.model.dto.Stamp;
import com.ssafy.cafe.model.dto.User;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao oDao;

    @Autowired
    OrderDetailDao dDao;

    @Autowired
    StampDao sDao;

    @Autowired
    UserDao uDao;

    @Override
    @Transactional
    public void makeOrder(Order order) {
        User user = uDao.selectById(order.getUserId());
        oDao.insert(order);
        int orderId = order.getId(); // DB에 저장된 주문 ID 가져오기

        for (OrderDetail od : order.getDetails()) {
            od.setOrderId(orderId);
            dDao.insert(od);
            sDao.insert(new Stamp(order.getUserId(), orderId, od.getQuantity()));
            user.setStamps(user.getStamps() + od.getQuantity());
        }

        uDao.updateStamp(user);
    }

    @Override
    public Order getOrderWithDetails(Integer orderId) {
        Order order = oDao.select(orderId);
        List<OrderDetailWithInfo> detailsWithInfo = oDao.selectOrderDetailWithInfo(orderId);
        order.setDetailsWithInfo(detailsWithInfo);
        return order;
    }

    @Override
    public List<Order> getOrderByUser(String id) {
        return oDao.selectByUser(id);
    }

    @Override
    public void updateOrder(Order order) {
        oDao.update(order);
    }

    @Override
    public OrderWithInfo getOrderWithInfo(Integer orderId) {
        return oDao.selectOrderWithInfo(orderId);
    }

    @Override
    public List<OrderWithInfo> getLastMonthOrder(String id) {
        return oDao.getLastMonthOrder(id); // 반환 타입 일치
    }

    @Override
    public List<OrderWithInfo> getLast6MonthOrder(String id) {
        return oDao.getLast6MonthOrder(id);
    }
}

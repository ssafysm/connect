package com.ssafy.cafe.model.service;

import java.util.List;

import com.ssafy.cafe.model.dto.Order;
import com.ssafy.cafe.model.dto.OrderWithInfo;

public interface OrderService {
    void makeOrder(Order order);
    Order getOrderWithDetails(Integer orderId);
    List<Order> getOrderByUser(String id);
    void updateOrder(Order order);
    OrderWithInfo getOrderWithInfo(Integer orderId);
    List<OrderWithInfo> getLastMonthOrder(String id); // 반환 타입 수정
    List<OrderWithInfo> getLast6MonthOrder(String id);
}

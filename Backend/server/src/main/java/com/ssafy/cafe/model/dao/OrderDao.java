package com.ssafy.cafe.model.dao;

import java.util.List;

import com.ssafy.cafe.model.dto.Order;
import com.ssafy.cafe.model.dto.OrderDetailWithInfo;
import com.ssafy.cafe.model.dto.OrderWithInfo;

public interface OrderDao {
    int insert(Order order);
    int update(Order order);
    List<Order> selectByUser(String userId);
    Order select(int orderId);
    List<OrderDetailWithInfo> selectOrderDetailWithInfo(int orderId);
    List<OrderWithInfo> getLastMonthOrder(String id); // 반환 타입 수정
    List<OrderWithInfo> getLast6MonthOrder(String id);

    // 추가된 메서드
    OrderWithInfo selectOrderWithInfo(Integer orderId);
    List<OrderWithInfo> selectByUserWithInfo(String userId); // 새 메서드 추가

}

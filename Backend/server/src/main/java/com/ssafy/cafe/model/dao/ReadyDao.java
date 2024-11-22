package com.ssafy.cafe.model.dao;

import java.util.List;
import com.ssafy.cafe.model.dto.Ready;

public interface ReadyDao {
    int insert(Ready ready);
    int update(Ready ready);
    List<Ready> selectAll();
    Ready selectByOrderId(Integer orderId);
    List<Ready> selectByPickUp(Boolean pickUp); // 추가된 메서드
}

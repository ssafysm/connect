package com.ssafy.cafe.model.service;

import java.util.List;

import com.ssafy.cafe.model.dto.Ready;

public interface ReadyService {
    void addReady(Ready ready);
    void updatePickUpStatus(Integer orderId, Boolean pickUp);
    List<Ready> getReadyList();
    Ready getReadyByOrderId(Integer orderId);
}

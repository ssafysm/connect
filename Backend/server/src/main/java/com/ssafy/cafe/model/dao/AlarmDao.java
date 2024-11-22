package com.ssafy.cafe.model.dao;

import java.util.List;
import com.ssafy.cafe.model.dto.Alarm;

public interface AlarmDao {
    int insert(Alarm alarm);
    List<Alarm> selectByUserId(String userId);
}

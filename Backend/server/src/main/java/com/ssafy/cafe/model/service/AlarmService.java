package com.ssafy.cafe.model.service;

import java.util.List;
import com.ssafy.cafe.model.dto.Alarm;

public interface AlarmService {
    int addAlarm(Alarm alarm);
    List<Alarm> getAlarmsByUserId(String userId);
}

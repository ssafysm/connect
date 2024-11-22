package com.ssafy.cafe.model.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ssafy.cafe.model.dao.AlarmDao;
import com.ssafy.cafe.model.dto.Alarm;

@Service
public class AlarmServiceImpl implements AlarmService {

    @Autowired
    private AlarmDao alarmDao;

    @Override
    public int addAlarm(Alarm alarm) {
        return alarmDao.insert(alarm);
    }

    @Override
    public List<Alarm> getAlarmsByUserId(String userId) {
        return alarmDao.selectByUserId(userId);
    }
}

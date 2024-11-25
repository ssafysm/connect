package com.ssafy.cafe.model.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.cafe.model.dao.AttendanceDao;
import com.ssafy.cafe.model.dto.Attendance;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceDao attendanceDao;

    @Override
    public void markAttendance(String userId, int year, int month, int day) {
        Attendance attendance = new Attendance(userId, year, month, day, true);
        Attendance existing = attendanceDao.selectByUserIdAndDate(attendance);
        if (existing == null) {
            attendanceDao.insert(attendance);
        } else {
            attendanceDao.update(attendance);
        }
    }

    @Override
    public List<Boolean> getMonthlyAttendance(String userId, int year, int month) {
        // 해당 월의 마지막 날 계산
        LocalDate date = LocalDate.of(year, month, 1);
        int lastDay = date.lengthOfMonth();

        // 모든 날에 대해 false로 초기화
        List<Boolean> attendanceList = new ArrayList<>();
        for (int i = 1; i <= lastDay; i++) {
            attendanceList.add(false);
        }

        // DB에서 해당 월의 출석 정보 가져오기
        List<Attendance> attendances = attendanceDao.selectByUserIdAndMonth(userId, year, month);
        for (Attendance attendance : attendances) {
            int day = attendance.getDay();
            if (day >= 1 && day <= lastDay) {
                attendanceList.set(day - 1, attendance.getAttended());
            }
        }

        return attendanceList;
    }
}

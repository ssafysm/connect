package com.ssafy.cafe.model.service;

import java.util.List;

import com.ssafy.cafe.model.dto.Attendance;

public interface AttendanceService {
    void markAttendance(String userId, int year, int month, int day);
    List<Boolean> getMonthlyAttendance(String userId, int year, int month);
}

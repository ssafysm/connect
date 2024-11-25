package com.ssafy.cafe.model.dao;

import java.util.List;

import com.ssafy.cafe.model.dto.Attendance;

public interface AttendanceDao {
    int insert(Attendance attendance);
    int update(Attendance attendance);
    Attendance selectByUserIdAndDate(Attendance attendance);
    List<Attendance> selectByUserIdAndMonth(String userId, int year, int month);
}

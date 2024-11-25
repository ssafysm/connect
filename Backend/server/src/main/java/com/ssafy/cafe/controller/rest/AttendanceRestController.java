package com.ssafy.cafe.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.cafe.model.service.AttendanceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/rest/attendance")
@CrossOrigin("*")
@Tag(name = "Attendance Controller", description = "출석 관리를 위한 API")
public class AttendanceRestController {

    @Autowired
    private AttendanceService attendanceService;

    @Operation(summary = "특정 사용자와 월에 대한 출석 정보를 반환한다.")
    @GetMapping("/{userId}/{year}/{month}")
    public ResponseEntity<?> getMonthlyAttendance(
            @PathVariable String userId,
            @PathVariable int year,
            @PathVariable int month) {
        List<Boolean> attendanceList = attendanceService.getMonthlyAttendance(userId, year, month);
        return ResponseEntity.ok(attendanceList);
    }

    @Operation(summary = "특정 날짜에 대한 사용자의 출석을 체크한다.")
    @PostMapping("/mark")
    public ResponseEntity<?> markAttendance(
            @RequestParam String userId,
            @RequestParam int year,
            @RequestParam int month,
            @RequestParam int day) {
        attendanceService.markAttendance(userId, year, month, day);
        return ResponseEntity.ok(true);
    }
}

package com.ssafy.cafe.model.dto;

public class Attendance {
    private String userId;
    private int year;
    private int month;
    private int day;
    private Boolean attended;

    public Attendance() {}

    public Attendance(String userId, int year, int month, int day, Boolean attended) {
        this.userId = userId;
        this.year = year;
        this.month = month;
        this.day = day;
        this.attended = attended;
    }

    // Getter and Setter methods
    public String getUserId() {
        return userId;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public Boolean getAttended() {
        return attended;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setAttended(Boolean attended) {
        this.attended = attended;
    }

    @Override
    public String toString() {
        return "Attendance [userId=" + userId + ", year=" + year + ", month=" + month + ", day=" + day
                + ", attended=" + attended + "]";
    }
}

package com.ssafy.cafe.model.dto;

import java.sql.Timestamp;

public class Chat {
    private Integer id;
    private String userId;
    private Integer level;
    private String plan;
    private String progress;
    private Integer alertIntervalMinutes;
    private Timestamp nextAlarmTime;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Chat() {}

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public Integer getAlertIntervalMinutes() {
        return alertIntervalMinutes;
    }

    public void setAlertIntervalMinutes(Integer alertIntervalMinutes) {
        this.alertIntervalMinutes = alertIntervalMinutes;
    }

    public Timestamp getNextAlarmTime() {
        return nextAlarmTime;
    }

    public void setNextAlarmTime(Timestamp nextAlarmTime) {
        this.nextAlarmTime = nextAlarmTime;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}

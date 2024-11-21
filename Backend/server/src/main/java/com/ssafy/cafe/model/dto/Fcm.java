package com.ssafy.cafe.model.dto;

public class Fcm {
    private String userId;
    private String fcmToken;

    public Fcm() {}

    public Fcm(String userId, String fcmToken) {
        this.userId = userId;
        this.fcmToken = fcmToken;
    }

    public String getUserId() {
        return userId;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    @Override
    public String toString() {
        return "Fcm [userId=" + userId + ", fcmToken=" + fcmToken + "]";
    }
}

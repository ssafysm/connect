package com.ssafy.cafe.model.dto;

import java.util.Date;

public class Alarm {
    private Integer id;
    private String userId;
    private String title;
    private String content;
    private Date sentTime;

    public Alarm() {}

    public Alarm(Integer id, String userId, String title, String content, Date sentTime) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.sentTime = sentTime;
    }

    public Alarm(String userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
    }

    // Getter 및 Setter 메서드
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    public Date getSentTime() {
        return sentTime;
    }

    public void setSentTime(Date sentTime) {
        this.sentTime = sentTime;
    }

    @Override
    public String toString() {
        return "Alarm [id=" + id + ", userId=" + userId + ", title=" + title + ", content=" + content + ", sentTime=" + sentTime + "]";
    }
}

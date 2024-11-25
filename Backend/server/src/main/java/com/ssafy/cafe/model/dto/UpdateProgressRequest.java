package com.ssafy.cafe.model.dto;

public class UpdateProgressRequest {
    private String progress;

    // 기본 생성자
    public UpdateProgressRequest() {}

    // Getter와 Setter
    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }
}

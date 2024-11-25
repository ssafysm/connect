package com.ssafy.cafe.model.dto;

public class ApiResponse {
    private String answer;
    private boolean success;

    // 생성자
    public ApiResponse() {}

    public ApiResponse(String answer, boolean success) {
        this.answer = answer;
        this.success = success;
    }

    // Getter와 Setter
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

package com.ssafy.cafe.model.dto;

public class Grade {

    private String img;
    private int step;
    private int stepMax;
    private int to;
    private String title;

    public Grade() {
    }

    public Grade(String img, int step, int stepMax, int to, String title) {
        this.img = img;
        this.step = step;
        this.stepMax = stepMax;
        this.to = to;
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getStepMax() {
        return stepMax;
    }

    public void setStepMax(int stepMax) {
        this.stepMax = stepMax;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Grade [img=" + img + ", step=" + step + ", stepMax=" + stepMax + ", to=" + to + ", title=" + title
                + "]";
    }

}

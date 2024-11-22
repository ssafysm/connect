package com.ssafy.cafe.model.dto;

public class Ready {
    private Integer oId;
    private Boolean pickUp;

    public Ready() {}

    public Ready(Integer oId, Boolean pickUp) {
        this.oId = oId;
        this.pickUp = pickUp;
    }

    public Integer getOId() {
        return oId;
    }

    public void setOId(Integer oId) {
        this.oId = oId;
    }

    public Boolean getPickUp() {
        return pickUp;
    }

    public void setPickUp(Boolean pickUp) {
        this.pickUp = pickUp;
    }

    @Override
    public String toString() {
        return "Ready [oId=" + oId + ", pickUp=" + pickUp + "]";
    }
}

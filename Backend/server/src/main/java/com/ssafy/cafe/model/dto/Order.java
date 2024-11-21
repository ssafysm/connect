package com.ssafy.cafe.model.dto;

import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Order {
    private Integer id;
    private String userId;
    private String orderTable;
    private Date orderTime;
    private String completed;
    private List<OrderDetail> details; // OrderDetail 사용

    public Order(Integer id, String userId, String orderTable, Date orderTime, String completed) {
        this.id = id;
        this.userId = userId;
        this.orderTable = orderTable;
        this.orderTime = orderTime;
        this.completed = completed;
    }

    public Order(String userId, String orderTable, Date orderTime, String completed) {
        this.userId = userId;
        this.orderTable = orderTable;
        this.orderTime = orderTime;
        this.completed = completed;
    }
    
    @JsonIgnore
    private List<OrderDetailWithInfo> detailsWithInfo;

    public Order() {
    }

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

    public String getOrderTable() {
        return orderTable;
    }

    public void setOrderTable(String orderTable) {
        this.orderTable = orderTable;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public List<OrderDetail> getDetails() {
        return details;  // OrderDetail만 반환
    }

    public void setDetails(List<OrderDetail> details) {
        this.details = details;  // OrderDetail 설정
    }

    public List<OrderDetailWithInfo> getDetailsWithInfo() {
        return detailsWithInfo;  // OrderDetailWithInfo 반환
    }

    public void setDetailsWithInfo(List<OrderDetailWithInfo> detailsWithInfo) {
        this.detailsWithInfo = detailsWithInfo;  // OrderDetailWithInfo 설정
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", userId=" + userId + ", orderTable=" + orderTable + ", orderTime=" + orderTime
                + ", completed=" + completed + ", details=" + details + ", detailsWithInfo=" + detailsWithInfo + "]";
    }
}

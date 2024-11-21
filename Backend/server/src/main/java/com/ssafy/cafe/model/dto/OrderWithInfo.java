package com.ssafy.cafe.model.dto;

import java.util.Date;
import java.util.List;

public class OrderWithInfo {
    private Integer id;
    private String userId;
    private String orderTable;
    private Date orderTime;
    private String completed;

    private List<OrderDetailWithInfo> details;

    public OrderWithInfo(Integer id, String userId, String orderTable, Date orderTime, String completed) {
        this.id = id;
        this.userId = userId;
        this.orderTable = orderTable;
        this.orderTime = orderTime;
        this.completed = completed;
    }

    public OrderWithInfo() {
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

    public List<OrderDetailWithInfo> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetailWithInfo> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "OrderWithInfo [id=" + id + ", userId=" + userId + ", orderTable=" + orderTable + ", orderTime="
                + orderTime + ", completed=" + completed + ", details=" + details + "]";
    }

}

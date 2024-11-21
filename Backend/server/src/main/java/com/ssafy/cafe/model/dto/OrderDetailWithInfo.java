package com.ssafy.cafe.model.dto;

public class OrderDetailWithInfo {
    private Integer id;
    private Integer orderId;
    private Integer productId;
    private Integer quantity;

    private String img;
    private String name;
    private String type;
    private String unitPrice; // 타입을 String으로 변경
    private Integer sumPrice;

    public OrderDetailWithInfo(Integer id, Integer orderId, Integer productId, Integer quantity, String img, String name,
            String type, String unitPrice, Integer sumPrice) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.img = img;
        this.name = name;
        this.type = type;
        this.unitPrice = unitPrice;
        this.sumPrice = sumPrice;
    }

    public OrderDetailWithInfo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(Integer sumPrice) {
        this.sumPrice = sumPrice;
    }

    @Override
    public String toString() {
        return "OrderDetailWithInfo [id=" + id + ", orderId=" + orderId + ", productId=" + productId + ", quantity="
                + quantity + ", img=" + img + ", name=" + name + ", type=" + type + ", unitPrice=" + unitPrice
                + ", sumPrice=" + sumPrice + "]";
    }

}

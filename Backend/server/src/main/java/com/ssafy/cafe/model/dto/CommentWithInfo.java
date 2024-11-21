package com.ssafy.cafe.model.dto;

public class CommentWithInfo {
    private Integer id;
    private String userId;
    private Integer productId;
    private Double rating;
    private String comment;
    private String userName;

    public CommentWithInfo(Integer id, String userId, Integer productId, Double rating, String comment,
            String userName) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.rating = rating;
        this.comment = comment;
        this.userName = userName;
    }

    public CommentWithInfo(String userId, Integer productId, Double rating, String comment, String userName) {
        this.userId = userId;
        this.productId = productId;
        this.rating = rating;
        this.comment = comment;
        this.userName = userName;
    }

    public CommentWithInfo() {

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

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "CommentWithInfo [id=" + id + ", userId=" + userId + ", productId=" + productId + ", rating=" + rating
                + ", comment=" + comment + ", userName=" + userName + "]";
    }

}

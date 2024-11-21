package com.ssafy.cafe.model.dto;

import java.util.Date;

public class Coupon {
	
	private Integer id;
	private String userId;
	private String name;
	private String image;
	private Integer price;
	private Date couponTime;
	
	public Coupon(Integer id, String userId, String name, String image, Integer price, Date couponTime) {
		super();
		this.id = id;
		this.userId = userId;
		this.name = name;
		this.image = image;
		this.price = price;
		this.couponTime = couponTime;
	}

	public Coupon(String userId, String name, String image, Integer price) {
		super();
		this.userId = userId;
		this.name = name;
		this.image = image;
		this.price = price;
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
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public Integer getPrice() {
		return price;
	}
	
	public void setPrice(Integer price) {
		this.price = price;
	}

	public Date getCouponTime() {
		return couponTime;
	}

	public void setCouponTime(Date couponTime) {
		this.couponTime = couponTime;
	}

}

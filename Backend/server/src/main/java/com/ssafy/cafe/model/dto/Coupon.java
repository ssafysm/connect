package com.ssafy.cafe.model.dto;

import java.time.LocalDateTime;

public class Coupon {
	
	private Integer id;
	private String userId;
	private String name;
	private String description;
	private String image;
	private LocalDateTime iat;
	private LocalDateTime exp;
	private Integer menuId;
	private Integer menuCount;
	
	public Coupon() {}
	
	public Coupon(Integer id, String userId, String name, String description, String image, Integer menuId, LocalDateTime iat, LocalDateTime exp, Integer menuCount) {
		super();
		this.id = id;
		this.userId = userId;
		this.name = name;
		this.description = description;
		this.image = image;
		this.iat = iat;
		this.exp = exp;
		this.menuId = menuId;
		this.menuCount = menuCount;
	}

	public Coupon(String userId, String name, String description, String image, Integer menuId, Integer menuCount) {
		super();
		this.userId = userId;
		this.name = name;
		this.description = description;
		this.image = image;
		this.menuId = menuId;
		this.menuCount = menuCount;
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
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public LocalDateTime getIat() {
		return iat;
	}
	
	public void setIat(LocalDateTime iat) {
		this.iat = iat;
	}
	
	public LocalDateTime getExp() {
		return exp;
	}
	
	public void setExp(LocalDateTime exp) {
		this.exp = exp;
	}
	
	public Integer getMenuId() {
		return menuId;
	}
	
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	
	public Integer getMenuCount() {
		return menuCount;
	}
	
	public void setMenuCount(Integer menuCount) {
		this.menuCount = menuCount;
	}

}

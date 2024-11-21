package com.ssafy.cafe.model.dto;

public class Product {
    private Integer id;
    private String name;
    private String type;
    private Integer price;
    private String img;
    private String description;
    private String mode;
    
	public Product(Integer id, String name, String type, Integer price, String img, String description, String mode) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.price = price;
		this.img = img;
		this.description = description;
		this.mode = mode;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
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
	
	public Integer getPrice() {
		return price;
	}
	
	public void setPrice(Integer price) {
		this.price = price;
	}
	
	public String getImg() {
		return img;
	}
	
	public void setImg(String img) {
		this.img = img;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getMode() {
		return mode;
	}
	
	public void setMode(String mode) {
		this.mode = mode;
	}

}

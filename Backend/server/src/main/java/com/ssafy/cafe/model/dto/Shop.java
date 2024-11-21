package com.ssafy.cafe.model.dto;

public class Shop {
	
	private Integer id;
	private String name;
	private String image;
	private String description;
	private String time;
	private double latitude;
	private double longitude;
	
	public Shop() {}
	
	public Shop(Integer id, String name, String image, String description, String time, double latitude,
			double longitude) {
		super();
		this.id = id;
		this.name = name;
		this.image = image;
		this.description = description;
		this.time = time;
		this.latitude = latitude;
		this.longitude = longitude;
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
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
}

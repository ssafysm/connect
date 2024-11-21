package com.ssafy.cafe.model.dto;

public class Event {
	
	private Integer id;
	private String name;
	private String image;
	private String url;
	
	public Event() {}
	
	public Event(Integer id, String name, String image, String url) {
		super();
		this.id = id;
		this.name = name;
		this.image = image;
		this.url = url;
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
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

}

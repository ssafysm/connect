package com.ssafy.cafe.model.dto;

public class CouponTemplate {
    private Integer id;
    private String name;
    private String description;
    private String image;
    private Integer menuId;
    private Integer menuCount;

    public CouponTemplate() {}

    public CouponTemplate(Integer id, String name, String description, String image, Integer menuId, Integer menuCount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.menuId = menuId;
        this.menuCount = menuCount;
    }

    // Getter 및 Setter 메서드
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

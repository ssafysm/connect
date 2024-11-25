package com.ssafy.cafe.model.dto;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String id;
    private String name;
    private String pass;
    private Integer stamps;
    private List<Stamp> stampList = new ArrayList<>();
    private boolean alarmMode;
    private Integer appTheme;

    public User(String id, String name, String pass, Integer stamps, boolean alarmMode, Integer appTheme) {
        this.id = id;
        this.name = name;
        this.pass = pass;
        this.stamps = stamps;
        this.alarmMode = alarmMode;
        this.appTheme = appTheme;
    }

    public User() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Integer getStamps() {
        return stamps;
    }

    public void setStamps(Integer stamps) {
        this.stamps = stamps;
    }

    public List<Stamp> getStampList() {
        return stampList;
    }

    public void setStampList(List<Stamp> stampList) {
        this.stampList = stampList;
    }

	public boolean isAlarmMode() {
		return alarmMode;
	}

	public void setAlarmMode(boolean alarmMode) {
		this.alarmMode = alarmMode;
	}

	public Integer getAppTheme() {
		return appTheme;
	}

	public void setAppTheme(Integer appTheme) {
		this.appTheme = appTheme;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", pass=" + pass + ", stamps=" + stamps + ", stampList="
				+ stampList + ", alarmMode=" + alarmMode + ", appTheme=" + appTheme + "]";
	}

}

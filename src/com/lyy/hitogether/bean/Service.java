package com.lyy.hitogether.bean;

import java.util.List;

import cn.bmob.v3.datatype.BmobDate;

public class Service {
	/**
	 * 向导单
	 */
	String userId; // 用户Id
	String ServiceId; // 向导单号
	String destination; // 目的地
	BmobDate goTime; // 出行时间
	Double price; // 价格
	String introduction;// 介绍
	String summary; // 简介
	List<String> photoPath;// 协同照片

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getServiceId() {
		return ServiceId;
	}

	public void setServiceId(String serviceId) {
		ServiceId = serviceId;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public BmobDate getGoTime() {
		return goTime;
	}

	public void setGoTime(BmobDate goTime) {
		this.goTime = goTime;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public List<String> getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(List<String> photoPath) {
		this.photoPath = photoPath;
	}

}

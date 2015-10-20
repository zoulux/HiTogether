package com.lyy.hitogether.bean;

import java.util.List;

import cn.bmob.v3.datatype.BmobDate;

public class Demand {
	/**
	 * 用户需求，订单
	 */
	String userId; // 用户Id
	String demandId; // 订单号
	String destination; // 目的地
	BmobDate goTime; // 出行时间
	Integer peopleNum; // 出行人数
	String demandProgress;// 订单进度
	List<String> photoPath; // 协同照片
	Integer zan;		//
	
	
	
	public Integer getZan() {
		return zan;
	}
	public void setZan(Integer zan) {
		this.zan = zan;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDemandId() {
		return demandId;
	}
	public void setDemandId(String demandId) {
		this.demandId = demandId;
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
	public Integer getPeopleNum() {
		return peopleNum;
	}
	public void setPeopleNum(Integer peopleNum) {
		this.peopleNum = peopleNum;
	}
	public String getDemandProgress() {
		return demandProgress;
	}
	public void setDemandProgress(String demandProgress) {
		this.demandProgress = demandProgress;
	}
	public List<String> getPhotoPath() {
		return photoPath;
	}
	public void setPhotoPath(List<String> photoPath) {
		this.photoPath = photoPath;
	}

	
}

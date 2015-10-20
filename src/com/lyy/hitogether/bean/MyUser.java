package com.lyy.hitogether.bean;

import java.util.List;

import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.v3.datatype.BmobDate;

public class MyUser extends BmobChatUser {
	String gender;
	String birthday;
	List<String> label;
	Boolean isAuthentication; // 是否实名认证
	String identity; // 身份证号
	Integer star; // 用户评星
	List<String> collectionDemands; // 收藏单号
	Integer age;

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public List<String> getCollectionDemands() {
		return collectionDemands;
	}

	public void setCollectionDemands(List<String> collectionDemands) {
		this.collectionDemands = collectionDemands;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public List<String> getLabel() {
		return label;
	}

	public void setLabel(List<String> label) {
		this.label = label;
	}

	public Boolean getIsAuthentication() {
		return isAuthentication;
	}

	public void setIsAuthentication(Boolean isAuthentication) {
		this.isAuthentication = isAuthentication;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}
}

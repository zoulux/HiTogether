package com.lyy.hitogether.bean;

public class HotScenic {
	/**
	 * 热门景点
	 */
	String hotId;// id
	String hotName;// 名字
	String photoPath;// 图片路径
	String extra; // 额外的
	String enjoy; // 正在参加的人

	public String getEnjoy() {
		return enjoy;
	}

	public void setEnjoy(String enjoy) {
		this.enjoy = enjoy;
	}

	public String getHotId() {
		return hotId;
	}

	public void setHotId(String hotId) {
		this.hotId = hotId;
	}

	public String getHotName() {
		return hotName;
	}

	public void setHotName(String hotName) {
		this.hotName = hotName;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

}

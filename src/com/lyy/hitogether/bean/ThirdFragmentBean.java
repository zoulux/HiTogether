package com.lyy.hitogether.bean;

public class ThirdFragmentBean {
	// �ط��羰ͼ
	private int scenImgId;
	// ����ͷ��
	private int guideImgId;
	// ��������
	private String guideName;

	// ������
	private int praisedCount;
	// ���η���
	private int tourGuideFee;
	// �õط�����
	private String description;
	// �ص�
	private String place;
	// ����
	private float grade;
	
	
	
	

	public ThirdFragmentBean(int scenImgId, int guideImgId, String guideName,
			String description, String place, float grade) {
		super();
		this.scenImgId = scenImgId;
		this.guideImgId = guideImgId;
		this.guideName = guideName;
		this.description = description;
		this.place = place;
		this.grade = grade;
	}

	public int getScenImgId() {
		return scenImgId;
	}

	public void setScenImgId(int scenImgId) {
		this.scenImgId = scenImgId;
	}

	public int getGuideImgId() {
		return guideImgId;
	}

	public void setGuideImgId(int guideImgId) {
		this.guideImgId = guideImgId;
	}

	public String getGuideName() {
		return guideName;
	}

	public void setGuideName(String guideName) {
		this.guideName = guideName;
	}

	public int getPraisedCount() {
		return praisedCount;
	}

	public void setPraisedCount(int praisedCount) {
		this.praisedCount = praisedCount;
	}

	public int getTourGuideFee() {
		return tourGuideFee;
	}

	public void setTourGuideFee(int tourGuideFee) {
		this.tourGuideFee = tourGuideFee;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public float getGrade() {
		return grade;
	}

	public void setGrade(float grade) {
		this.grade = grade;
	}

}

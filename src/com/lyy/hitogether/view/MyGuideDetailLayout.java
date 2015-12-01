package com.lyy.hitogether.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyGuideDetailLayout extends LinearLayout {
	private Context context;

	public MyGuideDetailLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOrientation(VERTICAL);
		this.context = context;
	}
	
	
	public void addImage(int souece){
		
		ImageView img = new ImageView(context);
		img.setImageResource(souece);
		img.setScaleType(ScaleType.CENTER_CROP);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,500);
		addView(img,lp);
	}
	
	public void addText(String text){
		TextView tv= new TextView(context);
		tv.setText(text);
		addView(tv);
	}
	
	

}

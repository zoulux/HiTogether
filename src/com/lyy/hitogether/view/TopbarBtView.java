package com.lyy.hitogether.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lyy.hitogether.R;
import com.lyy.hitogether.util.CircleImageDrawable;

public class TopbarBtView extends RelativeLayout {
	// 类型是文字的
	private static final int TYPE_TEXT = 0;
	// 类型是图片的
	private static final int TYPE_IMAGE = 1;
	// 类型是普通按钮的
	private static final int TYPE_BUTTON = 2;

	private ImageView img;
	private TextView tv;
	private Button bt;

	// 设置普通按钮的监听回调事件
	public onTopBarButtonListener topBarButtonListener;

	public interface onTopBarButtonListener {
		public void onBtClick(View v);
	}

	public void setOnTopbarButtonListener(
			onTopBarButtonListener topBarButtonListener) {
		this.topBarButtonListener = topBarButtonListener;
	}

	public onTopBarButtonListener getTopbarButtonListener() {
		return topBarButtonListener;
	}

	/**
	 * 设置topbar普通按钮上的文字
	 * 
	 * @param btText
	 */
	public void setTopBarButtontext(String btText) {
		bt.setText(btText);
	}

	/**
	 * 设置topbar上文字按钮的文字
	 * 
	 * @param tvText
	 */
	public void setTopBarTvText(String tvText) {
		tv.setText(tvText);
	}

	/**
	 * 设置topbar图片按钮的图片来源
	 * 
	 * @param idDrawable
	 */
	public void setTopbarImageDrawable(int idDrawable) {
		img.setImageDrawable(getResources().getDrawable(idDrawable));
		//Bitmap bp = BitmapFactory.decodeResource(getResources(), R.drawable.p3);
	}

	public void setTopbarCircleImageDrawable(int idDrawable) {
		Bitmap bp = BitmapFactory.decodeResource(getResources(),
				idDrawable);
		img.setImageDrawable(new CircleImageDrawable(bp));

	}

	public TopbarBtView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public TopbarBtView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public TopbarBtView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		View v = null;
		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.barView, defStyleAttr, 0);

		int type = ta.getInt(R.styleable.barView_type, 0);
		switch (type) {
		case TYPE_BUTTON:
			bt = new Button(context);
			bt.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					topBarButtonListener.onBtClick(v);
				}
			});
			v = bt;
			break;
		case TYPE_IMAGE:
			img = new ImageView(context);
			v = img;
			break;
		case TYPE_TEXT:
			tv = new TextView(context);
			tv.setTextSize(20);
			v = tv;
			break;
		}

		ta.recycle();
		addView(v);
	}

}

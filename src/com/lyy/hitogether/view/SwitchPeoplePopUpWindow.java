package com.lyy.hitogether.view;

import com.lyy.hitogether.R;
import com.lyy.hitogether.adapter.ArrayWheelAdapter;
import com.lyy.hitogether.view.WheelView.OnWheelChangedListener;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

public class SwitchPeoplePopUpWindow extends PopupWindow {

	private Context context;
	private int mWidth;
	private int mHeight;
	private View mConvertView;
	private ImageView yesImg;

	private WheelView peopleWV = null;
	private onCorrectClickListener3 mListener;

	private String peoples[] = new String[100];

	public interface onCorrectClickListener3 {
		void onCorrectClick3(View v, int count);
	}

	public void setOnCorrectClickListener3(onCorrectClickListener3 listener) {
		this.mListener = listener;
	}

	public SwitchPeoplePopUpWindow(Context context) {

		this.context = context;
		for (int i = 0; i < 100; i++) {
			peoples[i] = i + 1 + "人";
		}
		culculateWidthAndHeight();
		mConvertView = LayoutInflater.from(context).inflate(
				R.layout.pop_people_window, null);
		setContentView(mConvertView);
		setWidth(mWidth);
		setHeight(mHeight);

		setFocusable(true);
		setTouchable(true);
		setOutsideTouchable(true);
		setBackgroundDrawable(new BitmapDrawable());

		setTouchInterceptor(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					dismiss();
					return true;
				}
				return false;
			}
		});

		initId();
		initEvent();

	}

	private void initEvent() {
		yesImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mListener != null) {
					mListener.onCorrectClick3(v, Integer.parseInt(currentCount));
					SwitchPeoplePopUpWindow.this.dismiss();
				}
			}
		});

	}

	private void initId() {
		peopleWV = (WheelView) mConvertView.findViewById(R.id.id_people_count);
		yesImg = (ImageView) mConvertView.findViewById(R.id.id_yes);

		peopleWV.setVisibleItems(5);

		peopleWV.setCyclic(false);
		choseCount();

	}

	private String currentCount;

	private void choseCount() {
		peopleWV.setAdapter(new ArrayWheelAdapter<String>(peoples));
		currentCount = peoples[peopleWV.getCurrentItem()].substring(0, 1);

		peopleWV.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				currentCount = peoples[newValue].substring(0,
						peoples[newValue].length() - 1);
			}
		});

	}

	private void culculateWidthAndHeight() {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics displayMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(displayMetrics);

		mWidth = displayMetrics.widthPixels;
		mHeight = (int) (displayMetrics.heightPixels * 0.3);

	}

}

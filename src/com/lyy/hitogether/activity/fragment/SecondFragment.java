package com.lyy.hitogether.activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.lyy.hitogether.R;
import com.lyy.hitogether.activity.ShareMyTravalActivity;
import com.lyy.hitogether.view.SwitchDesPopUpWindow;
import com.lyy.hitogether.view.SwitchDesPopUpWindow.onCorrectClickListener;
import com.lyy.hitogether.view.SwitchPeoplePopUpWindow;
import com.lyy.hitogether.view.SwitchPeoplePopUpWindow.onCorrectClickListener3;
import com.lyy.hitogether.view.SwitchTimePopUpWindow;
import com.lyy.hitogether.view.SwitchTimePopUpWindow.onCorrectClickListener2;

public class SecondFragment extends BaseFragment implements OnClickListener {

	private Button bt;
	private View mBottomView;
	private TextView mTimeTextView;
	private TextView mDesTextView;
	private TextView mPeopleTextView;
	private SwitchDesPopUpWindow mSwitchDesPopUpWindow;
	private SwitchTimePopUpWindow mSwitchTimePopUpWindow;
	private SwitchPeoplePopUpWindow mSwitchPeoplePopUpWindow;
	private int mHeight;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_second, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initView(view);
		initEvent();
	}

	private void initEvent() {
		bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(SecondFragment.this.getActivity(),
						ShareMyTravalActivity.class));
			}
		});

	}

	private void initView(View view) {
		bt = (Button) view.findViewById(R.id.fragment_second_send_bt);
		mBottomView = view.findViewById(R.id.id_view);
		mTimeTextView = (TextView) view
				.findViewById(R.id.id_fragment_second_time);
		mDesTextView = (TextView) view
				.findViewById(R.id.id_fragment_second_destination);
		mPeopleTextView = (TextView) view
				.findViewById(R.id.id_fragment_second_people_count);
		mSwitchDesPopUpWindow = new SwitchDesPopUpWindow(getActivity());
		mSwitchTimePopUpWindow = new SwitchTimePopUpWindow(getActivity());
		mSwitchPeoplePopUpWindow = new SwitchPeoplePopUpWindow(getActivity());
		initListener();
		setPopUpWinDowListener();

	}

	private void initListener() {
		mTimeTextView.setOnClickListener(SecondFragment.this);
		mDesTextView.setOnClickListener(SecondFragment.this);
		mPeopleTextView.setOnClickListener(SecondFragment.this);

	}

	private void setPopUpWinDowListener() {
		WindowManager wm = (WindowManager) this.getActivity().getSystemService(
				Context.WINDOW_SERVICE);
		DisplayMetrics displayMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(displayMetrics);

		mHeight = displayMetrics.heightPixels;
		mSwitchDesPopUpWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				lightOn();
			}
		});
		mSwitchTimePopUpWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				lightOn();
			}
		});
		mSwitchPeoplePopUpWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				lightOn();
			}
		});

	}

	@Override
	protected void lazyLoad() {

	}

	@Override
	public boolean handleMessage(Message arg0) {

		return false;
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.id_fragment_second_destination:
			ShowToast(">>>");
			mSwitchDesPopUpWindow.showAsDropDown(mBottomView, 0, 0);
			setDesData();
			lightOff();
			break;
		case R.id.id_fragment_second_time:
			mSwitchTimePopUpWindow.showAsDropDown(mBottomView, 0, 0);
			setTimeData();
			lightOff();
			break;
		case R.id.id_fragment_second_people_count:
			mSwitchPeoplePopUpWindow.showAsDropDown(mBottomView, 0, 0);
			setPeopleData();
			lightOff();
			break;

		}

	}

	private void setPeopleData() {
		mSwitchPeoplePopUpWindow.setOnCorrectClickListener3(new onCorrectClickListener3() {
			
			@Override
			public void onCorrectClick3(View v, int count) {
				mPeopleTextView.setText(count+"人");
			}
		});
		
	}

	private void setTimeData() {
		mSwitchTimePopUpWindow
				.setOnCorrectClickListener2(new onCorrectClickListener2() {

					@Override
					public void onCorrectClick2(View v, String Year,
							String Month, String Day) {
						mTimeTextView.setText(Year+"年"+Month+"月"+Day+"日");

					}
				});

	}

	private void setDesData() {
		mSwitchDesPopUpWindow
				.setOnCorrectClickListener(new onCorrectClickListener() {

					@Override
					public void onCorrectClick(View v, String provinve,
							String city) {
						mDesTextView.setText(provinve + mSwitchDesPopUpWindow.getBigLable() + city + mSwitchDesPopUpWindow.getSmallLable());

					}
				});

	}

	protected void lightOn() {
		WindowManager.LayoutParams lp = getActivity().getWindow()
				.getAttributes();
		lp.alpha = 1.0f;
		getActivity().getWindow().setAttributes(lp);

	}

	protected void lightOff() {
		WindowManager.LayoutParams lp = getActivity().getWindow()
				.getAttributes();
		lp.alpha = .3f;
		getActivity().getWindow().setAttributes(lp);

	}
}

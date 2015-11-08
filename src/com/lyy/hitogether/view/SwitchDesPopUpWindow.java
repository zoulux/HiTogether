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

public class SwitchDesPopUpWindow extends PopupWindow {

	private Context context;
	private int mWidth;
	private int mHeight;
	private View mConvertView;
	private ImageView yesImg;

	String[] shengs = { "安徽", "福建", "广西", "贵州", "海南", "北京" };
	String[] scene01 = { "黄山", "宏村", "天柱山", "姥山岛", "花亭湖", "九华山" };
	String[] scene02 = { "武夷山", "霞浦" };
	String[] scene03 = { "阳塑" };
	String[] scene04 = { "黄果树瀑布" };
	String[] scene05 = { "南山", "亚龙湾" };
	String[] scene06 = { "故宫", "颐和园", "长城" };

	private WheelView shengWV = null;
	private WheelView shiWV = null;
	private onCorrectClickListener mListener;

	public interface onCorrectClickListener {
		void onCorrectClick(View v, String provinve, String city);
	}

	public void setOnCorrectClickListener(onCorrectClickListener listener) {
		this.mListener = listener;
	}

	public SwitchDesPopUpWindow(Context context) {

		this.context = context;
		culculateWidthAndHeight();
		mConvertView = LayoutInflater.from(context).inflate(
				R.layout.pop_des_window, null);
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
					mListener.onCorrectClick(v, currentProvence, currentCity);
					SwitchDesPopUpWindow.this.dismiss();
				}

				// Toast.makeText(context, currentProvence + "" + currentCity,
				// Toast.LENGTH_SHORT).show();

			}
		});

	}

	private void initId() {
		shengWV = (WheelView) mConvertView.findViewById(R.id.other_place_sheng);
		shiWV = (WheelView) mConvertView.findViewById(R.id.other_place_shi);
		yesImg = (ImageView) mConvertView.findViewById(R.id.id_yes);

		shengWV.setVisibleItems(5);
		shiWV.setVisibleItems(5);

		shengWV.setCyclic(false);
		shiWV.setCyclic(false);
		chosePlce();

	}

	public String getBigLable() {
		return shengWV.getLabel();
	}

	public String getSmallLable() {
		return shiWV.getLabel();
	}

	private String currentProvence;
	private String currentCity;

	private void chosePlce() {
		shengWV.setAdapter(new ArrayWheelAdapter<String>(shengs));
		shiWV.setAdapter(new ArrayWheelAdapter<String>(scene01));
		shengWV.setLabel("省");
		shiWV.setLabel("景区");
		currentProvence = shengs[shengWV.getCurrentItem()];
		currentCity = scene01[shiWV.getCurrentItem()];
		// placeTV.setText("您选的是:" + shengs[shengWV.getCurrentItem()]
		// + shengWV.getLabel() + beijing[shiWV.getCurrentItem()]
		// + shiWV.getLabel());

		shengWV.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				// TODO Auto-generated method stub
				shiWV.setCurrentItem(0);
				String sheng = shengs[newValue];
				String shi = "";
				if (sheng.equals("北京")) {
					shengWV.setLabel("市");
					shiWV.setLabel("景区");
					shiWV.setAdapter(new ArrayWheelAdapter<String>(scene06));
					shi = scene06[shiWV.getCurrentItem()];
				} else {
					shengWV.setLabel("省");
					shiWV.setLabel("景区");

					if (sheng.equals("安徽")) {
						shiWV.setAdapter(new ArrayWheelAdapter<String>(scene01));
						shi = scene01[shiWV.getCurrentItem()];
					} else if (sheng.equals("福建")) {

						shiWV.setAdapter(new ArrayWheelAdapter<String>(scene02));
						shi = scene02[shiWV.getCurrentItem()];
					} else if (sheng.equals("广西")) {
						shiWV.setAdapter(new ArrayWheelAdapter<String>(scene03));
						shi = scene03[shiWV.getCurrentItem()];
					} else if (sheng.equals("贵州")) {
						shiWV.setAdapter(new ArrayWheelAdapter<String>(scene04));
						shi = scene04[shiWV.getCurrentItem()];
					} else if (sheng.equals("海南")) {
						shiWV.setAdapter(new ArrayWheelAdapter<String>(scene05));
						shi = scene05[shiWV.getCurrentItem()];
					}

				}
				currentProvence = shengs[shengWV.getCurrentItem()];
				currentCity = shi;
			}
		});

		shiWV.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				// TODO Auto-generated method stub
				String sheng = shengs[shengWV.getCurrentItem()];
				String shi = "";
				if (sheng.equals("北京")) {
					shi = scene06[shiWV.getCurrentItem()];
				} else if (sheng.equals("安徽")) {
					shi = scene01[shiWV.getCurrentItem()];
				} else if (sheng.equals("福建")) {
					shi = scene02[shiWV.getCurrentItem()];
				} else if (sheng.equals("广西")) {
					shi = scene03[shiWV.getCurrentItem()];
				} else if (sheng.equals("贵州")) {
					shi = scene04[shiWV.getCurrentItem()];
				} else if (sheng.equals("海南")) {
					shi = scene05[shiWV.getCurrentItem()];
				}
				currentCity = shi;
				// placeTV.setText("您选的是:" + sheng + shengWV.getLabel() + shi
				// + shiWV.getLabel());
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

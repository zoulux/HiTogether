package com.lyy.hitogether.activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.lyy.hitogether.R;
import com.lyy.hitogether.view.MyGuideOrTravalersLayout;
import com.lyy.hitogether.view.MyGuideOrTravalersLayout.onGuideItemClickListener;

public class ShowSceneDetailsActivity extends BaseActivity {

	private MyGuideOrTravalersLayout mLayoutGuides;
	private MyGuideOrTravalersLayout mLayoutTravalers;
	private TextView guidesMore;
	private TextView travalersMore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_scene_details);
		initView();
		initEvent();

	}

	private void initView() {
		mLayoutTravalers = (MyGuideOrTravalersLayout) findViewById(R.id.id_travaler_layout);
		mLayoutGuides = (MyGuideOrTravalersLayout) findViewById(R.id.id_guide_layout);
		guidesMore = (TextView) findViewById(R.id.id_guids_more);
		travalersMore = (TextView) findViewById(R.id.id_travalers_more);

	}

	private void initEvent() {
		travalersMore.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ShowToast("travalersMore");

			}
		});

		guidesMore.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ShowToast("guidesMore");

			}
		});
		mLayoutGuides.addMyView(10);
		mLayoutTravalers.addMyView(12);
		mLayoutGuides
				.setOnGuideItemClickListener(new onGuideItemClickListener() {

					@Override
					public void onItemClick(View v, int pos) {
						ShowToast(pos+"");
					}
				});
		mLayoutTravalers
				.setOnGuideItemClickListener(new onGuideItemClickListener() {

					@Override
					public void onItemClick(View v, int pos) {
						ShowToast(pos+"");
					}
				});

	}

}

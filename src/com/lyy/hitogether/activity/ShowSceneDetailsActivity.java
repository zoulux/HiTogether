package com.lyy.hitogether.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import com.lyy.hitogether.R;
import com.lyy.hitogether.view.MyGuideOrTravalersLayout;
import com.lyy.hitogether.view.MyGuideOrTravalersLayout.onGuideItemClickListener;

public class ShowSceneDetailsActivity extends BaseActivity implements OnClickListener {

	private MyGuideOrTravalersLayout mLayoutGuides;
	private MyGuideOrTravalersLayout mLayoutTravalers;
	private TextView guidesMore;
	private TextView travalersMore;
	
//	private QuickCommentBar mQuickCommentBar;

	 
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_scene_details);
		initOnekeyShare();
		initView();
		initEvent();
		

	}

	private void initOnekeyShare() {
		
		
		
	}

	private void initView() {
	//	mQuickCommentBar=(QuickCommentBar) findViewById(R.id.id_qcb_reply);
		mLayoutTravalers = (MyGuideOrTravalersLayout) findViewById(R.id.id_travaler_layout);
		mLayoutGuides = (MyGuideOrTravalersLayout) findViewById(R.id.id_guide_layout);
		guidesMore = (TextView) findViewById(R.id.id_guids_more);
		travalersMore = (TextView) findViewById(R.id.id_travalers_more);
		
//		mQuickCommentBar.setTopic("123", "456", "789", "1011");
//		mQuickCommentBar.getBackButton().setOnClickListener(this);
		
//		mQuickCommentBar.setOnekeyShare();

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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}

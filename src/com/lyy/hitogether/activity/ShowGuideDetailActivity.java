package com.lyy.hitogether.activity;

import io.rong.imkit.RongIM;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lyy.hitogether.R;
import com.lyy.hitogether.view.CustomTitleBarView;
import com.lyy.hitogether.view.CustomTitleBarView.onLeftBarViewClickListener;
import com.lyy.hitogether.view.MyGuideDetailLayout;

public class ShowGuideDetailActivity extends BaseActivity implements
		OnClickListener {
	private MyGuideDetailLayout layout;
	private CustomTitleBarView customTitleBarView;
	private LinearLayout callHe;
	private Button talkToHe;
	private RatingBar grade;
	private TextView checkText;
	private ImageView alreadyCheckImg;
	private String targetUserId;
	private String targetUserName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_show_guide_detail);
		getIntentValue();
		initView();

	}

	private void getIntentValue() {
		Intent intent = getIntent();
		targetUserId = intent.getStringExtra("targetUserId");
		targetUserName = intent.getStringExtra("targetUserName");
		System.out.println(targetUserId+">>>>>>>>>"+targetUserName);
		
	}

	private void initView() {
		layout = (MyGuideDetailLayout) findViewById(R.id.id_show_guide_detail_MyLayout);
		customTitleBarView = (CustomTitleBarView) findViewById(R.id.id_show_guide_detail_title);
		callHe = (LinearLayout) findViewById(R.id.id_show_guide_detail_bottom_call_he);
		talkToHe = (Button) findViewById(R.id.id_show_guide_detail_bottom_talk);
		grade = (RatingBar) findViewById(R.id.id_show_guide_detail_grade);
		checkText = (TextView) findViewById(R.id.id_show_guide_detail_check_text);
		alreadyCheckImg = (ImageView) findViewById(R.id.id_show_guide_detail_already_check_img);
//		alreadyCheckImg.setVisibility(View.GONE);
		checkText.setText("已认证");
		grade.setRating(1.5f);
		layout.addImage(R.drawable.p3);
		layout.addText("这是我们的第一站");
		layout.addImage(R.drawable.girl);
		layout.addText("这是我们的第二站,景点不错");
		layout.addImage(R.drawable.p1);
		layout.addText("这是我们的第三站，这个也很好");
		layout.addImage(R.drawable.p2);
		layout.addText("这是我们的最后一站，感觉不错吧");

		customTitleBarView.setLeftImageSuorce(R.drawable.back_indicator);
		customTitleBarView.setCenterText("马云");
		customTitleBarView.setLeftImagePaddingLeft(10);
		customTitleBarView.setCenterTextColor(Color.WHITE);
		customTitleBarView.setCenterTextSize(18);

		setListener();
	}

	private void setListener() {
		customTitleBarView
				.setOnLeftBarViewClickListener(new onLeftBarViewClickListener() {

					@Override
					public void onclick(View v) {
						ShowGuideDetailActivity.this.finish();
					}
				});
		callHe.setOnClickListener(this);
		talkToHe.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		// 打电话
		case R.id.id_show_guide_detail_bottom_call_he:
			callToguide();
			break;
			//和导游聊天
		case R.id.id_show_guide_detail_bottom_talk:
			chatWithGuide();
			break;

		}
	}
	
	private void chatWithGuide() {
		RongIM.getInstance().startPrivateChat(this, targetUserId,
				"与" + targetUserName + "聊天中");

	}

	private void callToguide() {
		Intent intent = new Intent(Intent.ACTION_CALL);
		intent.setData(Uri.parse("tel:18365284756"));
		startActivity(intent);
		
		
	}

}

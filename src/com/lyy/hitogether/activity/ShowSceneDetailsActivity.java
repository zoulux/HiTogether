package com.lyy.hitogether.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.socialization.QuickCommentBar;
import cn.sharesdk.socialization.Socialization;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lyy.hitogether.R;
import com.lyy.hitogether.view.MyGuideOrTravalersLayout;
import com.lyy.hitogether.view.MyGuideOrTravalersLayout.onGuideItemClickListener;

public class ShowSceneDetailsActivity extends BaseActivity implements
		OnClickListener {

	private MyGuideOrTravalersLayout mLayoutGuides;
	private MyGuideOrTravalersLayout mLayoutTravalers;
	private TextView guidesMore;
	private TextView travalersMore;

	private QuickCommentBar mQuickCommentBar;
	private OnekeyShare oks;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_scene_details);
		ViewUtils.inject(this);
		initOnekeyShare();
		initView();
		initEvent();

	}

	private void initOnekeyShare() {
		ShareSDK.initSDK(this);
		ShareSDK.registerService(Socialization.class);
		oks = new OnekeyShare();
		oks.disableSSOWhenAuthorize();

		oks.setTitle("我是乐友游的好标题");
		oks.setTitleUrl("http://www.pgyer.com/hitogether");
		oks.setText("乐友游是个好软件，大家都去下载吧");
		oks.setImageUrl("http://7kttjt.com1.z0.glb.clouddn.com/image/view/app_icons/58027f02630e6ecc42393af0468b2153/120");
		oks.setUrl("http://www.pgyer.com/hitogether");
		oks.setComment("我是乐友游的超级内容部分");

		oks.setSite("乐友游的网站名称");
		oks.setSiteUrl("http://www.baidu.com");

	}

	private void initView() {
		mQuickCommentBar = (QuickCommentBar) findViewById(R.id.id_qcb_reply);
		mLayoutTravalers = (MyGuideOrTravalersLayout) findViewById(R.id.id_travaler_layout);
		mLayoutGuides = (MyGuideOrTravalersLayout) findViewById(R.id.id_guide_layout);
		guidesMore = (TextView) findViewById(R.id.id_guids_more);
		travalersMore = (TextView) findViewById(R.id.id_travalers_more);

		mQuickCommentBar.setTopic("123", "456", "789", "1011");
		mQuickCommentBar.getBackButton().setOnClickListener(this);

		mQuickCommentBar.setOnekeyShare(oks);

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
						ShowToast(pos + "");
					}
				});
		mLayoutTravalers
				.setOnGuideItemClickListener(new onGuideItemClickListener() {

					@Override
					public void onItemClick(View v, int pos) {
						ShowToast(pos + "");
					}
				});

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

//	@OnClick(R.id.id_bt_share)
//	private void share(View v) {
//
//		oks.show(this);
//
//	}

}

package com.lyy.hitogether.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.lyy.hitogether.R;
import com.lyy.hitogether.mydialog.SweetAlertDialog;
import com.lyy.hitogether.view.CircleImageView;
import com.lyy.hitogether.view.MyLoginView;
import com.lyy.hitogether.view.MyLoginView.onLoginListener;

public class LoginActivity extends BaseActivity {
	// 圆角头像
	private CircleImageView userAvarterImg;
	// 登录界面
	private MyLoginView myLoginView;
	// 登录按钮只能点一次，否走如果你连续速度很快的点登录按钮的话，dialog将会被连续触发，也就会产生很多个MainActivity
	private boolean isFirstClick = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置无标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		// 透明状态栏
		getWindow()
				.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		init();
	}

	private void init() {
		initId();
		initView();
		initEvent();
	}

	private void initEvent() {
		myLoginView.setOnLoginListener(new onLoginListener() {

			@Override
			public void onClick(View v) {
				if (isFirstClick) {
					isFirstClick = false;
					final SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(
							LoginActivity.this, 5);
					sweetAlertDialog.setTitleText("登录中...");
					sweetAlertDialog.showCancelButton(false);
					sweetAlertDialog.show();
					new Handler().postDelayed(new Runnable() {
						public void run() {
							sweetAlertDialog.dismiss();
							startActivity(new Intent(LoginActivity.this,
									MainActivity.class));
							LoginActivity.this.finish();

						}
					}, 2000);
				}

			}
		});

	}

	private void initView() {
		new Handler().postDelayed(new Runnable() {
			public void run() {
				userAvarterImg.setVisibility(View.VISIBLE);
			}
		}, 600);
	}

	private void initId() {
		userAvarterImg = (CircleImageView) findViewById(R.id.login_user_avarter);
		myLoginView = (MyLoginView) findViewById(R.id.id_my_login_view);

	}

	@Override
	public void onBackPressed() {
		isFirstClick = true;
		super.onBackPressed();
	}
}

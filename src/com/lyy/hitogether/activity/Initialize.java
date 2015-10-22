package com.lyy.hitogether.activity;

import io.rong.imkit.RongIM;

import com.lyy.hitogether.R;
import com.lyy.hitogether.test.TestActivity;
import com.lyy.hitogether.util.InitBmobAndRong;
import com.lyy.hitogether.util.InitBmobAndRong.LinitLisetener;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class Initialize extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_initialize);

		InitBmobAndRong.init(Initialize.this, new LinitLisetener() {

			@Override
			public void success(String userId) {
				ShowToast("连接成功");
				startActivity(new Intent(Initialize.this, TestActivity.class));
				Initialize.this.finish();

			}

			@Override
			public void faild() {
				ShowToast("连接失败");

			}
		});

		// // ��Ҫ�Ķ�
		// new Handler().postDelayed(new Runnable() {
		// public void run() {
		// startActivity(new Intent(Initialize.this, MainActivity.class));
		// Initialize.this.finish();
		// }
		// }, 1500);
	}
}

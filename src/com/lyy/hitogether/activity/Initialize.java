package com.lyy.hitogether.activity;

import com.lyy.hitogether.R;

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
		//需要改动
		new Handler().postDelayed(new Runnable() {
			public void run() {
				startActivity(new Intent(Initialize.this, LoginActivity.class));
				Initialize.this.finish();
			}
		}, 1500);
	}
}

package com.lyy.hitogether.activity;

import com.lyy.hitogether.R;
import com.lyy.hitogether.manager.SystemBarTintManager;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;

public class ConversationActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_conversation);
		// getIntent().getStringExtra(name)
		
//		getWindow()
//				.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//		SystemBarTintManager tintManager = new SystemBarTintManager(this);
//		tintManager.setStatusBarTintEnabled(true);
//		tintManager.setTintColor(Color.parseColor("#5CACEE"));
	}

}

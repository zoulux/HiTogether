package com.lyy.hitogether.test;

import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.listener.CloudCodeListener;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lyy.hitogether.R;
import com.lyy.hitogether.activity.BaseActivity;
import com.lyy.hitogether.bean.Service;
import com.lyy.hitogether.util.JsonUtils;

public class TestActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		Button bt = (Button) findViewById(R.id.id_bt_test);
		bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				getJsonFromBmob();
			}
		});
	}

	protected void getJsonFromBmob() {
		String cloudName = "test";
		AsyncCustomEndpoints cloudCode = new AsyncCustomEndpoints();
		cloudCode.callEndpoint(TestActivity.this, cloudName,
				new CloudCodeListener() {

					@Override
					public void onSuccess(Object result) {

						Message msg = Message.obtain();
						msg.obj = result;
						mhHandler.sendMessage(msg);

					}

					@Override
					public void onFailure(int errCode, String err) {

					}
				});

	}

	Handler mhHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			Gson gson = new Gson();

			// List<Service> list = gson.fromJson(msg.obj.toString(),
			// new TypeToken<List<Service>>() {
			// }.getType());

//			List<Service> list = JsonUtils.getList(msg.obj.toString());
//			for (Service service : list) {
//				Log.i("TAG", service.toString());
//			}
		};
	};
}

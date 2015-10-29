package com.lyy.hitogether.activity;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import cn.bmob.v3.listener.CloudCodeListener;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lyy.hitogether.R;
import com.lyy.hitogether.adapter.GroupAdapter;
import com.lyy.hitogether.adapter.GroupAdapter.GroupListener;
import com.lyy.hitogether.bean.Group;
import com.lyy.hitogether.util.HttpUtils;

public class GroupActivity extends BaseActivity {

	private Context mContext = GroupActivity.this;
	private String Tag = GroupActivity.class.getSimpleName();

	private List<Group> mData = null;
	private GroupAdapter groupAdapter = null;
	@ViewInject(R.id.id_lv_group_list)
	private ListView mListView;

	private Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			String json = (String) msg.obj;
			Log.i(Tag, json);
			Gson gson = new Gson();

			mData = gson.fromJson(json, new TypeToken<List<Group>>() {
			}.getType());

			groupAdapter = new GroupAdapter(GroupActivity.this, mData);
			mListView.setAdapter(groupAdapter);
			groupAdapter.setOnBtClick(new GroupListener() {

				@Override
				public void onClick(View v, int position) {
					ShowToast(position+"");

				}
			});
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group);
		ViewUtils.inject(this);

		HttpUtils.getHttpData(mContext, "getAllGroup", null,
				new CloudCodeListener() {

					@Override
					public void onSuccess(Object result) {
						Message msg = Message.obtain();
						msg.obj = result;
						mHandler.sendMessage(msg);
					}

					@Override
					public void onFailure(int code, String err) {

					}
				});

	}
}

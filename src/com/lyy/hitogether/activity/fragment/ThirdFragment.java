package com.lyy.hitogether.activity.fragment;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import cn.bmob.v3.listener.CloudCodeListener;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lyy.hitogether.R;
import com.lyy.hitogether.adapter.ThirdFragmentAdapter;
import com.lyy.hitogether.adapter.ThirdFragmentAdapter.OnThirdFragmentBtListener;
import com.lyy.hitogether.bean.MyUser;
import com.lyy.hitogether.bean.Service;
import com.lyy.hitogether.bean.ThirdFragmentBean;
import com.lyy.hitogether.global.App;
import com.lyy.hitogether.mydialog.SweetAlertDialog;
import com.lyy.hitogether.util.ConnectRong;
import com.lyy.hitogether.util.ConnectRong.MyConnectListener;
import com.lyy.hitogether.util.HttpUtils;

public class ThirdFragment extends BaseFragment {
	private GridView mGriView;
	private ThirdFragmentAdapter thirdFragmentAdapter;

	private boolean isPrepared;
	private List<Service> list;

	// private SweetAlertDialog alertDialog;

	// private boolean mHasLoadOnce=false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("ThirdFragment", "onCreateView");
		View view = inflater.inflate(R.layout.fragment_third, container, false);

		initView(view);
		isPrepared = true;

		Log.i("TAG", "2");
		lazyLoad();

		return view;
	}

	private void initEvent() {
		thirdFragmentAdapter
				.setOnThirdFragmentBtListener(new OnThirdFragmentBtListener() {

					@Override
					public void onBtclick(View v, int position) {
						// ShowToast(position + "");

						chatWithGuide(position);
						if (!isContain(position)) {
							MyUser user = list.get(position).getUser();
							Uri uri = Uri.parse(user.getAvatar());
							App.getInsatnce()
									.getUserInfos()
									.add(new UserInfo(user.getObjectId(), user
											.getNick(), uri));
							// ConnectRong.updateFrindsInfo(null);
						}

					}
				});
	}

	protected boolean isContain(int position) {

		for (UserInfo user : App.getInsatnce().getUserInfos()) {

			if (user.getUserId().equals(
					list.get(position).getUser().getObjectId())) {
				return true;
			}
		}

		return false;
	}

	protected void chatWithGuide(int position) {
		String targetUserId = list.get(position).getUser().getObjectId();
		String targetUserName = list.get(position).getUser().getUsername();
		RongIM.getInstance().startPrivateChat(getActivity(), targetUserId,
				"与" + targetUserName + "聊天中");

	}

	private void initView(View view) {
		// alertDialog=new SweetAlertDialog(getActivity(),
		// SweetAlertDialog.PROGRESS_TYPE);
		// thirdFragmentAdapter = new ThirdFragmentAdapter(getActivity(), null);
		mGriView = (GridView) view
				.findViewById(R.id.id_third_fragment_grideview);
		// mGriView.setAdapter(thirdFragmentAdapter);

	}

	private List<ThirdFragmentBean> getdatas() {

		List<ThirdFragmentBean> bean = new ArrayList<ThirdFragmentBean>();
		for (int i = 0; i < 30; i++) {
			bean.add(new ThirdFragmentBean(R.drawable.p1,
					R.drawable.default_avarter, "名字" + i, " 中国好景点" + i, " 景点"
							+ i, 2.5f));
		}
		return bean;
	}

	/**
	 * 返回正确结果，接下来去写适配器
	 * 
	 * @param json
	 *            json数据
	 */
	protected void handleSuccess(String json) {
		Log.i("ThirdFragment", "handleSuccess");
		baseProgress.cancel();
		Gson gson = new Gson();
		list = gson.fromJson(json, new TypeToken<List<Service>>() {
		}.getType());

		// Log.i("TAG", list.toString());
		thirdFragmentAdapter = new ThirdFragmentAdapter(getActivity(), list);
		mGriView.setAdapter(thirdFragmentAdapter);

		initEvent();
		// mHasLoadOnce = true;
	}

	/**
	 * 返回错误结果，提示。。
	 * 
	 * @param code
	 *            错误代码
	 */

	protected void handleFaild(String code) {
		Log.i("ThirdFragment", "handleFaild");
		baseProgress.cancel();
		ShowToast("加载失败，sorry!");
	}

	/**
	 * 不可见不加载，没准备好不加载
	 */
	@Override
	protected void lazyLoad() {

		if (!isPrepared || !isVisible) {
			Log.i("lazyLoad1", isPrepared + ":" + isPrepared);
			return;
		}

		Log.i("lazyLoad2", isPrepared + ":" + isPrepared);
		baseProgress.show();
		postAsync("getAllService", null);
	}

	@Override
	public void onPause() {
		isVisible = false;
		super.onPause();
	}

	@Override
	public boolean handleMessage(Message msg) {

		switch (msg.what) {
		case GET_SUCCESS:
			handleSuccess(msg.obj.toString());
			break;
		case GET_FAILD:
			handleFaild(msg.obj.toString());
			break;
		default:
			break;
		}

		return false;
	}
}

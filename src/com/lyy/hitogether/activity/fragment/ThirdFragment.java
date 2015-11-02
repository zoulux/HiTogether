package com.lyy.hitogether.activity.fragment;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.lyy.hitogether.R;
import com.lyy.hitogether.activity.fragment.first_fragment.FirstFragmentDestination;
import com.lyy.hitogether.adapter.ThirdFragmentAdapter;
import com.lyy.hitogether.adapter.ThirdFragmentAdapter.OnThirdFragmentBtListener;
import com.lyy.hitogether.bean.MyUser;
import com.lyy.hitogether.bean.Service;
import com.lyy.hitogether.bean.ThirdFragmentBean;
import com.lyy.hitogether.global.App;

public class ThirdFragment extends BaseFragment {
	private PullToRefreshGridView mGridView;
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
		mGridView = (PullToRefreshGridView) view
				.findViewById(R.id.id_third_fragment_grideview);
		// mGriView.setAdapter(thirdFragmentAdapter);

	}
	
	private void initIndicator() {
		ILoadingLayout startLabels = mGridView
				.getLoadingLayoutProxy(true, false);
		startLabels.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
		startLabels.setRefreshingLabel("正在刷新...");// 刷新时
		startLabels.setReleaseLabel("松开刷新数据");// 下来达到一定距离时，显示的提示

//		ILoadingLayout endLabels = gridView.getLoadingLayoutProxy(
//				false, true);
//		endLabels.setPullLabel("你可劲拉，拉2...");// 刚下拉时，显示的提示
//		endLabels.setRefreshingLabel("好嘞，正在刷新2...");// 刷新时
//		endLabels.setReleaseLabel("你敢放，我就敢刷新2...");// 下来达到一定距离时，显示的提示
	
}
	
	private class GetDataTask extends AsyncTask<Void, Void, Void>
	{

		@Override
		protected Void doInBackground(Void... params)
		{
			try
			{
				//Thread.sleep(2000);
				postAsync("getAllService", null);
			} catch (Exception e)
			{
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result)
		{
//			mListItems.add("" + mItemCount++);
//			mAdapter.notifyDataSetChanged();
			// Call onRefreshComplete when the list has been refreshed.
			mGridView.onRefreshComplete();
		}
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
		initIndicator();
		// Log.i("TAG", list.toString());
		thirdFragmentAdapter = new ThirdFragmentAdapter(getActivity(), list);
		mGridView.setAdapter(thirdFragmentAdapter);
		
		mGridView
		.setOnRefreshListener(new OnRefreshListener2<GridView>()
		{

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<GridView> refreshView)
			{
				Log.e("TAG", "onPullDownToRefresh"); // Do work to
				String label = DateUtils.formatDateTime(
						ThirdFragment.this.getActivity(),
						System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME
								| DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);

				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy()
						.setLastUpdatedLabel(label);

				new GetDataTask().execute();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<GridView> refreshView)
			{
				Log.e("TAG", "onPullUpToRefresh"); // Do work to refresh
													// the list here.
				new GetDataTask().execute();
			}
		});

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

package com.lyy.hitogether.activity.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.lyy.hitogether.R;
import com.lyy.hitogether.adapter.ThirdFragmentAdapter;
import com.lyy.hitogether.adapter.ThirdFragmentAdapter.OnThirdFragmentBtListener;
import com.lyy.hitogether.bean.ThirdFragmentBean;

public class ThirdFragment extends Fragment {
	private GridView mGriView;
	private ThirdFragmentAdapter thirdFragmentAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_third, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {

		super.onViewCreated(view, savedInstanceState);
		init(view);

	}

	private void init(View view) {
		initView(view);
		initEvent();
	}

	private void initEvent() {
			thirdFragmentAdapter.setOnThirdFragmentBtListener(new OnThirdFragmentBtListener() {
				
				@Override
				public void onBtclick(View v, int position) {
						Toast.makeText(ThirdFragment.this.getActivity(), position+"", 1).show();
				}
			});
	}

	private void initView(View view) {
		thirdFragmentAdapter = new ThirdFragmentAdapter(getActivity(), getdatas());
		mGriView = (GridView) view
				.findViewById(R.id.id_third_fragment_grideview);
		mGriView.setAdapter(thirdFragmentAdapter);
	}

	private List<ThirdFragmentBean> getdatas() {
		List<ThirdFragmentBean> bean = new ArrayList<ThirdFragmentBean>();
		for (int i = 0; i < 30; i++) {
			bean.add(new ThirdFragmentBean(R.drawable.p1,
					R.drawable.default_avarter, "雁哥" + i, "这是个好地方" + i, "中国"
							+ i, 2.5f));
		}
		return bean;
	}

}

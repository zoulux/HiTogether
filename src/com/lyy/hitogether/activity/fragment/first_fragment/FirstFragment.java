package com.lyy.hitogether.activity.fragment.first_fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lyy.hitogether.R;
import com.lyy.hitogether.view.MyViewPager;

public class FirstFragment extends Fragment {

	private MyViewPager viewPager;
	private List<Fragment> mTabs = new ArrayList<Fragment>();
	private FragmentPagerAdapter mAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.err.println("FirstFragment onCreateView");
		firstFragmentDestination.setCountMin();
		return inflater.inflate(R.layout.fragment_first, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		initView(view);
		initData();
		viewPager.setAdapter(mAdapter);
		viewPager.setScanScroll(false);
	}

	private FirstFragmentDestination firstFragmentDestination = new FirstFragmentDestination();

	private void initData() {

		mTabs.add(firstFragmentDestination);
		mTabs.add(new FirstFragmentOfFriend());
		mAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {

			@Override
			public int getCount() {
				return mTabs.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				return mTabs.get(arg0);
			}
		};
	}

	public void setItem(int pos) {
		viewPager.setCurrentItem(pos, false);

	}

	private void initView(View v) {
		viewPager = (MyViewPager) v
				.findViewById(R.id.id_viewpager_fragment_first);

	}
	@Override
	public void onDestroyView() {
		System.err.println("onDestroyViewMax");
		firstFragmentDestination.setCountMax();

		super.onDestroy();
	}

	public void setMin() {

	}

}

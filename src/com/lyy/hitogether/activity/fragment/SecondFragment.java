package com.lyy.hitogether.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.lyy.hitogether.R;
import com.lyy.hitogether.activity.ShareMyTravalActivity;

public class SecondFragment extends Fragment {
	
	private Button bt;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_second, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initView(view);
		initEvent();
	}

	private void initEvent() {
		bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(SecondFragment.this.getActivity(), ShareMyTravalActivity.class));
			}
		});
		
	}

	private void initView(View view) {
		bt = (Button) view.findViewById(R.id.button1);
		
	}

	
}

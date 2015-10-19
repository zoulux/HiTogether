package com.lyy.hitogether.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyBaseAdapter<T> extends BaseAdapter {

	public LayoutInflater commomInflater;
	public List<T> commonDatas;
	 
	public MyBaseAdapter(Context context,List<T> commonDatas) {
		super();
		this.commonDatas = commonDatas;
		commomInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return commonDatas.size();
	}

	@Override
	public T getItem(int position) {
		return commonDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

}

package com.lyy.hitogether.adapter;

import java.util.List;

import com.lyy.hitogether.R;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class FirstFragmentOfFriendGridViewAdapter extends MyBaseAdapter<Integer> {

	public FirstFragmentOfFriendGridViewAdapter(Context context,
			List<Integer> commonDatas) {
		super(context, commonDatas);
		
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView=commomInflater.inflate(
					R.layout.item_first_fragment_of_friend_listview_gridview, null, false);
			holder.image = (ImageView) convertView
					.findViewById(R.id.gridview_item_img);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (this.commonDatas != null) {
			holder.image.setImageResource(commonDatas.get(position));
			
		}
		return convertView;
	}
	
	
	
	 class ViewHolder {
		ImageView image;
	}

}

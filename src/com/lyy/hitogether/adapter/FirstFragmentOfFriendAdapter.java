package com.lyy.hitogether.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lyy.hitogether.R;
import com.lyy.hitogether.bean.FirstFragmentOfFriendbean;

public class FirstFragmentOfFriendAdapter extends
		MyBaseAdapter<FirstFragmentOfFriendbean> {

	private Context context;

	public FirstFragmentOfFriendAdapter(Context context,
			List<FirstFragmentOfFriendbean> commonDatas) {
		super(context, commonDatas);
		this.context = context;

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = commomInflater.inflate(
					R.layout.item_first_fragment_of_friend_listview, null);
			viewHolder.gridView = (GridView) convertView
					.findViewById(R.id.id_first_fragment_of_friend_list_gridview);
			viewHolder.myFriendAvartar = (ImageView) convertView
					.findViewById(R.id.id_first_fragment_of_friend_list_avartar);
			viewHolder.myFriendName = (TextView) convertView
					.findViewById(R.id.id_first_fragment_of_friend_list_friend_name);
			viewHolder.sendTime = (TextView) convertView
					.findViewById(R.id.id_first_fragment_of_friend_list_send_time);
			viewHolder.yourDesc = (TextView) convertView
					.findViewById(R.id.id_first_fragment_of_friend_list_desc);
			viewHolder.praiseCount = (TextView) convertView
					.findViewById(R.id.id_first_fragment_of_friend_list_praise_count);
			viewHolder.talkToFriendBt = (Button) convertView
					.findViewById(R.id.id_first_fragment_of_friend_list_talk_to_friend_bt);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.myFriendAvartar.setImageResource(commonDatas.get(position)
				.getMyFriendAvartar());
		viewHolder.myFriendName.setText(commonDatas.get(position)
				.getMyFriendName());
		viewHolder.praiseCount.setText(commonDatas.get(position)
				.getPraiseCount()+"");
		viewHolder.sendTime.setText(commonDatas.get(position).getSendTime());
		viewHolder.yourDesc.setText(commonDatas.get(position).getYourDesc());

		viewHolder.talkToFriendBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(context, position + "", Toast.LENGTH_SHORT)
						.show();

			}
		});
		
		viewHolder.gridView.setAdapter(new FirstFragmentOfFriendGridViewAdapter(context, getData(position)));
		return convertView;

	}
	
	private ArrayList<Integer> getData(int pos) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		int a = commonDatas.get(pos).getPicCount();
		for (int i = 0; i < a; i++) {
			list.add(R.drawable.p2);
		}
		return list;
	}

	class ViewHolder {
		GridView gridView;
		ImageView myFriendAvartar;
		TextView myFriendName;
		TextView sendTime;
		TextView yourDesc;
		TextView praiseCount;
		Button talkToFriendBt;
	}
}

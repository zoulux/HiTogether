package com.lyy.hitogether.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lyy.hitogether.R;
import com.lyy.hitogether.bean.Service;
import com.lyy.hitogether.bean.ThirdFragmentBean;
import com.lyy.hitogether.view.CircleImageView;

public class ThirdFragmentAdapter extends MyBaseAdapter<Service> {
	private OnThirdFragmentBtListener mThirdFragmentBtListener;

	public interface OnThirdFragmentBtListener {
		void onBtclick(View v, int position);
	}

	public void setOnThirdFragmentBtListener(
			OnThirdFragmentBtListener thirdFragmentBtListener) {
		this.mThirdFragmentBtListener = thirdFragmentBtListener;
	}

	public ThirdFragmentAdapter(Context context, List<Service> commonDatas) {
		super(context, commonDatas);

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = commomInflater.inflate(R.layout.item_third_fragment,
					null);

			viewHolder.guideImage = (CircleImageView) convertView
					.findViewById(R.id.id_third_fragment_guide_avartar);
			viewHolder.guideName = (TextView) convertView
					.findViewById(R.id.id_third_fragment_guide_name);
			viewHolder.scenImage = (ImageView) convertView
					.findViewById(R.id.id_third_fragment_scen_img);
			viewHolder.description = (TextView) convertView
					.findViewById(R.id.id_third_fragment_desc);
			viewHolder.place = (TextView) convertView
					.findViewById(R.id.id_third_fragment_place);
			viewHolder.grade = (RatingBar) convertView
					.findViewById(R.id.id_third_fragment_grade);
			viewHolder.apointmentBt = (Button) convertView
					.findViewById(R.id.id_fragment_bt_appointment);
			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.guideImage.setImageResource(R.drawable.icon);
		viewHolder.guideName.setText(commonDatas.get(position).getUser()
				.getUsername());
		viewHolder.scenImage.setImageResource(R.drawable.p2);
		viewHolder.description.setText(commonDatas.get(position).getSummary());
		viewHolder.place.setText(commonDatas.get(position).getDestination());
		viewHolder.grade.setRating((float) (Math.random() * 5));
		viewHolder.apointmentBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mThirdFragmentBtListener != null) {
					mThirdFragmentBtListener.onBtclick(v, position);
				}

			}
		});
		return convertView;
	}

	public class ViewHolder {
		ImageView scenImage;
		CircleImageView guideImage;
		TextView guideName;
		TextView praisedCount;
		TextView guideFee;
		TextView description;
		TextView place;
		RatingBar grade;
		Button apointmentBt;
	}

}

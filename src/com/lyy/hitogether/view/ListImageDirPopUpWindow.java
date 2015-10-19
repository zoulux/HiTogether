package com.lyy.hitogether.view;

import java.util.List;

import com.lyy.hitogether.R;
import com.lyy.hitogether.bean.FolderBean;
import com.lyy.hitogether.util.ImageLoader;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class ListImageDirPopUpWindow extends PopupWindow {
	private int mWidth;
	private int mHeight;
	private View mConvertView;
	private ListView mListView;
	private List<FolderBean> mDatas;

	public interface onDirSelectListener {
		void onSelected(FolderBean folderBean);
	}

	public onDirSelectListener mListener;

	// public void setOnDirSelectedListener(onDirSelectListener
	// dirSelectListener) {
	//
	// }

	public void setOnDirSelectedListener(onDirSelectListener dirSelectListener) {
		mListener = dirSelectListener;

	}

	public ListImageDirPopUpWindow(Context context, List<FolderBean> mDatas) {
		calWidthAndHeight(context);
		mConvertView = LayoutInflater.from(context).inflate(
				R.layout.popup_main, null);
		this.mDatas = mDatas;
		setContentView(mConvertView);
		setWidth(mWidth);
		setHeight(mHeight);

		setFocusable(true);
		setTouchable(true);
		setOutsideTouchable(true);
		setBackgroundDrawable(new BitmapDrawable());

		setTouchInterceptor(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					dismiss();
					return true;
				}
				return false;
			}
		});

		initViews(context);
		initEvent();

	}

	private void initEvent() {
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (mListener != null) {
					mListener.onSelected(mDatas.get(position));
				}
			}
		});
	}

	private void initViews(Context context) {
		mListView = (ListView) mConvertView.findViewById(R.id.id_list_dir);
		mListView.setAdapter(new ListDirAdapter(context, mDatas));
	}

	/**
	 * 计算popupwindow的宽度和高度
	 * 
	 * @param context
	 */
	private void calWidthAndHeight(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics displayMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(displayMetrics);

		mWidth = displayMetrics.widthPixels;
		mHeight = (int) (displayMetrics.heightPixels * 0.7);

	}

	private class ListDirAdapter extends ArrayAdapter<FolderBean> {
		private LayoutInflater mInflater;

		public ListDirAdapter(Context context, List<FolderBean> objects) {
			super(context, 0, objects);
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.item_popup, parent,
						false);
				viewHolder.mImage = (ImageView) convertView
						.findViewById(R.id.id_dir_item_image);
				viewHolder.mDirCount = (TextView) convertView
						.findViewById(R.id.id_dir_item_count);
				viewHolder.mDirName = (TextView) convertView
						.findViewById(R.id.id_dir_item_name);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			FolderBean folderBean = getItem(position);
			// 重置
			viewHolder.mImage.setImageResource(R.drawable.pictures_no);
			ImageLoader.getInstance().LoadImage(folderBean.getFirstImagePath(),
					viewHolder.mImage);
			viewHolder.mDirName.setText(folderBean.getName());
			viewHolder.mDirCount.setText(folderBean.getCount() + "");

			return convertView;
		}

		private class ViewHolder {
			ImageView mImage;
			TextView mDirName;
			TextView mDirCount;
		}

	}

}

package com.lyy.hitogether.activity.fragment.first_fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

import com.lyy.hitogether.R;
import com.lyy.hitogether.adapter.MyPagerAdapter;
import com.lyy.hitogether.adapter.PictureAndTextAdapter;
import com.lyy.hitogether.view.MyViewPager;

public class FirstFragmentDestination extends Fragment {

	private GridView gridView;
	private String[] scen = new String[] { "风景1", "风景2", "风景3", "风景4", "风景5",
			"风景6", "风景7", "风景8", "风景9", "风景10" };
	private int[] pics = new int[] { R.drawable.p1, R.drawable.p2,
			R.drawable.p3, R.drawable.p4, R.drawable.p1, R.drawable.p2,
			R.drawable.p3, R.drawable.p4, R.drawable.p1, R.drawable.p2 };

	private MyViewPager myViewPager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_first_destination, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		init(view);

	}

	private void init(View view) {
		initView(view);
		gridView.setAdapter(new PictureAndTextAdapter(scen, pics, getActivity()));
		initEvent();
	}

	private void initEvent() {
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(getActivity(), position + "", 1).show();

			}
		});
	}

	private void initView(View view) {
		gridView = (GridView) view.findViewById(R.id.id_gridview_des);
		myViewPager = (MyViewPager) view
				.findViewById(R.id.id_viewpager_fragment_first_dec);
		initAdapter();
	}

	private MyPagerAdapter adapter;

	private void initAdapter() {
		adapter = new MyPagerAdapter();
		ImageView v = new ImageView(getActivity());
		v.setLayoutParams(new LayoutParams(-1, -1));
		v.setScaleType(ScaleType.FIT_XY);
		v.setImageResource(R.drawable.p1);

		ImageView v2 = new ImageView(getActivity());
		v2.setLayoutParams(new LayoutParams(-1, -1));
		v2.setScaleType(ScaleType.FIT_XY);
		v2.setImageResource(R.drawable.p2);

		ImageView v3 = new ImageView(getActivity());
		v3.setLayoutParams(new LayoutParams(-1, -1));
		v3.setScaleType(ScaleType.FIT_XY);
		v3.setImageResource(R.drawable.p3);
		adapter.addItem("第一景点", v);
		adapter.addItem("第二景点", v2);
		adapter.addItem("第三景点", v3);
		myViewPager.setAdapter(adapter);
		controllMyViewPager();
	//	new Thread(new MyThread()).start();

	}

	private Handler mHandler;

	private void controllMyViewPager() {
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {

				int count = msg.arg1;
				switch (count) {
				case 1:
					myViewPager.setCurrentItem(0, false);
					break;
				case 2:
					myViewPager.setCurrentItem(1, false);
					break;

				case 3:
					myViewPager.setCurrentItem(2, false);
					break;

				}

			}
		};
	}

	public class MyThread extends Thread {
		int count = 1;

		@Override
		public void run() {
			super.run();
			while (true) {
				try {
					Thread.sleep(3000);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (count == 4) {
					count = 1;
				}
				// ����Ϣ���л�ȡ��Ϣ�����û����Ϣ������һ����Ϣ������У���ȡ������ϢЯ����ݣ���handler����
				Message message = Message.obtain();
				message.arg1 = count;
				count++;
				mHandler.sendMessage(message);

			}

		}
	}

}
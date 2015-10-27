package com.lyy.hitogether.activity.fragment.first_fragment;

import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lyy.hitogether.R;
import com.lyy.hitogether.activity.fragment.BaseFragment;
import com.lyy.hitogether.adapter.MyPagerAdapter;
import com.lyy.hitogether.adapter.PictureAndTextAdapter;
import com.lyy.hitogether.bean.HotScenic;
import com.lyy.hitogether.view.MyViewPager;
//github.com/zoulux/HiTogether

public class FirstFragmentDestination extends BaseFragment {

	private GridView gridView;
	private String[] scen = new String[] { "风景1", "风景2", "风景3", "风景4", "风景5",
			"风景6", "风景7", "风景8", "风景9", "风景10" };
	private int[] pics = new int[] { R.drawable.p1, R.drawable.p2,
			R.drawable.p3, R.drawable.p4, R.drawable.p1, R.drawable.p2,
			R.drawable.p3, R.drawable.p4, R.drawable.p1, R.drawable.p2 };

	private MyViewPager myViewPager;

//	private SweetAlertDialog alertDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_first_destination, null);
		isPrepared = true;
		init(view);
		lazyLoad();
		return view;
	}

	private void init(View view) {
		initView(view);

		initEvent();
	}

	private void initEvent() {
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ShowToast(position + "");

			}
		});
	}

	private void initView(View view) {
//		alertDialog = new SweetAlertDialog(getActivity(),
//				SweetAlertDialog.PROGRESS_TYPE);
		gridView = (GridView) view.findViewById(R.id.id_gridview_des);
		myViewPager = (MyViewPager) view
				.findViewById(R.id.id_viewpager_fragment_first_dec);
		initAdapter();

	}

	private MyPagerAdapter adapter;

	public void initAdapter() {
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

		// adapter.getp
		myViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(final int arg0) {
				/**
				 * 当用手切换页面时，来获得当前的view，从而给每个view设置监听
				 */
				adapter.getItem(arg0).v
						.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								Toast.makeText(
										FirstFragmentDestination.this
												.getActivity(), arg0 + "",
										Toast.LENGTH_SHORT).show();

							}
						});

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		/**
		 * 根据message获取的值来设定ViewPager的item
		 */
		controllMyViewPager();
		new Thread(new MyThread()).start();

	}

	private Handler mHandler;
	private boolean isPrepared;
	/**
	 * 根据message获取的值来设定ViewPager的item
	 */
	private void controllMyViewPager() {
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {

				final int count = msg.arg1;

				switch (count) {
				case 1:
					myViewPager.setCurrentItem(0, true);
					break;
				case 2:
					myViewPager.setCurrentItem(1, true);
					break;

				case 3:
					myViewPager.setCurrentItem(2, true);
					break;

				}

			}
		};

	}

	private int count = 1;

	public class MyThread extends Thread {

		@Override
		public void run() {
			super.run();
			/**
			 * 有三个页面，且每过三秒自动切换一次
			 */
			PagerItemlogic();

		}
	}

	@Override
	protected void lazyLoad() {

		if (!isPrepared || !isVisible) {
			// Log.i("lazyLoad1", isPrepared + ":" + isPrepared);
			return;
		}

		// Log.i("lazyLoad2", isPrepared + ":" + isPrepared);

		baseProgress.show();
		postAsync("getAllHotScenic", null);
	}

	/**
	 * 有三个页面，且每过三秒自动切换一次
	 */
	public synchronized void PagerItemlogic() {
		while (true) {

			try {
				Thread.sleep(3000);
				if (count > 4) {
					break;
				} else {
					if (count == 4) {
						count = 1;
					}
					/**
					 * 当每个页面自动切换时，获得当前的view，然后做监听
					 */
					setItemListener(count - 1);
					Message message = Message.obtain();
					message.arg1 = count;
					count++;
					mHandler.sendMessage(message);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	
	/**
	 * 当每个页面自动切换时，获得当前的view，然后做监听
	 */
	private synchronized void setItemListener(final int count) {
		adapter.getItem(count).v.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(FirstFragmentDestination.this.getActivity(),
						count + "", Toast.LENGTH_SHORT).show();

			}
		});

	}

	@Override
	public void onPause() {

		isVisible = false;
		baseProgress.dismiss();

		isPrepared = false;

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

	private void handleFaild(String string) {
		baseProgress.dismiss();

	}

	private void handleSuccess(String json) {
		baseProgress.dismiss();


		Gson gson = new Gson();
		List<HotScenic> list = gson.fromJson(json,
				new TypeToken<List<HotScenic>>() {
				}.getType());

		// Log.i("TAG", list.toString());
		PictureAndTextAdapter adapter = new PictureAndTextAdapter(
				getActivity(), list);
		gridView.setAdapter(adapter);
	}

	public void setCountMax() {

		count = 100;

	}

	public void setCountMin() {
		count = 1;

	}

}

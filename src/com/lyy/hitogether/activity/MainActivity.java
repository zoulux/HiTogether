package com.lyy.hitogether.activity;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation.ConversationType;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.lyy.hitogether.R;
import com.lyy.hitogether.activity.fragment.SecondFragment;
import com.lyy.hitogether.activity.fragment.ThirdFragment;
import com.lyy.hitogether.activity.fragment.first_fragment.FirstFragment;
import com.lyy.hitogether.manager.SystemBarTintManager;
import com.lyy.hitogether.view.ChangeColorIconWithText;
import com.lyy.hitogether.view.CustomTitleBarView;
import com.lyy.hitogether.view.CustomTitleBarView.onLeftBarViewClickListener;
import com.lyy.hitogether.view.MainTopbarView;
import com.lyy.hitogether.view.MyViewPager;

public class MainActivity extends FragmentActivity implements OnClickListener {

	private String[] mPlanetTitles;

	private DrawerLayout mDrawerLayout;

	private ListView mDrawerList;

	private ImageView imageViewAvar;
	private RadioGroup radioGroup;
	private RadioButton ra1;
	private RadioButton ra2;

	private MyViewPager mViewPager;
	private List<Fragment> mTabs = new ArrayList<Fragment>();

	private FragmentPagerAdapter mAdapter;

	private MainTopbarView mainTopbarView;
	private CustomTitleBarView customTitleBarView_1;
	private CustomTitleBarView customTitleBarView_2;
	private CustomTitleBarView customTitleBarView_3;
	
	private static final int MENU_ITEM_QUIT = 9; 

	private List<ChangeColorIconWithText> mTabIndicators = new ArrayList<ChangeColorIconWithText>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		getWindow()
				.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setTintColor(Color.parseColor("#5CACEE"));
		init();
	}

	private void init() {
		imageViewAvar = (ImageView) findViewById(R.id.id_main_title_bar_avatar);
		// topBarTitleText = (TextView) findViewById(R.id.id_topBartext);
		// left = (TopbarBtView) findViewById(R.id.id_topBarLeft);
		// topBarTitleText.setText(R.string.app_name);
		// left.setVisibility(View.VISIBLE);
		// left.setGravity(Gravity.CENTER_VERTICAL);
		// left.setTopbarCircleImageDrawable(R.drawable.default_avarter);
		imageViewAvar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
					mDrawerLayout.closeDrawer(Gravity.LEFT);
				} else {
					mDrawerLayout.openDrawer(Gravity.LEFT);
				}

			}
		});
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		// ��Listview
		mPlanetTitles = getResources().getStringArray(R.array.anim_type);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mPlanetTitles));
		mDrawerList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//Toast.makeText(MainActivity.this, position+"", 1).show();
				/**
				 * 左侧侧滑菜单的设置
				 * @param position
				 */
				menuItemeEvent(position);
				mDrawerList.setItemChecked(position, true);
				mDrawerLayout.closeDrawer(mDrawerList);

			}
		});
		initView();
		initData();
		mViewPager.setAdapter(mAdapter);
		initEvent();

	}
	
	
	/**
	 * 左侧侧滑菜单的设置
	 * @param position
	 */
	protected void menuItemeEvent(int position) {
				switch (position) {
				case MENU_ITEM_QUIT:
					startActivity(new Intent(MainActivity.this,LoginActivity.class));
					MainActivity.this.finish();
					break;

				default:
					break;
				}
		
	}

	private void initEvent() {
		mViewPager.setScanScroll(false);
	}

	private FirstFragment firstFragment = new FirstFragment();

	private void initData() {

		mTabs.add(firstFragment);
		mTabs.add(new SecondFragment());
		mTabs.add(new ThirdFragment());

		ConversationListFragment listFragment = ConversationListFragment
				.getInstance();
		Uri uri = Uri
				.parse("rong://" + getApplicationInfo().packageName)
				.buildUpon()
				.appendPath("conversationlist")
				.appendQueryParameter(ConversationType.PRIVATE.getName(),
						"flase")
				.appendQueryParameter(ConversationType.GROUP.getName(), "true")
				.appendQueryParameter(ConversationType.SYSTEM.getName(), "true")
				.build();
		listFragment.setUri(uri);

		mTabs.add(listFragment);
		// mTabs.add(new FourthFragment());
		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

			@Override
			public int getCount() {
				return mTabs.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				Log.i("Fragment", "getItem");
				return mTabs.get(arg0);
			}
		};
	}

	private void initView() {
		customTitleBarView_1 = (CustomTitleBarView) findViewById(R.id.id_main_CustomTitleBarView_1);
		customTitleBarView_2 = (CustomTitleBarView) findViewById(R.id.id_main_CustomTitleBarView_2);
		customTitleBarView_3 = (CustomTitleBarView) findViewById(R.id.id_main_CustomTitleBarView_3);
		mViewPager = (MyViewPager) findViewById(R.id.id_viewpager_main);
		mViewPager.setOffscreenPageLimit(1);
		ChangeColorIconWithText one = (ChangeColorIconWithText) findViewById(R.id.id_one);
		mTabIndicators.add(one);
		ChangeColorIconWithText two = (ChangeColorIconWithText) findViewById(R.id.id_two);
		mTabIndicators.add(two);
		ChangeColorIconWithText three = (ChangeColorIconWithText) findViewById(R.id.id_three);
		mTabIndicators.add(three);
		ChangeColorIconWithText four = (ChangeColorIconWithText) findViewById(R.id.id_four);
		mTabIndicators.add(four);

		radioGroup = (RadioGroup) findViewById(R.id.id_main_title_bar_radio_group);
		ra1 = (RadioButton) findViewById(R.id.id_main_title_bar_radio_group_radio_button_one);
		ra2 = (RadioButton) findViewById(R.id.id_main_title_bar_radio_group_radio_button_two);

		mainTopbarView = (MainTopbarView) findViewById(R.id.id_main_title_bar);

		one.setOnClickListener(this);
		two.setOnClickListener(this);
		three.setOnClickListener(this);
		four.setOnClickListener(this);
		ra1.setOnClickListener(this);
		ra2.setOnClickListener(this);
		one.setIconAlpha(1.0f);

	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {

		int id = v.getId();
		switch (id) {
		case R.id.id_one:
			customTitleBarView_1.setVisibility(View.GONE);
			customTitleBarView_2.setVisibility(View.GONE);
			customTitleBarView_3.setVisibility(View.GONE);
			mainTopbarView.setVisibility(View.VISIBLE);
			resetOtherTabs();
			mTabIndicators.get(0).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(0, true);
			break;
		case R.id.id_two:
			customTitleBarView_1.setVisibility(View.VISIBLE);
			customTitleBarView_2.setVisibility(View.GONE);
			customTitleBarView_3.setVisibility(View.GONE);
			mainTopbarView.setVisibility(View.GONE);

			customTitleBarView_1
					.setOnLeftBarViewClickListener(new onLeftBarViewClickListener() {

						@Override
						public void onclick(View v) {
							Toast.makeText(MainActivity.this, "2", 1).show();

						}
					});

			resetOtherTabs();
			mTabIndicators.get(1).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(1, true);

			break;
		case R.id.id_three:
			customTitleBarView_1.setVisibility(View.GONE);
			customTitleBarView_2.setVisibility(View.VISIBLE);
			customTitleBarView_3.setVisibility(View.GONE);
			mainTopbarView.setVisibility(View.GONE);

			customTitleBarView_2
					.setOnLeftBarViewClickListener(new onLeftBarViewClickListener() {

						@Override
						public void onclick(View v) {
							Toast.makeText(MainActivity.this, "3", 1).show();

						}
					});
			resetOtherTabs();
			mTabIndicators.get(2).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(2, false);
			break;

		case R.id.id_four:
			customTitleBarView_1.setVisibility(View.GONE);
			customTitleBarView_2.setVisibility(View.GONE);
			customTitleBarView_3.setVisibility(View.VISIBLE);
			mainTopbarView.setVisibility(View.GONE);
			resetOtherTabs();
			mTabIndicators.get(3).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(3, false);
			break;

		case R.id.id_main_title_bar_radio_group_radio_button_one:

			firstFragment.setItem(0);
			ra2.setBackgroundColor(Color.alpha(0));
			ra1.setBackground(getResources().getDrawable(
					R.drawable.main_title_radiobutton1));
			break;

		case R.id.id_main_title_bar_radio_group_radio_button_two:

			firstFragment.setItem(1);
			ra1.setBackgroundColor(Color.alpha(0));
			ra2.setBackground(getResources().getDrawable(
					R.drawable.main_title_radiobutton2));

			break;

		}

	}

	/**
	 * ���������Indicator����ɫ
	 */
	private void resetOtherTabs() {
		for (int i = 0; i < mTabIndicators.size(); i++) {
			mTabIndicators.get(i).setIconAlpha(0);
		}
	}

}

package com.lyy.hitogether.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow.OnDismissListener;

import com.lyy.hitogether.R;
import com.lyy.hitogether.adapter.MyJourneySetAdapter;
import com.lyy.hitogether.adapter.MyJourneySetAdapter.ItemClickListener;
import com.lyy.hitogether.bean.TripLocal;
import com.lyy.hitogether.util.FileService;
import com.lyy.hitogether.util.ImageUir;
import com.lyy.hitogether.view.CustomTitleBarView;
import com.lyy.hitogether.view.CustomTitleBarView.onLeftBarViewClickListener;
import com.lyy.hitogether.view.CustomTitleBarView.onRightBarViewClickListener;
import com.lyy.hitogether.view.MyJourneyPopupWindow;
import com.lyy.hitogether.view.MyJourneyPopupWindow.onToAlbumClickListener;
import com.lyy.hitogether.view.MyJourneyPopupWindow.onToCameraClickListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MyServiceActivity extends BaseActivity implements
		ItemClickListener, OnClickListener {

	private ListView mListView;

	private MyJourneySetAdapter mAdapter;
	private List<TripLocal> mList;
	private MyJourneyPopupWindow mMyJourneyPopupWindow;

	private int CODE_ALBUM = 0x001;
	private int CODE_CAMERA = 0x002;
	private Uri oriaginalUri;

	private int location;
	String realFilePath;
	private CustomTitleBarView titleBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_my_service);
		ImageLoaderConfiguration configuration = ImageLoaderConfiguration
				.createDefault(this);
		ImageLoader.getInstance().init(configuration);
		init();

	}

	private void init() {
		mListView = (ListView) findViewById(R.id.id_activity_my_service_lv);
		titleBar = (CustomTitleBarView) findViewById(R.id.id_my_service_title_bar);
		mList = new ArrayList<TripLocal>();

		TripLocal bt = new TripLocal();
		bt.beanType = TripLocal.BUTTON;
		mList.add(bt);

		TripLocal detail = new TripLocal();
		detail.beanType = TripLocal.DETAIL;
		detail.picPath = "drawable://" + R.drawable.pictures_no;
		detail.txt = "添加文字描述";
		mList.add(0, detail);

		mAdapter = new MyJourneySetAdapter(this, mList);
		mListView.setAdapter(mAdapter);
		mAdapter.setmListener(this);

		mMyJourneyPopupWindow = new MyJourneyPopupWindow(this);

		setMyJourneyPopupWindowListener();
		setTitleBarListener();
	}

	private void setTitleBarListener() {
		titleBar.setOnRightBarViewClickListener(new onRightBarViewClickListener() {

			@Override
			public void onclick(View v) {
				for (TripLocal bean : mList) {
					ShowLog(bean.toString());

				}

			}
		});
		titleBar.setOnLeftBarViewClickListener(new onLeftBarViewClickListener() {

			@Override
			public void onclick(View v) {
				File file = new File(Environment.getExternalStorageDirectory()
						+ "/Hitogether/");
				FileService.delete(file);
				// delete(file);
				MyServiceActivity.this.finish();
			}
		});

	}

	private void setMyJourneyPopupWindowListener() {
		mMyJourneyPopupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				mMyJourneyPopupWindow.lightOn(MyServiceActivity.this);

			}
		});
		mMyJourneyPopupWindow
				.setOnToAlbumClickListener(new onToAlbumClickListener() {

					@Override
					public void onToAlbumClick(View v) {
						pickPhoto();
						mMyJourneyPopupWindow.dismiss();
						mMyJourneyPopupWindow.lightOn(MyServiceActivity.this);

					}
				});

		mMyJourneyPopupWindow
				.setOnToCameraClickListener(new onToCameraClickListener() {

					@Override
					public void onToCameraClick(View v) {
						pickCamera();
						mMyJourneyPopupWindow.dismiss();
						mMyJourneyPopupWindow.lightOn(MyServiceActivity.this);
					}

				});

	}

	@Override
	public void onClick(View v) {
		onClick(v, 0);
	}

	@Override
	public void onClick(View v, int position) {
		switch (v.getId()) {
		case R.id.id_bt_add:
			addItem();
			break;
		case R.id.id_bt_edit_txt:
			this.location = position;
			editTxt(v);
			break;
		case R.id.id_bt_edit_photo:
			this.location = position;
			editPic(v);
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		ContentResolver resolver = getContentResolver();
		if (resultCode == RESULT_OK) {

			if (requestCode == CODE_ALBUM) {
				oriaginalUri = data.getData();
				getAlbumRealPath();
				dealWithPic();
			} else if (requestCode == CODE_CAMERA) {
				getCameraRealPath();
				dealWithPic();
			}

		}
	}

	private void getCameraRealPath() {
		realFilePath = "file://" + imageUir.getNewPath();
	}

	private void getAlbumRealPath() {
		realFilePath = "file://"
				+ getRealFilePath(MyServiceActivity.this, oriaginalUri);

	}

	private void dealWithPic() {

		mList.get(location).setPicPath(realFilePath);
		mAdapter.notifyDataSetChanged();
		new Handler().postDelayed(new Runnable() {
			public void run() {
				mListView.setSelection(location);
			}
		}, 1000);

	}

	ImageUir imageUir;

	private void pickPhoto() {
		Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);
		getImage.addCategory(Intent.CATEGORY_OPENABLE);
		getImage.setType("image/*");
		startActivityForResult(getImage, CODE_ALBUM);

	}

	private void pickCamera() {
		Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		FileService.makeFile(Environment.getExternalStorageDirectory()
				+ "/Hitogether/journeySet/");
		imageUir = new ImageUir();
		doIt(cameraintent);
	}

	private synchronized void doIt(final Intent cameraintent) {

		cameraintent.putExtra(MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(imageUir.getNewFile()));
		startActivityForResult(cameraintent, CODE_CAMERA);

	}

	private void editPic(View v) {
		mMyJourneyPopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
		mMyJourneyPopupWindow.lightOff(MyServiceActivity.this);

	}

	private void editTxt(View v) {
		final EditText content = new EditText(this);
		new AlertDialog.Builder(this).setTitle("请输入").setView(content)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						String str = content.getText().toString();
						mList.get(location).setTxt(str);
						mAdapter.notifyDataSetChanged();
						new Handler().postDelayed(new Runnable() {
							public void run() {
								mListView.setSelection(location);
							}
						}, 1000);
					}
				}).setNegativeButton("取消", null).show();

	}

	private void addItem() {

		mList.add(mList.size() - 1, new TripLocal(TripLocal.DETAIL,
				"drawable://" + R.drawable.pictures_no, "请编辑文字"));
		Log.i("TAG", mList.toString());

		mAdapter.notifyDataSetChanged();

		new Handler().postDelayed(new Runnable() {
			public void run() {
				mListView.smoothScrollToPosition(mListView.getHeight());
			}
		}, 200);
	}

	/**
	 * Try to return the absolute file path from the given Uri
	 * 
	 * @param context
	 * @param uri
	 * @return the file path or null
	 */
	public static String getRealFilePath(final Context context, final Uri uri) {
		if (null == uri)
			return null;
		final String scheme = uri.getScheme();
		String data = null;
		if (scheme == null)
			data = uri.getPath();
		else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
			data = uri.getPath();
		} else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
			Cursor cursor = context.getContentResolver().query(uri,
					new String[] { ImageColumns.DATA }, null, null, null);
			if (null != cursor) {
				if (cursor.moveToFirst()) {
					int index = cursor.getColumnIndex(ImageColumns.DATA);
					if (index > -1) {
						data = cursor.getString(index);
					}
				}
				cursor.close();
			}
		}
		return data;
	}

}

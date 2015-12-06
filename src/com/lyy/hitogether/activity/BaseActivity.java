package com.lyy.hitogether.activity;

import com.lyy.hitogether.R;
import com.lyy.hitogether.mydialog.SweetAlertDialog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * test
 * 
 * @author Administrator
 * 
 */
public class BaseActivity extends Activity {

	protected SweetAlertDialog baseDialog;

	protected DisplayImageOptions baseOptions = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.drawable.icon)
			.bitmapConfig(Bitmap.Config.ARGB_8888)
			.showImageForEmptyUri(R.drawable.icon)
			.showImageOnFail(R.drawable.icon).cacheInMemory(true)
			.cacheOnDisk(true).displayer(new FadeInBitmapDisplayer(2000))
			.build();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseDialog = new SweetAlertDialog(this);

	} 

	public void ShowLog(String msg) {
		Log.i(">>>>", msg);
	}

	public void ShowLog(int msg) {
		Log.i(">>>>", msg + "");
	}

	Toast mToast;

	public void ShowToast(final int resId) {
		// runOnUiThread(new Runnable() {
		//
		// @Override
		// public void run() {
		// // TODO Auto-generated method stub
		if (mToast == null) {
			mToast = Toast
					.makeText(BaseActivity.this, resId, Toast.LENGTH_LONG);
		} else {
			mToast.setText(resId);
		}
		mToast.show();
		// }
		// });
	}

	public void ShowToast(final String string) {
		// runOnUiThread(new Runnable() {
		//
		// @Override
		// public void run() {
		// TODO Auto-generated method stub
		if (mToast == null) {
			mToast = Toast.makeText(BaseActivity.this, string,
					Toast.LENGTH_LONG);
		} else {
			mToast.setText(string);
		}
		mToast.show();
		// }
		// });
	}
}

package com.lyy.hitogether.activity.fragment;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

public class BaseFragment extends Fragment {
	public void ShowLog(String msg) {
		Log.i(">>>>", msg);
	}

	public void ShowLog(int msg) {
		Log.i(">>>>", msg + "");
	}

	Toast mToast;

	public void ShowToast(final int resId) {

		if (mToast == null) {
			mToast = Toast.makeText(getActivity(), resId, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(resId);
		}
		mToast.show();

	}

	public void ShowToast(final String string) {

		if (mToast == null) {
			mToast = Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(string);
		}
		mToast.show();

	}

}

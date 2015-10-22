package com.lyy.hitogether.activity.fragment;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

public abstract class BaseFragment extends Fragment {
	protected boolean isVisible;

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (getUserVisibleHint()) {
			isVisible = true;
			onVisible();
		} else {
			isVisible = false;
			onInvisible();
		}

	}

	protected void onVisible() {
		Log.i("TAG", "1");
		lazyLoad();
	}

	protected void onInvisible() {

	}

	protected abstract void lazyLoad();

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

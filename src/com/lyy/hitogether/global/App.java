package com.lyy.hitogether.global;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import io.rong.imkit.RongIM;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;
import android.os.Process;

public class App extends Application {
	@Override
	public void onCreate() {

		super.onCreate();

		if (getApplicationInfo().packageName
				.equals(getCurrenPro(getApplicationContext()))
				|| "io.rong.push".equals(getCurrenPro(getApplicationContext()))) {
			RongIM.init(this);

			if (getApplicationInfo().packageName
					.equals(getCurrenPro(getApplicationContext()))) {
				AppContext.init(this);
				RongCloudEvent.init(this);
				ImageLoader.getInstance().init(
						ImageLoaderConfiguration.createDefault(this));
			}
		}

	}

	private Object getCurrenPro(Context context) {

		int pid = Process.myPid();
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);

		for (RunningAppProcessInfo appPro : activityManager
				.getRunningAppProcesses()) {
			if (appPro.pid == pid) {
				return appPro.processName;
			}
		}

		return null;
	}

}

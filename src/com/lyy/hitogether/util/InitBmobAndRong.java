package com.lyy.hitogether.util;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient.ConnectCallback;
import io.rong.imlib.RongIMClient.ErrorCode;
import cn.bmob.im.BmobChat;
import cn.bmob.im.BmobUserManager;
import cn.bmob.v3.Bmob;
import android.content.Context;
import android.util.Log;

public class InitBmobAndRong {

	private static final String APP_KEY = "c3cff4a755fce5957ddd4c9136d31039";
	private static final String TOKEN = "YmWwy/E0fHZ7U/65SO2Yenu3UiGLoPgSbxp4DJGG0XGL9YbTAXGpa0r0yCPzopVKfYq5j0qNrXU=";

	public interface LinitLisetener {
		void success(String userId);

		void faild();
	}

	public static void init(Context context, final LinitLisetener lisetener) {
		BmobChat.getInstance(context).init(APP_KEY);
		BmobChat.DEBUG_MODE = true;

		if (lisetener != null) {

			RongIM.connect(TOKEN, new ConnectCallback() {

				@Override
				public void onSuccess(String userId) {
					Log.i("InitBmobAndRong", userId);
					lisetener.success(userId);
				}

				@Override
				public void onError(ErrorCode code) {
					lisetener.faild();

				}

				@Override
				public void onTokenIncorrect() {
					Log.i("InitBmobAndRong", "onTokenIncorrect");

				}
			});
		}
	}
}

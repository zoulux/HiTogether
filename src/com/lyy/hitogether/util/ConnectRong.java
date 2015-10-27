package com.lyy.hitogether.util;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient.ConnectCallback;
import io.rong.imlib.RongIMClient.ErrorCode;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Conversation.ConversationType;
import io.rong.imlib.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import cn.bmob.im.BmobUserManager;
import cn.bmob.v3.BmobUser;

import com.lyy.hitogether.activity.LoginActivity;
import com.lyy.hitogether.bean.MyUser;
import com.lyy.hitogether.global.App;
import com.lyy.hitogether.util.HttpUtils.MyFindListener;

public class ConnectRong {
	private static Context mContext;

	public interface MyConnectListener {
		void onSuccess(List<UserInfo> users);

		void onFaild(String str);
	}

	protected static int WHAT = 0x211;

	public static void connect(Context context, final MyConnectListener listener) {
		mContext = context;
		/**
		 * 连接融云
		 */

		String token = BmobUser.getCurrentUser(context, MyUser.class)
				.getToken();

		Log.i(BmobUser.getCurrentUser(context, MyUser.class).getNick(), token);

		RongIM.connect(token, new ConnectCallback() {

			@Override
			public void onSuccess(String str) {
				// mHandler.sendEmptyMessage(WHAT);

				initUserInfo();
				updateFrindsInfo(listener);
			}

			@Override
			public void onError(ErrorCode arg0) {
				listener.onFaild("faild");
			}

			@Override
			public void onTokenIncorrect() {
				listener.onFaild("faild");

			}
		});

	}

	public static void updateFrindsInfo(final MyConnectListener listener) {

		List<Conversation> conversationList = RongIM.getInstance()
				.getRongIMClient()
				.getConversationList(ConversationType.PRIVATE);

		List<String> userIds = new ArrayList<String>();

		for (Conversation conversation : conversationList) {
			userIds.add(conversation.getTargetId());
		}
		HttpUtils.getFrends(mContext, userIds, new MyFindListener() {

			@Override
			public void onSuccess(List<UserInfo> users) {

				App.getInsatnce().setUserInfos(users);
				if (listener!=null) {
					
					listener.onSuccess(users);
				}

			}

			@Override
			public void onFaild(int code, String err) {

				if (listener!=null) {
					
					listener.onFaild(code + "" + err);
				}
			}
		});

	}

	/**
	 * 将自己身份信息包装发出去
	 * 
	 */
	protected static void initUserInfo() {
		Uri uri = Uri.parse(BmobUserManager.getInstance(mContext)
				.getCurrentUser().getAvatar());
		RongIM.getInstance()
				.setCurrentUserInfo(
						new UserInfo(BmobUserManager.getInstance(mContext)
								.getCurrentUserObjectId(), BmobUserManager
								.getInstance(mContext).getCurrentUser()
								.getNick(), uri));
		RongIM.getInstance().setMessageAttachedUserInfo(true);
	}

}

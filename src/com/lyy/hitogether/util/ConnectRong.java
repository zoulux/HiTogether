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
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import cn.bmob.im.BmobUserManager;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.CloudCodeListener;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lyy.hitogether.activity.LoginActivity;
import com.lyy.hitogether.bean.Group;
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

	public static void connect(Context context, MyConnectListener listener) {
		mContext = context;
		setMyConnectListener(listener);
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

				/**
				 * 更新好友消息
				 */
				new Thread(new Runnable() {

					@Override
					public void run() {
						updateFrindsInfo();

					}
				}).start();

				/**
				 * 更新群组消息
				 */
				new Thread(new Runnable() {

					@Override
					public void run() {
						updateGroupsInfo();

					}

				}).start();

			}

			@Override
			public void onError(ErrorCode arg0) {
				mListener.onFaild("faild");
			}

			@Override
			public void onTokenIncorrect() {
				mListener.onFaild("faild");

			}
		});

	}

	private static MyConnectListener mListener;

	public static void setMyConnectListener(MyConnectListener listener) {
		mListener = listener;
	}

	private static void updateGroupsInfo() {

		HttpUtils.getHttpData(mContext, "getAllGroup", null,
				new CloudCodeListener() {

					@Override
					public void onSuccess(Object result) {
						String json = (String) result;
						Gson gson = new Gson();
						List<Group> groupList = gson.fromJson(json,
								new TypeToken<List<Group>>() {
								}.getType());

						App.getInsatnce().setGroupList(groupList);
					}

					@Override
					public void onFailure(int code, String err) {
						Toast.makeText(mContext, "获取群组消息失败", Toast.LENGTH_SHORT)
								.show();
					}
				});

	}

	public static void updateFrindsInfo() {

		List<Conversation> conversationList = RongIM.getInstance()
				.getRongIMClient()
				.getConversationList(ConversationType.PRIVATE);

		List<String> userIds = new ArrayList<String>();

		if (conversationList == null || conversationList.size() == 0) {

			if (mListener != null) {

				mListener.onSuccess(null);
			}

			return;
		}

		for (Conversation conversation : conversationList) {
			userIds.add(conversation.getTargetId());
		}
		HttpUtils.getFrends(mContext, userIds, new MyFindListener() {

			@Override
			public void onSuccess(List<UserInfo> users) {

				App.getInsatnce().setUserInfos(users);
				if (mListener != null) {

					mListener.onSuccess(users);
				}

			}

			@Override
			public void onFaild(int code, String err) {

				if (mListener != null) {

					mListener.onFaild(code + "" + err);
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

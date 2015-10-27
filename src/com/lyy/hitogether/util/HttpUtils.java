package com.lyy.hitogether.util;

import io.rong.imlib.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.lyy.hitogether.bean.MyUser;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.CloudCodeListener;
import cn.bmob.v3.listener.FindListener;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

public class HttpUtils {
	private static AsyncCustomEndpoints code;

	public static void getHttpData(Context context, String method,
			JSONObject params, CloudCodeListener listener) {
		code = new AsyncCustomEndpoints();
		code.callEndpoint(context, method, params, listener);
	};

	public interface MyFindListener {
		void onSuccess(List<UserInfo> users);

		void onFaild(int code, String err);

	}

	public static void getFrends(Context mContext, List<String> objectIds,
			final MyFindListener myFindListener) {

		BmobQuery<MyUser> query = new BmobQuery<MyUser>();

		query.addWhereContainedIn("objectId", objectIds);
		query.findObjects(mContext, new FindListener<MyUser>() {

			@Override
			public void onSuccess(List<MyUser> users) {
				UserInfo userInfo = null;
				List<UserInfo> list = new ArrayList<UserInfo>();
				for (MyUser user : users) {
					userInfo = new UserInfo(user.getObjectId(), user.getNick(),
							Uri.parse(user.getAvatar()));
					list.add(userInfo);
				}

				myFindListener.onSuccess(list);
			}

			@Override
			public void onError(int arg0, String arg1) {
				myFindListener.onFaild(arg0, arg1);

			}
		});

	}
}

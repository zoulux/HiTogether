package com.lyy.hitogether.util;

import org.json.JSONObject;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.listener.CloudCodeListener;
import android.content.Context;

public class HttpUtils {
	private static AsyncCustomEndpoints code;

	public static void getHttpData(Context context, String method,
			JSONObject params, CloudCodeListener listener) {
		code = new AsyncCustomEndpoints();
		code.callEndpoint(context, method, params, listener);
	};

}

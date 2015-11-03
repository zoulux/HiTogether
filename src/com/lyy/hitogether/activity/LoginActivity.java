package com.lyy.hitogether.activity;

import java.util.List;

import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient.ConnectCallback;
import io.rong.imlib.RongIMClient.ErrorCode;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Conversation.ConversationType;
import io.rong.imlib.model.UserInfo;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import cn.bmob.im.BmobUserManager;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lyy.hitogether.R;
import com.lyy.hitogether.bean.MyUser;
import com.lyy.hitogether.global.App;
import com.lyy.hitogether.global.RongCloudEvent;
import com.lyy.hitogether.mydialog.SweetAlertDialog;
import com.lyy.hitogether.util.ConnectRong;
import com.lyy.hitogether.util.ConnectRong.MyConnectListener;
import com.lyy.hitogether.util.HttpUtils;
import com.lyy.hitogether.util.HttpUtils.MyResultListener;
import com.lyy.hitogether.view.CircleImageView;
import com.lyy.hitogether.view.MyLoginView;
import com.lyy.hitogether.view.MyLoginView.onLoginListener;

public class LoginActivity extends BaseActivity {

	private EditText mEditTextUserName;
	private EditText mEditTextPwd;

	SweetAlertDialog sweetAlertDialog = null;
	// Բ��ͷ��
	private CircleImageView userAvarterImg;
	// ��¼����
	private MyLoginView myLoginView;
	// 登录按钮只能点一次，否走如果你连续速度很快的点登录按钮的话，dialog将会被连续触发，也就会产生很多个MainActivity
	private boolean isFirstClick = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// �����ޱ�����
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		ViewUtils.inject(this);
		// ͸��״̬��
		getWindow()
				.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		init();
	}

	private void init() {
		initId();
		initView();
		initEvent();
	}

	private void initEvent() {

		myLoginView.setOnLoginListener(new onLoginListener() {

			@Override
			public void onClick(View v) {
				if (isFirstClick) {

					sweetAlertDialog.show();
					isFirstClick = false;
					login();

				}

			}
		});

	}

	private static final int HANDLE_WHAT = 0x123;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == HANDLE_WHAT) {

				ConnectRong.connect(LoginActivity.this,
						new MyConnectListener() {

							@Override
							public void onSuccess(List<UserInfo> users) {

								if (users != null && users.size() > 0) {

									for (UserInfo userInfo : users) {
										Log.i("ConnectRong", userInfo.getName());
									}
								}

								sweetAlertDialog.dismiss();

								startActivity(new Intent(LoginActivity.this,
										MainActivity.class));
								LoginActivity.this.finish();
							}

							@Override
							public void onFaild(String str) {
								sweetAlertDialog.dismiss();
								isFirstClick = true;
								ShowToast("连接失败");

							}
						});

				// /**
				// * 连接融云
				// */
				//
				// String token = BmobUser.getCurrentUser(LoginActivity.this,
				// MyUser.class).getToken();
				//
				// Log.i(BmobUser.getCurrentUser(LoginActivity.this,
				// MyUser.class)
				// .getNick(), token);
				// RongIM.connect(token, new ConnectCallback() {
				//
				// @Override
				// public void onSuccess(String arg0) {
				//
				// initUserInfo();
				// sweetAlertDialog.dismiss();
				//
				// List<Conversation> conversationList = RongIM
				// .getInstance().getRongIMClient()
				// .getConversationList(ConversationType.PRIVATE);
				// Log.i("conversationList", conversationList.get(0)
				// .getTargetId());
				//
				// startActivity(new Intent(LoginActivity.this,
				// MainActivity.class));
				// LoginActivity.this.finish();
				// }
				//
				// @Override
				// public void onError(ErrorCode arg0) {
				// ShowToast("连接失败");
				// LoginActivity.this.finish();
				// }
				//
				// @Override
				// public void onTokenIncorrect() {
				//
				// }
				// });

			}

		};

	};

	protected void login() {

		if (judge()) {

			MyUser user = new MyUser();
			user.setUsername(mEditTextUserName.getText().toString());
			user.setPassword(mEditTextPwd.getText().toString());

			Log.i("Installation", BmobInstallation.getInstallationId(this));
			BmobUserManager.getInstance(LoginActivity.this)
					.bindInstallationForRegister(user.getUsername());

			BmobUserManager.getInstance(LoginActivity.this).login(user,
					new SaveListener() {

						@Override
						public void onSuccess() {

							mHandler.sendEmptyMessage(HANDLE_WHAT);

						}

						@Override
						public void onFailure(int arg0, String arg1) {
							sweetAlertDialog.dismiss();
							isFirstClick = true;
							ShowToast("账号或密码有误");

						}
					});

		} else {
			sweetAlertDialog.dismiss();
			isFirstClick = true;
			ShowToast("输入有误");
		}

	}

	protected void initUserInfo() {

	}

	private boolean judge() {
		if (!TextUtils.isEmpty(mEditTextPwd.getText().toString())
				&& !TextUtils.isEmpty(mEditTextUserName.getText().toString())) {
			return true;
		}

		return false;

	}

	private void initView() {
		mEditTextUserName = (EditText) findViewById(R.id.id_userName);
		mEditTextPwd = (EditText) findViewById(R.id.id_userPwd);

		sweetAlertDialog = new SweetAlertDialog(LoginActivity.this, 5);
		sweetAlertDialog.setTitleText("正在登陆");
		sweetAlertDialog.showCancelButton(false);

		new Handler().postDelayed(new Runnable() {
			public void run() {
				userAvarterImg.setVisibility(View.VISIBLE);
			}
		}, 600);
	}

	private void initId() {
		userAvarterImg = (CircleImageView) findViewById(R.id.login_user_avarter);
		myLoginView = (MyLoginView) findViewById(R.id.id_my_login_view);

	}

	@Override
	public void onBackPressed() {

		// sweetAlertDialog.dismiss();
		// Todo 随时取消登录
		super.onBackPressed();
		isFirstClick = true;
		mHandler.removeMessages(HANDLE_WHAT);
	}

	@OnClick(R.id.id_newuser)
	public void newUser(View v) {

		Intent intent = new Intent(LoginActivity.this,
				RegisterActivityGetNumber.class);
		startActivity(intent);

		// HttpUtils.getGroup(LoginActivity.this, "00001",
		// new MyResultListener<String>() {
		//
		// @Override
		// public void onSuccess(List<String> list) {
		// Log.i("HttpUtils", list.toString());
		// }
		//
		// @Override
		// public void onFaild(int code, String err) {
		//
		// }
		// });
	}

}

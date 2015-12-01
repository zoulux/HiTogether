package com.lyy.hitogether.activity;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lyy.hitogether.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivitySetPwd extends BaseActivity {

	@ViewInject(R.id.id_et_pwd)
	private EditText pwd;
	@ViewInject(R.id.id_et_pwd_again)
	private EditText pwdAgain;

	@ViewInject(R.id.id_tv_number)
	private TextView number;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_set_pwd);
		ViewUtils.inject(this);

		initData();

	}

	private void initData() {
		String num = getIntent().getStringExtra(
				RegisterActivityGetNumber.PHONE_NUMBER);
		if (TextUtils.isEmpty(num)) {
			number.setText("账号获取错误，请重试！");

			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					RegisterActivitySetPwd.this.finish();

				}
			}, 2000);

		} else {
			number.setText(num);
		}

	}

	@OnClick(R.id.id_bt_submit)
	public void submit(View v) {
		if (isLegal()) {

		}

	}

	private String getText(EditText et) {
		// TODO Auto-generated method stub
		return null;
	}

	private boolean isLegal() {
		if (TextUtils.isEmpty(getText(pwd))
				|| TextUtils.isEmpty(getText(pwdAgain))) {
			ShowToast("请填写完整");

			return false;
		}

		if (!getText(pwd).equals(getText(pwdAgain))) {
			ShowToast("两次输入不一致");
			return false;
		}

		return true;
	}
}

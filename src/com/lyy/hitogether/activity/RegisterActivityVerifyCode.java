package com.lyy.hitogether.activity;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.VerifySMSCodeListener;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lyy.hitogether.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivityVerifyCode extends BaseActivity {

	@ViewInject(R.id.id_et_verify_code)
	private EditText verifyCode;

	@ViewInject(R.id.id_tv_phone)
	private TextView phoneTV;

	private String phone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_verify_code);
		ViewUtils.inject(this);
		phone = getIntent().getStringExtra(
				RegisterActivityGetNumber.PHONE_NUMBER);
		phoneTV.setText(phone);

	}

	@OnClick(R.id.id_bt_verify)
	public void verify(View v) {

		BmobSMS.verifySmsCode(RegisterActivityVerifyCode.this, phone,
				verifyCode.getText().toString(), new VerifySMSCodeListener() {

					@Override
					public void done(BmobException ex) {
						if (ex == null) {
							ShowToast("验证通过");
							Log.i("BmobSMS", "验证通过");
						} else {

							ShowToast("验证失败");
							Log.i("BmobSMS", "验证失败：code =" + ex.getErrorCode()
									+ ",msg = " + ex.getLocalizedMessage());
						}

					}
				});

	}
}

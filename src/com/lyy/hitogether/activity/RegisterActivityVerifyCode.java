package com.lyy.hitogether.activity;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.VerifySMSCodeListener;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lyy.hitogether.R;
import com.lyy.hitogether.mydialog.SweetAlertDialog;
import com.lyy.hitogether.mydialog.SweetAlertDialog.OnSweetClickListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivityVerifyCode extends BaseActivity {
	public static final int Verify_ERROR = 1;
	public static final String ACTION_BROADCASTRECIVER = RegisterActivityVerifyCode.class
			.getSimpleName();

	@ViewInject(R.id.id_et_verify_code)
	private EditText verifyCode;

	@ViewInject(R.id.id_tv_phone)
	private TextView phoneTV;

	private String phone;

	private SweetAlertDialog mDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_verify_code);
		ViewUtils.inject(this);
		phone = getIntent().getStringExtra(
				RegisterActivityGetNumber.PHONE_NUMBER);
		phoneTV.setText(phone);

		mDialog = new SweetAlertDialog(this);
		mDialog.setCancelable(false);
		mDialog.setConfirmText("确认");

	}

	@OnClick(R.id.id_bt_verify)
	public void verify(View v) {

		BmobSMS.verifySmsCode(RegisterActivityVerifyCode.this, phone,
				verifyCode.getText().toString(), new VerifySMSCodeListener() {

					@Override
					public void done(BmobException ex) {
						if (ex == null) {
							mDialog.setTitleText("验证通过");
							mDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

							mDialog.setConfirmClickListener(new SuccessOnSweetClickListener());
							mDialog.show();

							Log.i("BmobSMS", "验证通过");
						} else {
							mDialog.setTitleText("验证失败");
							mDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);

							mDialog.setConfirmClickListener(new FaildOnSweetClickListener());
							mDialog.show();

							Log.i("BmobSMS", "验证失败：code =" + ex.getErrorCode()
									+ ",msg = " + ex.getLocalizedMessage());
						}

					}
				});

	}

	class SuccessOnSweetClickListener implements OnSweetClickListener {

		@Override
		public void onClick(SweetAlertDialog sweetAlertDialog) {

			Intent i = new Intent(RegisterActivityVerifyCode.this,
					RegisterActivitySetPwd.class);
			i.putExtra(RegisterActivityGetNumber.PHONE_NUMBER, phone);

			startActivity(i);
		}

	}

	class FaildOnSweetClickListener implements OnSweetClickListener {

		@Override
		public void onClick(SweetAlertDialog sweetAlertDialog) {
			Activity c = RegisterActivityVerifyCode.this;

			Intent i = new Intent();
			i.setAction(ACTION_BROADCASTRECIVER);
			c.sendBroadcast(i);
			c.finish();

		}
	}
}

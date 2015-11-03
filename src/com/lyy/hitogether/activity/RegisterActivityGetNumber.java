package com.lyy.hitogether.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lyy.hitogether.R;
import com.lyy.hitogether.mydialog.SweetAlertDialog;

public class RegisterActivityGetNumber extends Activity {
	@ViewInject(R.id.id_et_phone)
	private EditText phoneNumber;

	public static final String PHONE_NUMBER = "PHONE_NUMBER";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_get_number);
		ViewUtils.inject(this);
	}

	@OnClick(R.id.id_bt_phone_next)
	public void phoneNext(View v) {
		/*
		 * SimpleDateFormat format = new
		 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); String sendTime =
		 * format.format(new Date());
		 * BmobSMS.requestSMS(RegisterActivityGetNumber.this, phoneNumber
		 * .getText().toString(), "整点报时：" + new Date(), sendTime, new
		 * RequestSMSCodeListener() {
		 * 
		 * @Override public void done(Integer smsId, BmobException exc) { if
		 * (exc == null) { Log.i("sms", smsId + "成功"); } else {
		 * 
		 * Log.i("sms", "errorCode = " + exc.getErrorCode() + ",errorMsg = " +
		 * exc.getLocalizedMessage()); } } });
		 */

		final SweetAlertDialog dialog = new SweetAlertDialog(
				RegisterActivityGetNumber.this, SweetAlertDialog.PROGRESS_TYPE);
		dialog.setTitleText("请稍后");

		dialog.show();

		BmobSMS.requestSMSCode(RegisterActivityGetNumber.this, phoneNumber
				.getText().toString(), "SMSTem1", new RequestSMSCodeListener() {

			@Override
			public void done(Integer smsId, BmobException ex) {
				if (ex == null) {
					dialog.dismiss();
					Log.i("bmob", smsId + "Success");

					Intent intent = new Intent(RegisterActivityGetNumber.this,
							RegisterActivityVerifyCode.class);
					intent.putExtra(PHONE_NUMBER, phoneNumber.getText()
							.toString());
					startActivity(intent);
				}

			}
		});
	}
}

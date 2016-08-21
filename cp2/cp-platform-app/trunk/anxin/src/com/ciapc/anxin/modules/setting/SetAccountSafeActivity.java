package com.ciapc.anxin.modules.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.AXSharedPreferences;
import com.ciapc.anxin.common.BaseActivity;
import com.ciapc.anxin.common.GlobalConstants;
import com.ciapc.anxin.common.callback.AXCallBack;
import com.ciapc.anxin.common.pojo.CardPojo;
import com.ciapc.anxin.common.view.PubTitle;
import com.ciapc.anxin.common.view.PubTitle.OnTitleClickListener;
import com.ciapc.anxin.modules.perfect.PerfectDataHandler;
import com.ciapc.anxin.modules.perfect.PersonEmailActivity;
import com.master.util.common.DialogUtil;
import com.master.util.common.GsonUtils;
import com.master.util.common.StringUtils;

/**
 * 
 * @author zhoumc
 * @Description: 设置——账号与安全
 * @ClassName: SetAccountSafeActivity.java
 * @date 2015-6-10 下午2:24:27
 * @说明 代码版权归 杭州安存网络科技有限公司 所有
 */
public class SetAccountSafeActivity extends BaseActivity {

	public static final String TAG = "SetAccountSafeActivity";

	private Context mContext;

	// 修改邮箱
	private final int UPDATE = 1;

	// 自己在values下面array中定义一个名称
	private PubTitle pubTitle;

	private String email;
	// 邮箱
	private TextView inputEmail;

	// 手机号,修改密码
	private RelativeLayout accountSafeRevisepwd, accountSafeEmail;

	private Button accountSafePhone;
	// 手机号
	private TextView phone, cardno;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.set_account_safe_layout);
		initView();
		initClick();
		initData();
	}

	/**
	 * @Auther: zhoumc
	 * @Description: 初始化数据
	 * @Date:2015-6-18下午2:34:09
	 * @return void
	 * @说明 代码版权归 杭州安存网络科技有限公司 所有
	 */
	private void initData() {
		// 获取并显示手机号
		String Strphone = AXSharedPreferences.getUserPhone(mContext);
		phone.setText(Strphone);
		// 获取并显示名片号
		String Strcardno = AXSharedPreferences.getCardId(mContext);
		cardno.setText(Strcardno);
		PerfectDataHandler.getInstance(mContext).getMyData(new AXCallBack() {

			@Override
			public void onCallBackString(String retStr) {
				super.onCallBackString(retStr);
				// 返回成功
				if (StringUtils.isNotEmpty(retStr)) {
					if (GlobalConstants.HTTP_SUCCESS_CODE.equals(GsonUtils
							.getJsonValue(retStr, GlobalConstants.HTTP_CODE))) {
						String data = GsonUtils.getJsonValue(retStr, "data");
						if (StringUtils.isNotEmpty(data)) {
							CardPojo cardPojo = GsonUtils.toObject(data,
									CardPojo.class);
							// 邮箱
							email = StringUtils.isNotEmpty(cardPojo.getEmail()) ? cardPojo
									.getEmail() : "";
							inputEmail.setText(email);
							setMapValue("email", email);
						}
					}
				}
			}
		});

	}

	@Override
	public void onResume() {
		super.onResume();
		// 获取邮箱
		if (StringUtils.isNotEmpty(null != getMapValue("email") ? getMapValue(
				"email").toString() : "")) {
			inputEmail.setText(getMapValue("email").toString());
		}
	}

	/**
	 * @Auther: zhoumc
	 * @Description: 初始化控件
	 * @Date:2015-6-10下午5:42:45
	 * @return void
	 * @说明 代码版权归 杭州安存网络科技有限公司 所有
	 */
	private void initView() {
		// 标头
		pubTitle = (PubTitle) findViewById(R.id.set_account_safe_title);
		// 手机号
		accountSafePhone = (Button) findViewById(R.id.account_safe_phone);
		// 修改密码
		accountSafeRevisepwd = (RelativeLayout) findViewById(R.id.account_safe_revisepwd);
		// 显示手机号
		phone = (TextView) findViewById(R.id.phone);
		// 显示名片号
		cardno = (TextView) findViewById(R.id.cardno);
		// 邮箱
		accountSafeEmail = (RelativeLayout) findViewById(R.id.account_safe_email);
		// 邮箱
		inputEmail = (TextView) findViewById(R.id.email);

	}

	/**
	 * @Auther: zhoumc
	 * @Description: 初始化监听事件
	 * @Date:2015-6-10下午5:47:03
	 * @return void
	 * @说明 代码版权归 杭州安存网络科技有限公司 所有
	 */
	private void initClick() {
		// 标题监听事件
		pubTitle.setShowLeftOrRight(true, false, false)
				.setOnTitleClickListener(new OnTitleClickListener() {

					@Override
					public void onRightClick() {
					}

					@Override
					public void onLeftClick() {
						mHandler.sendEmptyMessage(UPDATE);
					}
				});

		// 手机号监听事件
		accountSafePhone.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (GlobalConstants.isDebug) {
					Log.i(TAG, "電話");
				}
				startActivity(new Intent(SetAccountSafeActivity.this,
						AccountSafePhoneActivity.class));

			}
		});
		// 修改密码监听事件
		accountSafeRevisepwd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(SetAccountSafeActivity.this,
						AccountSafeRevisePwdActivity.class));

			}
		});

		accountSafeEmail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(SetAccountSafeActivity.this,
						PersonEmailActivity.class));
			}
		});

	}
	
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case UPDATE:
				update();
				break;

			default:
				break;
			}
		};
	};
	
	private void update(){
		email = inputEmail.getText().toString();
		PerfectDataHandler.getInstance(mContext).updatePerfect(AXSharedPreferences.getCardId(mContext),
				null, email, null, new AXCallBack() {
					public void onCallBackString(String retStr) {
						// 返回成功
						if (StringUtils.isNotEmpty(retStr)) {
							finish();
						} else {
							DialogUtil.showSystemToast(mContext,
									"网络异常，请稍后再试");
						}
					};
				});
	}
}

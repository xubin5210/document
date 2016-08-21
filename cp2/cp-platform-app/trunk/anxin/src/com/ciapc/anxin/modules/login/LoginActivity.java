package com.ciapc.anxin.modules.login;

import java.util.HashMap;

import org.apache.http.Header;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.AXSharedPreferences;
import com.ciapc.anxin.common.BaseActivity;
import com.ciapc.anxin.common.GlobalConstants;
import com.ciapc.anxin.common.InterfaceConstants;
import com.ciapc.anxin.common.view.CustomEditText;
import com.ciapc.anxin.common.view.ProgressShow;
import com.ciapc.anxin.common.view.PubTitle;
import com.ciapc.anxin.common.view.PubTitle.OnTitleClickListener;
import com.ciapc.anxin.modules.home.HomeActivity;
import com.ciapc.anxin.modules.home.HomeSharedPreferences;
import com.ciapc.anxin.modules.register.RegisterActivity;
import com.ciapc.anxin.utils.CheckStringUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.master.util.common.DialogUtil;
import com.master.util.common.GsonUtils;
import com.master.util.common.Md5Util;
import com.master.util.common.StringUtils;
import com.master.util.common.SystemUtils;
import com.master.util.http.MasterHttpClient;

/**
 * @author zhuwt
 * @Description: 登录
 * @ClassName: LoginActivity.java
 * @date 2015年6月2日 上午10:03:55
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */

public class LoginActivity extends BaseActivity {

	private final String TAG = "LoginActivity";

	private final int TO_LOGIN = 1;

	private View line;

	private Context mContext;

	private PubTitle pubTitle;

	// 忘记密码
	private TextView toForgetPwd;

	// 登录
	private Button login;

	// 登录的账号密码
	private ImageView loginPhone;
	private ImageView loginPwd;

	// 输入账号密码
	private CustomEditText inputPhone, inputPwd;

	// 账号密码
	private String phone, pwd;

	// 输入View
	private LinearLayout inputBg;

	private ProgressShow mProgressShow;

	// 用户两次点击时间
	private long quitTime = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);
		mContext = this;
		initView();
		initClick();
		if (!SystemUtils.isNetWorkConnected(mContext)) {
			DialogUtil.showSystemToast(mContext, "暂无网络，请连接网络");
			return;
		}
		initData();
		
	}

	/**
	 * @Auther: zhuwt
	 * @Description:初始化数据
	 * @Date:2015年6月3日下午1:46:05
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initData() {
		String str = getIntent().getStringExtra("login");
		if(StringUtils.isNotEmpty(str)){
			DialogUtil.showSystemToast(mContext, "您的账号在异地登录，请确认账号并重新登录");
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (!isFinishing()) {
				// 两次点击间隔大于2.5秒时，退出
				long nowTime = System.currentTimeMillis();
				if (quitTime == 0 || nowTime - quitTime > 2500) {
					Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
					quitTime = nowTime;
					return true;
				} else {
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_MAIN);
					intent.addCategory(Intent.CATEGORY_HOME);
					startActivity(intent);
				}
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 控件初始化
	 * @Date:2015年5月26日上午10:25:07
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initView() {
		pubTitle = (PubTitle) findViewById(R.id.login_title);
		toForgetPwd = (TextView) findViewById(R.id.to_forget_pwd);
		login = (Button) findViewById(R.id.to_login);
		loginPhone = (ImageView) findViewById(R.id.login_phone);
		loginPwd = (ImageView) findViewById(R.id.login_pwd);
		inputPhone = (CustomEditText) findViewById(R.id.login_input_phone);
		inputPwd = (CustomEditText) findViewById(R.id.login_input_pwd);
		inputBg = (LinearLayout) findViewById(R.id.login_input_pwd_bg);
		line = findViewById(R.id.login_line);
		if (StringUtils.isNotEmpty(AXSharedPreferences.getUserPhone(mContext))) {
			inputPhone.setText(AXSharedPreferences.getUserPhone(mContext));
		}
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 点击事件
	 * @Date:2015年6月4日下午5:52:45
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initClick() {

		inputPhone.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				if (StringUtils.isNotEmpty(arg0.toString())
						|| StringUtils
								.isNotEmpty(inputPwd.getText().toString())) {
					if (!inputBg.isSelected()) {
						inputBg.setSelected(true);
					}
					line.setBackgroundResource(R.color.C7);
					loginPhone.setImageResource(R.drawable.login_phone_fouse);
					loginPwd.setImageResource(R.drawable.login_pwd_fouse);
				} else {
					inputBg.setSelected(false);
					line.setBackgroundResource(R.color.C3);
					loginPhone.setImageResource(R.drawable.login_phone);
					loginPwd.setImageResource(R.drawable.login_pwd);
				}

			}
		});

		inputPwd.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				if (StringUtils.isNotEmpty(arg0.toString())
						|| StringUtils.isNotEmpty(inputPhone.getText()
								.toString())) {
					if (!inputBg.isSelected()) {
						inputBg.setSelected(true);
					}
					line.setBackgroundResource(R.color.C7);
					loginPhone.setImageResource(R.drawable.login_phone_fouse);
					loginPwd.setImageResource(R.drawable.login_pwd_fouse);
				} else {
					inputBg.setSelected(false);
					line.setBackgroundResource(R.color.C3);
					loginPhone.setImageResource(R.drawable.login_phone);
					loginPwd.setImageResource(R.drawable.login_pwd);
				}

			}
		});

		// 登录
		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				phone = inputPhone.getText().toString();
				if (!StringUtils.isNotEmpty(phone)
						|| !StringUtils.isMobileNO(phone)) {
					DialogUtil.showSystemToast(mContext, "请输入正确的手机号");
					return;
				}
				pwd = inputPwd.getText().toString();

				if (!StringUtils.isNotEmpty(pwd)
						|| !StringUtils.lenghtVerify(pwd, 6, 12)) {
					DialogUtil.showSystemToast(mContext, "密码格式不正确或者为空！");
					return;
				}
				CheckStringUtils.closeKeybord(inputPwd, mContext);
				CheckStringUtils.closeKeybord(inputPhone, mContext);
				// 去登陆
				mProgressShow = new ProgressShow(mContext, "登录中......");
				mProgressShow.show();
				mHandler.sendEmptyMessageDelayed(TO_LOGIN, 1500);
			}
		});

		// 找回密码
		toForgetPwd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(mContext, ForgetPwdActivity.class));
			}
		});

		// 标题头
		pubTitle.setShowLeftOrRight(false, false, true)
				.setOnTitleClickListener(new OnTitleClickListener() {

					@Override
					public void onRightClick() {
						Intent mIntent = new Intent(mContext,
								RegisterActivity.class);
						String str = inputPhone.getText().toString();
						if (StringUtils.isNotEmpty(str)) {
							mIntent.putExtra("phone", str);
						}
						startActivity(mIntent);
					}

					@Override
					public void onLeftClick() {
					}
				});
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 登录
	 * @Date:2015年6月16日上午10:25:29
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void toLogin() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("mobile", phone);
		map.put("pwd", Md5Util.encode(pwd));
		map.put(InterfaceConstants.INTERFACE_NAME,
				InterfaceConstants.LOGIN_CODE);
		MasterHttpClient.postForJava(mContext, map,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						String result = new String(arg2);
						// 返回成功
						if (StringUtils.isNotEmpty(result)) {
							if (GlobalConstants.isDebug) {
								Log.i(TAG, result);
							}
							// 返回成功
							if (GlobalConstants.HTTP_SUCCESS_CODE
									.equals(GsonUtils.getJsonValue(result,
											GlobalConstants.HTTP_CODE))) {
								// 登录成功
								AXSharedPreferences
										.setLoginType(mContext, true);

								// 保存手机号
								AXSharedPreferences.setUserPhone(mContext,
										phone);
								// 保存手机号
								AXSharedPreferences.setUserPwd(mContext, pwd);
								String data = GsonUtils.getJsonValue(result,
										GlobalConstants.HTTP_DATA);
								if (StringUtils.isNotEmpty(data)) {
									// 保存令牌
									AXSharedPreferences.setTokenId(mContext,
											GsonUtils.getJsonValue(data,
													"tokenid"));
									HomeSharedPreferences.setSignature(
											mContext, GsonUtils.getJsonValue(
													data, "motto"));
									HomeSharedPreferences.setSignature(
											mContext, GsonUtils.getJsonValue(
													data, "motto"));
									// 保存头像
									HomeSharedPreferences.setHeadPath(mContext,
											GsonUtils.getJsonValue(data,
													"iconUrl"));
									// 保存真实姓名
									HomeSharedPreferences.setNickName(mContext,
											GsonUtils.getJsonValue(data,
													"nickName"));
									// 保存真实姓名
									HomeSharedPreferences.setTrueName(mContext,
											GsonUtils.getJsonValue(data,
													"trueName"));
									// 名片号
									String str = GsonUtils.getJsonValue(data,
											"cardId");
									HomeSharedPreferences.setCardIdId(mContext,
											str);
									// 企业名片号
									String entId = GsonUtils.getJsonValue(data,
											"entId");
									HomeSharedPreferences.setEntId(mContext,
											entId);
									// 完善资料状态
									AXSharedPreferences.setUserInfoStatus(
											mContext, GsonUtils.getJsonValue(
													data, "infoCompleteStatus"));
									// 名片Id开关
									AXSharedPreferences.setPrivacy(mContext,
											GsonUtils.getJsonValue(data,
													"cardIdSwitch"),
											"cardIdSwitch");
									// 手机号开关
									AXSharedPreferences.setPrivacy(mContext,
											GsonUtils.getJsonValue(data,
													"mobileSwitch"),
											"mobileSwitch");
									// 邮箱Id开关
									AXSharedPreferences.setPrivacy(mContext,
											GsonUtils.getJsonValue(data,
													"emailSwitch"),
											"emailSwitch");
									// 职位Id开关
									AXSharedPreferences.setPrivacy(mContext,
											GsonUtils.getJsonValue(data,
													"positionSwitch"),
											"positionSwitch");
									// 昵称Id开关
									AXSharedPreferences.setPrivacy(mContext,
											GsonUtils.getJsonValue(data,
													"nickNameSwitch"),
											"nickNameSwitch");
									// 绑定企业状态审核
									AXSharedPreferences.setEntStatus(mContext,
											GsonUtils.getJsonValue(data,
													"cardApplyEntAuditStatus"));

								}
								startActivity(new Intent(mContext,
										HomeActivity.class));
							} else {
								DialogUtil.showSystemToast(mContext, GsonUtils
										.getJsonValue(result,
												GlobalConstants.HTTP_MSG));
							}
						} else {
							startActivity(new Intent(mContext,
									LoginActivity.class));
						}
						mProgressShow.dismiss();
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						if (GlobalConstants.isDebug) {
							Log.i(TAG, arg0 + "");
						}
						mProgressShow.dismiss();
						DialogUtil.showSystemToast(mContext, "连接异常，请稍后再试！");
					}
				});
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case TO_LOGIN:
				toLogin();
				break;

			default:
				break;
			}
		};
	};
}

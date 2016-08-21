package com.ciapc.anxin.modules.register;

import java.util.HashMap;

import org.apache.http.Header;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.AXSharedPreferences;
import com.ciapc.anxin.common.BaseActivity;
import com.ciapc.anxin.common.GlobalConstants;
import com.ciapc.anxin.common.InterfaceConstants;
import com.ciapc.anxin.common.pojo.GsonPojo;
import com.ciapc.anxin.common.view.AxinDialog;
import com.ciapc.anxin.common.view.AxinDialog.AxDialogClickListen;
import com.ciapc.anxin.common.view.CustomEditText;
import com.ciapc.anxin.common.view.ProgressShow;
import com.ciapc.anxin.common.view.PubTitle;
import com.ciapc.anxin.common.view.PubTitle.OnTitleClickListener;
import com.ciapc.anxin.modules.home.HomeActivity;
import com.ciapc.anxin.modules.home.HomeSharedPreferences;
import com.ciapc.anxin.utils.CheckStringUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.master.util.common.DialogUtil;
import com.master.util.common.GsonUtils;
import com.master.util.common.Md5Util;
import com.master.util.common.StringUtils;
import com.master.util.http.IHttpCallBack;
import com.master.util.http.MasterHttpClient;

/**
 * @author zhuwt
 * @Description: 注册
 * @ClassName: RegisterActivity.java
 * @date 2015年6月2日 上午11:27:00
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */

public class RegisterActivity extends BaseActivity {

	private final String TAG = "RegisterActivity";

	private Context mContext;

	// 获取验证码
	private final int GET_CODE = 1;

	// 登录
	private final int LOGIN = 2;

	private final int CHECK_PHONE = 3;

	// 手机号 验证码 密码
	private CustomEditText inputPhone, inputPwd;
	private EditText inputVerification;
	private String phone, verification, pwd;
	private TextView content;

	// 注册 第一步 第二步
	private LinearLayout registerFirster, registerSecond;

	// 当前是第几步注册
	private int index = 1;

	// 下一步
	private Button next;

	// 获取验证码
	private Button getCode;
	private String hint = "s后重新获取";
	private int time = 60;

	private PubTitle pubTitle;

	// 验证码发送是否成功
	private boolean flag = true;

	private ProgressShow mProgressShow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_layout);
		mContext = this;
		initView();
		initClick();
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 初始化控件
	 * @Date:2015年6月2日上午11:49:46
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initView() {
		next = (Button) findViewById(R.id.register_next);
		next.setText("下一步");
		inputPhone = (CustomEditText) findViewById(R.id.register_input_phone);
		inputVerification = (EditText) findViewById(R.id.register_input_verification);
		inputPwd = (CustomEditText) findViewById(R.id.register_input_pwd);
		registerFirster = (LinearLayout) findViewById(R.id.register_first);
		registerSecond = (LinearLayout) findViewById(R.id.register_second);
		pubTitle = (PubTitle) findViewById(R.id.register_title);
		getCode = (Button) findViewById(R.id.register_get_code);
		content = (TextView) findViewById(R.id.content);
		String str = getIntent().getStringExtra("phone");
		inputPhone.setText(str);
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 初始化点击事件
	 * @Date:2015年6月4日下午11:43:47
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initClick() {

		// 获取验证码
		getCode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 没有点击
				if (getCode.isClickable()) {
					flag = true;
					getCode();
					getCode.setSelected(true);
					getCode.setClickable(false);
					mHandler.postDelayed(new Runnable() {

						@Override
						public void run() {
							time--;
							if (flag) {
								mHandler.sendEmptyMessage(GET_CODE);
								mHandler.postDelayed(this, 1000);
							}
						}
					}, 0);
				}
			}
		});

		// 下一步
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				runOnUiThread(new Runnable() {
					public void run() {
						// 验证手机号码
						if (index == 1) {
							mHandler.sendEmptyMessage(CHECK_PHONE);
							return;
						}

						// 去注册
						if (index == 2) {
							registerFirster.setVisibility(View.GONE);
							registerSecond.setVisibility(View.VISIBLE);
							verification = inputVerification.getText()
									.toString();
							// 校验验证码
							if (!StringUtils.isNotEmpty(verification)) {
								DialogUtil.showSystemToast(mContext, "验证码不能为空");
								return;
							}
							pwd = inputPwd.getText().toString();
							// 校验密码
							if (!CheckStringUtils.checkPwd(mContext, pwd)) {
								return;
							}
							forRegiseter();
							return;
						}
					}
				});

			}
		});

		// 返回
		pubTitle.setShowLeftOrRight(true, false, false)
				.setOnTitleClickListener(new OnTitleClickListener() {

					@Override
					public void onRightClick() {

					}

					@Override
					public void onLeftClick() {
						if (index == 1) {
							finish();
							return;
						}
						if (index == 2) {
							content.setVisibility(View.INVISIBLE);
							registerSecond.setVisibility(View.GONE);
							registerFirster.setVisibility(View.VISIBLE);
							index--;
							time = 0;
							pubTitle.setMainTitle("注册新用户");
							next.setText("下一步");
							inputPhone.setText("");
							inputVerification.setText("");
							inputPwd.setText("");
						}
					}
				});
	}

	/**
	 * @Auther: zhuwt
	 * @Description:获取验证码
	 * @Date:2015年6月16日下午3:09:23
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void getCode() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("mobile", phone);
		map.put("type", "register");
		map.put(InterfaceConstants.INTERFACE_NAME,
				InterfaceConstants.GET_CODE_CODE);
		MasterHttpClient.postForJava(mContext, map,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1,
							final byte[] arg2) {
						String result = new String(arg2);
						if (StringUtils.isNotEmpty(result)) {
							if (GlobalConstants.isDebug) {
								Log.i(TAG, result + "," + arg0);
							}
							GsonPojo gr = GsonUtils.toObject(result,
									GsonPojo.class);
							// 返回失败
							if (!GlobalConstants.HTTP_SUCCESS_CODE.equals(gr
									.getCode())) {
								DialogUtil.showSystemToast(mContext,
										gr.getMsg());
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1,
							final byte[] arg2, Throwable arg3) {
						if (GlobalConstants.isDebug) {
							Log.i(TAG, "code=" + arg0);
						}
						DialogUtil.showSystemToast(mContext, "网络连接错误，请稍后再试");
					}
				});
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 去注册
	 * @Date:2015年6月11日下午5:01:48
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void forRegiseter() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("mobile", phone);
		map.put("pwd", Md5Util.encode(pwd));
		map.put("code", verification);
		map.put(InterfaceConstants.INTERFACE_NAME,
				InterfaceConstants.REGERIST_CODE);
		MasterHttpClient.postIpush(mContext, map, new IHttpCallBack() {

			@Override
			public void onSuccess(String code, final String message) {
				runOnUiThread(new Runnable() {
					public void run() {
						if (GlobalConstants.isDebug) {
							Log.i(TAG, message);
						}
						GsonPojo gr = GsonUtils.toObject(message,
								GsonPojo.class);
						// 返回成功
						if (GlobalConstants.HTTP_SUCCESS_CODE.equals(gr
								.getCode())) {
							// 去登陆
							mProgressShow = new ProgressShow(mContext,
									"登录中......");
							mProgressShow.show();
							mHandler.sendEmptyMessageDelayed(LOGIN, 1000);

						} else {
							DialogUtil.showSystemToast(mContext, gr.getMsg());
						}
					}
				});

			}

			@Override
			public void onFailed(String code, String message) {
				if (GlobalConstants.isDebug) {
					Log.i(TAG, "code=" + code);
				}
			}

		});
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case GET_CODE:
				runOnUiThread(new Runnable() {
					public void run() {
						if (time == 0) {
							getCode.setSelected(false);
							getCode.setClickable(true);
							getCode.setText("获取验证码");
							time = 60;
							flag = false;
							return;
						} else {
							getCode.setText(time + hint);
						}
					}
				});
				break;

			case LOGIN:
				toLogin();
				break;
			case CHECK_PHONE:
				checkPhone();
				break;
			default:
				break;
			}
		}
	};

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

	@Override
	public void finish() {
		super.finish();
		if (null != mProgressShow) {
			mProgressShow.dismiss();
		}
	}

	private void checkPhone() {
		phone = inputPhone.getText().toString();
		if (!StringUtils.isMobileNO(phone)) {
			DialogUtil.showSystemToast(mContext, "请输入正确的手机号码");
			return;
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("mobile", phone);
		map.put(InterfaceConstants.INTERFACE_NAME,
				InterfaceConstants.CHECK_PHONE);
		MasterHttpClient.postForJava(mContext, map,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1,
							final byte[] arg2) {
						runOnUiThread(new Runnable() {
							public void run() {
								String str = new String(arg2);
								if (GlobalConstants.isDebug) {
									Log.i(TAG, str);
								}
								// 返回成功
								if (GlobalConstants.HTTP_SUCCESS_CODE
										.equals(GsonUtils.getJsonValue(str,
												GlobalConstants.HTTP_CODE))) {
									String type = GsonUtils.getJsonValue(str,
											"data");
									if ("true".equals(type)) {
										new AxinDialog(mContext,
												AxinDialog.DIALOG_CHANGE)
												.setTitle("手机号已注册，可以直接登录")
												.setConfirmStr("去登录")
												.setCancelStr("取消")
												.setConfirmClick(
														new AxDialogClickListen() {

															@Override
															public void onClick(
																	AxinDialog axinDialog) {
																axinDialog
																		.dismiss();
																finish();
															}
														})
												.setCancelClick(
														new AxDialogClickListen() {

															@Override
															public void onClick(
																	AxinDialog axinDialog) {
																axinDialog
																		.dismiss();
															}
														}).show();
									} else {
										getCode.performClick();
										pubTitle.setMainTitle("设置密码");
										registerFirster
												.setVisibility(View.GONE);
										content.setText("手机号：" + phone);
										content.setVisibility(View.VISIBLE);
										registerSecond
												.setVisibility(View.VISIBLE);
										next.setText("注册完成");
										index++;
									}

								} else {
									DialogUtil.showSystemToast(mContext,
											"网络异常，请稍后再试！");
								}
							}
						});

					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						if (GlobalConstants.isDebug) {
							Log.i(TAG, arg0 + "");
						}
						DialogUtil.showSystemToast(mContext, "网络异常，请稍后再试！");
					}
				});
	}

}

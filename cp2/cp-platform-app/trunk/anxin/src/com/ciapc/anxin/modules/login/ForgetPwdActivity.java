package com.ciapc.anxin.modules.login;

import java.util.HashMap;

import org.apache.http.Header;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.AXSharedPreferences;
import com.ciapc.anxin.common.BaseActivity;
import com.ciapc.anxin.common.GlobalConstants;
import com.ciapc.anxin.common.InterfaceConstants;
import com.ciapc.anxin.common.pojo.GsonPojo;
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
import com.master.util.http.MasterHttpClient;

/**
 * @author zhuwt
 * @Description: 修改密码
 * @ClassName: ForgetPwdActivity.java
 * @date 2015年6月4日 下午10:55:50
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */

public class ForgetPwdActivity extends BaseActivity {

	private final String TAG = "ForgetPwdActivity";

	private Context mContext;

	private LinearLayout view;

	private PubTitle pubTitle;

	// 获取验证码
	private final int GET_CODE = 1;

	// 修改成功后 直接去登陆
	private final int LOGIN = 2;

	// 获取验证码
	private Button getCode;
	private String hint = "s后重新获取";
	private int time = 60;

	// 验证码发送是否成功
	private boolean flag = true;

	// 手机号码 验证码 新密码
	private CustomEditText inputPhone, inputPwd;
	private EditText inputCode;
	private String phone, code, pwd;

	// 提交
	private Button submit;

	private ProgressShow show;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forgrt_pwd_layout);
		mContext = this;
		initView();
		initClick();
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 初始化控件
	 * @Date:2015年6月16日下午3:02:59
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initView() {
		view = (LinearLayout) findViewById(R.id.view);
		view.setSelected(true);
		pubTitle = (PubTitle) findViewById(R.id.forget_pwd_title);
		getCode = (Button) findViewById(R.id.forget_pwd_getcode);
		getCode.setSelected(false);
		submit = (Button) findViewById(R.id.forget_pwd_submit);
		inputPhone = (CustomEditText) findViewById(R.id.forget_input_phone);
		if (StringUtils.isNotEmpty(AXSharedPreferences.getUserPhone(mContext))) {
			inputPhone.setText(AXSharedPreferences.getUserPhone(mContext));
		}
		inputCode = (EditText) findViewById(R.id.forget_input_verification);
		inputPwd = (CustomEditText) findViewById(R.id.forget_input_pwd);
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 点击事件
	 * @Date:2015年6月16日下午3:04:14
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initClick() {
		// 提交
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				phone = inputPhone.getText().toString();
				if (!StringUtils.isMobileNO(phone)) {
					DialogUtil.showSystemToast(mContext, "手机号码不能为空!");
					return;
				}
				code = inputCode.getText().toString();
				if (!StringUtils.isNotEmpty(code)) {
					DialogUtil.showSystemToast(mContext, "验证码不能为空");
					return;
				}
				pwd = inputPwd.getText().toString();
				if (!CheckStringUtils.checkPwd(mContext, pwd)) {
					return;
				}
				if (null == show) {
					show = new ProgressShow(mContext, "请稍后...");
				}
				show.show();
				// 去修改密码
				mHandler.postDelayed(new Runnable() {

					@Override
					public void run() {
						ChangePwd();
					}
				}, 800);
			}
		});

		// 获取验证码
		getCode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				phone = inputPhone.getText().toString();
				if (!StringUtils.isNotEmpty(phone) || !StringUtils.isMobileNO(phone)) {
					DialogUtil.showSystemToast(mContext, "手机号不能为空");
					return;
				}
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

		pubTitle.setShowLeftOrRight(true, false, false)
				.setOnTitleClickListener(new OnTitleClickListener() {

					@Override
					public void onRightClick() {

					}

					@Override
					public void onLeftClick() {
						finish();
					}
				});
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			// 去登录
			case LOGIN:
				toLogin();
				break;

			// 获取验证码
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

			default:
				break;
			}
		}
	};

	/**
	 * @Auther: zhuwt
	 * @Description:获取验证码
	 * @Date:2015年6月16日下午3:09:23
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void getCode() {
		phone = inputPhone.getText().toString();
		if (!StringUtils.isMobileNO(phone)) {
			DialogUtil.showSystemToast(mContext, "手机号不能为空或错误");
			return;
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("mobile", phone);
		map.put("type", "resetPwd");
		map.put("sys_request_code", InterfaceConstants.GET_CODE_CODE);
		MasterHttpClient.postForJava(mContext, map,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1,
							final byte[] arg2) {
						String result = new String(arg2);
						if (GlobalConstants.isDebug) {
							Log.i(TAG, result + "," + arg0);
						}
						GsonPojo gr = GsonUtils
								.toObject(result, GsonPojo.class);
						// 返回失败
						if (!GlobalConstants.HTTP_SUCCESS_CODE.equals(gr
								.getCode())) {
							DialogUtil.showSystemToast(mContext, gr.getMsg());
						}

					}

					@Override
					public void onFailure(int arg0, Header[] arg1,
							final byte[] arg2, Throwable arg3) {
						DialogUtil.showSystemToast(mContext, "网络连接错误，请稍后再试");
					}
				});
	}

	/**
	 * @Auther: zhuwt
	 * @Description:修改密码
	 * @Date:2015年6月16日下午3:26:21
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void ChangePwd() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("mobile", phone);
		map.put("code", code);
		map.put("pwd", Md5Util.encode(pwd));
		map.put(InterfaceConstants.INTERFACE_NAME,
				InterfaceConstants.FORGET_PWD_CODE);
		MasterHttpClient.postForJava(mContext, map,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						String result = new String(arg2);
						if (StringUtils.isNotEmpty(result)) {
							if (GlobalConstants.isDebug) {
								Log.i(TAG, result + "," + arg0);
							}
							GsonPojo gr = GsonUtils.toObject(result,
									GsonPojo.class);
							// 返回成功
							if (GlobalConstants.HTTP_SUCCESS_CODE.equals(gr
									.getCode())) {
								HomeSharedPreferences.setUserPwd(mContext, pwd);
								// 重新登录
								SharedPreferences spf = mContext
										.getSharedPreferences(
												AXSharedPreferences.SHARED_PREFERENCEK_KEY,
												0);
								Editor editor = spf.edit();
								editor.clear();
								editor.commit();
								mHandler.sendEmptyMessage(LOGIN);
							} else {
								show.dismiss();
								DialogUtil.showSystemToast(mContext,
										gr.getMsg());
							}
						}else{
							show.dismiss();
							DialogUtil.showSystemToast(mContext, "网络异常，请稍后再试!");
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						show.dismiss();
						DialogUtil.showSystemToast(mContext, "网络异常,请稍后再试！");
					}
				});
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 去登陆
	 * @Date:2015年6月17日下午4:13:51
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
								finish();
							} else {
								show.dismiss();
								DialogUtil.showSystemToast(mContext, GsonUtils
										.getJsonValue(result,
												GlobalConstants.HTTP_MSG));
							}
						}else{
							DialogUtil.showSystemToast(mContext, "网络异常，请重新登录");
							finish();
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						show.dismiss();
						DialogUtil.showSystemToast(mContext, "连接异常，请稍后再试！");
					}
				});
	}
	
	@Override
	public void finish() {
		super.finish();
		if(null != show){
			show.dismiss();
		}
	}
}

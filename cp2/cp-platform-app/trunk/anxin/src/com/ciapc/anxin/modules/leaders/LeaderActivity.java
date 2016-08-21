/**    
 * @author zhuwt 
 * @Description: TODO(用一句话描述该文件做什么)   
 * @Package com.ciapc.anxin.modules.leaders   
 * @Title: LeaderActivity.java   
 * @date 2015年6月2日 上午9:33:03   
 * @version V1.0 
 * @说明  代码版权归 杭州反盗版中心有限公司 所有
 */
package com.ciapc.anxin.modules.leaders;

import java.util.HashMap;

import org.apache.http.Header;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.AXSharedPreferences;
import com.ciapc.anxin.common.BaseActivity;
import com.ciapc.anxin.common.GlobalConstants;
import com.ciapc.anxin.common.InterfaceConstants;
import com.ciapc.anxin.modules.home.HomeActivity;
import com.ciapc.anxin.modules.home.HomeSharedPreferences;
import com.ciapc.anxin.modules.lead.LeadActivity;
import com.ciapc.anxin.modules.login.LoginActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.master.util.common.DialogUtil;
import com.master.util.common.GsonUtils;
import com.master.util.common.Md5Util;
import com.master.util.common.StringUtils;
import com.master.util.http.MasterHttpClient;

/**
 * @author zhuwt
 * @Description: 初始化
 * @ClassName: LeaderActivity.java
 * @date 2015年6月2日 上午9:33:03
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */

public class LeaderActivity extends BaseActivity {

	private Context mContext;

	// 登录
	private static final int GO_LOGIN = 1;

	// 引导页
	private static final int GO_LEAD = 2;

	private final String TAG = "LeaderActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leader_layout);
		mContext = this;
		// // 是否是第一次启动
		// if (LeaderSharedPreferences.getInitialize(mContext)) {
		if (AXSharedPreferences.getLoginType(mContext)
				&& StringUtils.isNotEmpty(AXSharedPreferences
						.getUserPwd(mContext))) {
			mHandler.sendEmptyMessageDelayed(GO_LOGIN, 2000);
		} else {
			mHandler.postDelayed(new Runnable() {

				@Override
				public void run() {
					mContext.startActivity(new Intent(mContext,
							LoginActivity.class));
					finish();
				}
			}, 2000);
		}
		// }
		// else {
		// // 第一次启动 到引导页
		// mHandler.sendEmptyMessageDelayed(GO_LEAD, 2300);
		// }
	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			// 去引导页
			case GO_LEAD:
				startActivity(new Intent(mContext, LeadActivity.class));
				finish();
				break;

			// 去登录
			case GO_LOGIN:
				toLogin();
				break;

			default:
				break;
			}

		}
	};

	@Override
	public void finish() {
		super.finish();
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
		map.put("mobile", AXSharedPreferences.getUserPhone(mContext));
		map.put("pwd", Md5Util.encode(AXSharedPreferences.getUserPwd(mContext)));
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
								startActivity(new Intent(mContext,
										LoginActivity.class));
								DialogUtil.showSystemToast(mContext, GsonUtils
										.getJsonValue(result,
												GlobalConstants.HTTP_MSG));
								finish();
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						if (GlobalConstants.isDebug) {
							Log.i(TAG, arg0 + "");
						}
						startActivity(new Intent(mContext, LoginActivity.class));
						finish();
					}
				});
	}
}

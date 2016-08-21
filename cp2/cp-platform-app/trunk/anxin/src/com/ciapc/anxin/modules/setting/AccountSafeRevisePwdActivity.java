package com.ciapc.anxin.modules.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.AXSharedPreferences;
import com.ciapc.anxin.common.BaseActivity;
import com.ciapc.anxin.common.GlobalConstants;
import com.ciapc.anxin.common.callback.AXCallBack;
import com.ciapc.anxin.common.view.CustomEditText;
import com.ciapc.anxin.common.view.ProgressShow;
import com.ciapc.anxin.common.view.PubTitle;
import com.ciapc.anxin.common.view.PubTitle.OnTitleClickListener;
import com.ciapc.anxin.modules.login.LoginActivity;
import com.ciapc.anxin.utils.CheckStringUtils;
import com.master.util.common.DialogUtil;
import com.master.util.common.GsonUtils;
import com.master.util.common.Md5Util;
import com.master.util.common.StringUtils;

/**
 * 
 * @author zhoumc
 * @Description: 账号与安全——修改密码
 * @ClassName: AccountSafeRevisePwdActivity.java
 * @date 2015-6-11 下午3:43:16
 * @说明 代码版权归 杭州安存网络科技有限公司 所有
 */
public class AccountSafeRevisePwdActivity extends BaseActivity {

	private static final String TAG = "AccountSafeRevisePwdActivity";

	private Context mContext;

	private final int REVISE_LOGIN = 1;

	// 确认
	private Button revisePwdOk;

	// 自己在values下面array中定义一个名称
	private PubTitle pubTitle;

	// 输入旧密码、新密码、确认新密码
	private CustomEditText oldPwdInput, etNewPwd, etConfirmNewPwd;

	private String pwd, newpwd, oldPwd;

	private ProgressShow mProgressShow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_safe_revisepwd_layout);
		mContext = this;
		initView();
		initClick();
		initData();
	}

	/**
	 * @Auther: zhoumc
	 * @Description: 初始化数据
	 * @Date:2015-6-18下午4:56:23
	 * @return void
	 * @说明 代码版权归 杭州安存网络科技有限公司 所有
	 */
	private void initData() {
	}

	/**
	 * @Auther: zhoumc
	 * @Description: 初始化控件
	 * @Date:2015-6-18下午4:54:28
	 * @return void
	 * @说明 代码版权归 杭州安存网络科技有限公司 所有
	 */
	private void initView() {
		// 标题
		pubTitle = (PubTitle) findViewById(R.id.account_safe_revisepwd_title);
		// 确认
		revisePwdOk = (Button) findViewById(R.id.revisepwd_ok);
		// 旧密码
		oldPwdInput = (CustomEditText) findViewById(R.id.old_pwd);
		// 新密码
		etNewPwd = (CustomEditText) findViewById(R.id.new_pwd);
		// 确认新密码
		etConfirmNewPwd = (CustomEditText) findViewById(R.id.confirm_newpwd);

	}

	/**
	 * @Auther: zhoumc
	 * @Description: 初始化控件
	 * @Date:2015-6-18下午4:55:17
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
						finish();
					}
				});
		revisePwdOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 输入新密码
				pwd = etNewPwd.getText().toString();
				// 输入确认新密码
				newpwd = etConfirmNewPwd.getText().toString();
				oldPwd = oldPwdInput.getText().toString();

				if (!StringUtils.isNotEmpty(oldPwd)) {
					DialogUtil.showSystemToast(mContext, "旧密码不能为空");
					return;
				}
				if (!StringUtils.isNotEmpty(newpwd)) {
					DialogUtil.showSystemToast(mContext, "确认新密码不能为空");
					return;
				}
				if (!StringUtils.isNotEmpty(pwd)) {
					DialogUtil.showSystemToast(mContext, "新密码不能为空");
					return;
				}
				if (oldPwd.equals(pwd) || oldPwd.equals(newpwd)) {
					DialogUtil.showSystemToast(mContext, "新密码不能与旧密码一样");
					return;
				}
				if (!pwd.equals(newpwd)) {
					DialogUtil.showSystemToast(mContext, "新密码与确认密码不一致");
					return;
				}
				if (!CheckStringUtils.checkPwd(mContext, newpwd)
						|| !CheckStringUtils.checkPwd(mContext, pwd)) {
					return;
				}
				if (null == mProgressShow) {
					mProgressShow = new ProgressShow(mContext, "请稍后...");
					mProgressShow.show();
				} else {
					mProgressShow.show();
				}
				// 修改密码
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						toRevisePwd();
					}
				}, 1000);
			}
		});

	}

	/**
	 * @Auther: zhoumc
	 * @Description: 修改密码
	 * @Date:2015-6-18下午5:07:33
	 * @return void
	 * @说明 代码版权归 杭州安存网络科技有限公司 所有
	 */
	public void toRevisePwd() {
		SettingDataHandler.getInstance(mContext).toRevisePwd(oldPwd, newpwd,
				new AXCallBack() {
					@Override
					public void onCallBackString(String retStr) {
						super.onCallBackString(retStr);
						if (StringUtils.isNotEmpty(retStr)) {
							// 返回成功
							if (GlobalConstants.HTTP_SUCCESS_CODE
									.equals(GsonUtils.getJsonValue(retStr,
											GlobalConstants.HTTP_CODE))) {
								// 保存新密码
								AXSharedPreferences
										.setUserPwd(mContext, newpwd);
								// 登录成功
								AXSharedPreferences.setLoginType(mContext,
										false);
								DialogUtil.showSystemToast(mContext,
										"密码修改成功,请重新登录");
								startActivity(new Intent(mContext,
										LoginActivity.class));
								finish();
							} else {
								DialogUtil.showSystemToast(mContext, GsonUtils
										.getJsonValue(retStr,
												GlobalConstants.HTTP_MSG));
							}
						} else {
							DialogUtil.showSystemToast(mContext, "网络异常，请稍后再试！");
						}
						mProgressShow.dismiss();
					}

				});

	}

}

package com.ciapc.anxin.modules.setting;

import java.util.HashMap;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.AXSharedPreferences;
import com.ciapc.anxin.common.BaseActivity;
import com.ciapc.anxin.common.view.AxinDialog;
import com.ciapc.anxin.common.view.PubTitle;
import com.ciapc.anxin.common.view.AxinDialog.AxDialogClickListen;
import com.ciapc.anxin.common.view.PubTitle.OnTitleClickListener;
import com.ciapc.anxin.modules.home.HomeActivity;
import com.ciapc.anxin.modules.login.LoginActivity;
import com.ciapc.anxin.modules.perfect.PerfectInfoActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @author zhoumc
 * @Description: 设置的主页面
 * @ClassName: SettingMainActivity.java
 * @date 2015-6-9 上午9:43:01
 * @说明 代码版权归 杭州安存网络科技有限公司 所有
 */
public class SettingMainActivity extends BaseActivity {

	public static final String TAG = "SettingMainActivity";

	private Context mContext;

	// 自己在values下面array中定义一个名称
	private PubTitle pubTitle;

	// RelativeLayout每一行的id
	private RelativeLayout setNewInfo, setConceal, setAccountSafe, setAbout,
			setGiveFeedback, setCheckVersion;

	// 退出登录按钮
	private Button setBackLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_main_layout);
		mContext = this;
		initView();
		initClick();

	}

	/**
	 * @Auther: zhoumc
	 * @Description: 初始化控件
	 * @Date:2015-6-9上午11:25:53
	 * @return void
	 * @说明 代码版权归 杭州安存网络科技有限公司 所有
	 */
	private void initView() {
		// 标题
		pubTitle = (PubTitle) findViewById(R.id.set_detalis_title);
		// 新消息提醒
		setNewInfo = (RelativeLayout) findViewById(R.id.set_new_info);
		// 隐私
		setConceal = (RelativeLayout) findViewById(R.id.set_conceal);
		// 账号与安全
		setAccountSafe = (RelativeLayout) findViewById(R.id.set_Account_safe);
		// 关于安信
		setAbout = (RelativeLayout) findViewById(R.id.set_about);
		// 意见反馈
		setGiveFeedback = (RelativeLayout) findViewById(R.id.set_Give_feedback);
		// 退出登录
		setBackLogin = (Button) findViewById(R.id.set_back_login);
	}

	/**
	 * @Auther: zhoumc
	 * @Description: 初始化点击事件
	 * @Date:2015-6-9上午11:28:23
	 * @return void
	 * @说明 代码版权归 杭州安存网络科技有限公司 所有
	 */
	private void initClick() {
		// 标题
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

		// 新消息提醒监听事件
		setNewInfo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(SettingMainActivity.this,
						SetNewInfoActivity.class));
			}
		});
		// 隐私监听事件
		setConceal.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(SettingMainActivity.this,
						SetConcealActivity.class));
			}
		});
		// 账号与安全监听事件
		setAccountSafe.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i(TAG, "点击了账号与安全");
				startActivity(new Intent(SettingMainActivity.this,
						SetAccountSafeActivity.class));
			}
		});
		// 退出登陆
		setBackLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// AXSharedPreferences.setUserPhone(mContext, "");
				// AXSharedPreferences.setLoginType(mContext, false);
				// AXSharedPreferences.setUserPwd(mContext, "");
				// AXSharedPreferences.setEntStatus(mContext,"");
				// HomeSharedPreferences.setUpdateUnRead(mContext, "");
				// HomeSharedPreferences.setNewCardUnRead(mContext, "");
				runOnUiThread(new Runnable() {
					public void run() {
						new AxinDialog(mContext, AxinDialog.DIALOG_CHANGE)
						.setConfirmStr("确认退出").setCancelStr("稍后再说")
						.setTitle("确认退出当前账号吗？")
						.setCancelClick(new AxDialogClickListen() {

							@Override
							public void onClick(AxinDialog axinDialog) {
								axinDialog.dismiss();

							}
						}).setConfirmClick(new AxDialogClickListen() {

							@Override
							public void onClick(AxinDialog axinDialog) {
								axinDialog.dismiss();
								SharedPreferences spf = mContext
										.getSharedPreferences(
												AXSharedPreferences.SHARED_PREFERENCEK_KEY,
												0);
								Editor editor = spf.edit();
								editor.clear();
								editor.commit();
								setMap(new HashMap<String,Object>());
								startActivity(new Intent(
										SettingMainActivity.this,
										LoginActivity.class));
								finish();
							}
						}).show();
					}
				});

			}
		});

	}

}

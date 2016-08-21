package com.ciapc.anxin.modules.setting;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.AXSharedPreferences;
import com.ciapc.anxin.common.BaseActivity;
import com.ciapc.anxin.common.GlobalConstants;
import com.ciapc.anxin.common.callback.AXCallBack;
import com.ciapc.anxin.common.view.PubTitle;
import com.ciapc.anxin.common.view.PubTitle.OnTitleClickListener;
import com.ciapc.anxin.modules.home.HomeSharedPreferences;
import com.ciapc.anxin.modules.perfect.PerfectDataHandler;
import com.master.util.common.DialogUtil;
import com.master.util.common.GsonUtils;
import com.master.util.common.StringUtils;

/**
 * 
 * @author zhoumc
 * @Description: 设置——隐私
 * @ClassName: SetConcealActivity.java
 * @date 2015-6-9 下午7:25:07
 * @说明 代码版权归 杭州安存网络科技有限公司 所有
 */
public class SetConcealActivity extends BaseActivity {

	// 设置日志文件
	public static final String TAG = "SetConcealActivity";

	// 黑名单
	private RelativeLayout blackList;

	// 修改状态
	private final int UPDATE_STATUS = 1;

	private String cardIdStr, mobileStr, emailStr, positionStr, nickNameStr;

	private CheckBox ckCardId, ckNickName, ckMobile, ckPosition, ckEmail;

	private Context mContext;

	// 关闭状态
	public final int SWITCH_OFF = 0;
	// 打开状态
	public final int SWITCH_ON = 1;

	// 自己在values下面array中定义一个名称
	private PubTitle pubTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_conceal_layout);
		mContext = this;
		initView();
		initClick();
		initData();
	}

	/**
	 * @Auther: zhoumc
	 * @Description: 初始化数据
	 * @Date:2015-7-2上午11:09:10
	 * @return void
	 * @说明 代码版权归 杭州安存网络科技有限公司 所有
	 */
	private void initData() {
		// 获取卡片Id 开关
		cardIdStr = AXSharedPreferences.getPrivacy("cardIdSwitch", mContext);
		// 获取手机号Id 开关
		mobileStr = AXSharedPreferences.getPrivacy("mobileSwitch", mContext);
		// 获取邮箱Id 开关
		emailStr = AXSharedPreferences.getPrivacy("emailSwitch", mContext);
		// 获取职务Id 开关
		positionStr = AXSharedPreferences
				.getPrivacy("positionSwitch", mContext);
		// 获取昵称Id 开关
		nickNameStr = AXSharedPreferences
				.getPrivacy("nickNameSwitch", mContext);

		// 判断名片状态
		if ("0".equals(cardIdStr)) {
			ckCardId.setChecked(false);
		} else {
			ckCardId.setChecked(true);
		}
		// 判断手机号状态
		if ("0".equals(mobileStr)) {
			ckMobile.setChecked(false);
		} else {
			ckMobile.setChecked(true);
		}
		// 判断邮箱状态
		if ("0".equals(emailStr)) {
			ckEmail.setChecked(false);
		} else {
			ckEmail.setChecked(true);
		}
		// 判断职务状态
		if ("0".equals(positionStr)) {
			ckPosition.setChecked(false);
		} else {
			ckPosition.setChecked(true);
		}
		// 判断昵称状态
		if ("0".equals(nickNameStr)) {
			ckNickName.setChecked(false);
		} else {
			ckNickName.setChecked(true);
		}

	}

	/**
	 * @Auther: zhoumc
	 * @Description: 初始化控件
	 * @Date:2015-6-10上午10:29:28
	 * @return void
	 * @说明 代码版权归 杭州安存网络科技有限公司 所有
	 */
	private void initView() {
		// 标题
		pubTitle = (PubTitle) findViewById(R.id.set_conceal_title);

		// 名片号
		ckCardId = (CheckBox) findViewById(R.id.ck_cardId);
		// 昵称
		ckNickName = (CheckBox) findViewById(R.id.ck_nickName);
		// 手机号
		ckMobile = (CheckBox) findViewById(R.id.ck_mobile);
		// 职位
		ckPosition = (CheckBox) findViewById(R.id.ck_position);
		// 邮箱号
		ckEmail = (CheckBox) findViewById(R.id.ck_email);

	}

	/**
	 * @Auther: zhoumc
	 * @Description: 初始化点击事件
	 * @Date:2015-6-10上午10:31:11
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
						mHandler.sendEmptyMessage(UPDATE_STATUS);
						finish();

					}
				});

		// 名片号
		ckCardId.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (ckCardId.isChecked()) {
					cardIdStr = "1";
					ckCardId.setChecked(true);
					HomeSharedPreferences.setPrivacy(mContext, "1",
							"cardIdSwitch");
				} else {
					cardIdStr = "0";
					ckCardId.setChecked(false);
					HomeSharedPreferences.setPrivacy(mContext, "0",
							"cardIdSwitch");
				}
			}
		});
		// 昵称
		ckNickName.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (ckNickName.isChecked()) {
					nickNameStr = "1";
					ckNickName.setChecked(true);
					HomeSharedPreferences.setPrivacy(mContext, "1",
							"nickNameSwitch");
				} else {
					ckNickName.setChecked(false);
					nickNameStr = "0";
					HomeSharedPreferences.setPrivacy(mContext, "0",
							"nickNameSwitch");
				}

			}
		});
		// 手机号
		ckMobile.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (ckMobile.isChecked()) {
					ckMobile.setChecked(true);
					mobileStr = "1";
					HomeSharedPreferences.setPrivacy(mContext, "1",
							"mobileSwitch");
				} else {
					mobileStr = "0";
					ckMobile.setChecked(false);
					HomeSharedPreferences.setPrivacy(mContext, "0",
							"mobileSwitch");
				}

			}
		});
		// 职务
		ckPosition.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (ckPosition.isChecked()) {
					positionStr = "1";
					ckPosition.setChecked(true);
					HomeSharedPreferences.setPrivacy(mContext, "1",
							"positionSwitch");
				} else {
					positionStr = "0";
					ckPosition.setChecked(false);
					HomeSharedPreferences.setPrivacy(mContext, "0",
							"positionSwitch");
				}

			}
		});
		// 邮箱
		ckEmail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (ckEmail.isChecked()) {
					emailStr = "1";
					ckEmail.setChecked(true);
					HomeSharedPreferences.setPrivacy(mContext, "1",
							"emailSwitch");
				} else {
					emailStr = "0";
					ckEmail.setChecked(false);
					HomeSharedPreferences.setPrivacy(mContext, "0",
							"emailSwitch");
				}

			}
		});

	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case UPDATE_STATUS:
				PerfectDataHandler.getInstance(mContext).updateSwithStatus(
						cardIdStr, mobileStr, emailStr, positionStr,
						nickNameStr, new AXCallBack() {
							public void onCallBackString(String retStr) {
								// 返回成功
								if (StringUtils.isNotEmpty(retStr)) {
									// 返回成功
									if (GlobalConstants.HTTP_SUCCESS_CODE
											.equals(GsonUtils.getJsonValue(
													retStr,
													GlobalConstants.HTTP_CODE))) {
									} else {
										DialogUtil
												.showSystemToast(
														mContext,
														GsonUtils
																.getJsonValue(
																		retStr,
																		GlobalConstants.HTTP_MSG));
									}
								} else {
									DialogUtil.showSystemToast(mContext,
											"网络异常，请稍后再试");
								}

							};
						});
				break;

			default:
				break;
			}
		}
	};
}

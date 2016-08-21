package com.ciapc.anxin.modules.setting.person;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.AXSharedPreferences;
import com.ciapc.anxin.common.BaseActivity;
import com.ciapc.anxin.common.GlobalConstants;
import com.ciapc.anxin.common.callback.AXCallBack;
import com.ciapc.anxin.common.view.CustomEditText;
import com.ciapc.anxin.common.view.PubTitle;
import com.ciapc.anxin.common.view.PubTitle.OnTitleClickListener;
import com.ciapc.anxin.modules.home.HomeSharedPreferences;
import com.ciapc.anxin.modules.perfect.PerfectDataHandler;
import com.igexin.getuiext.ui.promotion.m;
import com.master.util.common.DialogUtil;
import com.master.util.common.GsonUtils;
import com.master.util.common.StringUtils;

/**
 * 
 * @author zhoumc
 * @Description: 个人信息——签名
 * @ClassName: PersonEmailReActivity.java
 * @date 2015-6-15 上午11:42:35
 * @说明 代码版权归 杭州安存网络科技有限公司 所有
 */
public class PersonQianminActivity extends BaseActivity {

	public static final String TAG = "PersonEenglishNameActivity";

	// 自己在values下面array中定义一个名称
	private PubTitle pubTitle;
	private Context mContext;

	private CustomEditText inputEmail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.perfect_qianml);
		initView();
		initClick();
		mContext = this;
		String qianm = HomeSharedPreferences.getSignature(mContext);
		if (!StringUtils.isNotEmpty(qianm)
				&& "这个家伙太懒了，什么都没写".equals(HomeSharedPreferences
						.getSignature(mContext))) {
			inputEmail.setText("");
		} else {
			if (StringUtils.isNotEmpty(qianm)) {
				inputEmail
						.setText(HomeSharedPreferences.getSignature(mContext));
			} else {
				inputEmail.setText("");
			}
		}
	}

	/**
	 * @Auther: zhoumc
	 * @Description: 初始化控件
	 * @Date:2015-6-26下午5:43:53
	 * @return void
	 * @说明 代码版权归 杭州安存网络科技有限公司 所有
	 */
	private void initView() {
		// 标题
		pubTitle = (PubTitle) findViewById(R.id.email_title);
		// 邮箱
		inputEmail = (CustomEditText) findViewById(R.id.input_qianm);
	}

	/**
	 * @Auther: zhoumc
	 * @Description: 初始化事件
	 * @Date:2015-6-26下午5:44:51
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
						String qianm = inputEmail.getText().toString();
						if (!StringUtils.isNotEmpty(qianm)) {
							finish();
						}
						updateQianm(qianm);
					}
				});

	}

	/**
	 * @Auther: zhuwt
	 * @Description: 修改签名
	 * @Date:2015年7月7日下午11:45:12
	 * @param str
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void updateQianm(final String str) {
		PerfectDataHandler.getInstance(mContext).updateMotto(str,
				new AXCallBack() {
					@Override
					public void onCallBackString(final String retStr) {
						super.onCallBackString(retStr);
						if (StringUtils.isNotEmpty(retStr)) {
							runOnUiThread(new Runnable() {
								public void run() {
									if (GlobalConstants.HTTP_SUCCESS_CODE
											.equals(GsonUtils.getJsonValue(
													retStr,
													GlobalConstants.HTTP_CODE))) {
										HomeSharedPreferences.setSignature(
												mContext, str);
										finish();
									} else {
										DialogUtil.showSystemToast(mContext,
												GsonUtils.getJsonValue(
														retStr,
														GlobalConstants.HTTP_MSG));
									}
								}
							});
						} else {
							DialogUtil.showSystemToast(mContext, "网络异常，请稍后再试！");
						}

					}
				});
	}
}

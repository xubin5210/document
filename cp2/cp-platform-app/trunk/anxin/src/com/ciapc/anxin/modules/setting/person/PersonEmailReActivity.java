package com.ciapc.anxin.modules.setting.person;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.BaseActivity;
import com.ciapc.anxin.common.GlobalConstants;
import com.ciapc.anxin.common.view.CustomEditText;
import com.ciapc.anxin.common.view.PubTitle;
import com.ciapc.anxin.common.view.PubTitle.OnTitleClickListener;
import com.igexin.getuiext.ui.promotion.m;
import com.master.util.common.DialogUtil;
import com.master.util.common.StringUtils;

/**
 * 
 * @author zhoumc
 * @Description: 个人信息——邮箱
 * @ClassName: PersonEmailReActivity.java
 * @date 2015-6-15 上午11:42:35
 * @说明 代码版权归 杭州安存网络科技有限公司 所有
 */
public class PersonEmailReActivity extends BaseActivity {

	public static final String TAG = "PersonEenglishNameActivity";

	// 自己在values下面array中定义一个名称
	private PubTitle pubTitle;
	private Context mContext;

	private CustomEditText inputEmail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_email_re_layout);
		initView();
		initClick();
		mContext = this;
		if (StringUtils.isNotEmpty(null != getMapValue("email") ? getMapValue(
				"email").toString() : "")) {
			inputEmail.setText(getMapValue("email").toString());
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
		pubTitle = (PubTitle) findViewById(R.id.person_email_re_title);
		// 邮箱
		inputEmail = (CustomEditText) findViewById(R.id.et_person_email);
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
		pubTitle.setShowLeftOrRight(true, false, false).setOnTitleClickListener(
				new OnTitleClickListener() {

					@Override
					public void onRightClick() {
					}

					@Override
					public void onLeftClick() {
						String str = inputEmail.getText().toString();
						if (StringUtils.isNotEmpty(str)) {
							if (!StringUtils.checkEmail(str)) {
								DialogUtil.showSystemToast(mContext, "邮箱格式不正确哦");
								return;
							}
						}
						setMapValue("email", str);
						finish();
					}
				});

	}
}

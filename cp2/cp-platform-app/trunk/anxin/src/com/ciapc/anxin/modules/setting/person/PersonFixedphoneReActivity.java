package com.ciapc.anxin.modules.setting.person;

import android.content.Context;
import android.os.Bundle;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.BaseActivity;
import com.ciapc.anxin.common.view.CustomEditText;
import com.ciapc.anxin.common.view.PubTitle;
import com.ciapc.anxin.common.view.PubTitle.OnTitleClickListener;
import com.ciapc.anxin.utils.CheckStringUtils;
import com.master.util.common.DialogUtil;
import com.master.util.common.StringUtils;

/**
 * 
 * @author zhoumc
 * @Description: 个人信息——座机
 * @ClassName: PersonFixedphoneReActivity.java
 * @date 2015-6-15 上午11:10:10
 * @说明 代码版权归 杭州安存网络科技有限公司 所有
 */
public class PersonFixedphoneReActivity extends BaseActivity {

	public static final String TAG = "PersonFixedphoneReActivity";

	// 自己在values下面array中定义一个名称
	private PubTitle pubTitle;

	private Context mContext;

	private CustomEditText inputFixedPhone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_fixedphone_re_layout);
		mContext = this;
		initView();
		initClick();
		if (StringUtils.isNotEmpty(null != getMapValue("phone") ? getMapValue(
				"phone").toString() : "")) {
			inputFixedPhone.setText(getMapValue("phone").toString());
		}

	}

	/**
	 * @Auther: zhoumc
	 * @Description: 初始化控件
	 * @Date:2015-6-26下午5:00:09
	 * @return void
	 * @说明 代码版权归 杭州安存网络科技有限公司 所有
	 */
	private void initView() {
		// 标题
		pubTitle = (PubTitle) findViewById(R.id.person_fixphone_re_title);
		// 座机
		inputFixedPhone = (CustomEditText) findViewById(R.id.et_person_fixphone);
	}

	/**
	 * @Auther: zhoumc
	 * @Description: 初始化事件
	 * @Date:2015-6-26下午5:00:51
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
						String str = inputFixedPhone.getText().toString();
						if (StringUtils.isNotEmpty(str)) {
							if (!CheckStringUtils.checkPattern(
									"(\\d){1,3}[ ]?([-]?((\\d)|[ ]){1,12})+$", str) || !StringUtils.onlyNum(str)) {
								DialogUtil.showSystemToast(mContext,
										"请输入正确的座机号码");
								return;
							}
						}
						setMapValue("phone", str);
						finish();
					}
				});

	}
}

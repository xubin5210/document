package com.ciapc.anxin.modules.setting.person;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

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
 * @Description: 传真
 * @ClassName: PersonFAXReActivity.java
 * @date 2015-6-26 下午5:17:34
 * @说明 代码版权归 杭州安存网络科技有限公司 所有
 */
public class PersonFAXReActivity extends BaseActivity {

	public static final String TAG = "PersonFAXReActivity";

	// 自己在values下面array中定义一个名称
	private PubTitle pubTitle;

	private Context mContext;

	private CustomEditText inputFax;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_fax_layout);
		mContext = this;
		initView();
		initClick();
		if (StringUtils.isNotEmpty(null != getMapValue("fax") ? getMapValue(
				"fax").toString() : "")) {
			inputFax.setText(getMapValue("fax").toString());
		}
	}

	/**
	 * @Auther: zhoumc
	 * @Description: 初始化控件
	 * @Date:2015-6-26下午5:26:19
	 * @return void
	 * @说明 代码版权归 杭州安存网络科技有限公司 所有
	 */
	private void initView() {
		// 标题
		pubTitle = (PubTitle) findViewById(R.id.person_fax_re_title);
		// 传真
		inputFax = (CustomEditText) findViewById(R.id.et_person_fax);

	}

	/**
	 * @Auther: zhoumc
	 * @Description: 初始化事件
	 * @Date:2015-6-26下午5:26:49
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
						String str = inputFax.getText().toString();
						if (StringUtils.isNotEmpty(str)) {
							if (!CheckStringUtils.checkPattern(
									"(\\d){1,3}[ ]?([-]?((\\d)|[ ]){1,12})+$", str) || !StringUtils.onlyNum(str)) {
								DialogUtil.showSystemToast(mContext,
										"请输入正确的传真格式");
								return;
							}
						}
						setMapValue("fax", str);
						finish();
					}
				});

	}
}

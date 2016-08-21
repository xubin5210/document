package com.ciapc.anxin.modules.setting.person;

import android.content.Context;
import android.os.Bundle;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.BaseActivity;
import com.ciapc.anxin.common.view.CustomEditText;
import com.ciapc.anxin.common.view.PubTitle;
import com.ciapc.anxin.common.view.PubTitle.OnTitleClickListener;
import com.master.util.common.DialogUtil;
import com.master.util.common.StringUtils;

/**
 * 
 * @author zhoumc
 * @Description: 个人信息——英文名
 * @ClassName: PersonEenglishNameActivity.java
 * @date 2015-6-12 下午4:46:06
 * @说明 代码版权归 杭州安存网络科技有限公司 所有
 */
public class PersonEenglishNameActivity extends BaseActivity {

	public static final String TAG = "PersonEenglishNameActivity";

	private Context mContext;

	private CustomEditText inputEnglishName;

	// 自己在values下面array中定义一个名称
	private PubTitle pubTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_english_name_layout);
		mContext = this;
		initView();
		initClick();
		if (StringUtils.isNotEmpty(null != getMapValue("ename") ? getMapValue(
				"ename").toString() : "")) {
			inputEnglishName.setText(getMapValue("ename").toString());
		}
	}

	/**
	 * @Auther: zhoumc
	 * @Description: 初始化控件
	 * @Date:2015-6-26下午4:34:10
	 * @return void
	 * @说明 代码版权归 杭州安存网络科技有限公司 所有
	 */
	private void initView() {
		// 标题
		pubTitle = (PubTitle) findViewById(R.id.person_english_name_title);
		// 英文名
		inputEnglishName = (CustomEditText) findViewById(R.id.et_person_name);
	}

	/**
	 * @Auther: zhoumc
	 * @Description:初始化事件
	 * @Date:2015-6-26下午4:34:56
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
						String str = inputEnglishName.getText().toString();
						if (StringUtils.isNotEmpty(str)) {
							if (!StringUtils.onlyAZ(str)) {
								DialogUtil.showSystemToast(mContext, "只能是英文哦");
								return;
							}
						}
						setMapValue("ename", str);
						finish();
					}
				});
	}
}

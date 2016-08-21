package com.ciapc.anxin.modules.setting.person;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.AXSharedPreferences;
import com.ciapc.anxin.common.BaseActivity;
import com.ciapc.anxin.common.view.PubTitle;
import com.ciapc.anxin.common.view.PubTitle.OnTitleClickListener;
import com.ciapc.anxin.modules.home.HomeSharedPreferences;
import com.master.util.common.StringUtils;

/**
 * 
 * @author zhoumc
 * @Description: 个人信息——昵称
 * @ClassName: PersonNameActivity.java
 * @date 2015-6-12 下午4:46:53
 * @说明 代码版权归 杭州安存网络科技有限公司 所有
 */
public class PersonNameActivity extends BaseActivity {

	public static final String TAG = "PersonNameActivity";

	private Context mContext;

	private EditText inputName;
	// 自己在values下面array中定义一个名称
	private PubTitle pubTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_name_layout);
		mContext = this;
		initView();
		initClick();
		if (StringUtils
				.isNotEmpty(null != getMapValue("nickName") ? getMapValue(
						"nickName").toString() : "")) {
			inputName.setText(getMapValue("nickName").toString());
		}

	}

	/**
	 * @Auther: zhoumc
	 * @Description: 初始化控件
	 * @Date:2015-6-24上午11:17:52
	 * @return void
	 * @说明 代码版权归 杭州安存网络科技有限公司 所有
	 */
	private void initView() {
		// 标题
		pubTitle = (PubTitle) findViewById(R.id.person_title);
		// 昵称
		inputName = (EditText) findViewById(R.id.et_person_name);
		inputName.setSelected(false);
	}

	/**
	 * @Auther: zhoumc
	 * @Description: 初始化事件
	 * @Date:2015-6-24上午11:18:23
	 * @return void
	 * @说明 代码版权归 杭州安存网络科技有限公司 所有
	 */
	private void initClick() {
		// 标题监听事件
		pubTitle.setShowLeftOrRight(true, false, false).setOnTitleClickListener(
				new OnTitleClickListener() {
					@Override
					public void onRightClick() {
					}

					@Override
					public void onLeftClick() {
						String str = inputName.getText().toString();
						setMapValue("nickName", str);
						HomeSharedPreferences.setNickName(mContext, str);
						finish();
					}
				});

	}

}

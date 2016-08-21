package com.ciapc.anxin.modules.setting.person;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.BaseActivity;
import com.ciapc.anxin.common.view.PubTitle;
import com.ciapc.anxin.common.view.PubTitle.OnTitleClickListener;
import com.ciapc.anxin.utils.CheckStringUtils;
import com.master.util.common.DialogUtil;
import com.master.util.common.StringUtils;

/**
 * 
 * @author zhoumc
 * @Description: 个人信息——微信
 * @ClassName: PersonWeixinReActivity.java
 * @date 2015-6-15 下午2:48:29
 * @说明 代码版权归 杭州安存网络科技有限公司 所有
 */
public class PersonWeixinReActivity extends BaseActivity {

	public static final String TAG = "PersonQqReActivity";

	// 自己在values下面array中定义一个名称
	private PubTitle pubTitle;

	private Context mContext;

	private EditText inputWeiXin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_weixin_re_layout);
		mContext = this;
		initView();
		initClick();

		inputWeiXin = (EditText) findViewById(R.id.et_person_weixin);
		if (StringUtils
				.isNotEmpty(null != getMapValue("wx") ? getMapValue("wx")
						.toString() : "")) {
			inputWeiXin.setText(getMapValue("wx").toString());
		}
	}

	/**
	 * @Auther: zhoumc
	 * @Description: 初始化控件
	 * @Date:2015-6-26下午6:09:45
	 * @return void
	 * @说明 代码版权归 杭州安存网络科技有限公司 所有
	 */
	private void initView() {
		// 标题
		pubTitle = (PubTitle) findViewById(R.id.person_weixin_re_title);
		// 微信
		inputWeiXin = (EditText) findViewById(R.id.et_person_weixin);
	}

	/**
	 * @Auther: zhoumc
	 * @Description:初始化事件
	 * @Date:2015-6-26下午6:10:35
	 * @return void
	 * @说明 代码版权归 杭州安存网络科技有限公司 所有
	 */
	private void initClick() {
		// 标题
		pubTitle.setShowLeftOrRight(true, false, true).setOnTitleClickListener(
				new OnTitleClickListener() {

					@Override
					public void onRightClick() {

					}

					@Override
					public void onLeftClick() {
						String str = inputWeiXin.getText().toString();
						if (StringUtils.isNotEmpty(str)) {
							if (CheckStringUtils.checkPattern("^[a-zA-Z][a-zA-Z0-9_-]{6,20}$", str)) {
								setMapValue("wx", str);
								finish();
								return;
							}else{
								DialogUtil.showSystemToast(mContext, "请输入正确的格式");
								return;
							}
						}
						setMapValue("wx", str);
						finish();
					}
				});

	}
}

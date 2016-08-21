package com.ciapc.anxin.modules.perfect;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.BaseActivity;
import com.ciapc.anxin.common.view.CustomEditText;
import com.ciapc.anxin.common.view.PubTitle;
import com.ciapc.anxin.common.view.PubTitle.OnTitleClickListener;
import com.ciapc.anxin.utils.CheckNameIdUtils;
import com.master.util.common.DialogUtil;
import com.master.util.common.StringUtils;

/**
 * 
 * @author zhuwt 身份证号
 * @ClassName: PersonNameIdActivity.java
 * @date 2015年6月19日 下午5:00:39
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */
public class PersonNameIdActivity extends BaseActivity {
	private CustomEditText name;

	private PubTitle pubTitle;

	private Context mContext;

	private TextView content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_name_id);
		mContext = this;
		initView();
		initClick();
		if (StringUtils.isNotEmpty(null != getMapValue("nameId") ? getMapValue(
				"nameId").toString() : "")) {
			name.setText(getMapValue("nameId").toString());
		}
		// content.setText(Html.fromHtml("实名身份认证将提高你的诚信\n认证后可交换<font size='5' color='blue'>1000</font>张名片！"));

	}

	/**
	 * @Auther: zhoumc
	 * @Description: 初始化事件
	 * @Date:2015-7-8下午5:17:04
	 * @return void
	 * @说明 代码版权归 杭州安存网络科技有限公司 所有
	 */
	private void initClick() {

		pubTitle.setShowLeftOrRight(true, false, false)
				.setOnTitleClickListener(new OnTitleClickListener() {

					@Override
					public void onRightClick() {

					}

					@Override
					public void onLeftClick() {
						String str = name.getText().toString();
						if (StringUtils.isNotEmpty(str)) {
							String result = CheckNameIdUtils
									.IDCardValidate(str);
							if (!StringUtils.isNotEmpty(result)) {
								setMapValue("nameId", str);
								finish();
							} else {
								DialogUtil.showSystemToast(mContext, result);
							}
						} else {
							setMapValue("nameId", str);
							finish();
						}
					}
				});

	}

	/**
	 * @Auther: zhoumc
	 * @Description: 初始化控件
	 * @Date:2015-7-8下午5:16:13
	 * @return void
	 * @说明 代码版权归 杭州安存网络科技有限公司 所有
	 */
	private void initView() {
		pubTitle = (PubTitle) findViewById(R.id.name_id_title);

		name = (CustomEditText) findViewById(R.id.input_name_id);
		content = (TextView) findViewById(R.id.content);

	}
}

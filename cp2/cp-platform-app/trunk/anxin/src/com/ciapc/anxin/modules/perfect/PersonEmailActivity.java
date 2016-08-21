/**    
 * @author zhuwt 
 * @Description: TODO(用一句话描述该文件做什么)   
 * @Package com.ciapc.anxin.modules.person   
 * @Title: PersonTrueNameActivity.java   
 * @date 2015年6月18日 下午5:40:20   
 * @version V1.0 
 * @说明  代码版权归 杭州反盗版中心有限公司 所有
 */
package com.ciapc.anxin.modules.perfect;

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
 * @author zhuwt
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @ClassName: PersonTrueNameActivity.java
 * @date 2015年6月18日 下午5:40:20
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */

public class PersonEmailActivity extends BaseActivity {

	private CustomEditText name;

	private PubTitle pubTitle;

	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.perfect_email);
		mContext = this;
		name = (CustomEditText) findViewById(R.id.input_email_name);
		if (StringUtils.isNotEmpty(null != getMapValue("email") ? getMapValue(
				"email").toString() : "")) {
			name.setText(getMapValue("email").toString());
		}
		pubTitle = (PubTitle) findViewById(R.id.email_title);
		pubTitle.setShowLeftOrRight(true, false, false).setOnTitleClickListener(
				new OnTitleClickListener() {

					@Override
					public void onRightClick() {}

					@Override
					public void onLeftClick() {
						String str = name.getText().toString();
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

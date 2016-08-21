package com.ciapc.anxin.modules.share;

import android.content.Context;
import android.os.Bundle;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.BaseActivity;
import com.ciapc.anxin.common.view.PubTitle;
import com.ciapc.anxin.common.view.PubTitle.OnTitleClickListener;

/**
 * 
 * @author zhuwt
 * @Description: 分享功能
 * @ClassName: ShareActivity.java
 * @date 2015年6月25日 下午2:24:39
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */
public class ShareActivity extends BaseActivity {

	private PubTitle pubTitle;

	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.share_layout);
		mContext = this;
		initView();
		initClick();
	}

	private void initView() {
		pubTitle = (PubTitle) findViewById(R.id.share_title);
	}

	private void initClick() {
		pubTitle.setShowLeftOrRight(true, false, false)
				.setOnTitleClickListener(new OnTitleClickListener() {

					@Override
					public void onRightClick() {

					}

					@Override
					public void onLeftClick() {
						finish();
					}
				});
	}
}

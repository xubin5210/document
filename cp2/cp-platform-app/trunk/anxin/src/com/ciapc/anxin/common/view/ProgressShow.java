package com.ciapc.anxin.common.view;


import com.ciapc.anxin.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author zhuwt
 * @Description: 加载等待
 * @ClassName: ProgressDialog.java
 * @date 2015年6月23日 上午11:52:02
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */

public class ProgressShow extends ProgressDialog {
	

	private String message;
	private TextView define_progress_msg;
	private ImageView mImageview;

	public ProgressShow(Context context) {
		super(context);
	}

	public ProgressShow(Context context, String content) {
		super(context);
		this.message = content;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// initView();
		initData();
		setCanceledOnTouchOutside(true);
	}

	private void initData() {
		setContentView(R.layout.loading_dialog_layout);
		define_progress_msg = (TextView) findViewById(R.id.id_tv_loadingmsg);
		mImageview = (ImageView) findViewById(R.id.iv_progress_dialog_loading);
		define_progress_msg.setText(message);
		// 开始动画
		AnimationDrawable animationDrawable = (AnimationDrawable) mImageview
				.getBackground();
		animationDrawable.start();
	}
}

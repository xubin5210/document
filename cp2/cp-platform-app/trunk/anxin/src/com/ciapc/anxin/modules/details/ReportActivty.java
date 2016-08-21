package com.ciapc.anxin.modules.details;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.AXSharedPreferences;
import com.ciapc.anxin.common.BaseActivity;
import com.ciapc.anxin.common.GlobalConstants;
import com.ciapc.anxin.common.callback.AXCallBack;
import com.ciapc.anxin.common.view.PubTitle;
import com.ciapc.anxin.common.view.PubTitle.OnTitleClickListener;
import com.master.util.common.DialogUtil;
import com.master.util.common.GsonUtils;
import com.master.util.common.StringUtils;

/**
 * @author zhuwt
 * @Description: 举报中心
 * @ClassName: ReportActivty.java
 * @date 2015年6月10日 下午9:46:43
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */

public class ReportActivty extends BaseActivity {

	private final int REPORT = 1;

	private Context mContext;

	public PubTitle pubTitle;

	// 是否选中
	private ImageView sham, cheat;

	private String content;

	private EditText mEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report_layout);
		mContext = this;
		initView();
		initClick();
	}

	/**
	 * @Auther: zhuwt
	 * @Description:
	 * @Date:2015年6月10日下午10:08:33
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initView() {
		pubTitle = (PubTitle) findViewById(R.id.report_title);
		sham = (ImageView) findViewById(R.id.sham);
		cheat = (ImageView) findViewById(R.id.cheat);
		mEditText = (EditText) findViewById(R.id.report_edit);
		mEditText.setSelected(false);
	}

	/**
	 * @Auther: zhuwt
	 * @Description:点击事件
	 * @Date:2015年6月10日下午10:10:07
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initClick() {
		// 虚假身份
		sham.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (sham.isSelected()) {
					sham.setSelected(false);
					return;
				} else {
					sham.setSelected(true);
					cheat.setSelected(false);
					content = "虚假身份";
				}

			}
		});

		// 欺诈行为
		cheat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (cheat.isSelected()) {
					cheat.setSelected(false);
					return;
				} else {
					cheat.setSelected(true);
					sham.setSelected(false);
					content = "欺诈行为";
				}
			}
		});

		pubTitle.setShowLeftOrRight(true, false, true).setOnTitleClickListener(
				new OnTitleClickListener() {

					@Override
					public void onRightClick() {
						mHandler.sendEmptyMessage(REPORT);
					}

					@Override
					public void onLeftClick() {
						finish();
					}
				});
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case REPORT:
				report();
				break;

			default:
				break;
			}
		};
	};

	/**
	 * @Auther: zhuwt
	 * @Description:举报
	 * @Date:2015年6月25日上午11:14:22
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void report() {
		if (!StringUtils.isNotEmpty(content)) {
			DialogUtil.showSystemToast(mContext, "请选择举报原因");
			return;
		}
		String reason = mEditText.getText().toString();
		DetailsDataHandler.getInstance(mContext).report(
				AXSharedPreferences.getCardId(mContext), "1", content, reason,
				new AXCallBack() {
					@Override
					public void onCallBackString(String retStr) {
						super.onCallBackString(retStr);
						if (StringUtils.isNotEmpty(retStr)) {
							if (GlobalConstants.HTTP_SUCCESS_CODE
									.equals(GsonUtils.getJsonValue(retStr,
											GlobalConstants.HTTP_CODE))) {
								finish();
							}
						} else {
							DialogUtil.showSystemToast(mContext, "网络异常，请稍后再试");
						}
					}
				});
	}
}

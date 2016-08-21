package com.ciapc.anxin.modules.contacts;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.BaseActivity;
import com.ciapc.anxin.common.GlobalConstants;
import com.ciapc.anxin.common.callback.AXCallBack;
import com.ciapc.anxin.common.pojo.ContactPojo;
import com.ciapc.anxin.common.view.PubTitle;
import com.ciapc.anxin.common.view.PubTitle.OnTitleClickListener;
import com.google.gson.reflect.TypeToken;
import com.master.util.common.DialogUtil;
import com.master.util.common.GsonUtils;
import com.master.util.common.StringUtils;
import com.master.util.common.SystemUtils;

/**
 * @author zhuwt
 * @Description:
 * @ClassName: AllContectActivity.java
 * @date 2015年7月6日 下午6:31:18
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */

public class AllContectActivity extends BaseActivity {

	private Context mContext;

	private final int INIT_DATE = 1;

	private ListView mListView;
	private ContactColleagueAdapter adapter;
	private PubTitle pubTitle;

	private List<ContactPojo> list = new ArrayList<ContactPojo>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_contacts);
		mContext = this;
		initView();
		initClick();
		if (!SystemUtils.isNetWorkConnected(mContext)) {
			DialogUtil.showSystemToast(mContext, "网络异常 请检查您的网络状态");
			return;
		}
		mHandler.sendEmptyMessage(INIT_DATE);
	}

	private void initDate() {
		String content = getIntent().getStringExtra("content");
		ContactDateHandler.getInstance(mContext).getContactData("trueName",
				content, new AXCallBack() {
					@Override
					public void onCallBackString(final String retStr) {
						super.onCallBackString(retStr);
						runOnUiThread(new Runnable() {
							public void run() {
								if (StringUtils.isNotEmpty(retStr)) {
									if (GlobalConstants.HTTP_SUCCESS_CODE
											.equals(GsonUtils.getJsonValue(
													retStr,
													GlobalConstants.HTTP_CODE))) {
										if (retStr.indexOf("list") < 0) {
											return;
										}
										String str = GsonUtils.getJsonValue(
												retStr, "list");
										if (StringUtils.isNotEmpty(str)) {
											list = GsonUtils
													.toObjectList(
															str,
															new TypeToken<List<ContactPojo>>() {
															}.getType());
											adapter = new ContactColleagueAdapter(
													mContext, list);
											mListView.setAdapter(adapter);
										}

									} else {
										DialogUtil
												.showSystemToast(
														mContext,
														GsonUtils
																.getJsonValue(
																		retStr,
																		GlobalConstants.HTTP_MSG));
									}
								} else {
									DialogUtil.showSystemToast(mContext,
											"网络异常，请稍后再试");
								}
							}
						});
					}
				});
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

	private void initView() {
		pubTitle = (PubTitle) findViewById(R.id.contact_detalis_title);
		mListView = (ListView) findViewById(R.id.all_contacts_listview);
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case INIT_DATE:
				initDate();
				break;

			default:
				break;
			}
		};
	};
}

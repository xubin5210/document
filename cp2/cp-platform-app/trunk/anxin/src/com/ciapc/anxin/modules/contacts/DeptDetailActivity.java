package com.ciapc.anxin.modules.contacts;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.BaseActivity;
import com.ciapc.anxin.common.GlobalConstants;
import com.ciapc.anxin.common.callback.AXCallBack;
import com.ciapc.anxin.common.pojo.ContactPojo;
import com.google.gson.reflect.TypeToken;
import com.master.util.common.DialogUtil;
import com.master.util.common.GsonUtils;
import com.master.util.common.StringUtils;
import com.master.util.common.SystemUtils;

public class DeptDetailActivity extends BaseActivity {

	private Context mContext;

	private TextView title;

	private ImageView back;

	private ListView mListView;
	private DeptDetailAdapter adapter;
	private List<ContactPojo> list = new ArrayList<ContactPojo>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dept_details);
		mContext = this;
		initView();
		initClick();
		if (!SystemUtils.isNetWorkConnected(mContext)) {
			DialogUtil.showSystemToast(mContext, "网络异常 请检查您的网络状态");
			return;
		}
		initDate();
	}

	private void initView() {
		title = (TextView) findViewById(R.id.title_text);
		back = (ImageView) findViewById(R.id.title_back_btn);
		mListView = (ListView) findViewById(R.id.dept_details_listview);
	}

	private void initDate() {
		String deptName = getIntent().getStringExtra("deptName");
		title.setText(deptName);
		String deptId = getIntent().getStringExtra("deptId");
		ContactDateHandler.getInstance(mContext).getContactData("deptName",
				deptId, new AXCallBack() {
					@Override
					public void onCallBackString(String retStr) {
						super.onCallBackString(retStr);
						if (StringUtils.isNotEmpty(retStr)) {
							if (GlobalConstants.HTTP_SUCCESS_CODE
									.equals(GsonUtils.getJsonValue(retStr,
											GlobalConstants.HTTP_CODE))) {
								if (retStr.indexOf("list") < 0) {
									return;
								}
								String str = GsonUtils.getJsonValue(retStr,
										"list");
								if (StringUtils.isNotEmpty(str)) {
									list = GsonUtils.toObjectList(str,
											new TypeToken<List<ContactPojo>>() {
											}.getType());
									adapter = new DeptDetailAdapter(mContext,
											list);
									mListView.setAdapter(adapter);
								}
							} else {
								DialogUtil.showSystemToast(mContext, GsonUtils
										.getJsonValue(retStr,
												GlobalConstants.HTTP_MSG));
							}
						}
					}
				});
	}

	private void initClick() {
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}
}

package com.ciapc.anxin.modules.details;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.BaseActivity;
import com.ciapc.anxin.common.GlobalConstants;
import com.ciapc.anxin.common.callback.AXCallBack;
import com.ciapc.anxin.common.pojo.ContactPojo;
import com.ciapc.anxin.common.view.PubTitle;
import com.ciapc.anxin.common.view.PubTitle.OnTitleClickListener;
import com.ciapc.anxin.modules.contacts.ContactsAdapter;
import com.google.gson.reflect.TypeToken;
import com.master.util.common.DialogUtil;
import com.master.util.common.GsonUtils;
import com.master.util.common.StringUtils;
import com.master.util.common.SystemUtils;

public class CompanyContactActivity extends BaseActivity {

	private PubTitle pubTitle;

	private Context mcContext;

	private List<ContactPojo> list = new ArrayList<ContactPojo>();
	private ListView mAxListView;
	private ContactsAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.company_contact);
		mcContext = this;
		initView();
		initClick();
		if (!SystemUtils.isNetWorkConnected(mcContext)) {
			DialogUtil.showSystemToast(mcContext, "暂无网络，请连接网络");
			return;
		}
		initData();
	}

	private void initData() {
		String entId = getIntent().getStringExtra("entId");
		if (!StringUtils.isNotEmpty(entId)) {
			return;
		}
		DetailsDataHandler.getInstance(mcContext).getEntContact(entId,
				new AXCallBack() {
					@Override
					public void onCallBackString(String retStr) {
						super.onCallBackString(retStr);
						if (StringUtils.isNotEmpty(retStr)) {
							// 返回成功
							if (GlobalConstants.HTTP_SUCCESS_CODE
									.equals(GsonUtils.getJsonValue(retStr,
											GlobalConstants.HTTP_CODE))) {
								if (retStr.indexOf("list") > 0) {
									String data = GsonUtils.getJsonValue(
											retStr, "list");
									list = GsonUtils.toObjectList(data,
											new TypeToken<List<ContactPojo>>() {
											}.getType());
									if (list.size() > 0) {
										adapter = new ContactsAdapter(
												mcContext, list);
										mAxListView.setAdapter(adapter);
									}
								}
							} else {
								DialogUtil.showSystemToast(mcContext, GsonUtils
										.getJsonValue(retStr,
												GlobalConstants.HTTP_MSG));
							}
						} else {
							DialogUtil
									.showSystemToast(mcContext, "网错错误,请稍后再试!");
						}
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
		pubTitle = (PubTitle) findViewById(R.id.company_contact_title);
		mAxListView = (ListView) findViewById(R.id.company_listview);
	}
}

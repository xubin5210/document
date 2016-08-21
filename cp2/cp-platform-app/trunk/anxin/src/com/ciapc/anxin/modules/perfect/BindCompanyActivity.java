package com.ciapc.anxin.modules.perfect;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.BaseActivity;
import com.ciapc.anxin.common.callback.AXCallBack;
import com.ciapc.anxin.common.pojo.CompanyPojo;
import com.ciapc.anxin.common.view.AxListView;
import com.ciapc.anxin.common.view.CustomEditText;
import com.google.gson.reflect.TypeToken;
import com.master.util.common.GsonUtils;
import com.master.util.common.StringUtils;

/**
 * 
 * @author zhuwt
 * @Description: 绑定企业
 * @ClassName: BindCompanyActivity.java
 * @date 2015年6月10日 下午2:50:25
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */
public class BindCompanyActivity extends BaseActivity {

	private Context mContext;

	private final int SEARCH = 1;

	// 没有数据
	private final int NO_DATE = 2;

	// 显示结果
	private final int SHOW_DATE = 3;
	
	//保存结果
	private final int SAVE_COMPANY = 5;

	private AxListView listView;
	private PersonCompanyAdapter mAdapter;

	// 搜索
	private TextView search, searchBg;

	private CustomEditText inputSearch;

	// 没有数据
	private LinearLayout noData;

	// 注册码界面
	private LinearLayout showCodeLayout;

	// 显示注册码
	private Button showCode;
	
	//搜索内容
	private String content;
	private TextView noCompany;

	private List<CompanyPojo> list = new ArrayList<CompanyPojo>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bind_company);
		mContext = this;
		initView();
		initClick();
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 初始化控件
	 * @Date:2015年6月10日下午3:13:27
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initView() {
		search = (TextView) findViewById(R.id.search_company_for);
		searchBg = (TextView) findViewById(R.id.search_company_text);
		listView = (AxListView) findViewById(R.id.search_company_listview);
		inputSearch = (CustomEditText) findViewById(R.id.search_company);
		noData = (LinearLayout) findViewById(R.id.company_no_date);
		showCode = (Button) findViewById(R.id.register_company_btn);
		showCodeLayout = (LinearLayout) findViewById(R.id.company_code);
		noCompany = (TextView) findViewById(R.id.no_date_content);
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 点击事件
	 * @Date:2015年6月10日下午3:14:33
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initClick() {
		search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String data = inputSearch.getText().toString();
				if (!StringUtils.isNotEmpty(data)) {
					return;
				}
				content = data;
				Message msg = new Message();
				msg.what = SEARCH;
				msg.obj = data;
				handler.sendMessage(msg);
			}
		});

//		showCode.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				showCode.setVisibility(View.GONE);
//				showCodeLayout.setVisibility(View.VISIBLE);
//			}
//		});
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SEARCH:
				String content = (String) msg.obj;
				PerfectDataHandler.getInstance(mContext).searchCompany(content,
						new AXCallBack() {
							public void onCallBackString(String retStr) {
								if (StringUtils.isNotEmpty(retStr)) {
									// 没有数据 则提示去注册该企业
									if (retStr.indexOf("list") < 0) {
										handler.sendEmptyMessage(NO_DATE);
										return;
									}
									String str = GsonUtils.getJsonValue(retStr,
											"list");
									list = GsonUtils
											.toObjectList(
													str,
													new TypeToken<List<CompanyPojo>>() {
													}.getType());
									if (null != list) {
										if (list.size() > 0) {
											handler.sendEmptyMessage(SHOW_DATE);
										}
									}
								}
							};
						});

				break;

			case NO_DATE:
				noResult();
				break;

			case SHOW_DATE:
				showResult();
				break;
				
			case SAVE_COMPANY:
				String company = (String) msg.obj;
				setMapValue("company", company);
				finish();
				break;

			default:
				break;
			}
		};
	};

	/**
	 * @Auther: zhuwt
	 * @Description: 没有结果时
	 * @Date:2015年7月1日下午1:31:09
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void noResult() {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// 已经显示
				listView.setVisibility(View.GONE);
				searchBg.setVisibility(View.GONE);
				noCompany.setText(content+"还没有入驻安信平台");
				if (noData.getVisibility() == View.VISIBLE) {
					return;
				} else {
					noData.setVisibility(View.VISIBLE);
//					showCode.setVisibility(View.VISIBLE);
					showCodeLayout.setVisibility(View.GONE);
				}
			}
		});
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 显示结果
	 * @Date:2015年7月3日下午2:22:46
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void showResult() {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				searchBg.setVisibility(View.GONE);
				listView.setVisibility(View.VISIBLE);
				noData.setVisibility(View.GONE);
				showCode.setVisibility(View.GONE);
				showCodeLayout.setVisibility(View.GONE);
				if (null == mAdapter) {
					mAdapter = new PersonCompanyAdapter(mContext, list,handler);
					listView.setAdapter(mAdapter);
					return;
				} else {
					mAdapter.setData(list);
				}
			}
		});
	}
}

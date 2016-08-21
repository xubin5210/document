package com.ciapc.anxin.modules.contacts;

import java.util.ArrayList;
import java.util.List;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.GlobalConstants;
import com.ciapc.anxin.common.callback.AXCallBack;
import com.ciapc.anxin.common.pojo.ContactPojo;
import com.ciapc.anxin.common.view.AxListView;
import com.ciapc.anxin.common.view.CustomEditText;
import com.ciapc.anxin.common.view.ProgressShow;
import com.ciapc.anxin.common.view.PubTitle;
import com.ciapc.anxin.common.view.PubTitle.OnTitleClickListener;
import com.ciapc.anxin.common.view.SideBar;
import com.ciapc.anxin.common.view.SideBar.OnTouchingLetterChangedListener;
import com.google.gson.reflect.TypeToken;
import com.master.util.common.DialogUtil;
import com.master.util.common.GsonUtils;
import com.master.util.common.StringUtils;
import com.master.util.common.SystemUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ContactsActivity extends Activity {

	private final int FOR_SEARCH = 1;

	private PubTitle pubTitle;

	private Context mContext;
	private ListView mListView;
	private SideBar sideBar;

	// 主页通讯录
	private List<ContactPojo> list = new ArrayList<ContactPojo>();
	// 搜索结果为部门
	private List<ContactPojo> depList = new ArrayList<ContactPojo>();
	// 搜索结果为个人
	private List<ContactPojo> perList = new ArrayList<ContactPojo>();
	private ContactsAdapter mAdapter;

	// 搜索框
	private CustomEditText search;

	// 搜索结果为部门和个人
	private AxListView colleage;
	private ContactColleagueAdapter mColleagueAdapter;
	private AxListView deparment;
	private ContactDepartmentAdapter mDepartmentAdapter;

	// 排序的数据
	private FrameLayout sort;

	// 部门搜索结果
	private LinearLayout departmentResult;
	private LinearLayout departmentResultHint;
	// 同事搜索结果
	private LinearLayout colleagueResult;
	private LinearLayout colleagueResultHint;
	// 初始化数据
	private final int INIT_DATA = 2;

	private String content;

	private ProgressShow mProgressShow;

	private TextView deptCount, personCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_layout);
		mContext = this;
		initView();
		initClick();
		// test();
		if (!SystemUtils.isNetWorkConnected(mContext)) {
			DialogUtil.showSystemToast(mContext, "网络异常 请检查您的网络状态");
			return;
		}
		mProgressShow = new ProgressShow(mContext, "加载中，请稍后...");
		mProgressShow.show();
		mHandler.sendEmptyMessageDelayed(INIT_DATA, 1000);
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 控件初始化
	 * @Date:2015年6月8日下午3:11:00
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initView() {
		sort = (FrameLayout) findViewById(R.id.contact_search_sort);
		mListView = (ListView) findViewById(R.id.list_view);
		sideBar = (SideBar) findViewById(R.id.contact_bar);
		search = (CustomEditText) findViewById(R.id.contact_for_search);
		search.addTextChangedListener(new ContactSearch());
		colleage = (AxListView) findViewById(R.id.person_listview);
		deparment = (AxListView) findViewById(R.id.department_listview);
		colleagueResult = (LinearLayout) findViewById(R.id.contact_colleague_result);
		departmentResult = (LinearLayout) findViewById(R.id.contact_department_result);
		colleagueResultHint = (LinearLayout) findViewById(R.id.contact_colleague_result_hint);
		departmentResultHint = (LinearLayout) findViewById(R.id.contact_department_result_hint);
		pubTitle = (PubTitle) findViewById(R.id.contact_title);
		deptCount = (TextView) findViewById(R.id.dept_cpount);
		personCount = (TextView) findViewById(R.id.person_cpount);
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 点击事件
	 * @Date:2015年6月8日下午3:15:49
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
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

		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String s) {
				if (null == mAdapter) {
					return;
				}
				// 该字母首次出现的位置
				int position = mAdapter.getPositionForSection(s.charAt(0));
				if (position != -1) {
					mListView.setSelection(position);
				}

			}
		});

		// 查看个人 更多
		colleagueResult.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent mIntent = new Intent(mContext, AllContectActivity.class);
				mIntent.putExtra("content", content);
				startActivity(mIntent);
			}
		});

		// 查看部门 更多
		departmentResult.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent mIntent = new Intent(mContext, AllDeptActivity.class);
				mIntent.putExtra("content", content);
				startActivity(mIntent);
			}
		});

	}

	/**
	 * 
	 * @author zhuwt
	 * @Description: 搜索类
	 * @ClassName: ContactSearch.java
	 * @date 2015年6月8日 下午3:29:20
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private class ContactSearch implements TextWatcher {

		@Override
		public void afterTextChanged(Editable arg0) {
			String str = arg0.toString();
			if (StringUtils.isNotEmpty(str)) {
				if (sort.getVisibility() == View.VISIBLE) {
					sort.setVisibility(View.GONE);
				}
				content = str;
				mHandler.sendEmptyMessage(FOR_SEARCH);
			} else {
				sort.setVisibility(View.VISIBLE);
				colleagueResult.setVisibility(View.GONE);
				departmentResult.setVisibility(View.GONE);
			}
		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
		}

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
		}

	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case FOR_SEARCH:
				searchDate(content);
				break;

			case INIT_DATA:
				initData();
				break;

			default:
				break;
			}
		};
	};

	/**
	 * @Auther: zhuwt
	 * @Description: 初始化数据
	 * @Date:2015年6月30日下午4:38:12
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void initData() {
		ContactDateHandler.getInstance(mContext).getContactData("trueName", "",
				new AXCallBack() {

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
									mAdapter = new ContactsAdapter(mContext,
											list);
									mListView.setAdapter(mAdapter);
								}
							} else {
								DialogUtil.showSystemToast(mContext, GsonUtils
										.getJsonValue(retStr,
												GlobalConstants.HTTP_MSG));
							}
						} else {
							DialogUtil.showSystemToast(mContext, "出错了，请稍后再试!");
						}
						mProgressShow.dismiss();
					}
				});
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 搜索
	 * @Date:2015年7月3日下午6:02:01
	 * @param content
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void searchDate(String str) {
		ContactDateHandler.getInstance(mContext).searchContact(str,
				new AXCallBack() {
					@Override
					public void onCallBackString(final String retStr) {
						super.onCallBackString(retStr);
						if (StringUtils.isNotEmpty(retStr)) {
							runOnUiThread(new Runnable() {
								public void run() {
									if (GlobalConstants.HTTP_SUCCESS_CODE
											.equals(GsonUtils.getJsonValue(
													retStr,
													GlobalConstants.HTTP_CODE))) {
										String data = GsonUtils.getJsonValue(
												retStr, "data");
										// 结果为部门
										String dep = GsonUtils.getJsonValue(
												data, "deptList");
										String isMoreDept = GsonUtils
												.getJsonValue(data,
														"isMoreDept");
										// 更多显示
										if ("1".equals(isMoreDept)) {
											deptCount
													.setVisibility(View.VISIBLE);
											deptCount.setText("查看全部("
													+ GsonUtils.getJsonValue(
															data,
															"cardDeptCount")
													+ ")");
										} else {
											deptCount.setVisibility(View.GONE);
											departmentResult
													.setVisibility(View.GONE);
										}
										if (StringUtils.isNotEmpty(dep)
												&& !"[]".equals(dep)) {
											depList = GsonUtils
													.toObjectList(
															dep,
															new TypeToken<List<ContactPojo>>() {
															}.getType());
											departmentResult
													.setVisibility(View.VISIBLE);
											departmentResultHint
													.setVisibility(View.VISIBLE);
											if (null == mDepartmentAdapter) {
												mDepartmentAdapter = new ContactDepartmentAdapter(
														mContext, depList);
												deparment
														.setAdapter(mDepartmentAdapter);
											} else {
												mDepartmentAdapter
														.setData(depList);
											}
										} else {
											departmentResult
													.setVisibility(View.GONE);
										}

										// 结果为个人
										String person = GsonUtils.getJsonValue(
												data, "clientList");
										String isMoreClient = GsonUtils
												.getJsonValue(data,
														"isMoreClient");
										if ("1".equals(isMoreClient)) {
											personCount.setText("查看全部("
													+ GsonUtils.getJsonValue(
															data,
															"entStaffCount")
													+ ")");
											personCount
													.setVisibility(View.VISIBLE);
										} else {
											personCount
													.setVisibility(View.GONE);
											colleagueResult
													.setVisibility(View.GONE);
										}
										if (StringUtils.isNotEmpty(person)
												&& !"[]".equals(person)) {
											perList = GsonUtils
													.toObjectList(
															person,
															new TypeToken<List<ContactPojo>>() {
															}.getType());
											colleagueResult
													.setVisibility(View.VISIBLE);
											colleagueResultHint
													.setVisibility(View.VISIBLE);
											if (null == mColleagueAdapter) {
												mColleagueAdapter = new ContactColleagueAdapter(
														mContext, perList);
												colleage.setAdapter(mColleagueAdapter);
											} else {
												mColleagueAdapter
														.setData(perList);
											}
										} else {
											colleagueResult
													.setVisibility(View.GONE);
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

								}
							});
						}
					}
				});
	}
}

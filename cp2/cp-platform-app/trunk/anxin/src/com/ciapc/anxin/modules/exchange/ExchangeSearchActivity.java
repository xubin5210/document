package com.ciapc.anxin.modules.exchange;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.BaseActivity;
import com.ciapc.anxin.common.GlobalConstants;
import com.ciapc.anxin.common.callback.AXCallBack;
import com.ciapc.anxin.common.pojo.CompanyPojo;
import com.ciapc.anxin.common.pojo.ExchangePojo;
import com.ciapc.anxin.common.view.CustomEditText;
import com.ciapc.anxin.modules.details.CompanyDetails;
import com.ciapc.anxin.modules.search.SearchCompanyAdapter;
import com.ciapc.anxin.utils.CheckStringUtils;
import com.ciapc.anxin.utils.ListView.XListView;
import com.ciapc.anxin.utils.ListView.XListView.IXListViewListener;
import com.google.gson.reflect.TypeToken;
import com.master.util.common.DialogUtil;
import com.master.util.common.GsonUtils;
import com.master.util.common.StringUtils;
import com.master.util.common.SystemUtils;

public class ExchangeSearchActivity extends BaseActivity implements
		OnClickListener {

	private Context mContext;

	// 搜索
	private final int SERACH = 1;

	// 取消
	private TextView cancel;

	// 搜索框
	private CustomEditText search;

	// 根据 名片号 名字 昵称 邮箱 工作 公司 电话 搜索
	private RelativeLayout card, nickName, mail, job, company, phone;

	// 及时显示 搜索大的内容
	private TextView cardContent, nameContent, nickNameContent, mailContent,
			jobContent, companyContent, phoneContent;

	private LinearLayout condition;

	// 搜索的条件选择 是否已经初始化
	private boolean flag = false;

	// 搜索类型
	private String type;
	// 搜索内容
	private String content;

	// 默认背景
	private LinearLayout bg;

	// 搜索没有结果时的背景
	private LinearLayout noDate;
	// 没有搜索数据时 请求推荐接口
	private final int NO_DATA = 2;

	private XListView listView;
	// 搜索结果不是企业时
	private List<ExchangePojo> list = new ArrayList<ExchangePojo>();
	private ExchangeCardAdapter adapter;
	// 搜索结果为企业时
	private List<CompanyPojo> comList = new ArrayList<CompanyPojo>();
	private SearchCompanyAdapter mCompanyAdapter;

	private int pageNo = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exchange_search_layout);
		mContext = this;
		initView();
		initClick();
		if (!SystemUtils.isNetWorkConnected(mContext)) {
			DialogUtil.showSystemToast(mContext, "暂无网络，请连接网络");
			return;
		}
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				CheckStringUtils.openKeybord(search, mContext);
			}
		}, 500);
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 初始化
	 * @Date:2015年6月8日下午4:32:50
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initView() {
		cancel = (TextView) findViewById(R.id.exchange_search_cancel);
		bg = (LinearLayout) findViewById(R.id.exchange_bg);
		card = (RelativeLayout) findViewById(R.id.exchange_search_card);
		// name = (RelativeLayout) findViewById(R.id.exchange_search_name);
		nickName = (RelativeLayout) findViewById(R.id.exchange_search_nickname);
		job = (RelativeLayout) findViewById(R.id.exchange_search_job);
		company = (RelativeLayout) findViewById(R.id.exchange_search_organization);
		phone = (RelativeLayout) findViewById(R.id.exchange_search_phone);
		mail = (RelativeLayout) findViewById(R.id.exchange_search_mail);
		card.setOnClickListener(this);
		// name.setOnClickListener(this);
		nickName.setOnClickListener(this);
		job.setOnClickListener(this);
		company.setOnClickListener(this);
		phone.setOnClickListener(this);
		phone.setOnClickListener(this);
		mail.setOnClickListener(this);
		search = (CustomEditText) findViewById(R.id.exchange_search);
		condition = (LinearLayout) findViewById(R.id.condition);
		search.addTextChangedListener(new ExchangeTextWatcher());
		listView = (XListView) findViewById(R.id.exchange_listview);
		listView.setPullLoadEnable(true);
		listView.setPullRefreshEnable(false);
		cardContent = (TextView) findViewById(R.id.exchange_search_card_content);
		nameContent = (TextView) findViewById(R.id.exchange_search_name_content);
		nickNameContent = (TextView) findViewById(R.id.exchange_search_nickname_content);
		phoneContent = (TextView) findViewById(R.id.exchange_search_phone_content);
		companyContent = (TextView) findViewById(R.id.exchange_search_organization_content);
		jobContent = (TextView) findViewById(R.id.exchange_search_job_content);
		mailContent = (TextView) findViewById(R.id.exchange_search_mail_content);
		noDate = (LinearLayout) findViewById(R.id.show_date);
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 点击事件
	 * @Date:2015年6月8日下午4:42:18
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initClick() {
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// 搜索结果为企业时 点击进入企业详情
				if ("orgName".equals(type)) {
					Intent mIntent = new Intent(mContext, CompanyDetails.class);
					mIntent.putExtra("entId", comList.get(arg2).getEntId());
					startActivity(mIntent);
				}
			}
		});

		listView.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {

			}

			@Override
			public void onLoadMore() {
				pageNo++;
				mHandler.sendEmptyMessageDelayed(SERACH, 600);
			}
		});
	}

	/**
	 * 
	 * @author zhuwt
	 * @Description: 监听输入
	 * @ClassName: ExchangeTextWatcher.java
	 * @date 2015年6月8日 下午6:21:42
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private class ExchangeTextWatcher implements TextWatcher {

		@Override
		public void afterTextChanged(Editable arg0) {
			if (StringUtils.isNotEmpty(arg0.toString())) {
				// 获取搜索内容首字母
				String str = arg0.toString().substring(0, 1);
				content = arg0.toString();
				// 隐藏默认的背景 显示搜索条件选择
				bg.setVisibility(View.GONE);
				condition.setVisibility(View.VISIBLE);
				listView.setVisibility(View.GONE);
				list.clear();
				// 如果首字母是字母 满足的条件是企业 名称 昵称 职务
				if (StringUtils.onlyCH(str)) {
					if (!flag) {
						// name.setVisibility(View.VISIBLE);
						nickName.setVisibility(View.VISIBLE);
						job.setVisibility(View.VISIBLE);
						company.setVisibility(View.VISIBLE);
						flag = true;
					}
					nameContent.setText(content);
					nickNameContent.setText(content);
					jobContent.setText(content);
					companyContent.setText(content);
					return;
				}

				// 如果首字母是英文 满足的搜索条件是 邮件 名字 昵称 机构
				if (StringUtils.onlyAZ(str)) {
					if (!flag) {
						// name.setVisibility(View.VISIBLE);
						nickName.setVisibility(View.VISIBLE);
						company.setVisibility(View.VISIBLE);
						mail.setVisibility(View.VISIBLE);
					}
					nameContent.setText(content);
					nickNameContent.setText(content);
					mailContent.setText(content);
					companyContent.setText(content);
					return;
				}

				// 首字母是数字 满足的搜索条件是 手机号 名片号 邮件 名字 昵称
				if (StringUtils.onlyNum(str)) {
					if (!flag) {
						// name.setVisibility(View.VISIBLE);
						nickName.setVisibility(View.VISIBLE);
						phone.setVisibility(View.VISIBLE);
						card.setVisibility(View.VISIBLE);
						mail.setVisibility(View.VISIBLE);
						flag = true;
					}
					nameContent.setText(content);
					nickNameContent.setText(content);
					phoneContent.setText(content);
					cardContent.setText(content);
					mailContent.setText(content);
					return;
				}
			} else {
				// 清空搜索结果
				flag = false;
				bg.setVisibility(View.VISIBLE);
				job.setVisibility(View.GONE);
				condition.setVisibility(View.GONE);
				// name.setVisibility(View.GONE);
				nickName.setVisibility(View.GONE);
				phone.setVisibility(View.GONE);
				card.setVisibility(View.GONE);
				mail.setVisibility(View.GONE);
				company.setVisibility(View.GONE);
				listView.setVisibility(View.GONE);
				noDate.setVisibility(View.GONE);
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

	@Override
	public void onClick(View arg0) {
		CheckStringUtils.closeKeybord(search, mContext);
		switch (arg0.getId()) {
		// 名片号
		case R.id.exchange_search_card:
			list.clear();
			pageNo = 1;
			listView.setVisibility(View.VISIBLE);
			condition.setVisibility(View.GONE);
			noDate.setVisibility(View.GONE);
			type = "cardId";
			mHandler.sendEmptyMessage(SERACH);
			break;
		// 部门
		case R.id.exchange_search_job:
			list.clear();
			pageNo = 1;
			type = "position";
			listView.setVisibility(View.VISIBLE);
			noDate.setVisibility(View.GONE);
			condition.setVisibility(View.GONE);
			mHandler.sendEmptyMessage(SERACH);
			break;
		case R.id.exchange_search_nickname:
			list.clear();
			type = "nickName";
			pageNo = 1;
			listView.setVisibility(View.VISIBLE);
			condition.setVisibility(View.GONE);
			noDate.setVisibility(View.GONE);
			mHandler.sendEmptyMessage(SERACH);
			break;
		// 电话
		case R.id.exchange_search_phone:
			list.clear();
			pageNo = 1;
			type = "mobile";
			listView.setVisibility(View.VISIBLE);
			condition.setVisibility(View.GONE);
			noDate.setVisibility(View.GONE);
			mHandler.sendEmptyMessage(SERACH);
			break;
		// 机构
		case R.id.exchange_search_organization:
			comList.clear();
			pageNo = 1;
			type = "orgName";
			listView.setVisibility(View.VISIBLE);
			condition.setVisibility(View.GONE);
			noDate.setVisibility(View.GONE);
			mHandler.sendEmptyMessage(SERACH);
			break;
		// 邮箱
		case R.id.exchange_search_mail:
			list.clear();
			pageNo = 1;
			type = "email";
			listView.setVisibility(View.VISIBLE);
			condition.setVisibility(View.GONE);
			noDate.setVisibility(View.GONE);
			mHandler.sendEmptyMessage(SERACH);
			break;

		default:
			break;
		}
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			// 搜索
			case SERACH:
				forSearch();
				break;
			case NO_DATA:
				noData();
				break;
			default:
				break;
			}
		};
	};

	/**
	 * @Auther: zhuwt
	 * @Description: 搜索
	 * @Date:2015年7月1日上午11:43:29
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void forSearch() {
		ExchangeDataHandler.getInstance(mContext).exchangeSerch(pageNo, type,
				content, new AXCallBack() {
					public void onCallBackString(final String retStr) {
						runOnUiThread(new Runnable() {
							public void run() {
								if (StringUtils.isNotEmpty(retStr)) {
									if (GlobalConstants.HTTP_SUCCESS_CODE
											.equals(GsonUtils.getJsonValue(
													retStr,
													GlobalConstants.HTTP_CODE))) {
										String page = GsonUtils.getJsonValue(
												retStr, "page");
										pageNo = Integer.parseInt(GsonUtils
												.getJsonValue(page, "pageNo"));
										if (retStr.indexOf("list") < 0
												&& pageNo <= 1) {
											noDate.setVisibility(View.VISIBLE);
											list.clear();
											comList.clear();
											mHandler.sendEmptyMessage(NO_DATA);
											listView.stopLoadMore();
											return;
										}
										if (retStr.indexOf("list") < 0) {
											listView.stopLoadMore();
											return;
										}
										String str = GsonUtils.getJsonValue(
												retStr, "list");
										if (!StringUtils.isNotEmpty(str)) {
											listView.stopLoadMore();
											return;
										}
										// 搜索结果为企业
										if ("orgName".equals(type)) {
											List<CompanyPojo> data = GsonUtils
													.toObjectList(
															str,
															new TypeToken<List<CompanyPojo>>() {
															}.getType());
											if (null != data) {
												// 返回成功
												if (data.size() > 0) {
													if(data.size() >=10){
														listView.stopLoadMore();
														listView.setPullLoadEnable(true);
													}else{
														listView.stopLoadMore();
														listView.setPullLoadEnable(false);
													}
													listView.setVisibility(View.VISIBLE);
													if (null == mCompanyAdapter) {
														mCompanyAdapter = new SearchCompanyAdapter(
																mContext, data);
														comList = data;
														listView.setAdapter(mCompanyAdapter);
													} else {
														comList.addAll(data);
														mCompanyAdapter
																.setDate(comList);
													}
												}
											}
										} else {
											// 搜索结果不为企业
											List<ExchangePojo> data = GsonUtils
													.toObjectList(
															str,
															new TypeToken<List<ExchangePojo>>() {
															}.getType());
											if (null != data) {
												// 返回成功
												if (data.size() > 0) {
													if(data.size() >=10){
														listView.stopLoadMore();
														listView.setPullLoadEnable(true);
													}else{
														listView.stopLoadMore();
														listView.setPullLoadEnable(false);
													}
													listView.setVisibility(View.VISIBLE);
													if (null == adapter) {
														adapter = new ExchangeCardAdapter(
																mContext, data,
																2);
														list = data;
														listView.setAdapter(adapter);
														return;
													} else {
														list.addAll(data);
														adapter.setData(list,2);
													}
												}
											}
										}
									} else {
										listView.stopLoadMore();
										DialogUtil
												.showSystemToast(
														mContext,
														GsonUtils
																.getJsonValue(
																		retStr,
																		GlobalConstants.HTTP_MSG));
									}
								} else {
									listView.stopLoadMore();
									DialogUtil.showSystemToast(mContext,
											"网络异常，请稍后再试!");
								}
							}
						});
					};
				});
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 没有数据时的操作
	 * @Date:2015年7月1日上午11:44:41
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void noData() {
		ExchangeDataHandler.getInstance(mContext).commentCard(new AXCallBack() {
			@Override
			public void onCallBackString(final String retStr) {
				super.onCallBackString(retStr);

				runOnUiThread(new Runnable() {
					public void run() {
						if (StringUtils.isNotEmpty(retStr)) {
							if (retStr.indexOf("list") < 0) {
								listView.setVisibility(View.GONE);
								return;
							}
							String str = GsonUtils.getJsonValue(retStr, "list");
							if (!StringUtils.isNotEmpty(str)) {
								return;
							}
							// 推荐的结果
							List<ExchangePojo> data = GsonUtils.toObjectList(
									str, new TypeToken<List<ExchangePojo>>() {
									}.getType());
							if (null != data) {
								// 返回成功
								if (data.size() > 0) {
									listView.setVisibility(View.VISIBLE);
									if (null == adapter) {
										adapter = new ExchangeCardAdapter(
												mContext, data, 1);
										listView.setAdapter(adapter);
										return;
									} else {
										adapter.setData(data,2);
									}
								}
							}

						}
					}
				});
			}
		});
	}
	
	
	@Override
	public void finish() {
		CheckStringUtils.closeKeybord(search, mContext);
		super.finish();
	}
}
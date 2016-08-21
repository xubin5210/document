package com.ciapc.anxin.modules.search;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ZbarZxing.XZbar.HxBarcode;
import com.ciapc.anxin.R;
import com.ciapc.anxin.common.BaseActivity;
import com.ciapc.anxin.common.callback.AXCallBack;
import com.ciapc.anxin.common.pojo.CompanyPojo;
import com.ciapc.anxin.common.pojo.ExchangePojo;
import com.ciapc.anxin.common.view.CustomEditText;
import com.ciapc.anxin.modules.details.ScanDetailActivity;
import com.ciapc.anxin.modules.exchange.ExchangeCardAdapter;
import com.ciapc.anxin.modules.exchange.ExchangeDataHandler;
import com.ciapc.anxin.modules.home.HomeDataHandler;
import com.ciapc.anxin.utils.CheckStringUtils;
import com.ciapc.anxin.utils.ListView.XListView;
import com.ciapc.anxin.utils.ListView.XListView.IXListViewListener;
import com.google.gson.reflect.TypeToken;
import com.master.util.common.GsonUtils;
import com.master.util.common.StringUtils;

/**
 * @author zhuwt
 * @Description: 主页的搜索
 * @ClassName: HomeSearchActivity.java
 * @date 2015年6月9日 上午11:17:19
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */

public class HomeSearchActivity extends BaseActivity implements OnClickListener {

	private Context mContext;

	private final String TAG = "HomeSearchActivity";

	// 显示搜索结果
	private final int UPDATE_DATA = 1;

	// 搜索
	private final int SERACH = 2;

	// 没有搜索数据时 请求推荐接口
	private final int NO_DATA = 3;

	// 取消
	private TextView cancel;

	// 搜索
	private CustomEditText search;

	// 没有搜索结果是 显示扫描二维码的功能界面
	private LinearLayout searchQr;

	private List<ExchangePojo> list = new ArrayList<ExchangePojo>();

	// 个人和企业
	private XListView person;
	private ExchangeCardAdapter mPersonAdapter;

	// 扫描二维码
	private HxBarcode hxBarcod;

	// 根据 名片号 名字 昵称 邮箱 工作 公司 电话 搜索
	private RelativeLayout card, nickName, mail, job, company, phone;

	// 及时显示 搜索大的内容
	private TextView cardContent, nameContent, nickNameContent, mailContent,
			jobContent, companyContent, phoneContent;

	private LinearLayout condition;

	// 没有搜索结果时 搜索内容
	private String content;
	// 搜索的条件选择 是否已经初始化
	private boolean flag = false;

	// 搜索类型
	private String type;

	// 搜索结果为企业时
	private List<CompanyPojo> comList = new ArrayList<CompanyPojo>();
	private SearchCompanyAdapter mCompanyAdapter;

	// 1 搜索结果为本地 名片 2 搜索结果为服务端 名片 3 搜索结果为服务端 企业
	private int local;

	private LinearLayout noDate;

	private int pageNO = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_search_layout_2);
		mContext = this;
		initView();
		initClick();
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				CheckStringUtils.openKeybord(search, mContext);
			}
		}, 500);
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 初始化控件
	 * @Date:2015年6月9日下午3:06:25
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initView() {
		card = (RelativeLayout) findViewById(R.id.exchange_search_card);
		nickName = (RelativeLayout) findViewById(R.id.exchange_search_nickname);
		job = (RelativeLayout) findViewById(R.id.exchange_search_job);
		company = (RelativeLayout) findViewById(R.id.exchange_search_organization);
		phone = (RelativeLayout) findViewById(R.id.exchange_search_phone);
		mail = (RelativeLayout) findViewById(R.id.exchange_search_mail);
		card.setOnClickListener(this);
		nickName.setOnClickListener(this);
		job.setOnClickListener(this);
		company.setOnClickListener(this);
		phone.setOnClickListener(this);
		phone.setOnClickListener(this);
		mail.setOnClickListener(this);
		condition = (LinearLayout) findViewById(R.id.condition);
		person = (XListView) findViewById(R.id.person_listview);
		person.setPullLoadEnable(true);
		person.setPullRefreshEnable(false);
		cardContent = (TextView) findViewById(R.id.exchange_search_card_content);
		nameContent = (TextView) findViewById(R.id.exchange_search_name_content);
		nickNameContent = (TextView) findViewById(R.id.exchange_search_nickname_content);
		phoneContent = (TextView) findViewById(R.id.exchange_search_phone_content);
		companyContent = (TextView) findViewById(R.id.exchange_search_organization_content);
		jobContent = (TextView) findViewById(R.id.exchange_search_job_content);
		mailContent = (TextView) findViewById(R.id.exchange_search_mail_content);

		cancel = (TextView) findViewById(R.id.home_search_cancel);
		search = (CustomEditText) findViewById(R.id.home_search);
		search.addTextChangedListener(new HomeSearchtextWatcher());
		searchQr = (LinearLayout) findViewById(R.id.home_search_qr);
		noDate = (LinearLayout) findViewById(R.id.show_date);
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 点击事件
	 * @Date:2015年6月9日下午3:07:14
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initClick() {

		person.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {

			}

			@Override
			public void onLoadMore() {
				pageNO++;
				mHandler.sendEmptyMessageDelayed(SERACH, 1000);
			}
		});
		// 扫描二维码
		searchQr.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				CheckStringUtils.closeKeybord(search, mContext);
				hxBarcod = new HxBarcode();
				// 扫描二维码 520 表示返回参数
				hxBarcod.scan(mContext, 520, false);
			}
		});

		// 搜索
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		// person.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
		// long arg3) {
		// if (2 == local) {
		// Intent mIntent = new Intent(mContext, PersonDetails.class);
		// mIntent.putExtra("cardId", list.get(arg2 - 1).getCardId());
		// startActivity(mIntent);
		// }
		// if (1 == local) {
		// return;
		// }
		// if (3 == local) {
		// Intent mIntent = new Intent(mContext, CompanyDetails.class);
		// mIntent.putExtra("entId", comList.get(arg2 - 1).getEntId());
		// startActivity(mIntent);
		// }
		// }
		// });
	}

	/**
	 * 
	 * @author zhuwt
	 * @Description: 搜索监听类
	 * @ClassName: HomeSearchtextWatcher.java
	 * @date 2015年6月9日 下午5:45:34
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private class HomeSearchtextWatcher implements TextWatcher {

		@Override
		public void afterTextChanged(Editable arg0) {
			String str = arg0.toString();
			if (StringUtils.isNotEmpty(str)) {
				person.setVisibility(View.GONE);
				list.clear();
				noDate.setVisibility(View.GONE);
				Message msg = new Message();
				msg.what = UPDATE_DATA;
				msg.obj = str;
				mHandler.sendMessage(msg);
			} else {
				// 没有数据 显示扫描二维码的页面
				flag = false;
				job.setVisibility(View.GONE);
				noDate.setVisibility(View.GONE);
				condition.setVisibility(View.GONE);
				nickName.setVisibility(View.GONE);
				phone.setVisibility(View.GONE);
				card.setVisibility(View.GONE);
				mail.setVisibility(View.GONE);
				company.setVisibility(View.GONE);
				if (null != person) {
					person.setVisibility(View.GONE);
				}
				searchQr.setVisibility(View.VISIBLE);
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

	/**
	 * @Auther: zhuwt
	 * @Description: 根据搜索内容搜索
	 * @Date:2015年6月9日下午5:46:44
	 * @param str
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private synchronized void updateDate(final String str) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				list = HomeDataHandler.getInstance().queryByCondition(
						str);
				if (null != list) {
					if (list.size() > 0) {
						person.setVisibility(View.VISIBLE);
						if (searchQr.getVisibility() == View.VISIBLE) {
							searchQr.setVisibility(View.GONE);
						}
						if(list.size() >10){
							person.setPullLoadEnable(true);
						}else{
							person.setPullLoadEnable(false);
						}
						if (local != 1) {
							local = 1;
							mPersonAdapter = new ExchangeCardAdapter(mContext,
									list, 1);
							person.setAdapter(mPersonAdapter);
						} else {
							if (searchQr.getVisibility() == View.VISIBLE) {
								searchQr.setVisibility(View.GONE);
							}
							if (null != mPersonAdapter) {
								mPersonAdapter.setData(list,local);
							} else {
								mPersonAdapter = new ExchangeCardAdapter(
										mContext, list, 2);
								person.setAdapter(mPersonAdapter);
							}
						}
						// 清空搜索结果
						flag = false;
						job.setVisibility(View.GONE);
						condition.setVisibility(View.GONE);
						nickName.setVisibility(View.GONE);
						phone.setVisibility(View.GONE);
						card.setVisibility(View.GONE);
						mail.setVisibility(View.GONE);
						company.setVisibility(View.GONE);
						return;
					}
				}
				// 没有数据 选择去服务器搜索
				// 获取搜索内容首字母
				String headStr = str.substring(0, 1);
				content = str;
				// 隐藏默认的背景 显示搜索条件选择
				condition.setVisibility(View.VISIBLE);
				// 如果首字母是字母 满足的条件是企业 名称 昵称 职务
				if (StringUtils.onlyCH(headStr)) {
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
				if (StringUtils.onlyAZ(headStr)) {
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
				if (StringUtils.onlyNum(headStr)) {
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

			}
		});
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			// 搜索 并显示搜索结果
			case UPDATE_DATA:
				String condition = (String) msg.obj;
				updateDate(condition);
				break;
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

	@Override
	protected void onActivityResult(final int requestCode, int resultCode,
			final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (520 == requestCode) {
					if (null == data) {
						return;
					}
					Bundle extras = data.getBundleExtra("data");
					if (null != extras) {
						String result = extras.getString("result");
						if (StringUtils.isNotEmpty(result)) {
							if (result.indexOf("=") > 0) {
								String strs = result.substring(result.indexOf("=")+1, result.length());
								Intent mIntent = new Intent(mContext,
										ScanDetailActivity.class);
								mIntent.putExtra("cardId", strs);
								startActivity(mIntent);
							}
						}
					}
					return;
				}
			}
		});
	}

	@Override
	public void onClick(View arg0) {
		CheckStringUtils.closeKeybord(search, mContext);
		switch (arg0.getId()) {
		// 名片号
		case R.id.exchange_search_card:
			list.clear();
			pageNO = 1;
			person.setVisibility(View.VISIBLE);
			condition.setVisibility(View.GONE);
			type = "cardId";
			searchQr.setVisibility(View.GONE);
			mHandler.sendEmptyMessage(SERACH);
			break;
		// 部门
		case R.id.exchange_search_job:
			type = "position";
			list.clear();
			pageNO = 1;
			person.setVisibility(View.VISIBLE);
			condition.setVisibility(View.GONE);
			searchQr.setVisibility(View.GONE);
			mHandler.sendEmptyMessage(SERACH);
			break;
		case R.id.exchange_search_nickname:
			type = "nickName";
			list.clear();
			pageNO = 1;
			person.setVisibility(View.VISIBLE);
			condition.setVisibility(View.GONE);
			searchQr.setVisibility(View.GONE);
			mHandler.sendEmptyMessage(SERACH);
			break;
		// 电话
		case R.id.exchange_search_phone:
			type = "mobile";
			list.clear();
			pageNO = 1;
			person.setVisibility(View.VISIBLE);
			condition.setVisibility(View.GONE);
			searchQr.setVisibility(View.GONE);
			mHandler.sendEmptyMessage(SERACH);
			break;
		// 机构
		case R.id.exchange_search_organization:
			comList.clear();
			type = "orgName";
			pageNO = 1;
			person.setVisibility(View.VISIBLE);
			condition.setVisibility(View.GONE);
			searchQr.setVisibility(View.GONE);
			mHandler.sendEmptyMessage(SERACH);
			break;
		// 邮箱
		case R.id.exchange_search_mail:
			list.clear();
			type = "email";
			pageNO = 1;
			person.setVisibility(View.VISIBLE);
			condition.setVisibility(View.GONE);
			searchQr.setVisibility(View.GONE);
			mHandler.sendEmptyMessage(SERACH);
			break;

		default:
			break;
		}
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 搜索
	 * @Date:2015年7月1日上午11:43:29
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void forSearch() {
		ExchangeDataHandler.getInstance(mContext).exchangeSerch(pageNO, type,
				content, new AXCallBack() {
					public void onCallBackString(final String retStr) {
						runOnUiThread(new Runnable() {
							public void run() {
								person.stopLoadMore();
								if (StringUtils.isNotEmpty(retStr)) {
									String page = GsonUtils.getJsonValue(
											retStr, "page");
									pageNO = Integer.parseInt(GsonUtils
											.getJsonValue(page, "pageNo"));
									if (retStr.indexOf("list") < 0 && pageNO <= 1) {
										searchQr.setVisibility(View.GONE);
										noDate.setVisibility(View.VISIBLE);
										person.setVisibility(View.GONE);
										list.clear();
										comList.clear();
										mHandler.sendEmptyMessage(NO_DATA);
										return;
									}else{
										noDate.setVisibility(View.GONE);
										person.setVisibility(View.VISIBLE);
									}
									if (retStr.indexOf("list") < 0){
										return;
									}
									String str = GsonUtils.getJsonValue(retStr,
											"list");
									if (!StringUtils.isNotEmpty(str)) {
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
													person.stopLoadMore();
													person.setPullLoadEnable(true);
												}else{
													person.stopLoadMore();
													person.setPullLoadEnable(false);
												}
												local = 3;
												person.setVisibility(View.VISIBLE);
												if (null == mCompanyAdapter) {
													mCompanyAdapter = new SearchCompanyAdapter(
															mContext, data);
													comList = data;
													person.setAdapter(mCompanyAdapter);
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
													person.stopLoadMore();
													person.setPullLoadEnable(true);
												}else{
													person.stopLoadMore();
													person.setPullLoadEnable(false);
												}
												person.setVisibility(View.VISIBLE);
												local = 2;
												if (null == mPersonAdapter) {
													mPersonAdapter = new ExchangeCardAdapter(
															mContext, data, 2);
													list = data;
													person.setAdapter(mPersonAdapter);
												} else {
													list.addAll(data);
													mPersonAdapter
															.setData(list,local);
												}

											}
										}
									}
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
//						noDate.setVisibility(View.VISIBLE);
						if (StringUtils.isNotEmpty(retStr)) {
							if (retStr.indexOf("list") < 0) {
								person.setVisibility(View.GONE);
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
									local = 1;
									person.setVisibility(View.VISIBLE);
									if (null == mPersonAdapter) {
										mPersonAdapter = new ExchangeCardAdapter(
												mContext, data, 1);
										person.setAdapter(mPersonAdapter);
										return;
									} else {
										mPersonAdapter.setData(data,local);
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
		super.finish();
		CheckStringUtils.closeKeybord(search, mContext);
	}

}

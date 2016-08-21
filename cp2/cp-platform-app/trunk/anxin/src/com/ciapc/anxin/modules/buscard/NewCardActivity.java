package com.ciapc.anxin.modules.buscard;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.BaseActivity;
import com.ciapc.anxin.common.GlobalConstants;
import com.ciapc.anxin.common.callback.AXCallBack;
import com.ciapc.anxin.common.pojo.ExchangePojo;
import com.ciapc.anxin.common.view.PubTitle;
import com.ciapc.anxin.common.view.PubTitle.OnTitleClickListener;
import com.ciapc.anxin.modules.home.HomeSharedPreferences;
import com.ciapc.anxin.utils.ListView.XListView;
import com.ciapc.anxin.utils.ListView.XListView.IXListViewListener;
import com.google.gson.reflect.TypeToken;
import com.master.util.common.DialogUtil;
import com.master.util.common.GsonUtils;
import com.master.util.common.StringUtils;
import com.master.util.common.SystemUtils;

/**
 * @author zhuwt
 * @Description:新名片
 * @ClassName: NewCardActivity.java
 * @date 2015年6月4日 下午3:21:04
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */

public class NewCardActivity extends BaseActivity {

	private final String TAG = "NewCardActivity";
	// 初始化数据
	private final int INIT_DATA = 1;

	// 加载更多
	private final int LOAD_MORE = 3;

	// 刷新
	private final int REFRESH = 5;

	private Context mContext;

	private XListView listView;

	private PubTitle pubTitle;

	private List<ExchangePojo> list = new ArrayList<ExchangePojo>();
	private NewCardAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_card_layout);
		mContext = this;
		initView();
		initClick();
		if (!SystemUtils.isNetWorkConnected(mContext)) {
			DialogUtil.showSystemToast(mContext, "网络异常 请检查您的网络状态");
			return;
		}
		// 清除新消息记录
		HomeSharedPreferences.setNewCardUnRead(mContext, "0");
		HomeSharedPreferences.setNewCardUnReadContent(mContext, "");
		mHandler.sendEmptyMessage(INIT_DATA);
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 初始化控件
	 * @Date:2015年6月4日下午3:31:41
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initView() {
		listView = (XListView) findViewById(R.id.new_card_listview);
		listView.setPullRefreshEnable(false);
		pubTitle = (PubTitle) findViewById(R.id.new_card_title);
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 点击事件
	 * @Date:2015年6月4日下午3:31:57
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

		listView.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
//				mHandler.sendEmptyMessageDelayed(REFRESH, 1000);
			}

			@Override
			public void onLoadMore() {
				mHandler.sendEmptyMessageDelayed(LOAD_MORE, 1000);
			}
		});
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 初始化数据
	 * @Date:2015年6月8日上午10:52:33
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initData() {
		NewCardDateHandler.getInstance(mContext).getData("0", "all",
				new AXCallBack() {

					@Override
					public void onCallBackString(String retStr) {
						super.onCallBackString(retStr);
						// 返回成功
						if (StringUtils.isNotEmpty(retStr)) {
							// 返回成功
							if (GlobalConstants.HTTP_SUCCESS_CODE
									.equals(GsonUtils.getJsonValue(retStr,
											GlobalConstants.HTTP_CODE))) {
								if (retStr.indexOf("list") < 0) {
									listView.setVisibility(View.GONE);
									return;
								}
								listView.setVisibility(View.VISIBLE);
								String str = GsonUtils.getJsonValue(retStr,
										"list");
								if (StringUtils.isNotEmpty(str)) {
									list = GsonUtils
											.toObjectList(
													str,
													new TypeToken<List<ExchangePojo>>() {
													}.getType());
									if(list.size() >=10){
										listView.setPullLoadEnable(true);
									}else{
										listView.setPullLoadEnable(false);
									}
									adapter = new NewCardAdapter(mContext, list);
									listView.setAdapter(adapter);
								}
							} else {
								DialogUtil.showSystemToast(mContext, GsonUtils
										.getJsonValue(retStr,
												GlobalConstants.HTTP_MSG));
							}
						} else {
							DialogUtil.showSystemToast(mContext, "出错了，请稍后再试!");
						}
					}
				});
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case INIT_DATA:
				initData();
				break;
			case LOAD_MORE:
				loadDate(list.size());
				break;
			case REFRESH:
				loadDate(0);
				break;
			default:
				break;
			}
		};
	};

	/**
	 * @Auther: zhuwt
	 * @Description: 加载数据
	 * @Date:2015年7月3日上午11:39:32
	 * @param start
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void loadDate(final int index) {
		NewCardDateHandler.getInstance(mContext).getData(index + "", "all",
				new AXCallBack() {

					@Override
					public void onCallBackString(final String retStr) {
						super.onCallBackString(retStr);
						runOnUiThread(new Runnable() {
							public void run() {
								// 返回成功
								if (StringUtils.isNotEmpty(retStr)) {
									if (GlobalConstants.HTTP_SUCCESS_CODE
											.equals(GsonUtils.getJsonValue(
													retStr,
													GlobalConstants.HTTP_CODE))) {
										if (retStr.indexOf("list") < 0) {
											listView.stopLoadMore();
											listView.setPullLoadEnable(false);
											return;
										}
										String str = GsonUtils.getJsonValue(
												retStr, "list");
										if (!StringUtils.isNotEmpty(str)) {
											listView.stopLoadMore();
											return;
										}
										List<ExchangePojo> data = new ArrayList<ExchangePojo>();
										data = GsonUtils
												.toObjectList(
														str,
														new TypeToken<List<ExchangePojo>>() {
														}.getType());
										// 重新刷新数据
										if (0 == index) {
											list = data;
										} else {
											// 加载更多
											list.addAll(data);
										}
										if(list.size() >=10){
											listView.stopLoadMore();
											listView.setPullLoadEnable(true);
										}else{
											listView.stopLoadMore();
											listView.setPullLoadEnable(false);
										}
										adapter.setDate(list);
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
											"出错了，请稍后再试!");
								}
							}
						});
					}
				});

	}
}

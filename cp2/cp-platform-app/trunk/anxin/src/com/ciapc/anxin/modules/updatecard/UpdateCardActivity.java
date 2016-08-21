package com.ciapc.anxin.modules.updatecard;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.BaseActivity;
import com.ciapc.anxin.common.callback.AXCallBack;
import com.ciapc.anxin.common.pojo.UpdatePojo;
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

public class UpdateCardActivity extends BaseActivity {

	private Context mContext;

	private XListView listView;
	private UpdateCardAdapter adapter;

	private PubTitle pubTitle;

	List<UpdatePojo> list = new ArrayList<UpdatePojo>();

	// 初始化数据
	private final int INIT_DATA = 1;

	// 加载数据
	private final int LOAD_DATE = 2;

	private int start;

	private String time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update_card_layout);
		mContext = this;
		initView();
		initClick();
		if (!SystemUtils.isNetWorkConnected(mContext)) {
			DialogUtil.showSystemToast(mContext, "网络异常 请检查您的网络状态");
			return;
		}
		// 清除新消息记录
		HomeSharedPreferences.setUpdateUnRead(mContext, "0");
		HomeSharedPreferences.setUpdateUnReadContent(mContext, "");
		mHandler.sendEmptyMessage(INIT_DATA);
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 初始化数据
	 * @Date:2015年6月16日下午6:22:47
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initData() {
		UpdateHandler.getInstance(mContext).getUpdate(0, "", new AXCallBack() {
			@Override
			public void onCallBackString(String retStr) {
				super.onCallBackString(retStr);
				if (StringUtils.isNotEmpty(retStr)) {
					if (retStr.indexOf("list") < 0) {
						return;
					}
					String data = GsonUtils.getJsonValue(retStr, "list");
					if (!StringUtils.isNotEmpty(data)) {
						return;
					}
					list = GsonUtils.toObjectList(data,
							new TypeToken<List<UpdatePojo>>() {
							}.getType());
					if(list.size() > 0 && list.size() < 10){
						listView.setPullLoadEnable(false);
					}else{
						listView.setPullLoadEnable(true);
					}
					time = list.get(list.size() - 1).getDynamicTime();
					adapter = new UpdateCardAdapter(mContext, list);
					listView.setAdapter(adapter);
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

		listView.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {

			}

			@Override
			public void onLoadMore() {
				mHandler.sendEmptyMessageAtTime(LOAD_DATE, 1000);
			}
		});
	}

	private void initView() {
		listView = (XListView) findViewById(R.id.list_view);
		listView.setPullRefreshEnable(false);
//		listView.setPullLoadEnable(true);
		pubTitle = (PubTitle) findViewById(R.id.update_title);
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			// 初始化数据
			case INIT_DATA:
				initData();
				break;
			// 加载更多
			case LOAD_DATE:
				String time = list.get(start).getDynamicTime();
				loadDate(time);
				break;

			default:

				break;
			}
		};
	};

	/**
	 * @Auther: zhuwt
	 * @Description: 加载数据
	 * @Date:2015年7月3日下午2:59:14
	 * @param start
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void loadDate(String str) {
		UpdateHandler.getInstance(mContext).getUpdate(0, str, new AXCallBack() {
			@Override
			public void onCallBackString(final String retStr) {
				super.onCallBackString(retStr);
				runOnUiThread(new Runnable() {
					public void run() {
						if (StringUtils.isNotEmpty(retStr)) {
							if (retStr.indexOf("list") < 0) {
								listView.stopLoadMore();
								return;
							}
							String data = GsonUtils
									.getJsonValue(retStr, "list");
							if (!StringUtils.isNotEmpty(data)) {
								listView.stopLoadMore();
								return;
							}
							List<UpdatePojo> update = GsonUtils.toObjectList(
									data, new TypeToken<List<UpdatePojo>>() {
									}.getType());
							if(update.size() > 0 && update.size() < 10){
								listView.stopLoadMore();
								listView.setPullLoadEnable(false);
							}else{
								listView.stopLoadMore();
								listView.setPullLoadEnable(true);
							}
							list.addAll(update);
							time = list.get(list.size() - 1).getDynamicTime();
							adapter.setDate(list);
						} else {
							listView.stopLoadMore();
							DialogUtil.showSystemToast(mContext, "网络错误,请稍后再试");
						}
					}
				});
			}
		});
	}
}

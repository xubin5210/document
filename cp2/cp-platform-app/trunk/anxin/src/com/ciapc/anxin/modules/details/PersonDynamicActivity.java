package com.ciapc.anxin.modules.details;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.BaseActivity;
import com.ciapc.anxin.common.GlobalConstants;
import com.ciapc.anxin.common.callback.AXCallBack;
import com.ciapc.anxin.common.pojo.PersonUpdatePojo;
import com.ciapc.anxin.common.view.AxListView;
import com.ciapc.anxin.common.view.PubTitle;
import com.ciapc.anxin.common.view.PubTitle.OnTitleClickListener;
import com.ciapc.anxin.common.view.RoundView;
import com.google.gson.reflect.TypeToken;
import com.master.util.common.DialogUtil;
import com.master.util.common.GsonUtils;
import com.master.util.common.StringUtils;
import com.master.util.common.SystemUtils;
import com.master.util.image.MasterImg;

/**
 * 
 * @author zhuwt
 * @Description: 个人动态
 * @ClassName: PersonDynamicActivity.java
 * @date 2015年7月5日 下午2:27:01
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */
public class PersonDynamicActivity extends BaseActivity {

	private Context mContext;

	private PubTitle pubTitle;

	// 真实姓名 昵称 名片号
	private TextView trueName, nickName, cardId;
	private String name, nick, id, iconUrl;

	private RoundView icon;

	private List<PersonUpdatePojo> list = new ArrayList<PersonUpdatePojo>();
	private AxListView listView;
	private PersonUpdateAdapter adapter;

	private TextView noData;

	// 初始化数据
	private final int INIT_DATE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_dynamic);
		mContext = this;
		initView();
		initClick();
		if (!SystemUtils.isNetWorkConnected(mContext)) {
			DialogUtil.showSystemToast(mContext, "网络异常 请检查您的网络状态");
			return;
		}
		mHandler.sendEmptyMessage(INIT_DATE);
	}

	private void initView() {
		pubTitle = (PubTitle) findViewById(R.id.dynamic_title);
		trueName = (TextView) findViewById(R.id.true_name);
		nickName = (TextView) findViewById(R.id.nickname_card);
		cardId = (TextView) findViewById(R.id.visiting_card);
		listView = (AxListView) findViewById(R.id.dynamic_listview);
		icon = (RoundView) findViewById(R.id.home_person_head);
		noData = (TextView) findViewById(R.id.no_data);
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

	private void initDate() {
		name = getIntent().getStringExtra("trueName");
		if (StringUtils.isNotEmpty(name)) {
			trueName.setText(name);
		} else {
			trueName.setText("无名氏");
		}
		nick = getIntent().getStringExtra("nickName");
		if (StringUtils.isNotEmpty(nick)) {
			nickName.setText("昵称：" + nick);
		} else {
			nickName.setText("昵称：未填写");
		}
		id = getIntent().getStringExtra("cardId");
		iconUrl = getIntent().getStringExtra("iconUrl");
		if (StringUtils.isNotEmpty(iconUrl) && !"null".equals(iconUrl)) {
			icon.setTag(iconUrl);
			MasterImg.getInstance(mContext).loadImg(iconUrl, icon, true, true,
					0, R.drawable.toux_default);
		}
		cardId.setText("名片号：" + id);
		DetailsDataHandler.getInstance(mContext).getPersonDynamci(id,
				new AXCallBack() {
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
										String data = GsonUtils.getJsonValue(
												retStr, "data");
										if (data.indexOf("dyList") < 0) {
											noData.setText("无名片更新记录");
											return;
										}
										String listData = GsonUtils
												.getJsonValue(data, "dyList");
										list = GsonUtils
												.toObjectList(
														listData,
														new TypeToken<List<PersonUpdatePojo>>() {
														}.getType());
										if(list.size() <= 0){
											noData.setText("无名片更新记录");
											return;
										}else{
											noData.setText("以上信息已自动完成更新");
										}
										adapter = new PersonUpdateAdapter(
												mContext, list);
										listView.setAdapter(adapter);
									} else {
										DialogUtil
												.showSystemToast(
														mContext,
														GsonUtils
																.getJsonValue(
																		retStr,
																		GlobalConstants.HTTP_MSG));
										noData.setText("无名片更新记录");
									}
								} else {
									noData.setText("无名片更新记录");
									DialogUtil.showSystemToast(mContext,
											"网络异常,请稍后再试！");
								}
							}
						});
					}
				});
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

package com.ciapc.anxin.modules.home;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.ciapc.anxin.R;
import com.ciapc.anxin.common.AXSharedPreferences;
import com.ciapc.anxin.common.BaseActivity;
import com.ciapc.anxin.common.GlobalConstants;
import com.ciapc.anxin.common.callback.AXCallBack;
import com.ciapc.anxin.common.pojo.ExchangePojo;
import com.ciapc.anxin.common.view.AxinDialog;
import com.ciapc.anxin.common.view.AxinDialog.AxDialogClickListen;
import com.ciapc.anxin.common.view.DragLayout;
import com.ciapc.anxin.common.view.DragLayout.DragListener;
import com.ciapc.anxin.common.view.RoundView;
import com.ciapc.anxin.modules.buscard.NewCardActivity;
import com.ciapc.anxin.modules.buscard.NewCardDateHandler;
import com.ciapc.anxin.modules.exchange.ExchangeCardActivity;
import com.ciapc.anxin.modules.perfect.PerfectInfoActivity;
import com.ciapc.anxin.modules.perfect.PersonActivity;
import com.ciapc.anxin.modules.search.HomeSearchActivity;
import com.ciapc.anxin.modules.setting.person.PersonQianminActivity;
import com.ciapc.anxin.modules.updatecard.UpdateCardActivity;
import com.ciapc.anxin.utils.ListView.XListView;
import com.ciapc.anxin.utils.ListView.XListView.IXListViewListener;
import com.google.gson.reflect.TypeToken;
import com.igexin.sdk.PushManager;
import com.master.util.common.DialogUtil;
import com.master.util.common.GsonUtils;
import com.master.util.common.QRCodeUtils;
import com.master.util.common.StringUtils;
import com.master.util.image.MasterImg;

/**
 * 
 * @author zhuwt
 * @Description: 主页
 * @ClassName: HomeActivity.java
 * @date 2015年5月26日 上午10:11:46
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */
public class HomeActivity extends BaseActivity {

	public static final String TAG = "HomeActivity";

	// 初始化数据
	private final int INIT_DATA = 1;

	// 批量插入数据库
	private final int BATCH_CARD = 2;

	// 加载更多
	private final int LOAD_MORE = 3;

	// 刷新
	private final int REFRESH = 5;

	private final int PERFECT = 7;

	// 刷新数据
	private final int REFRESH_LISTVIEW = 6;

	private Context mContext;

	private DragLayout mDragLayout;// 左侧菜单栏

	// 标题栏
	private RelativeLayout title;

	// 主页名片
	private XListView listView;
	private static HomeAdapter adapter;
	private List<ExchangePojo> list = new ArrayList<ExchangePojo>();

	// 左侧菜单
	private ListView listViewLeft;
	private List<String> listLeft = new ArrayList<String>();
	private HomeLeftAdapter leftAdapter;

	private View home_new;
	private View home_update;

	// 新名片更新提示
	private TextView newCardContent;
	// 名片更新提示
	private TextView updateContent;

	// 新名片未读个数 更新未读个数
	private Button newPoint, updatePoint;

	// 去交换名片
	private ImageView toExchange;

	// 主页搜索
	private RelativeLayout search;

	// 搜索动画的移动坐标和高度
	private int height;

	// 主页头像 左侧菜单栏头像
	private RoundView headRight, headLeft;

	// 昵称 名片ID 签名
	private TextView nickNmae, cardId, signature;

	// 二维码
	private LinearLayout qR;

	// 测试
	public static boolean isFirst;

	// 二维码显示的宽高
	private int qrW;

	private RelativeLayout personTitle;
	// 用户两次点击时间
	private long quitTime = 0;

	private List<ExchangePojo> insertList = new ArrayList<ExchangePojo>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_layout);
		mContext = this;
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		float scale = mContext.getResources().getDisplayMetrics().density;
		qrW = (int) (290.0 * scale + 0.5f);
		// 个推初始化
		PushManager.getInstance().initialize(this.getApplicationContext());
		initView();
		initClick();
		changePerfect();
	}

	@Override
	public void onResume() {
		super.onResume();
		// 刷新界面
		mHandler.sendEmptyMessage(INIT_DATA);
		// mHandler.sendEmptyMessage(REFRESH_LISTVIEW);
	}

	/**
	 * @Auther: zhuwt
	 * @Description:初始化数据
	 * @Date:2015年6月3日下午1:46:05
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initData() {
		// 查询本地数据
		list = HomeDataHandler.getInstance().queryCard(0, 10);
		if (list.size() > 0) {
			if (list.size() < 10) {
				listView.setPullLoadEnable(false);
			} else {
				listView.setPullLoadEnable(true);
			}
			listView.setVisibility(View.VISIBLE);
			if (StringUtils.isNotEmpty(HomeSharedPreferences
					.getNewCardUnReadContent(mContext))) {
				if (!HomeSharedPreferences.getNewRemind(mContext)) {
					listView.addHeaderView(home_new);
					HomeSharedPreferences.setNewRemind(mContext, true);
				}
			}
			if (StringUtils.isNotEmpty(HomeSharedPreferences
					.getUpdateUnReadContent(mContext))) {
				if (!HomeSharedPreferences.getUpdateRemind(mContext)) {
					listView.addHeaderView(home_update);
					HomeSharedPreferences.setUpdateRemind(mContext, true);
				}
			}
			adapter = new HomeAdapter(mContext, list);
			listView.setAdapter(adapter);
			adapter.setData(list);
			mHandler.sendEmptyMessage(REFRESH_LISTVIEW);
			return;
		}
		// 没有则去服务端获取数据
		NewCardDateHandler.getInstance(mContext).getData("0", "exchange",
				new AXCallBack() {
					@Override
					public void onCallBackString(final String retStr) {
						super.onCallBackString(retStr);
						runOnUiThread(new Runnable() {
							public void run() {
								if (StringUtils.isNotEmpty(retStr)) {
									// 返回成功
									if (GlobalConstants.HTTP_SUCCESS_CODE
											.equals(GsonUtils.getJsonValue(
													retStr,
													GlobalConstants.HTTP_CODE))) {
										// 没有数据
										if (retStr.indexOf("list") > 0) {
											// 有数据 则使用服务端的数据
											String str = GsonUtils
													.getJsonValue(retStr,
															"list");
											if (!StringUtils.isNotEmpty(str)) {
												listView.setVisibility(View.GONE);
												return;
											}
											list = GsonUtils
													.toObjectList(
															str,
															new TypeToken<List<ExchangePojo>>() {
															}.getType());
											if (list.size() < 10
													&& list.size() > 0) {
												listView.setPullLoadEnable(false);
											} else {
												listView.setPullLoadEnable(true);
											}
											listView.setVisibility(View.VISIBLE);
											insertList = list;
											// 插入数据库
											mHandler.sendEmptyMessage(BATCH_CARD);
										} else {
											listView.setVisibility(View.GONE);
										}
										if (StringUtils
												.isNotEmpty(HomeSharedPreferences
														.getNewCardUnReadContent(mContext))) {
											if (!HomeSharedPreferences
													.getNewRemind(mContext)) {
												listView.addHeaderView(home_new);
												HomeSharedPreferences
														.setNewRemind(mContext,
																true);
											}
										}
										if (StringUtils
												.isNotEmpty(HomeSharedPreferences
														.getUpdateUnReadContent(mContext))) {
											if (!HomeSharedPreferences
													.getUpdateRemind(mContext)) {
												listView.addHeaderView(home_update);
												HomeSharedPreferences
														.setUpdateRemind(
																mContext, true);
											}
										}
										adapter = new HomeAdapter(mContext,
												list);
										listView.setAdapter(adapter);
										mHandler.sendEmptyMessage(REFRESH_LISTVIEW);
									} else {
										listView.setVisibility(View.GONE);
									}
								} else {
									listView.setVisibility(View.GONE);
								}
							}
						});
					}
				});
	}

	/**
	 * @Auther: zhuw
	 * @Description: 控件初始化
	 * @Date:2015年5月26日上午10:25:07
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initView() {
		search = (RelativeLayout) findViewById(R.id.home_search);
		mDragLayout = (DragLayout) findViewById(R.id.dl);
		toExchange = (ImageView) findViewById(R.id.title_right_btn);
		home_new = LayoutInflater.from(mContext).inflate(
				R.layout.home_new_item, null);
		home_update = LayoutInflater.from(mContext).inflate(
				R.layout.home_update_item, null);
		newCardContent = (TextView) home_new
				.findViewById(R.id.new_card_content);
		newPoint = (Button) home_new.findViewById(R.id.new_card_red_point);
		updateContent = (TextView) home_update
				.findViewById(R.id.update_content);
		updatePoint = (Button) home_update.findViewById(R.id.update_red_point);
		listView = (XListView) findViewById(R.id.home_listview);
		listView.setPullRefreshEnable(false);
		listViewLeft = (ListView) findViewById(R.id.home_left_layout);
		leftAdapter = new HomeLeftAdapter(mContext, listLeft);
		listViewLeft.setAdapter(leftAdapter);
		title = (RelativeLayout) findViewById(R.id.home_title);
		headRight = (RoundView) findViewById(R.id.title_back_btn);
		nickNmae = (TextView) findViewById(R.id.nick_name);
		cardId = (TextView) findViewById(R.id.visiting_card);
		signature = (TextView) findViewById(R.id.signature);
		headLeft = (RoundView) findViewById(R.id.home_person_head);
		qR = (LinearLayout) findViewById(R.id.home_qr_code);
		personTitle = (RelativeLayout) findViewById(R.id.home_person_title);
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 初始化 点击事件
	 * @Date:2015年5月26日上午10:24:54
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initClick() {

		personTitle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if ("1".equals(AXSharedPreferences.getUserInfoStatus(mContext))) {
					mContext.startActivity(new Intent(mContext,
							PersonActivity.class));
				} else {
					mContext.startActivity(new Intent(mContext,
							PerfectInfoActivity.class));
				}
			}
		});

		home_update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(mContext, UpdateCardActivity.class));

			}
		});

		home_new.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(mContext, NewCardActivity.class));

			}
		});

		qR.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String url = GlobalConstants.SHARE
						+ HomeSharedPreferences.getCardId(mContext);
				Bitmap content = null;
				if (StringUtils.isNotEmpty(HomeSharedPreferences
						.getHeadPath(mContext))) {
					AQuery a = new AQuery(mContext);
					content = a.getCachedImage(HomeSharedPreferences
							.getHeadPath(mContext));
				} else {
					content = BitmapFactory.decodeResource(getResources(),
							R.drawable.toux_default);
				}
				Bitmap bitmap = QRCodeUtils.createQRCode(url, content, qrW,
						qrW, 30);
				new AxinDialog(mContext, AxinDialog.DIALOG_IMAGE)
						.setImg(bitmap).setImgClick(new AxDialogClickListen() {

							@Override
							public void onClick(AxinDialog axinDialog) {
								axinDialog.dismiss();

							}
						}).show();
			}
		});

		headLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if ("1".equals(AXSharedPreferences.getUserInfoStatus(mContext))) {
					mContext.startActivity(new Intent(mContext,
							PersonActivity.class));
				} else {
					mContext.startActivity(new Intent(mContext,
							PerfectInfoActivity.class));
				}
			}
		});

		// 头像
		headRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				mDragLayout.open();

			}
		});

		// 跳转到搜索
		search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				height = title.getHeight();
				// 移动的动画
				TranslateAnimation animation = new TranslateAnimation(0, 0, 0,
						-height);
				animation.setDuration(250);
				animation.setFillAfter(true);
				animation.setAnimationListener(new AnimationListener() {
					@Override
					public void onAnimationStart(Animation animation) {

					}

					@Override
					public void onAnimationRepeat(Animation animation) {

					}

					@Override
					public void onAnimationEnd(Animation animation) {
						Intent intent = new Intent();
						intent.setClass(mContext, HomeSearchActivity.class);
						startActivityForResult(intent, 100);
						overridePendingTransition(R.anim.contact_search_b,
								R.anim.contact_search_a);
					}
				});
				mDragLayout.startAnimation(animation);

			}
		});

		// 去交换名片
		toExchange.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(mContext, ExchangeCardActivity.class));
			}
		});

		// 跳转到详情
		// listView.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> arg0, View arg1,
		// int position, long arg3) {
		// int index = 1;
		// if (HomeSharedPreferences.getNewRemind(mContext)) {
		// index++;
		// }
		// if (HomeSharedPreferences.getUpdateRemind(mContext)) {
		// index++;
		// }
		// Intent mIntent = new Intent(mContext, PersonDetails.class);
		// int weizhi = position - index;
		// if (weizhi < 0) {
		// if (null != adapter) {
		// adapter.notifyDataSetChanged();
		// }
		// return;
		// }
		// mIntent.putExtra("cardId", list.get(weizhi).getCardId() + "");
		// startActivity(mIntent);
		// }
		// });

		// listview 上拉下拉事件
		listView.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
				// mHandler.sendEmptyMessageDelayed(REFRESH, 500);
			}

			@Override
			public void onLoadMore() {
				mHandler.sendEmptyMessageDelayed(LOAD_MORE, 1000);
			}
		});

		// 左侧滑动拦
		mDragLayout.setDragListener(new DragListener() {

			@Override
			public void onOpen() {
				leftAdapter.notifyDataSetChanged();
			}

			@Override
			public void onDrag(float percent) {
			}

			@Override
			public void onClose() {
			}
		});

		signature.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(mContext, PersonQianminActivity.class));
			}
		});
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(final android.os.Message msg) {
			switch (msg.what) {
			case PERFECT:

				break;

			// 刷新数据
			case REFRESH_LISTVIEW:
				refreshListview();
				break;

			case INIT_DATA:
				initData();
				break;

			// 刷新
			case LOAD_MORE:
				if (null != list) {
					updateDate(list.size());
				} else {
					listView.stopLoadMore();
				}
				break;

			// 刷新
			case REFRESH:
				list.clear();
				updateDate(0);
				break;

			// 批量插入
			case BATCH_CARD:
				// 开始批量插入数据
				new Thread(new Runnable() {

					@Override
					public void run() {
						HomeDataHandler.getInstance().batchInsertCard(
								insertList);
					}
				}).start();

				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (100 == requestCode) {
			TranslateAnimation animation = new TranslateAnimation(0, 0,
					-height, 0);
			animation.setDuration(500);
			animation.setFillAfter(true);
			mDragLayout.startAnimation(animation);
		}
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 加载更多 或者 刷新
	 * @Date:2015年6月9日下午5:25:23
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void updateDate(final int start) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// 获取本地数据
				List<ExchangePojo> data = HomeDataHandler.getInstance()
						.queryCard(start, 10);
				// 本地有数据
				if (0 < data.size()) {
					if (data.size() >= 10) {
						listView.setPullLoadEnable(true);
					} else {
						listView.setPullLoadEnable(false);
					}
					// 下拉重新刷新
					if (0 == start) {
						list = data;
						adapter.setData(list);
					} else {
						// 上拉加载更多
						list.addAll(data);
						adapter.setData(list);
					}
					listView.stopLoadMore();
					return;
				}
				// 本地没有数据 则去服务端获取
				NewCardDateHandler.getInstance(mContext).getData(start + "",
						"exchange", new AXCallBack() {
							@Override
							public void onCallBackString(final String retStr) {
								super.onCallBackString(retStr);
								runOnUiThread(new Runnable() {
									public void run() {
										if (StringUtils.isNotEmpty(retStr)) {
											if (GlobalConstants.HTTP_SUCCESS_CODE.equals(GsonUtils
													.getJsonValue(
															retStr,
															GlobalConstants.HTTP_CODE))) {
												// 没有数据
												if (retStr.indexOf("list") < 0) {
													// 停止刷新
													listView.stopLoadMore();
													listView.setPullLoadEnable(false);
													return;
												}
												// 有数据 则使用服务端的数据
												String str = GsonUtils
														.getJsonValue(retStr,
																"list");
												if (!StringUtils
														.isNotEmpty(str)) {
													// 停止刷新
													listView.stopLoadMore();
													return;
												}
												List<ExchangePojo> data = GsonUtils
														.toObjectList(
																str,
																new TypeToken<List<ExchangePojo>>() {
																}.getType());
												if (data.size() >= 10) {
													// 停止刷新
													listView.stopLoadMore();
													listView.setPullLoadEnable(true);
												} else {
													// 停止刷新
													listView.stopLoadMore();
													listView.setPullLoadEnable(false);
												}
												insertList = new ArrayList<ExchangePojo>();
												for (ExchangePojo pojo : data) {
													if (HomeDataHandler
															.getInstance()
															.searchCardId(
																	pojo.getCardId()
																			+ "")) {
														insertList.add(pojo);
													}
												}
												list.addAll(insertList);
												if (null != adapter) {
													adapter.setData(list);
												} else {
													adapter = new HomeAdapter(
															mContext, list);
													listView.setAdapter(adapter);
												}

												if (insertList.size() > 0) {
													mHandler.sendEmptyMessage(BATCH_CARD);
												}
											} else {
												// 停止刷新
												listView.stopLoadMore();
												DialogUtil.showSystemToast(
														mContext, "网络异常,请稍后再试");
											}
										} else {
											// 停止刷新
											listView.stopLoadMore();
											DialogUtil.showSystemToast(
													mContext, "网络异常,请稍后再试！");
										}
									}
								});
							}
						});
			}
		});
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 完善资料
	 * @Date:2015年6月18日下午3:42:07
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void changePerfect() {
		String str = AXSharedPreferences.getUserInfoStatus(mContext);
		// 未完善资料
		if ("2".equals(str)) {
			new AxinDialog(mContext, AxinDialog.DIALOG_CHANGE)
					.setConfirmStr("立即完善").setCancelStr("稍后再说")
					.setTitle("完善个人资料，挂靠实名单位，权威认证，将拓展你的人脉！")
					.setCancelClick(new AxDialogClickListen() {

						@Override
						public void onClick(AxinDialog axinDialog) {
							axinDialog.dismiss();

						}
					}).setConfirmClick(new AxDialogClickListen() {

						@Override
						public void onClick(AxinDialog axinDialog) {
							startActivity(new Intent(mContext,
									PerfectInfoActivity.class));
							axinDialog.dismiss();
						}
					}).show();
			return;
		}

		// 未完善资料 但已在企业名单里
		if ("3".equals(str)) {
			new AxinDialog(mContext, AxinDialog.DIALOG_CHANGE)
					.setConfirmStr("信息有误").setCancelStr("马上报道")
					.setTitle("经过组织研究决定！杭州安存网络科技有限公司已将你纳入麾下")
					.setCancelClick(new AxDialogClickListen() {

						@Override
						public void onClick(AxinDialog axinDialog) {
							axinDialog.dismiss();
							Message msg = new Message();
							msg.what = PERFECT;
							msg.obj = "0";
							mHandler.sendMessage(msg);
						}
					}).setConfirmClick(new AxDialogClickListen() {

						@Override
						public void onClick(AxinDialog axinDialog) {
							startActivity(new Intent(mContext,
									PerfectInfoActivity.class));
							axinDialog.dismiss();
							Message msg = new Message();
							msg.what = PERFECT;
							msg.obj = "0";
							mHandler.sendMessage(msg);
						}
					}).show();
		}

	}

	/**
	 * @Auther: zhuwt
	 * @Description:刷新listview数据
	 * @Date:2015年7月3日上午9:32:29
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void refreshListview() {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				String nick = HomeSharedPreferences.getNickName(mContext);
				String trueName = HomeSharedPreferences.getTrueName(mContext);
				if (StringUtils.isNotEmpty(trueName)) {
					if (StringUtils.isNotEmpty(nick)) {
						// 昵称
						nickNmae.setText(trueName + "(" + nick + ")");
					} else {
						nickNmae.setText(trueName);
					}
				} else {
					if (StringUtils.isNotEmpty(nick)) {
						nickNmae.setText(nick);
					} else {
						nickNmae.setText("无名氏");
					}
				}
				String qianm = HomeSharedPreferences.getSignature(mContext);
				if (StringUtils.isNotEmpty(qianm)
						&& !"这个家伙太懒了，什么都没写".equals(qianm)) {
					// 个性签名
					if (qianm.length() > 12) {
						signature.setText(qianm.substring(0, 12) + "...");
					} else {
						signature.setText(qianm);
					}
				} else {
					signature.setText("这个家伙太懒了，什么都没写");
				}
				// 名片号
				cardId.setText("名片号: "
						+ HomeSharedPreferences.getCardId(mContext));
				// 头像
				String path = HomeSharedPreferences.getHeadPath(mContext);
				if (StringUtils.isNotEmpty(path) && !"null".equals(path)) {
					headLeft.setTag(path);
					headRight.setTag(path);
					MasterImg.getInstance(mContext).loadImg(path, headLeft,
							true, true, 0, R.drawable.toux_default);
					MasterImg.getInstance(mContext).loadImg(path, headRight,
							true, true, 0, R.drawable.toux_default);
				}
				if (HomeSharedPreferences.getUpdateRemind(mContext)) {
					listView.setVisibility(View.VISIBLE);
					if (StringUtils.isNotEmpty(HomeSharedPreferences
							.getUpdateUnReadContent(mContext))) {
						if (home_update.getVisibility() != View.VISIBLE) {
							home_update.setPadding(0, home_update.getHeight(),
									0, 0);
							home_update.setVisibility(View.VISIBLE);
							updatePoint.setVisibility(View.VISIBLE);
						}
						String content = HomeSharedPreferences
								.getUpdateUnReadContent(mContext);
						if (StringUtils.isNotEmpty(content)) {
							if (content.indexOf(",") > -1) {
								updateContent.setText(content + "等名片有更新");
							} else {
								updateContent.setText(content + "名片有更新");
							}
						}
						updatePoint.setText(HomeSharedPreferences
								.getUpdateUnRead(mContext));
					} else {
						if (home_update.getVisibility() == View.VISIBLE) {
							home_update.setPadding(0,
									-1 * home_update.getHeight(), 0, 0);
							home_update.setVisibility(View.GONE);
							updatePoint.setVisibility(View.GONE);
						}
					}
				}
				// 是否有更新消息 有则添加新名片消息的Item
				if (HomeSharedPreferences.getNewRemind(mContext)) {
					listView.setVisibility(View.VISIBLE);
					if (StringUtils.isNotEmpty(HomeSharedPreferences
							.getNewCardUnReadContent(mContext))) {
						if (home_new.getVisibility() != View.VISIBLE) {
							home_new.setPadding(0, home_new.getHeight(), 0, 0);
							home_new.setVisibility(View.VISIBLE);
							newPoint.setVisibility(View.VISIBLE);
						}
						String content = HomeSharedPreferences
								.getNewCardUnReadContent(mContext);
						if (StringUtils.isNotEmpty(content)) {
							if (StringUtils.isNotEmpty(content)) {
								if (content.indexOf(",") > -1) {
									newCardContent.setText(content + "等递来新名片");
								} else {
									newCardContent.setText(content + "递来新名片");
								}
							}
						}
						newPoint.setText(HomeSharedPreferences
								.getNewCardUnRead(mContext));
					} else {
						if (home_new.getVisibility() == View.VISIBLE) {
							home_new.setPadding(0, -1 * home_new.getHeight(),
									0, 0);
							home_new.setVisibility(View.GONE);
							newPoint.setVisibility(View.GONE);
						}
					}
				}
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (!isFinishing()) {
				// 两次点击间隔大于2.5秒时，退出
				long nowTime = System.currentTimeMillis();
				if (quitTime == 0 || nowTime - quitTime > 2500) {
					Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
					quitTime = nowTime;
					return true;
				} else {
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_MAIN);
					intent.addCategory(Intent.CATEGORY_HOME);
					startActivity(intent);
				}
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 完善资料
	 * @Date:2015年7月27日下午4:17:49
	 * @param type
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void perfectData(final String type) {
		HomeDataHandler.getInstance().perfectFlag(type,
				new AXCallBack() {
					@Override
					public void onCallBackString(String retStr) {
						super.onCallBackString(retStr);
						if (StringUtils.isNotEmpty(retStr)) {
							if (GlobalConstants.HTTP_SUCCESS_CODE
									.equals(GsonUtils.getJsonValue(retStr,
											GlobalConstants.HTTP_CODE))) {
								// 确定
								if ("1".equals(type)) {
									AXSharedPreferences.setEntStatus(mContext,
											"1");
									AXSharedPreferences.setUserInfoStatus(
											mContext, "1");
									startActivity(new Intent(mContext,
											PersonActivity.class));
									return;
								}

								// 取消
								if ("0".equals(type)) {
									AXSharedPreferences.setUserInfoStatus(
											mContext, "2");
									AXSharedPreferences.setEntId(mContext, "");
									AXSharedPreferences.setEntStatus(mContext,
											"-1");
									startActivity(new Intent(mContext,
											PerfectInfoActivity.class));
									return;
								}
							} else {
								DialogUtil.showSystemToast(mContext, GsonUtils
										.getJsonValue(retStr,
												GlobalConstants.HTTP_MSG));
							}
						} else {
							DialogUtil.showSystemToast(mContext, "网络异常,请稍后再试");
						}
					}
				});
	}
}

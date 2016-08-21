package com.ciapc.anxin.modules.exchange;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ZbarZxing.XZbar.HxBarcode;
import com.ciapc.anxin.R;
import com.ciapc.anxin.common.AXSharedPreferences;
import com.ciapc.anxin.common.BaseActivity;
import com.ciapc.anxin.common.GlobalConstants;
import com.ciapc.anxin.common.callback.AXCallBack;
import com.ciapc.anxin.common.pojo.CardPojo;
import com.ciapc.anxin.common.pojo.ExchangePojo;
import com.ciapc.anxin.common.view.PubTitle;
import com.ciapc.anxin.common.view.PubTitle.OnTitleClickListener;
import com.ciapc.anxin.modules.details.ScanDetailActivity;
import com.ciapc.anxin.modules.home.HomeDataHandler;
import com.ciapc.anxin.modules.perfect.PerfectDataHandler;
import com.ciapc.anxin.modules.share.ShareUtils;
import com.master.util.common.DialogUtil;
import com.master.util.common.GsonUtils;
import com.master.util.common.StringUtils;

public class ExchangeCardActivity extends BaseActivity {

	private Context mContext;

	// 扫描二维码 添加名片
	private final int TO_ADD = 1;

	// 插入数据库
	private final int INSERT = 2;

	private final int MESSAGE = 3;

	private PubTitle pubTitle;

	// 扫描二维码 微信 短信 邮件
	private RelativeLayout scan, wx, message, mail;

	// 扫描二维码
	private HxBarcode hxBarcod;

	// 搜索框
	private RelativeLayout search;

	// 整个界面 用于搜索动画
	private LinearLayout view;

	// 搜索动画的移动坐标和高度
	private int height;

	// 交换的名片
	private ExchangePojo mPojo;

	private CardPojo mCardPojo;

	// 1 短信 2 邮件
	private int flag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exchange_card_layout);
		mContext = this;
		hxBarcod = new HxBarcode();
		initView();
		initClick();
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 初始化控件
	 * @Date:2015年6月7日下午10:17:32
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initView() {
		pubTitle = (PubTitle) findViewById(R.id.exchange_title);
		scan = (RelativeLayout) findViewById(R.id.exchange_scan);
		wx = (RelativeLayout) findViewById(R.id.exchange_wx);
		message = (RelativeLayout) findViewById(R.id.exchange_message);
		mail = (RelativeLayout) findViewById(R.id.exchange_mail);
		view = (LinearLayout) findViewById(R.id.exchange_layout);
		search = (RelativeLayout) findViewById(R.id.exchange_search);
	}

	/**
	 * @Auther: zhuwt
	 * @Description:初始化点击事件
	 * @Date:2015年6月7日下午10:17:43
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initClick() {
		message.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				flag = 1;
				mHandler.sendEmptyMessage(MESSAGE);
			}
		});

		mail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							flag = 2;
							mHandler.sendEmptyMessage(MESSAGE);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}).start();

			}
		});

		// 微信分享
		wx.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				PerfectDataHandler.getInstance(mContext).getMyData(new AXCallBack() {
					@Override
					public void onCallBackString(final String retStr) {
						super.onCallBackString(retStr);
						runOnUiThread(new Runnable() {
							public void run() {
								if (StringUtils.isNotEmpty(retStr)) {
									if (GlobalConstants.HTTP_SUCCESS_CODE
											.equals(GsonUtils.getJsonValue(retStr,
													GlobalConstants.HTTP_CODE))) {
										String data = GsonUtils.getJsonValue(
												retStr, "data");
										if (!StringUtils.isNotEmpty(data)) {
											DialogUtil.showSystemToast(
													mContext, "网络错误，请稍后再试");
											return;
										}
										mPojo = GsonUtils.toObject(data,
												ExchangePojo.class);
										String title = mPojo.getTrueName()+"的电子名片";
										String content = mPojo.getOrgName()+"的"+mPojo.getJob()+"...";
										String url = GlobalConstants.SHARE+AXSharedPreferences.getCardId(mContext);
										ShareUtils.initWx(mContext, content, url,title);
									}
								}
							}
						});
					}
				});
			}
		});

		// 扫描二维码
		scan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 扫描二维码 520 表示返回参数
				hxBarcod.scan(mContext, 520, false);
			}
		});

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

		search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				height = pubTitle.getHeight();
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
						intent.setClass(mContext, ExchangeSearchActivity.class);
						startActivityForResult(intent, 100);
						overridePendingTransition(R.anim.contact_search_b,
								R.anim.contact_search_a);
					}
				});
				view.startAnimation(animation);

			}
		});

	}

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
				if (100 == requestCode) {
					TranslateAnimation animation = new TranslateAnimation(0, 0,
							-height, 0);
					animation.setDuration(250);
					animation.setFillAfter(true);
					view.startAnimation(animation);
				}
			}
		});
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case TO_ADD:
				String cardId = (String) msg.obj;
				ExchangeDataHandler.getInstance(mContext).exchangeCard(cardId,
						new AXCallBack() {
							public void onCallBackString(String retStr) {
								if (StringUtils.isNotEmpty(retStr)) {
									if (GlobalConstants.HTTP_SUCCESS_CODE
											.equals(GsonUtils.getJsonValue(
													retStr,
													GlobalConstants.HTTP_CODE))) {
										String data = GsonUtils.getJsonValue(
												retStr, "data");
										if (!StringUtils.isNotEmpty(data)) {
											DialogUtil.showSystemToast(
													mContext, "网络错误，请稍后再试");
											return;
										}
										mPojo = GsonUtils.toObject(data,
												ExchangePojo.class);
										mHandler.sendEmptyMessage(INSERT);
									} else {
										DialogUtil
												.showSystemToast(
														mContext,
														GsonUtils
																.getJsonValue(
																		retStr,
																		GlobalConstants.HTTP_MSG));
									}
								} else {
									DialogUtil.showSystemToast(mContext,
											"网络错误，请稍后再试");
								}

							};
						});
				break;

			case INSERT:
				insertExchange(mPojo);
				break;

			case MESSAGE:
				getMsgDate();
				break;
			default:
				break;
			}
		};
	};

	/**
	 * @Auther: zhuwt
	 * @Description: 插入数据
	 * @Date:2015年7月5日下午2:13:50
	 * @param mExchangePojo
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void insertExchange(final ExchangePojo mExchangePojo) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				HomeDataHandler.getInstance().insertCard(mExchangePojo);

			}
		}).start();
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 发送短信或者邮件
	 * @Date:2015年7月8日上午11:02:40
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void getMsgDate() {
		PerfectDataHandler.getInstance(mContext).getMyData(new AXCallBack() {
			@Override
			public void onCallBackString(final String retStr) {
				super.onCallBackString(retStr);
				runOnUiThread(new Runnable() {
					public void run() {
						if (StringUtils.isNotEmpty(retStr)) {
							if (GlobalConstants.HTTP_SUCCESS_CODE
									.equals(GsonUtils.getJsonValue(retStr,
											GlobalConstants.HTTP_CODE))) {
								String data = GsonUtils.getJsonValue(retStr,
										"data");
								mCardPojo = GsonUtils.toObject(data,
										CardPojo.class);
								StringBuilder sb = new StringBuilder();
								String name = mCardPojo.getTrueName();
								if (StringUtils.isNotEmpty(name)) {
									sb.append("姓名：" + name + "\n");
								} else {
									sb.append("姓名：\n");
								}
								String moblie = mCardPojo.getMobile();
								if (StringUtils.isNotEmpty(moblie)) {
									sb.append("手机号：" + moblie + "\n");
								} else {
									sb.append("手机号：\n");
								}
								String jobs = mCardPojo.getJobs();
								if (StringUtils.isNotEmpty(jobs)) {
									sb.append("职位：" + jobs + "\n");
								} else {
									sb.append("职位：\n");
								}
								String orgName = mCardPojo.getOrgName();
								if (StringUtils.isNotEmpty(orgName)
										&& "1".equals(AXSharedPreferences
												.getEntStatus(mContext))) {
									sb.append("公司：" + orgName + "\n");
								} else {
									sb.append("公司：\n");
								}
								// 短信
								if (1 == flag) {
									// 联系人地址
									Uri smsToUri = Uri.parse("smsto:");
									Intent mIntent = new Intent(
											android.content.Intent.ACTION_SENDTO,
											smsToUri);
									String strContent1 = sb.append(
											"---“信乎”,诚信世界的入口！详情").toString();
									// 短信内容
									mIntent.putExtra("sms_body", strContent1);
									startActivity(mIntent);
								}
								// 邮件
								if (2 == flag) {
									try {
										Intent intent = new Intent(
												Intent.ACTION_SEND);
										intent.setData(Uri.parse("mailto:"));
										intent.setType("message/rfc822");
										/* 设置邮件的标题 */
										intent.putExtra(Intent.EXTRA_SUBJECT,
												"来自信乎");
										/* 设置邮件的内容 */
										String strContent2 = sb
												.append("你还在担心离职后人脉的流失吗？你还在被各种名片头衔忽悠吗?"
														+ "\n"
														+ "---“信乎”,真人真企业，值得信赖！下载xxxxxxx,你也可以拥有真实的电子名片")
												.toString();
										intent.putExtra(Intent.EXTRA_TEXT,
												strContent2);
										// 开始调用
										mContext.startActivity(intent);
										startActivity(Intent.createChooser(
												intent, "发送邮件"));
									} catch (Exception e) {
										e.printStackTrace();
									}
								}

							}
						}
					}
				});
			}
		});
	}
}

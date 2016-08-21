package com.ciapc.anxin.modules.details;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.AXSharedPreferences;
import com.ciapc.anxin.common.BaseActivity;
import com.ciapc.anxin.common.GlobalConstants;
import com.ciapc.anxin.common.callback.AXCallBack;
import com.ciapc.anxin.common.pojo.CardPojo;
import com.ciapc.anxin.common.view.AxinDialog;
import com.ciapc.anxin.common.view.PubTitle;
import com.ciapc.anxin.common.view.RoundView;
import com.ciapc.anxin.common.view.AxinDialog.AxDialogClickListen;
import com.ciapc.anxin.common.view.PubTitle.OnTitleClickListener;
import com.ciapc.anxin.modules.contacts.ContactDateHandler;
import com.ciapc.anxin.modules.exchange.ExchangeVerifyActivity;
import com.ciapc.anxin.modules.perfect.PerfectDataHandler;
import com.ciapc.anxin.modules.perfect.PerfectInfoActivity;
import com.master.util.common.DialogUtil;
import com.master.util.common.GsonUtils;
import com.master.util.common.StringUtils;
import com.master.util.image.MasterImg;

/**
 * @author zhuwt
 * @Description: 企业通讯录 个人详情
 * @ClassName: ContactDetailActivity.java
 * @date 2015年7月6日 下午3:19:05
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */

public class ScanDetailActivity extends BaseActivity {

	private PubTitle pubTitle;

	private Context mContext;

	private TextView moblie, name, qianm, toMobile, toCompany;

	private String id;

	private RoundView head;

	private Button exchange;

	private String isExchange, cardId;

	private final int INIT_DATE = 1;

	private CardPojo pojo;
	private final int SEND_MSG = 2;
	private int flag;
	// 个人信息 用于分享
	private CardPojo mCardPojo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scan_detail);
		mContext = this;
		initView();
		initClick();
		initDate();
	}

	private void initView() {
		moblie = (TextView) findViewById(R.id.contacts_detail_mobile);
		name = (TextView) findViewById(R.id.contact_true_name);
		qianm = (TextView) findViewById(R.id.contact_qianm);
		pubTitle = (PubTitle) findViewById(R.id.scan_detalis_title);
		exchange = (Button) findViewById(R.id.exchange_card);
		head = (RoundView) findViewById(R.id.business_card_head);
		toMobile = (TextView) findViewById(R.id.for_mobile);
		toCompany = (TextView) findViewById(R.id.scan_company);
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

		exchange.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (AXSharedPreferences.getCardId(mContext).equals(cardId)) {
					DialogUtil.showSystemToast(mContext, "无法递交自己的名片");
					return;
				}
				if ("2".equals(AXSharedPreferences.getUserInfoStatus(mContext))) {
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
									mContext.startActivity(new Intent(mContext,
											PerfectInfoActivity.class));
									axinDialog.dismiss();
								}
							}).show();
					return;
				}
				Intent mIntent = new Intent(mContext,
						ExchangeVerifyActivity.class);
				mIntent.putExtra("cardId", cardId);
				mContext.startActivity(mIntent);
			}
		});

		toMobile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				try {
					if (StringUtils.isNotEmpty(pojo.getMobile())) {
						Uri uri = Uri.parse("tel:" + pojo.getMobile());
						Intent intent = new Intent(Intent.ACTION_DIAL, uri);
						startActivity(intent);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	private void initDate() {
		cardId = getIntent().getStringExtra("cardId");
		ContactDateHandler.getInstance(mContext).getContectDetail(cardId,
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
										if (StringUtils.isNotEmpty(data)) {
											pojo = GsonUtils.toObject(data,
													CardPojo.class);
											String mobile = pojo.getMobile();
											if (StringUtils.isNotEmpty(mobile)) {
												moblie.setText(mobile);
											} else {
												moblie.setText("未填写");
											}
											String trueName = pojo
													.getTrueName();
											if (StringUtils
													.isNotEmpty(trueName)) {
												name.setText(trueName);
											} else {
												name.setText("无名氏");
											}
											String mottoName = pojo.getMotto();
											if (StringUtils
													.isNotEmpty(mottoName)) {
												if (mottoName.length() > 12) {
													mottoName = mottoName
															.substring(0, 12)
															+ "...";
													qianm.setText("座右铭："
															+ mottoName);
												} else {
													qianm.setText("座右铭："
															+ mottoName);
												}
											} else {
												qianm.setText("座右铭："
														+ "这个家伙好懒，什么都没有写");
											}
											String companyNam = pojo
													.getOrgEname();
											if (StringUtils
													.isNotEmpty(companyNam)) {
												toCompany.setText(companyNam);
											} else {
												toCompany.setText("未填写");
											}
											String path = pojo.getIconUrl();
											if (StringUtils.isNotEmpty(path)) {
												head.setTag(path);
												MasterImg
														.getInstance(mContext)
														.loadImg(
																path,
																head,
																true,
																true,
																0,
																R.drawable.toux_default);
											}
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
								} else {
									DialogUtil.showSystemToast(mContext,
											"网络异常,请稍后再试!");
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
			case SEND_MSG:
				getMsgDate();
				break;
			default:
				break;
			}
		};
	};

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
								// 短信
								if (1 == flag) {
									// 联系人地址
									Uri smsToUri = Uri.parse("smsto:"
											+ mCardPojo.getMobile());
									Intent mIntent = new Intent(
											android.content.Intent.ACTION_SENDTO,
											smsToUri);
									String strContent1 = mCardPojo
											.getTrueName()
											+ "\n"
											+ AXSharedPreferences
													.getUserPhone(mContext)
											+ "\n"
											+ (StringUtils.isNotEmpty(mCardPojo
													.getJobs()) ? mCardPojo
													.getJobs() : "")
											+ "\n"
											+ (StringUtils.isNotEmpty(mCardPojo
													.getOrgName()) ? mCardPojo
													.getOrgName() : "")
											+ "\n"
											+ "---“信乎”,诚信世界的入口！详情";
									// 短信内容
									mIntent.putExtra("sms_body", strContent1);
									startActivity(mIntent);
								}
								// 邮件
								if (2 == flag) {
									try {
										Intent intent = new Intent();
										intent.setData(Uri.parse("mailto:"));
										/* 设置邮件的标题 */
										intent.putExtra(Intent.EXTRA_SUBJECT,
												"来自信乎");
										/* 设置邮件的内容 */
										String strContent1 = mCardPojo
												.getTrueName()
												+ "_信乎名片"
												+ "\n"
												+ AXSharedPreferences
														.getUserPhone(mContext)
												+ "，"
												+ (StringUtils
														.isNotEmpty(mCardPojo
																.getEmail()) ? mCardPojo
														.getEmail() : "")
												+ "\n"
												+ (StringUtils
														.isNotEmpty(mCardPojo
																.getOrgName()) ? mCardPojo
														.getOrgName()

												: "")
												+ "\n"
												+ "你还在担心离职后人脉的流失吗？你还在被各种名片头衔忽悠吗?"
												+ "\n"
												+ "---“信乎”,真人真企业，值得信赖！下载xxxxxxx,你也可以拥有真实的电子名片";
										intent.putExtra(Intent.EXTRA_TEXT,
												strContent1);
										// 开始调用
										mContext.startActivity(intent);
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

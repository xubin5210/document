package com.ciapc.anxin.modules.details;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.AXSharedPreferences;
import com.ciapc.anxin.common.BaseActivity;
import com.ciapc.anxin.common.GlobalConstants;
import com.ciapc.anxin.common.callback.AXCallBack;
import com.ciapc.anxin.common.pojo.CardPojo;
import com.ciapc.anxin.common.pojo.ExchangePojo;
import com.ciapc.anxin.common.view.AxShareDialog;
import com.ciapc.anxin.common.view.AxShareDialog.AxDialogShareClickListen;
import com.ciapc.anxin.common.view.AxinDialog;
import com.ciapc.anxin.common.view.AxinDialog.AxDialogClickListen;
import com.ciapc.anxin.common.view.PubTitle;
import com.ciapc.anxin.common.view.PubTitle.OnTitleClickListener;
import com.ciapc.anxin.common.view.RoundView;
import com.ciapc.anxin.modules.exchange.ExchangeDataHandler;
import com.ciapc.anxin.modules.home.HomeDataHandler;
import com.ciapc.anxin.modules.perfect.PerfectDataHandler;
import com.ciapc.anxin.modules.share.ShareUtils;
import com.master.util.common.DialogUtil;
import com.master.util.common.GsonUtils;
import com.master.util.common.StringUtils;
import com.master.util.common.SystemUtils;
import com.master.util.image.MasterImg;

/**
 * @author zhuwt
 * @Description: 个人名片详情
 * @ClassName: HomeDetails.java
 * @date 2015年6月4日 上午9:51:10
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */

public class PersonDetails extends BaseActivity {

	private Context mContext;
	private PopupWindow popupwindow;
	private PubTitle pubTitle;
	private String id;
	private CardPojo pojo;

	// 名片ID 真实姓名 昵称 在职状态 企业认证 企业 昵称 座右铭 职位 部门 手机 座机 传真 邮箱 QQ 微信
	private TextView cardId, trueName, nickName, activeStatus,
			certificationStatus, orgName, motto, position, department, mobile,
			phone, fax, email, qq, wx, toMail;

	// 初始化数据
	private final int INIT_DATA = 1;

	private final int SEND_MSG = 2;

	private final int DEL = 3;

	private final String TAG = "PersonDetails";

	// 跳转到企业详情
	private LinearLayout toCompany;

	// 企业ID
	private String endId;

	// 二维码
	private ImageView qr;

	private RoundView head;

	private int flag;

	private TextView toMobile, toPhone;

	private int width;
	private int hight;
	
	private String mailAdress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.home_details_layout);
		initView();
		initClick();
		if (!SystemUtils.isNetWorkConnected(mContext)) {
			DialogUtil.showSystemToast(mContext, "暂无网络，请连接网络");
			return;
		}
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		float scale = mContext.getResources().getDisplayMetrics().density;
		width = (int) (100 * scale + 0.5f);
		hight = (int) (150 * scale + 0.5f);
		mHandler.sendEmptyMessage(INIT_DATA);
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 初始化数据
	 * @Date:2015年6月26日上午11:12:55
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initData() {
		id = getIntent().getStringExtra("cardId");
		if (!StringUtils.isNotEmpty(id)) {
			return;
		}
		PerfectDataHandler.getInstance(mContext).getPersonData(id + "",
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
										pojo = GsonUtils.toObject(data,
												CardPojo.class);
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
										String nameTrue = pojo.getTrueName();
										if(StringUtils.isNotEmpty(nameTrue)){
											trueName.setText(nameTrue);
										}else{
											trueName.setText("无名氏");
										}
										cardId.setText(Html.fromHtml("名片号：" + "<font color='#222222'>"+pojo.getId()+"</font>"));
										String nick = pojo.getNickName();
										if (StringUtils.isNotEmpty(nick)) {
											nickName.setText(Html.fromHtml("昵称："
													+ "<font color='#222222'>"+nick+"</font>"));
										} else {
											nickName.setText(Html.fromHtml("昵称：<font color='#222222'>未填写</font>"));
										}

										String status = pojo
												.getActivationStatus();
										if ("0".equals(status)) {
											activeStatus.setText("在职");
										} else {
											activeStatus.setText("离职");
										}
										String url = pojo.getIconUrl();
										if (StringUtils.isNotEmpty(url)
												&& !"null".equals(url)) {
											head.setTag(url);
											MasterImg
													.getInstance(mContext)
													.loadImg(
															url,
															head,
															true,
															true,
															0,
															R.drawable.toux_default);
										}
										String type = pojo
												.getCertificationStatus();
										if (!StringUtils.isNotEmpty(type)) {
											certificationStatus
													.setVisibility(View.GONE);
										} else {
											certificationStatus
													.setVisibility(View.VISIBLE);
											// 未认证
											if ("0".equals(type)) {
												certificationStatus
														.setText("未认证");
												certificationStatus
														.setTextColor(mContext
																.getResources()
																.getColor(
																		R.color.C3));
												certificationStatus
														.setBackgroundDrawable(mContext
																.getResources()
																.getDrawable(
																		R.drawable.accept_alread_card_style));
											} else {
												certificationStatus
														.setText("已认证");
												certificationStatus
														.setTextColor(mContext
																.getResources()
																.getColor(
																		R.color.white));
												certificationStatus
														.setBackgroundDrawable(mContext
																.getResources()
																.getDrawable(
																		R.drawable.certifications));
											}
										}
										endId = pojo.getEntId();
										String companyName = pojo.getOrgName();
										if(StringUtils.isNotEmpty(companyName)){
											orgName.setText(companyName);
										}else{
											orgName.setText("未填写");
										}
										// 座右铭
										String mottoText = pojo.getMotto();
										if (StringUtils.isNotEmpty(mottoText)) {
											if (mottoText.length() > 12) {
												motto.setText(mottoText.substring(
																0, 12)
														+ "...");
											} else {
												motto.setText(mottoText);
											}
										} else {
											motto.setText("这个家伙好懒，什么都没有写");
										}
										// 职位
										String post = pojo.getJobs();
										if (StringUtils.isNotEmpty(post)) {
											position.setText(post);
										} else {
											position.setText("未填写");
										}
										// 部门
										String dept = pojo.getDeptNames();
										if (StringUtils.isNotEmpty(dept)) {
											department.setText(dept);
										} else {
											department.setText("未填写");
										}
										// 手机号
										String numA = pojo.getMobile();
										if (StringUtils.isNotEmpty(numA)) {
											mobile.setText(numA);
										} else {
											mobile.setText("未填写");
										}
										// 座机
										String numB = pojo.getPhone();
										if (StringUtils.isNotEmpty(numB)) {
											phone.setText(numB);
										} else {
											phone.setText("未填写");
										}
										// 传真
										String faxText = pojo.getFax();
										if (StringUtils.isNotEmpty(faxText)) {
											fax.setText(faxText);
										} else {
											fax.setText("未填写");
										}
										// 邮箱
										String emailText = pojo.getEmail();
										if (StringUtils.isNotEmpty(emailText)) {
											email.setText(emailText);
										} else {
											email.setText("未填写");
										}
										// qq
										String qqText = pojo.getQq();
										if (StringUtils.isNotEmpty(qqText)) {
											qq.setText(qqText);
										} else {
											qq.setText("未填写");
										}
										// 微信
										String wxText = pojo.getWeiXin();
										if (StringUtils.isNotEmpty(wxText)) {
											wx.setText(wxText);
										} else {
											wx.setText("未填写");
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
											"网络异常,请稍后再试！");
								}
							}
						});
					}
				});
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 控件初始化
	 * @Date:2015年6月4日下午2:20:21
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initView() {
		pubTitle = (PubTitle) findViewById(R.id.home_detalis_title);
		trueName = (TextView) findViewById(R.id.home_detail_name);
		cardId = (TextView) findViewById(R.id.home_detail_num);
		nickName = (TextView) findViewById(R.id.home_dateil_nick_name);
		activeStatus = (TextView) findViewById(R.id.home_detail_work);
		certificationStatus = (TextView) findViewById(R.id.person_detail_certificationStatus);
		orgName = (TextView) findViewById(R.id.home_detail_company);
		motto = (TextView) findViewById(R.id.home_detail_motto);
		position = (TextView) findViewById(R.id.home_detail_post);
		department = (TextView) findViewById(R.id.home_detail_department);
		mobile = (TextView) findViewById(R.id.home_detail_mobile);
		phone = (TextView) findViewById(R.id.person_detail_phone);
		fax = (TextView) findViewById(R.id.person_detail_fax);
		email = (TextView) findViewById(R.id.person_detail_email);
		qq = (TextView) findViewById(R.id.person_detail_qq);
		wx = (TextView) findViewById(R.id.person_detail_wx);
		toCompany = (LinearLayout) findViewById(R.id.home_detail_company_info);
		head = (RoundView) findViewById(R.id.home_detail_head);
		qr = (ImageView) findViewById(R.id.home_detail_code);
		toMobile = (TextView) findViewById(R.id.mobile);
		toPhone = (TextView) findViewById(R.id.phone);
		toMail = (TextView) findViewById(R.id.to_mail);
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 点击事件
	 * @Date:2015年6月4日下午2:20:30
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initClick() {
		toMail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				flag = 3;
				mHandler.sendEmptyMessage(SEND_MSG);
			}
		});

		pubTitle.setShowLeftOrRight(true, false, true).setOnTitleClickListener(
				new OnTitleClickListener() {

					@Override
					public void onRightClick() {
						runOnUiThread(new Runnable() {
							public void run() {
								if (popupwindow != null
										&& popupwindow.isShowing()) {
									popupwindow.dismiss();
									return;
								} else {
									initmPopupWindowView();
									popupwindow.showAsDropDown(
											pubTitle.getRightTextView(), 0, 5);
								}
							}
						});
					}

					@Override
					public void onLeftClick() {
						finish();
					}
				});

		// 企业详情
		toCompany.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (StringUtils.isNotEmpty(endId)) {
					Intent mIntent = new Intent(mContext, CompanyDetails.class);
					mIntent.putExtra("entId", endId);
					startActivity(mIntent);
				}
			}
		});

		// 去二维码名片
		qr.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent mIntent = new Intent(mContext, QrDetailsActivity.class);
				mIntent.putExtra("cardId", id + "");
				mIntent.putExtra("icon", pojo.getIconUrl() + "");
				mIntent.putExtra("name", pojo.getTrueName());
				startActivity(mIntent);
			}
		});

		toPhone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				try {
					if (StringUtils.isNotEmpty(pojo.getPhone())) {
						Uri uri = Uri.parse("tel:" + pojo.getPhone());
						Intent intent = new Intent(Intent.ACTION_DIAL, uri);
						startActivity(intent);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
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

	/**
	 * @Auther: zhuwt
	 * @Description: 自定义菜单
	 * @Date:2015年6月4日下午1:56:43
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void initmPopupWindowView() {

		// 自定义布局文件
		View customView = getLayoutInflater().inflate(
				R.layout.home_detail_spinner, null, false);
		// 创建PopupWindow实例,定义宽度和高度
		popupwindow = new PopupWindow(customView, width, hight);
		popupwindow.setFocusable(true); // 设置PopupWindow可获得焦点
		popupwindow.setTouchable(true); // 设置PopupWindow可触摸
		popupwindow.setOutsideTouchable(true); // 设置非PopupWindow区域可触摸
		// 自定义view添加触摸事件
		customView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				if (popupwindow != null && popupwindow.isShowing()) {
					popupwindow.dismiss();
					popupwindow = null;
				}

				return false;
			}
		});

		final LinearLayout report = (LinearLayout) customView
				.findViewById(R.id.popwindow_report);
		final LinearLayout share = (LinearLayout) customView
				.findViewById(R.id.popwindow_share);
		final LinearLayout del = (LinearLayout) customView
				.findViewById(R.id.popwindow_del);
		final LinearLayout dynamic = (LinearLayout) customView
				.findViewById(R.id.popwindow_dynamic);

		// 动态
		dynamic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent mIntent = new Intent(mContext,
						PersonDynamicActivity.class);
				mIntent.putExtra("trueName", pojo.getTrueName() + "");
				mIntent.putExtra("nickName", pojo.getNickName() + "");
				mIntent.putExtra("iconUrl", pojo.getIconUrl() + "");
				mIntent.putExtra("cardId", id + "");
				startActivity(mIntent);
			}
		});
		// 删除
		del.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				new AxinDialog(mContext, AxinDialog.DIALOG_CHANGE)
						.setTitle("删除该名片时，你的名片也会在对方名片里删除").setCancelStr("取消")
						.setConfirmStr("确认删除")
						.setCancelClick(new AxDialogClickListen() {

							@Override
							public void onClick(AxinDialog axinDialog) {
								axinDialog.dismiss();
							}
						}).setConfirmClick(new AxDialogClickListen() {

							@Override
							public void onClick(final AxinDialog axinDialog) {
								axinDialog.dismiss();
								mHandler.sendEmptyMessage(DEL);
							}
						}).show();
			}
		});
		// 举报
		report.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(mContext, ReportActivty.class));
			}
		});
		// 分享
		share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				new AxShareDialog(mContext).setAxDialogShareClickListen(
						new AxDialogShareClickListen() {

							@Override
							public void onShareWxClick(
									AxShareDialog axClickListener) {
								String title = pojo.getTrueName()+"的电子名片";
								String content = pojo.getOrgName()+"的"+pojo.getJobs()+"...";
								String url = GlobalConstants.SHARE+pojo.getCardId();
								ShareUtils.initWx(mContext, content, url,title);
							}

							@Override
							public void onShareMsgClick(
									AxShareDialog axClickListener) {
								flag = 1;
								mHandler.sendEmptyMessage(SEND_MSG);
							}

							@Override
							public void onShareEmailClick(
									AxShareDialog axClickListener) {
								flag = 2;
								mHandler.sendEmptyMessage(SEND_MSG);
							}
						}).show();
			}
		});
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case DEL:
				delCard();
				break;
			case INIT_DATA:
				initData();
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
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				StringBuilder sb = new StringBuilder();
				String name = pojo.getTrueName();
				if(StringUtils.isNotEmpty(name)){
					sb.append("姓名："+name+ "\n");
				}else{
					sb.append("姓名：\n");
				}
				String moblie = pojo.getMobile();
				if(StringUtils.isNotEmpty(moblie)){
					sb.append("手机号："+moblie+ "\n");
				}else{
					sb.append("手机号：\n");
				}
				String jobs = pojo.getJobs();
				if(StringUtils.isNotEmpty(jobs)){
					sb.append("职位："+jobs+ "\n");
				}else{
					sb.append("职位：\n");
				}
				String orgName = pojo.getOrgName();
				if(StringUtils.isNotEmpty(orgName) && "1".equals(pojo.getEntActiveStatus())){
					sb.append("公司："+orgName+ "\n");
				}else{
					sb.append("公司：\n");
				}
				// 短信
				if (1 == flag) {
					// 联系人地址
					Uri smsToUri = Uri.parse("smsto:");
					Intent mIntent = new Intent(
							android.content.Intent.ACTION_SENDTO,
							smsToUri);
				
					String strContent1 = sb.append("---“信乎”,诚信世界的入口！详情").toString();
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
				
				if (3 == flag) {
					try {
						Intent intent = new Intent(
								Intent.ACTION_SEND);
						intent.putExtra(Intent.EXTRA_EMAIL, new String[]    
								{ pojo.getEmail()});
						intent.setType("message/rfc822");
						/* 设置邮件的标题 */
						intent.putExtra(Intent.EXTRA_SUBJECT,
								"");
						intent.putExtra(Intent.EXTRA_TEXT,
								"");
						// 开始调用
						mContext.startActivity(intent);
						startActivity(Intent.createChooser(
								intent, "发送邮件"));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		});
	}

	/**
	 * @Auther: zhuwt
	 * @Description:删除名片
	 * @Date:2015年7月14日下午7:09:48
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void delCard() {
		DetailsDataHandler.getInstance(mContext).delCard(id + "",
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
										DialogUtil.showSystemToast(mContext,
												"删除成功!");
										HomeDataHandler.getInstance()
												.delCard(id);
										finish();
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
											"网络异常,请稍后再试！");
								}
							}
						});
					}
				});
	}
}

package com.ciapc.anxin.modules.details;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.AXSharedPreferences;
import com.ciapc.anxin.common.BaseActivity;
import com.ciapc.anxin.common.GlobalConstants;
import com.ciapc.anxin.common.callback.AXCallBack;
import com.ciapc.anxin.common.pojo.CompanyPojo;
import com.ciapc.anxin.common.pojo.JobPojo;
import com.ciapc.anxin.common.view.PubTitle;
import com.ciapc.anxin.common.view.RoundView;
import com.ciapc.anxin.common.view.PubTitle.OnTitleClickListener;
import com.master.util.common.DateUtil;
import com.master.util.common.DialogUtil;
import com.master.util.common.GsonUtils;
import com.master.util.common.StringUtils;
import com.master.util.common.SystemUtils;
import com.master.util.image.MasterImg;

/**
 * @author zhuwt
 * @Description: 企业详情
 * @ClassName: CompanyDetails.java
 * @date 2015年6月10日 下午5:26:19
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */

public class CompanyDetails extends BaseActivity {

	private PubTitle pubTitle;

	private Context mContext;

	// 更多
	private PopupWindow popupwindow;

	// 企业ID
	private String entId;

	// 初始化数据
	private final int INIT_DATE = 1;

	private ImageView companyWx;
	private RoundView head;

	private TextView name, code, representative, checkCode, address,
			registerTime, validity, note, url, phone, mail, contactCount,weiXinNum;

	private LinearLayout showFriend;

	private Button statu;

	// 企业人脉
	private LinearLayout contact;

	private CompanyPojo pojo;

	private List<JobPojo> list = new ArrayList<JobPojo>();
	private CompanyPeoAdapter adapter;
	private GridView mGridView;

	private LinearLayout show;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.company_detail);
		mContext = this;
		initView();
		initClick();
		if (!SystemUtils.isNetWorkConnected(mContext)) {
			DialogUtil.showSystemToast(mContext, "暂无网络，请连接网络");
			return;
		}
		mHandler.sendEmptyMessage(INIT_DATE);
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 初始化数据
	 * @Date:2015年6月29日下午2:12:36
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initDate() {
		entId = getIntent().getStringExtra("entId");
		if (!StringUtils.isNotEmpty(entId)) {
			return;
		}
		DetailsDataHandler.getInstance(mContext).getCompanyInfo(entId,
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
										String date = GsonUtils.getJsonValue(
												retStr, "data");
										pojo = GsonUtils.toObject(date,
												CompanyPojo.class);
										name.setText(pojo.getOrgName());
										phone.setText(pojo.getPhone());
										String addressText = pojo
												.getEntAddress();
										if (StringUtils.isNotEmpty(addressText)) {
											address.setText("地址:"
													+ addressText);
										} else {
											address.setText("地址:未填写");
										}
										String weixin = pojo.getWeiXin();
										if (StringUtils.isNotEmpty(weixin)) {
											weiXinNum.setText("微信号:"
													+ weixin);
										} else {
											weiXinNum.setText("微信号:未填写");
										}
										String path = pojo.getLogoUrl();
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
										String path2 = pojo.getWeiXinUrl();
										if (StringUtils.isNotEmpty(path2)) {
											companyWx.setTag(path2);
											MasterImg
													.getInstance(mContext)
													.loadImg(
															path2,
															companyWx,
															true,
															true,
															0,
															R.drawable.toux_default);
										}
										list = pojo.getConnList();
										if (null != list) {
											if (list.size() > 0) {
												showFriend
														.setVisibility(View.VISIBLE);
												contact.setVisibility(View.VISIBLE);
												contactCount.setText("企业人脉("
														+ pojo.getEntConnNum()
														+ ")");
												adapter = new CompanyPeoAdapter(
														mContext, list);
												mGridView
														.setAdapter(adapter);
											} else {
												showFriend
														.setVisibility(View.GONE);
												contact.setVisibility(View.GONE);
											}
										} else {
											showFriend
													.setVisibility(View.GONE);
											contact.setVisibility(View.GONE);
										}
										// 属于当前企业 并且审核通过 才能查看企业机构代码
										if (pojo.getEntId().equals(
												AXSharedPreferences
														.getEntId(mContext))
												&& "1".equals(AXSharedPreferences
														.getEntStatus(mContext))) {
											code.setText(pojo.getOrgCode());
											representative.setText(pojo
													.getArtificialPerson());
											checkCode.setText(pojo
													.getRegistrationno());
											String time = pojo
													.getRegisterDate();
											if (StringUtils.isNotEmpty(time)) {
												String dateTime = DateUtil.getDateYHD(Long
														.parseLong(time));
												registerTime.setText(dateTime);
											} else {
												registerTime.setText("");
											}

											validity.setText(pojo
													.getValidDateFrom()
													+ "--"
													+ pojo.getValidDateTo());
										} else {
											show.setVisibility(View.GONE);
										}
										note.setText("    "
												+ pojo.getOrgIntroduction());
										url.setText("官网:"
												+ (StringUtils.isNotEmpty(pojo
														.getOfficialWebsite()) ? pojo
														.getOfficialWebsite()
														: ""));
										phone.setText("客服电话:"
												+ (StringUtils.isNotEmpty(pojo
														.getPhone()) ? pojo
														.getPhone() : ""));
										mail.setText("企业邮箱:"
												+ (StringUtils.isNotEmpty(pojo
														.getEmail()) ? pojo
														.getEmail() : ""));

										String status = pojo
												.getCertificationStatus();
										// 未认证
										if ("0".equals(status)) {
											statu.setText("未认证");
											statu.setTextColor(mContext
													.getResources().getColor(
															R.color.C2));
											statu.setBackgroundDrawable(mContext
													.getResources()
													.getDrawable(
															R.drawable.accept_alread_card_style));
										} else {
											statu.setText("已认证");
											statu.setTextColor(mContext
													.getResources().getColor(
															R.color.white));
											statu.setBackgroundDrawable(mContext
													.getResources()
													.getDrawable(
															R.drawable.certifications));
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
											"网络异常，请稍后再试");
								}
							}
						});
					}
				});
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 初始化控件
	 * @Date:2015年6月10日下午9:10:39
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initView() {
		pubTitle = (PubTitle) findViewById(R.id.company_detalis_title);
		name = (TextView) findViewById(R.id.company_detail_name);
		representative = (TextView) findViewById(R.id.representative);
		checkCode = (TextView) findViewById(R.id.checkCode);
		address = (TextView) findViewById(R.id.address);
		registerTime = (TextView) findViewById(R.id.register_time);
		validity = (TextView) findViewById(R.id.validity);
		url = (TextView) findViewById(R.id.url);
		phone = (TextView) findViewById(R.id.phone);
		mail = (TextView) findViewById(R.id.mail);
		contact = (LinearLayout) findViewById(R.id.conpany_contact);
		code = (TextView) findViewById(R.id.code);
		statu = (Button) findViewById(R.id.status);
		note = (TextView) findViewById(R.id.note);
		contactCount = (TextView) findViewById(R.id.contact_count);
		head = (RoundView) findViewById(R.id.company_detail_head);
		companyWx = (ImageView) findViewById(R.id.wx_icon);
		mGridView = (GridView) findViewById(R.id.grid);
		show = (LinearLayout) findViewById(R.id.is_show);
		showFriend = (LinearLayout) findViewById(R.id.show_friends);
		weiXinNum = (TextView) findViewById(R.id.weixin_num);
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 点击事件
	 * @Date:2015年6月10日下午9:12:21
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initClick() {

		mGridView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				Intent mIntent = new Intent(mContext,
						CompanyContactActivity.class);
				mIntent.putExtra("entId", pojo.getEntId());
				startActivity(mIntent);
				return false;
			}
		});

		showFriend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent mIntent = new Intent(mContext,
						CompanyContactActivity.class);
				mIntent.putExtra("entId", pojo.getEntId());
				startActivity(mIntent);
			}
		});
		pubTitle.setShowLeftOrRight(true, false, false)
				.setOnTitleClickListener(new OnTitleClickListener() {

					@Override
					public void onRightClick() {
						if (popupwindow != null && popupwindow.isShowing()) {
							popupwindow.dismiss();
							return;
						} else {
							initmPopupWindowView();
							popupwindow.showAsDropDown(
									pubTitle.getRightTextView(), 0, 5);
						}

					}

					@Override
					public void onLeftClick() {
						finish();
					}
				});

		contact.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent mIntent = new Intent(mContext,
						CompanyContactActivity.class);
				mIntent.putExtra("entId", pojo.getEntId());
				startActivity(mIntent);
			}
		});

		phone.setOnClickListener(new OnClickListener() {

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

		mail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (StringUtils.isNotEmpty(pojo.getEmail())) {
					try {
						Intent intent = new Intent(Intent.ACTION_SEND);
						intent.putExtra(Intent.EXTRA_EMAIL,
								new String[] { pojo.getEmail() });
						intent.setType("message/rfc822");
						/* 设置邮件的标题 */
						intent.putExtra(Intent.EXTRA_SUBJECT, "");
						intent.putExtra(Intent.EXTRA_TEXT, "");
						// 开始调用
						mContext.startActivity(intent);
						startActivity(Intent.createChooser(intent, "发送邮件"));
					} catch (Exception e) {
						e.printStackTrace();
					}
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
		popupwindow = new PopupWindow(customView, 200, 200);
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

		// j举报
		final LinearLayout report = (LinearLayout) customView
				.findViewById(R.id.popwindow_report);
		report.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(mContext, ReportActivty.class));
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

	private class CompanyPeoAdapter extends BaseAdapter {

		private Context context;
		private List<JobPojo> data;

		public CompanyPeoAdapter(Context context, List<JobPojo> data) {
			this.context = context;
			this.data = data;
		}

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Object getItem(int arg0) {
			return data.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			RoundView head = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.company_contact_item, null);
				head = (RoundView) convertView
						.findViewById(R.id.company_contact_head);
				convertView.setTag(head);
			} else {
				head = (RoundView) convertView.getTag();
			}
			String path = list.get(position).getIconUrl();
			if (StringUtils.isNotEmpty(path)) {
				head.setTag(path);
				MasterImg.getInstance(mContext).loadImg(
						list.get(position).getIconUrl(), head, true, true, 0,
						R.drawable.toux_default);
			} else {
				head.setTag("");
				head.setImageResource(R.drawable.toux_default);
			}
			return convertView;
		}

	}
}

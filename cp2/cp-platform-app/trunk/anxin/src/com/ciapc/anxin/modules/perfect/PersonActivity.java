package com.ciapc.anxin.modules.perfect;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.AXSharedPreferences;
import com.ciapc.anxin.common.BaseActivity;
import com.ciapc.anxin.common.GlobalConstants;
import com.ciapc.anxin.common.InterfaceConstants;
import com.ciapc.anxin.common.callback.AXCallBack;
import com.ciapc.anxin.common.pojo.CardPojo;
import com.ciapc.anxin.common.pojo.JobPojo;
import com.ciapc.anxin.common.view.AxinDialog;
import com.ciapc.anxin.common.view.AxinDialog.AxDialogClickListen;
import com.ciapc.anxin.common.view.ProgressShow;
import com.ciapc.anxin.common.view.PubTitle;
import com.ciapc.anxin.common.view.PubTitle.OnTitleClickListener;
import com.ciapc.anxin.common.view.RoundView;
import com.ciapc.anxin.common.view.TakePhotoPopup;
import com.ciapc.anxin.modules.details.CompanyDetails;
import com.ciapc.anxin.modules.details.QrDetailsActivity;
import com.ciapc.anxin.modules.home.HomeSharedPreferences;
import com.ciapc.anxin.modules.setting.AccountSafePhoneActivity;
import com.ciapc.anxin.modules.setting.person.PersonEenglishNameActivity;
import com.ciapc.anxin.modules.setting.person.PersonEmailReActivity;
import com.ciapc.anxin.modules.setting.person.PersonFAXReActivity;
import com.ciapc.anxin.modules.setting.person.PersonFixedphoneReActivity;
import com.ciapc.anxin.modules.setting.person.PersonDeptJobActivity;
import com.ciapc.anxin.modules.setting.person.PersonNameActivity;
import com.ciapc.anxin.modules.setting.person.PersonQqReActivity;
import com.ciapc.anxin.modules.setting.person.PersonWeixinReActivity;
import com.ciapc.anxin.utils.CheckStringUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.master.util.common.DialogUtil;
import com.master.util.common.GsonUtils;
import com.master.util.common.MasterUtils;
import com.master.util.common.StringUtils;
import com.master.util.http.MasterHttpClient;
import com.master.util.image.MasterImg;

/**
 * @author zhuwt
 * @Description: 个人信息
 * @ClassName: PersonActivity.java
 * @date 2015年6月9日 下午10:43:55
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */

public class PersonActivity extends BaseActivity {
	public static final String TAG = "PersonActivity";

	private Context mContext;

	// 每一行的RelativeLayout的id
	private RelativeLayout personNameRe, personEnglishName, personBusinessCard,
			personMotto, personDept, personJob, personPhoneRe,
			personFixedphoneRe, personFAXRe, personEmailRe, personQqRe,
			personWeixinRe, personIDcardRe, personIscertificateRe, trueNameRe;

	// 自己在values下面array中定义一个名称
	private PubTitle pubTitle;

	// 拍照返回
	private final int TAKE_PHONE_BACK = 1;

	// 拍照返回
	private final int CHOOSE_ALBUM = 2;

	// 修改信息
	private final int UPDATE_PERSON = 4;

	// 初始化数据
	private final int INIT_DATA = 5;

	// 处理拍完的照片
	private final int DISPOSE_PHOTO = 3;

	// 头像
	private RoundView head;

	// 修改之前数据和修改之后的数据
	private String beforeIndata, afteIndata;

	// 设置头像的弹出框
	private TakePhotoPopup popup;

	// 图片保存地址
	private static Uri mUri;

	// 获取屏幕宽度
	private int width;

	private TextView inputPersonName, inputEnglishName, inputJob, inputDept,
			inputPhone, inputFixedphone, inputFAX, inputEmail, inputQQ,
			inputWeixin, inputIDcard, inputIscertificate, trueName;

	private String id, Strcardno, nickName, ename, jobs, deptName, mobile,
			phone, fax, email, qq, wx, company, EntStatusStr, nameId, zhenshi;

	private ProgressShow mProgressShow;

	private FrameLayout personlayout;
	// 卡片号、手机号
	private TextView businessCard, personPphone;
	// 绑定和认证状态
	private Button personBing, isCertificate;
	private String updateJob;

	private static List<JobPojo> jobList = new ArrayList<JobPojo>();

	private CardPojo cardPojo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_layout);
		mContext = this;
		// 获取屏幕信息
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		// 获取屏幕的宽
		width = dm.widthPixels;
		initView();
		initClick();
		initData();
	}

	private void initData() {

		// 根据企业名片号，判断是否显示页面
		if (StringUtils.isNotEmpty(AXSharedPreferences.getEntId(mContext))) {
			personlayout.setVisibility(View.GONE);
		} else {
			personlayout.setVisibility(View.VISIBLE);
		}
		// 认证状态
		if (StringUtils.isNotEmpty(AXSharedPreferences.getEntStatus(mContext))) {
			EntStatusStr = AXSharedPreferences.getEntStatus(mContext);
			if ("0".equals(EntStatusStr)) {
				isCertificate.setText("等待验证");
				isCertificate.setSelected(false);
			} else {
				isCertificate.setText("已绑定");
				isCertificate.setSelected(true);
			}
		} else {
			isCertificate.setVisibility(View.GONE);
		}
		// 获取并显示手机号
		String Strphone = AXSharedPreferences.getUserPhone(mContext);
		personPphone.setText(Strphone);
		// 获取并显示名片号
		Strcardno = AXSharedPreferences.getCardId(mContext);
		businessCard.setText(Strcardno);

		PerfectDataHandler.getInstance(mContext).getMyData(new AXCallBack() {
			@Override
			public void onCallBackString(String retStr) {
				super.onCallBackString(retStr);
				// 返回成功
				if (StringUtils.isNotEmpty(retStr)) {
					// 返回成功
					if (GlobalConstants.HTTP_SUCCESS_CODE.equals(GsonUtils
							.getJsonValue(retStr, GlobalConstants.HTTP_CODE))) {
						String data = GsonUtils.getJsonValue(retStr, "data");
						if (StringUtils.isNotEmpty(data)) {
							cardPojo = GsonUtils.toObject(data, CardPojo.class);
							String path = cardPojo.getIconUrl();
							head.setTag(path);
							if (StringUtils.isNotEmpty(path)) {
								MasterImg.getInstance(mContext).loadImg(path,
										head, true, true, 0,
										R.drawable.toux_default);
							}
							zhenshi = StringUtils.isNotEmpty(cardPojo
									.getTrueName()) ? cardPojo.getTrueName()
									: "";
							trueName.setText(zhenshi);
							setMapValue("trueName", zhenshi);
							// 昵称
							nickName = StringUtils.isNotEmpty(cardPojo
									.getNickName()) ? cardPojo.getNickName()
									: "";
							inputPersonName.setText(nickName);
							setMapValue("nickName", nickName);
							// 英文名
							ename = StringUtils.isNotEmpty(cardPojo.getEname()) ? cardPojo
									.getEname() : "";
							inputEnglishName.setText(ename);
							setMapValue("ename", ename);
							// 职位
							jobList = cardPojo.getPositionList();
							if (jobList.size() > 0) {
								jobs = cardPojo.getJobs();
								StringBuilder sb = new StringBuilder();
								for (JobPojo jobPojo : jobList) {
									sb.append(jobPojo.getPositionName() + " ");
								}
								String content = sb.toString();
								if (content.length() > 12) {
									updateJob = content.substring(0, 12)
											+ "...";
								} else {
									updateJob = content;
								}
								setMapValue("job", jobList);
							}
							inputJob.setText(updateJob);
							// 部门
							deptName = StringUtils.isNotEmpty(cardPojo
									.getDeptNames()) ? cardPojo.getDeptNames()
									: "";
							inputDept.setText(deptName);
							setMapValue("deptName", deptName);
							mobile = AXSharedPreferences.getUserPhone(mContext);
							inputPhone.setText(AXSharedPreferences
									.getUserPhone(mContext));
							// 座机
							phone = StringUtils.isNotEmpty(cardPojo.getPhone()) ? cardPojo
									.getPhone() : "";
							inputFixedphone.setText(phone);
							setMapValue("phone", phone);
							// 传真
							fax = StringUtils.isNotEmpty(cardPojo.getFax()) ? cardPojo
									.getFax() : "";
							inputFAX.setText(fax);
							setMapValue("fax", fax);
							// 邮箱
							email = StringUtils.isNotEmpty(cardPojo.getEmail()) ? cardPojo
									.getEmail() : "";
							inputEmail.setText(email);
							setMapValue("email", email);
							// qq
							qq = StringUtils.isNotEmpty(cardPojo.getQq()) ? cardPojo
									.getQq() : "";
							inputQQ.setText(qq);
							setMapValue("qq", qq);
							// 微信
							wx = StringUtils.isNotEmpty(cardPojo.getWeiXin()) ? cardPojo
									.getWeiXin() : "";
							inputWeixin.setText(wx);
							setMapValue("wx", wx);
							// 所在企业
							company = StringUtils.isNotEmpty(cardPojo
									.getOrgName()) ? cardPojo.getOrgName() : "";
							inputIscertificate.setText(company);
							setMapValue("company", company);

							nameId = StringUtils.isNotEmpty(cardPojo
									.getUserIdcard()) ? cardPojo
									.getUserIdcard() : "";
							inputIDcard.setText(nameId);
							setMapValue("nameId", nameId);

							beforeIndata = zhenshi + nickName + ename
									+ deptName + updateJob + mobile + phone
									+ fax + email + qq + wx + company + nameId;
						}

					} else {
						DialogUtil.showSystemToast(mContext, GsonUtils
								.getJsonValue(retStr, GlobalConstants.HTTP_MSG));
					}

				}
			}

		});
	}

	@Override
	public void onResume() {
		super.onResume();
		if(null == getMap()){
			return;
		}
		// 获取昵称
		if (StringUtils
				.isNotEmpty(null != getMapValue("nickName") ? getMapValue(
						"nickName").toString() : "")) {
			inputPersonName.setText(getMapValue("nickName").toString());
		} else {
			inputPersonName.setText("");
		}
		// 获取英文名
		if (StringUtils.isNotEmpty(null != getMapValue("ename") ? getMapValue(
				"ename").toString() : "")) {
			inputEnglishName.setText(getMapValue("ename").toString());
		} else {
			inputEnglishName.setText("");
		}

		// 获取部门
		if (StringUtils
				.isNotEmpty(null != getMapValue("deptName") ? getMapValue(
						"deptName").toString() : "")) {
			inputDept.setText(getMapValue("deptName").toString());
		} else {
			inputDept.setText("");
		}
		// 获取职位
		List<JobPojo> list = new ArrayList<JobPojo>();
		list = (List<JobPojo>) getMapValue("job");
		if (null != list) {
			if (list.size() > 0) {
				StringBuilder sb = new StringBuilder();
				StringBuilder sb2 = new StringBuilder();
				for (JobPojo jobPojo : list) {
					sb.append(jobPojo.getPositionName() + ",");
					sb2.append(jobPojo.getPositionName() + " ");
				}
				if (CheckStringUtils.compare(list, jobs)) {
					inputJob.setText(updateJob);
				} else {
					String content = sb2.toString();
					if (content.length() > 12) {
						updateJob = content.substring(0, 12) + "...";
					} else {
						updateJob = content;
					}
					inputJob.setText(updateJob);
					jobs = sb.toString();
					setMapValue("job", list);
				}
			}
		}
		inputPhone.setText(AXSharedPreferences.getUserPhone(mContext));
		// 获取座机
		if (StringUtils.isNotEmpty(null != getMapValue("phone") ? getMapValue(
				"phone").toString() : "")) {
			inputFixedphone.setText(getMapValue("phone").toString());
		} else {
			inputFixedphone.setText("");
		}
		// 获取传真
		if (StringUtils.isNotEmpty(null != getMapValue("fax") ? getMapValue(
				"fax").toString() : "")) {
			inputFAX.setText(getMapValue("fax").toString());
		} else {
			inputFAX.setText("");
		}
		// 获取邮箱
		if (StringUtils.isNotEmpty(null != getMapValue("email") ? getMapValue(
				"email").toString() : "")) {
			inputEmail.setText(getMapValue("email").toString());
		} else {
			inputEmail.setText("");
		}
		// 获取QQ
		if (StringUtils
				.isNotEmpty(null != getMapValue("qq") ? getMapValue("qq")
						.toString() : "")) {
			inputQQ.setText(getMapValue("qq").toString());
		} else {
			inputQQ.setText("");
		}
		// 获取微信
		if (StringUtils
				.isNotEmpty(null != getMapValue("wx") ? getMapValue("wx")
						.toString() : "")) {
			inputWeixin.setText(getMapValue("wx").toString());
		} else {
			inputWeixin.setText("");
		}
		// 获取所在企业
		if (StringUtils
				.isNotEmpty(null != getMapValue("company") ? getMapValue(
						"company").toString() : "")) {
			inputIscertificate.setText(getMapValue("company").toString());
		}
		// 获取所在企业
		if (StringUtils.isNotEmpty(null != getMapValue("nameId") ? getMapValue(
				"nameId").toString() : "")) {
			inputIDcard.setText(getMapValue("nameId").toString());
		} else {
			inputIDcard.setText("");
		}
		// 获取所在企业
		if (StringUtils
				.isNotEmpty(null != getMapValue("trueName") ? getMapValue(
						"trueName").toString() : "")) {
			trueName.setText(getMapValue("trueName").toString());
		} else {
			trueName.setText("");
		}
	}

	/**
	 * @Auther: zhoumc
	 * @Description: 初始化控件
	 * @Date:2015-6-12下午1:47:42
	 * @return void
	 * @说明 代码版权归 杭州安存网络科技有限公司 所有
	 */
	private void initView() {
		trueNameRe = (RelativeLayout) findViewById(R.id.person_true_name_re);
		trueName = (TextView) findViewById(R.id.true_name);
		// 标题
		pubTitle = (PubTitle) findViewById(R.id.person_title);
		// 绑定布局
		personlayout = (FrameLayout) findViewById(R.id.person);
		// 认证状态
		isCertificate = (Button) findViewById(R.id.iscertificate);
		// 头像
		head = (RoundView) findViewById(R.id.person_head);
		// 昵称
		personNameRe = (RelativeLayout) findViewById(R.id.person_name_re);
		// 英文名
		personEnglishName = (RelativeLayout) findViewById(R.id.person_english_name);
		// 名片号
		personBusinessCard = (RelativeLayout) findViewById(R.id.person_business_card);
		// 部门
		personDept = (RelativeLayout) findViewById(R.id.person_dept);
		// 职务
		personJob = (RelativeLayout) findViewById(R.id.person_job);
		// 手机号
		personPhoneRe = (RelativeLayout) findViewById(R.id.person_phone_re);
		// 座机
		personFixedphoneRe = (RelativeLayout) findViewById(R.id.person_fixedphone_re);
		// 传真
		personFAXRe = (RelativeLayout) findViewById(R.id.person_FAX_re);
		// 邮箱
		personEmailRe = (RelativeLayout) findViewById(R.id.person_email_re);
		// QQ
		personQqRe = (RelativeLayout) findViewById(R.id.person_QQ_re);
		// 微信
		personWeixinRe = (RelativeLayout) findViewById(R.id.person_weixin_re);
		// 身份证
		personIDcardRe = (RelativeLayout) findViewById(R.id.person_iDcard_re);
		// 是否认证
		personIscertificateRe = (RelativeLayout) findViewById(R.id.person_iscertificate_re);
		// 名片号的ID
		businessCard = (TextView) findViewById(R.id.business_card);
		// 手机号的ID
		personPphone = (TextView) findViewById(R.id.person_phone);
		// 昵称
		inputPersonName = (TextView) findViewById(R.id.person_name);
		// 英文名
		inputEnglishName = (TextView) findViewById(R.id.english_name);
		// 职位
		inputJob = (TextView) findViewById(R.id.job);
		// 部门
		inputDept = (TextView) findViewById(R.id.dept);
		// 电话
		inputPhone = (TextView) findViewById(R.id.person_phone);
		// 座机
		inputFixedphone = (TextView) findViewById(R.id.person_fixedphone);
		// 传真
		inputFAX = (TextView) findViewById(R.id.person_FAX);
		// 邮箱
		inputEmail = (TextView) findViewById(R.id.person_email);
		// QQ
		inputQQ = (TextView) findViewById(R.id.person_QQ);
		// 微信
		inputWeixin = (TextView) findViewById(R.id.person_weixin);
		// 身份证
		inputIDcard = (TextView) findViewById(R.id.person_iDcard);
		// 所在企业
		inputIscertificate = (TextView) findViewById(R.id.person_iscertificate);
		// 绑定
		personBing = (Button) findViewById(R.id.person_bing);

	}

	/**
	 * @Auther: zhuwt
	 * @Description: 初始化点击事件
	 * @Date:2015年6月9日下午11:00:37
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initClick() {
		personBusinessCard.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent mIntent = new Intent(mContext, QrDetailsActivity.class);
				mIntent.putExtra("cardId",
						AXSharedPreferences.getCardId(mContext));
				mIntent.putExtra("icon", cardPojo.getIconUrl() + "");
				mIntent.putExtra("name", cardPojo.getTrueName());
				startActivity(mIntent);
			}
		});
		trueNameRe.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(mContext, PersonTrueNameActivity.class));
			}
		});

		inputIscertificate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (StringUtils.isNotEmpty(AXSharedPreferences
						.getEntId(mContext))) {
					Intent mIntent = new Intent(mContext, CompanyDetails.class);
					mIntent.putExtra("entId",
							AXSharedPreferences.getEntId(mContext));
					startActivity(mIntent);
				}
			}
		});

		// 标题点击事件
		pubTitle.setShowLeftOrRight(true, false, true).setOnTitleClickListener(
				new OnTitleClickListener() {
					@Override
					public void onRightClick() {
						zhenshi = trueName.getText().toString();
						// 昵称
						nickName = inputPersonName.getText().toString();
						// 英文名
						ename = inputEnglishName.getText().toString();
						// 部门
						deptName = inputDept.getText().toString();
						// 手机号
						mobile = inputPhone.getText().toString();
						// 座机
						phone = inputFixedphone.getText().toString();
						// 传真
						fax = inputFAX.getText().toString();
						// 邮箱
						email = inputEmail.getText().toString();
						// QQ
						qq = inputQQ.getText().toString();
						// 微信
						wx = inputWeixin.getText().toString();

						// 所在企业
						company = inputIscertificate.getText().toString();
						nameId = inputIDcard.getText().toString();
						if (null == mProgressShow
								&& !PersonActivity.this.isFinishing()) {
							mProgressShow = new ProgressShow(mContext,
									"保存中......");
							mProgressShow.show();
						} else {
							mProgressShow.show();
						}

						mHandler.sendEmptyMessageDelayed(UPDATE_PERSON, 0);

					}

					@Override
					public void onLeftClick() {
						zhenshi = trueName.getText().toString();
						// 昵称
						nickName = inputPersonName.getText().toString();
						// 英文名
						ename = inputEnglishName.getText().toString();
						// 部门
						deptName = inputDept.getText().toString();
						// 手机号
						mobile = inputPhone.getText().toString();
						// 座机
						phone = inputFixedphone.getText().toString();
						// 传真
						fax = inputFAX.getText().toString();
						// 邮箱
						email = inputEmail.getText().toString();
						// QQ
						qq = inputQQ.getText().toString();
						// 微信
						wx = inputWeixin.getText().toString();
						nameId = inputIDcard.getText().toString();

						// 所在企业
						company = inputIscertificate.getText().toString();
						afteIndata = zhenshi + nickName + ename + deptName
								+ updateJob + mobile + phone + fax + email + qq
								+ wx + company + nameId;
						if (beforeIndata.equals(afteIndata)) {
							finish();
						} else {
							new AxinDialog(mContext, AxinDialog.DIALOG_CHANGE)
									.setTitle("是否确定修改数据")
									.setCancelStr("否")
									.setConfirmStr("是")
									.setConfirmClick(new AxDialogClickListen() {

										@Override
										public void onClick(
												AxinDialog axinDialog) {
											if (null == mProgressShow
													&& !PersonActivity.this
															.isFinishing()) {
												mProgressShow = new ProgressShow(
														mContext, "保存中......");
												mProgressShow.show();
											} else {
												mProgressShow.show();
											}

											mHandler.sendEmptyMessageDelayed(
													UPDATE_PERSON, 0);
											axinDialog.dismiss();
										}

									})
									.setCancelClick(new AxDialogClickListen() {

										@Override
										public void onClick(
												AxinDialog axinDialog) {
											finish();

										}

									}).show();
						}

					}

				});

		// 头像点击事件
		head.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 弹出框初始化
				popup = new TakePhotoPopup(PersonActivity.this, itemsOnClick);
				// 显示窗口
				popup.showAtLocation(
						PersonActivity.this.findViewById(R.id.person_main),
						// 设置layout在PopupWindow中显示的位置
						Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
			}
		});
		// 昵称点击事件
		personNameRe.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(PersonActivity.this,
						PersonNameActivity.class));

			}
		});
		// 英文名点击事件
		personEnglishName.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(PersonActivity.this,
						PersonEenglishNameActivity.class));
			}
		});
		// 部门点击事件
		personDept.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(PersonActivity.this,
						PersonDeptJobActivity.class));

			}
		});
		// 职务
		personJob.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(PersonActivity.this,
						PersonPostActivity.class));

			}
		});

		// 手机号点击事件
		personPhoneRe.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(PersonActivity.this,
						AccountSafePhoneActivity.class));
			}
		});
		// 座机点击事件
		personFixedphoneRe.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(PersonActivity.this,
						PersonFixedphoneReActivity.class));
			}
		});
		// 传真
		personFAXRe.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(PersonActivity.this,
						PersonFAXReActivity.class));
			}
		});
		// 邮箱点击事件
		personEmailRe.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(PersonActivity.this,
						PersonEmailReActivity.class));
			}
		});
		// QQ点击事件
		personQqRe.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(PersonActivity.this,
						PersonQqReActivity.class));

			}
		});
		// 微信点击事件
		personWeixinRe.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				startActivity(new Intent(PersonActivity.this,
						PersonWeixinReActivity.class));
			}
		});
		// 身份证点击事件
		personIDcardRe.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(PersonActivity.this,
						PersonNameIdActivity.class));
			}
		});

		// 绑定
		personBing.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(PersonActivity.this,
						BindCompanyActivity.class));
			}
		});

	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			// 完善资料
			case UPDATE_PERSON:
				PerfectDataHandler.getInstance(mContext).updatePersonInfo(
						Strcardno, nickName, zhenshi, ename, jobs, deptName,
						mobile, phone, fax, email, qq, wx, nameId,
						new AXCallBack() {
							public void onCallBackString(String retStr) {
								// 返回成功
								if (StringUtils.isNotEmpty(retStr)) {
									// 返回成功
									if (GlobalConstants.HTTP_SUCCESS_CODE
											.equals(GsonUtils.getJsonValue(
													retStr,
													GlobalConstants.HTTP_CODE))) {
										DialogUtil.showSystemToast(mContext,
												"修改成功");
										HomeSharedPreferences.setTrueName(
												mContext, zhenshi);
										HomeSharedPreferences.setNickName(mContext, nickName);
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
								finish();

							};
						});
				break;

			case INIT_DATA:
				initData();
				break;
			default:
				break;
			}
		};
	};
	// 自定义的点击事件 用在设置头像的弹出框 对应 拍照 相册选择 取消
	private OnClickListener itemsOnClick = new OnClickListener() {

		public void onClick(View v) {
			popup.dismiss();
			switch (v.getId()) {
			// 拍照
			case R.id.btn_take_photo:
				takePhoto();
				break;

			// 相册选择
			case R.id.btn_pick_photo:
				mUri = getLocalUrl();
				Intent mIntents = null;
				mIntents = MasterImg.getInstance(mContext).takeAlbum(mUri,
						PersonConstants.HPX, PersonConstants.BPX);
				startActivityForResult(mIntents, CHOOSE_ALBUM);
				break;
			default:
				break;
			}
		}
	};

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		// 相册选择处理方法
		case CHOOSE_ALBUM:
			if (-1 != resultCode) {
				return;
			}
			Bitmap mBitmap;
			try {
				mBitmap = MasterImg.getInstance(mContext).getBitmapByUri(mUri);
				if (null == mBitmap)
					return;
				// 显示图片 并上传图片
				uploadPhoto(mBitmap);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		// 拍照返回
		case TAKE_PHONE_BACK:
			Intent mIntent = null;
			mIntent = MasterImg.getInstance(mContext).cropBytakeP(mUri,
					PersonConstants.HPX, PersonConstants.BPX);
			startActivityForResult(mIntent, DISPOSE_PHOTO);
			break;

		// 拍照处理方法
		case DISPOSE_PHOTO:

			if (-1 != resultCode) {
				return;
			}
			Bitmap mBitmap2;
			try {
				mBitmap2 = MasterImg.getInstance(mContext).getBitmapByUri(mUri);
				// 显示图片 并上传图片
				uploadPhoto(mBitmap2);
			} catch (IOException e) {
				e.printStackTrace();
			}

			break;
		default:
			break;
		}
	};

	/**
	 * @Auther: zhuwt
	 * @Description: 拍照方法
	 * @Date:2015年6月9日下午11:03:56
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void takePhoto() {
		// 设置每次的地址不同 防止图片重复
		mUri = getLocalUrl();
		Intent mIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		mIntent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
		startActivityForResult(mIntent, TAKE_PHONE_BACK);
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 生成的图片名称 用时间戳和 UID命名 防止重复
	 * @Date:2015年6月9日下午11:12:21
	 * @return
	 * @return Uri
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private Uri getLocalUrl() {
		String path;
		// 本地是否存在SD卡
		if (!MasterUtils.isExitsSdcard()) {
			File mFile = new File(GlobalConstants.PHOTO_SDK_PATH);
			if (!mFile.exists()) {
				Log.i("xx", mFile.mkdirs() + "");
			}
			path = "file://" + GlobalConstants.PHOTO_SDK_PATH + "uid_"
					+ System.currentTimeMillis() + ".jpg";
		} else {
			File mFile = new File(GlobalConstants.PHOTO_LOCAL_PATH);
			if (!mFile.exists()) {
				Log.i("xx", mFile.mkdirs() + "");
			}
			path = "file://" + GlobalConstants.PHOTO_LOCAL_PATH + "uid_"
					+ System.currentTimeMillis() + ".jpg";
		}
		return Uri.parse(path);
	}

	/**
	 * @Auther: zhuwt
	 * @Description:上传头像并显示
	 * @Date:2015年6月9日下午11:26:07
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void uploadPhoto(Bitmap bitmap) {
		Matrix m = new Matrix();
		// 图片按一定比例先放大显示 然后在居中
		float w = head.getWidth();
		float bw = bitmap.getWidth();
		float bh = bitmap.getHeight();
		// 图片压缩至需要的大小
		m.postScale(w / bw, (float) (126.0 / bh));
		bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), m, true);
		head.setImageBitmap(bitmap);

		// 开始上传
		File img = new File(mUri.getPath());
		try {
			String url = GlobalConstants.URL + "?"
					+ InterfaceConstants.INTERFACE_NAME + "="
					+ InterfaceConstants.UPLOAD_IMG_CODE + "&tokenid="
					+ AXSharedPreferences.getTokenId(mContext);
			RequestParams params = new RequestParams();
			params.put("tokenid", AXSharedPreferences.getTokenId(mContext));
			params.put("stream", img);
			params.put(InterfaceConstants.INTERFACE_NAME,
					InterfaceConstants.UPLOAD_IMG_CODE);
			MasterHttpClient.postUploadFileByUrl(mContext, url, params,
					new AsyncHttpResponseHandler() {

						@Override
						public void onSuccess(int arg0, Header[] arg1,
								byte[] arg2) {
							String data = new String(arg2);
							if (StringUtils.isNotEmpty(data)) {
								String path = GsonUtils.getJsonValue(data,
										"data");
								HomeSharedPreferences.setHeadPath(mContext,
										path);
							} else {

							}
						}

						@Override
						public void onFailure(int arg0, Header[] arg1,
								byte[] arg2, Throwable arg3) {
							Log.i(TAG, "code = " + arg0);
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void finish() {
		super.finish();
		if (null != mProgressShow) {
			mProgressShow.dismiss();
		}
	}
}

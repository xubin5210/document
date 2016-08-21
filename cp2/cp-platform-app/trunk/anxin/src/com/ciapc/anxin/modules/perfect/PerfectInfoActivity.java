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
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.ciapc.anxin.common.view.TakePhotoPopup;
import com.ciapc.anxin.modules.home.HomeSharedPreferences;
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
 * 
 * @author zhuwt
 * @Description:完善个人信息
 * @ClassName: PerfectInfoActivity.java
 * @date 2015年6月10日 上午10:32:28
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */
public class PerfectInfoActivity extends BaseActivity {

	private Context mContext;

	private final String TAG = "PerfectInfoActivity";

	// 名字 职位 公司 身份证号
	private LinearLayout name, job, bindCompany, email;

	// 名片号
	private TextView cardId;

	// 标题头
	private PubTitle pubTitle;

	// 拍照返回
	private final int TAKE_PHONE_BACK = 1;

	// 拍照返回
	private final int CHOOSE_ALBUM = 2;

	// 处理拍完的照片
	private final int DISPOSE_PHOTO = 3;

	private final int UPDATE_PERSON = 4;

	// 初始化数据
	private final int INIT_DATA = 5;

	// 头像
	private ImageView head;

	// 设置头像的弹出框
	private TakePhotoPopup popup;

	// 图片保存地址
	private static Uri mUri;

	// 获取屏幕宽度
	private int width;

	private TextView inputName, inputJob, inputEmail, inputCompany, jobFlag;

	private String trueName, jobs, emails, company;
	private String updateJob;

	private String id;

	private ProgressShow mProgressShow;

	// 是否完善资料
	private String type = "2";

	private String first, second;

	private List<JobPojo> a = new ArrayList<JobPojo>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.perfect_info_layout);
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

	/**
	 * @Auther: zhuwt
	 * @Description:初始化数据
	 * @Date:2015年6月18日下午3:51:46
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initData() {
		String path = HomeSharedPreferences.getHeadPath(mContext);
		if (StringUtils.isNotEmpty(path) && !"null".equals(path)) {
			head.setTag(path);
			MasterImg.getInstance(mContext).loadImg(path, head, true, true, 0,
					R.drawable.toux_default);
		}
		cardId.setText("手机号: " + AXSharedPreferences.getUserPhone(mContext));
		// 获取个人数据
		mHandler.sendEmptyMessage(INIT_DATA);
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 初始化控件
	 * @Date:2015年6月10日下午2:10:38
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initView() {
		head = (ImageView) findViewById(R.id.perfect_head);
		name = (LinearLayout) findViewById(R.id.perfect_realname);
		bindCompany = (LinearLayout) findViewById(R.id.perfect_for_company);
		job = (LinearLayout) findViewById(R.id.perfect_job);
		pubTitle = (PubTitle) findViewById(R.id.perfect_title);
		cardId = (TextView) findViewById(R.id.perfect_card);
		inputName = (TextView) findViewById(R.id.person_input_truename);
		inputJob = (TextView) findViewById(R.id.person_input_job);
		inputEmail = (TextView) findViewById(R.id.person_input_email);
		// inputNameId = (TextView) findViewById(R.id.person_name_id);
		inputCompany = (TextView) findViewById(R.id.perfect_company);
		email = (LinearLayout) findViewById(R.id.perfect_email);
		jobFlag = (TextView) findViewById(R.id.job_flag);
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 初始化点击事件
	 * @Date:2015年6月9日下午11:00:37
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initClick() {
		// 头像设置
		head.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 弹出框初始化
				popup = new TakePhotoPopup(PerfectInfoActivity.this,
						itemsOnClick);
				// 显示窗口
				popup.showAtLocation(PerfectInfoActivity.this
						.findViewById(R.id.perfect_main),
				// 设置layout在PopupWindow中显示的位置
						Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
			}
		});

		// 去绑定企业
		bindCompany.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(mContext, BindCompanyActivity.class));
			}
		});

		pubTitle.setShowLeftOrRight(true, false, true).setOnTitleClickListener(
				new OnTitleClickListener() {

					@Override
					public void onRightClick() {
						// 姓名
						trueName = inputName.getText().toString();
						// 企业
						company = inputCompany.getText().toString();
						// 邮箱
						emails = inputEmail.getText().toString();
						if (StringUtils.isNotEmpty(trueName)
								&& StringUtils.isNotEmpty(updateJob)
								&& StringUtils.isNotEmpty(company)
								&& StringUtils.isNotEmpty(emails)) {
							if (null == mProgressShow
									&& !PerfectInfoActivity.this.isFinishing()) {
								mProgressShow = new ProgressShow(mContext,
										"保存中......");
								mProgressShow.show();
							} else {
								mProgressShow.show();
							}
							type = "1";
							mHandler.sendEmptyMessageDelayed(UPDATE_PERSON,
									1000);
						} else {
							new AxinDialog(mContext, AxinDialog.DIALOG_CHANGE)
									.setTitle("你还有资料未填写完成，会影响到你其他功能的使用")
									.setCancelStr("继续完善")
									.setConfirmStr("我知道了")
									.setConfirmClick(new AxDialogClickListen() {

										@Override
										public void onClick(
												AxinDialog axinDialog) {
											if (null == mProgressShow
													&& !PerfectInfoActivity.this
															.isFinishing()) {
												mProgressShow = new ProgressShow(
														mContext, "保存中......");
												mProgressShow.show();
											} else {
												mProgressShow.show();
											}
											type = "2";
											mHandler.sendEmptyMessageDelayed(
													UPDATE_PERSON, 1000);
											axinDialog.dismiss();
										}
									})
									.setCancelClick(new AxDialogClickListen() {

										@Override
										public void onClick(
												AxinDialog axinDialog) {
											axinDialog.dismiss();
										}
									}).show();

						}

					}

					@Override
					public void onLeftClick() {
						// 姓名
						trueName = inputName.getText().toString();
						// 企业
						company = inputCompany.getText().toString();
						// 邮箱
						emails = inputEmail.getText().toString();
						second = trueName + company + updateJob + emails;
						if (first.equals(second)) {
							finish();
						} else {
							new AxinDialog(mContext, AxinDialog.DIALOG_CHANGE)
									.setConfirmStr("立即保存")
									.setCancelStr("取消")
									.setTitle("是否保存修改的资料")
									.setCancelClick(new AxDialogClickListen() {

										@Override
										public void onClick(
												AxinDialog axinDialog) {
											axinDialog.dismiss();
											finish();
										}
									})
									.setConfirmClick(new AxDialogClickListen() {

										@Override
										public void onClick(
												AxinDialog axinDialog) {

											if (StringUtils
													.isNotEmpty(trueName)
													&& StringUtils
															.isNotEmpty(jobs)
													&& StringUtils
															.isNotEmpty(company)
													&& StringUtils
															.isNotEmpty(emails)) {
												type = "1";
											} else {
												type = "2";
											}
											if (null == mProgressShow
													&& !PerfectInfoActivity.this
															.isFinishing()) {
												mProgressShow = new ProgressShow(
														mContext, "保存中......");
												mProgressShow.show();
											} else {
												mProgressShow.show();
											}
											mHandler.sendEmptyMessageDelayed(
													UPDATE_PERSON, 1000);
										}
									}).show();
						}
					}
				});
		// 修改名字
		name.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(mContext, PersonTrueNameActivity.class));

			}
		});

		job.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (StringUtils.isNotEmpty(AXSharedPreferences
						.getEntId(mContext))) {
					startActivity(new Intent(mContext, PersonPostActivity.class));
				} else {
					DialogUtil.showSystemToast(mContext, "请先绑定企业后，再设置职位");
				}

			}
		});

		email.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(mContext, PersonEmailActivity.class));
			}
		});

	}

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
						PersonConstants.BPX, PersonConstants.BPX);
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
					PersonConstants.BPX, PersonConstants.BPX);
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
		// 图片裁剪 适应控件大小
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
								if (GlobalConstants.HTTP_SUCCESS_CODE
										.equals(GsonUtils.getJsonValue(data,
												GlobalConstants.HTTP_CODE))) {
									String path = GsonUtils.getJsonValue(data,
											"data");
									HomeSharedPreferences.setHeadPath(mContext,
											path);
								} else {
									DialogUtil.showSystemToast(mContext,
											GsonUtils.getJsonValue(data,
													GlobalConstants.HTTP_MSG));
								}

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

	@SuppressWarnings("unchecked")
	@Override
	public void onResume() {
		// 获取真实名字
		if (StringUtils
				.isNotEmpty(null != getMapValue("trueName") ? getMapValue(
						"trueName").toString() : "")) {
			inputName.setText(getMapValue("trueName").toString());
		}else{
			inputName.setText("");
		}
		// 获取邮箱
		if (StringUtils.isNotEmpty(null != getMapValue("email") ? getMapValue(
				"email").toString() : "")) {
			inputEmail.setText(getMapValue("email").toString());
		}
		else{
			inputEmail.setText("");
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
					if(content.length() > 12){
						updateJob = content.substring(0, 12)+"...";
					}else{
						updateJob = content;
					}
					inputJob.setText(updateJob);
					jobs = sb.toString();
					setMapValue("job", list);
				}
			}
		}
		// 获得企业
		if (StringUtils
				.isNotEmpty(null != getMapValue("company") ? getMapValue(
						"company").toString() : "")) {
			inputCompany.setText(getMapValue("company").toString());
			inputJob.setTextColor(mContext.getResources().getColor(R.color.C2));
			jobFlag.setTextColor(mContext.getResources().getColor(R.color.C2));
		} else {
			jobFlag.setTextColor(mContext.getResources().getColor(R.color.C3));
			inputJob.setTextColor(mContext.getResources().getColor(R.color.C3));
		}
		super.onResume();
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			// 完善资料
			case UPDATE_PERSON:
				PerfectDataHandler.getInstance(mContext).updatePerfect(id,
						trueName, emails, jobs, new AXCallBack() {
							public void onCallBackString(String retStr) {
								// 返回成功
								if (StringUtils.isNotEmpty(retStr)) {
									// 返回成功
									if (GlobalConstants.HTTP_SUCCESS_CODE
											.equals(GsonUtils.getJsonValue(
													retStr,
													GlobalConstants.HTTP_CODE))) {
										AXSharedPreferences.setUserInfoStatus(
												mContext, type);
										HomeSharedPreferences.setTrueName(mContext, trueName);
										PerfectInfoActivity.this.finish();
										DialogUtil.showSystemToast(mContext,
												"修改成功");
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
								if (null != mProgressShow)
									mProgressShow.dismiss();
							};
						});
				break;

			case INIT_DATA:
				getInitData();
				break;
			default:
				break;
			}
		};
	};

	/**
	 * @Auther: zhuwt
	 * @Description: 获取初始化数据
	 * @Date:2015年6月23日下午2:24:31
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void getInitData() {
		PerfectDataHandler.getInstance(mContext).getMyData(new AXCallBack() {

			@Override
			public void onCallBackString(final String retStr) {
				super.onCallBackString(retStr);
				runOnUiThread(new Runnable() {
					public void run() {
						// 返回成功
						if (StringUtils.isNotEmpty(retStr)) {
							// 返回成功
							if (GlobalConstants.HTTP_SUCCESS_CODE
									.equals(GsonUtils.getJsonValue(retStr,
											GlobalConstants.HTTP_CODE))) {
								String data = GsonUtils.getJsonValue(retStr,
										"data");
								if (StringUtils.isNotEmpty(data)) {
									CardPojo cardPojo = GsonUtils.toObject(
											data, CardPojo.class);
									// 真实姓名
									trueName = StringUtils.isNotEmpty(cardPojo
											.getTrueName()) ? cardPojo
											.getTrueName() : "";
									HomeSharedPreferences.setTrueName(mContext,
											trueName);
									inputName.setText(trueName);
									setMapValue("trueName", trueName);
									// 邮箱
									emails = StringUtils.isNotEmpty(cardPojo
											.getEmail()) ? cardPojo.getEmail()
											: "";
									inputEmail.setText(emails);
									setMapValue("email", emails);
									// 如果没有企业ID 则不允许修改职位
									if (StringUtils
											.isNotEmpty(AXSharedPreferences
													.getEntId(mContext))
											&& !"null".equals(AXSharedPreferences
													.getEntId(mContext))) {
										a = cardPojo.getPositionList();
										if (a.size() > 0) {
											jobs = cardPojo.getJobs();
											StringBuilder sb = new StringBuilder();
											for (JobPojo jobPojo : a) {
												sb.append(jobPojo
														.getPositionName());
											}
											String content = sb.toString();
											if(content.length() > 12){
												updateJob = content.substring(0, 12)+"...";
											}else{
												updateJob = content;
											}
											setMapValue("job", a);
										}
										inputJob.setText(updateJob);
										jobFlag.setTextColor(mContext
												.getResources().getColor(
														R.color.C2));
										// // 工作
										// jobs =
										// StringUtils.isNotEmpty(cardPojo
										// .getJobs()) ? cardPojo
										// .getJobs() : "";
										// if (StringUtils.isNotEmpty(jobs)) {
										// String[] strs = jobs.split(",");
										// List<JobPojo> mJobPojos = new
										// ArrayList<JobPojo>();
										// StringBuilder sb = new
										// StringBuilder();
										// for (int i = 0; i < strs.length; i++)
										// {
										// JobPojo pojo = new JobPojo();
										// pojo.setPositionName(strs[i]);
										// mJobPojos.add(pojo);
										// sb.append(strs[i] + " ");
										// }
										// updateJob = sb.toString();
										// setMapValue("job", mJobPojos);
										// }
									} else {
										jobFlag.setTextColor(mContext
												.getResources().getColor(
														R.color.C3));
									}
									company = StringUtils.isNotEmpty(cardPojo
											.getOrgName()) ? cardPojo
											.getOrgName() : "";
									inputCompany.setText(company);
									setMapValue("company", company);
								}
								first = trueName + company + updateJob + emails;
							} else {
								DialogUtil.showSystemToast(mContext, GsonUtils
										.getJsonValue(retStr,
												GlobalConstants.HTTP_MSG));
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
		if (null != mProgressShow) {
			mProgressShow.dismiss();
		}
	}

}

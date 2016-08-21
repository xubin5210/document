package com.ciapc.anxin.modules.perfect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.BaseActivity;
import com.ciapc.anxin.common.GlobalConstants;
import com.ciapc.anxin.common.callback.AXCallBack;
import com.ciapc.anxin.common.pojo.JobPojo;
import com.ciapc.anxin.common.view.CustomEditText;
import com.ciapc.anxin.common.view.ProgressShow;
import com.ciapc.anxin.common.view.PubTitle;
import com.ciapc.anxin.common.view.PubTitle.OnTitleClickListener;
import com.master.util.common.DialogUtil;
import com.master.util.common.GsonUtils;
import com.master.util.common.StringUtils;

/**
 * @author zhuwt
 * @Description:选择职业
 * @ClassName: PersonPostActivity.java
 * @date 2015年6月18日 下午10:08:22
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */

public class PersonPostActivity extends BaseActivity {

	public static final int JOB_DETAIL = 1;

	// 初始化
	private final int INIT_DATA = 2;

	// 上传
	private final int UPLOAD = 3;

	private PubTitle pubTitle;

	private Context mContext;

	private String[] jobs;

	private GridView gridView;

	private PostAdapter adapter;

	private List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

	private CustomEditText inputPost;

	private StringBuilder changeJob = new StringBuilder();

	private List<JobPojo> listJob = new ArrayList<JobPojo>();

	private int count = 0;

	private Button save;

	private String content;

	private ProgressShow progressShow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_post_layout);
		mContext = this;
		initView();
		initClick();
		// initData();
		mHandler.sendEmptyMessage(INIT_DATA);
	}

	/**
	 * @Auther: zhuwt
	 * @Description:初始化数据
	 * @Date:2015年6月19日下午1:55:52
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initData() {
		PerfectDataHandler.getInstance(mContext).getJob(new AXCallBack() {

			@Override
			public void onCallBackString(final String retStr) {
				super.onCallBackString(retStr);
				runOnUiThread(new Runnable() {
					@SuppressWarnings("unchecked")
					public void run() {
						if (StringUtils.isNotEmpty(retStr)) {
							String data = GsonUtils
									.getJsonValue(retStr, "data");
							if (!StringUtils.isNotEmpty(data)) {
								DialogUtil.showSystemToast(mContext,
										"网络异常，请稍后再试!");
								return;
							}
							// 本地是否已经有数据
							boolean flag = false;
							listJob = (List<JobPojo>) getMapValue("job");
							if (null != listJob) {
								if (listJob.size() > 0) {
									flag = true;
								}
							}
							jobs = data.split(",");
							for (int i = 0; i < jobs.length; i++) {
								HashMap<String, String> map = new HashMap<String, String>();
								if (flag) {
									for (JobPojo pojo : listJob) {
										if (pojo.getPositionName().equals(
												jobs[i])) {
											map.put("select", "2");
											break;
										}
									}
									map.put("job", jobs[i]);
								} else {
									map.put("job", jobs[i]);
									map.put("select", "1");
								}
								list.add(map);
							}
							if (null == adapter) {
								adapter = new PostAdapter(mContext);
								gridView.setAdapter(adapter);
							}

						} else {
							DialogUtil.showSystemToast(mContext, "网络异常，请稍后再试!");
						}
					}
				});
			}
		});
	}

	/**
	 * @Auther: zhuwt
	 * @Description:
	 * @Date:2015年6月19日下午1:55:10
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initView() {
		gridView = (GridView) findViewById(R.id.grid);
		pubTitle = (PubTitle) findViewById(R.id.person_post_title);
		inputPost = (CustomEditText) findViewById(R.id.input_post_name);
		save = (Button) findViewById(R.id.perfect_save);
	}

	/**
	 * @Auther: zhuwt
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @Date:2015年6月19日下午1:55:48
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initClick() {

		// 保存提交
		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String str = inputPost.getText().toString();
				if (!StringUtils.isNotEmpty(str)) {
					return;
				}
				if (!StringUtils.onlyNumAndCH(str)) {
					DialogUtil.showSystemToast(mContext, "请不要输入特殊字符");
					return;
				}
				if (count >= 3) {
					DialogUtil.showSystemToast(mContext, "最多只能选择三个职位");
					return;
				}
				for (HashMap<String, String> a : list) {
					if (a.get("job").equals(str)) {
						DialogUtil.showSystemToast(mContext, "请不要重复添加职位");
						return;
					}
				}
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("job", str);
				map.put("select", "2");
				list.add(map);
				adapter = new PostAdapter(mContext);
				gridView.setAdapter(adapter);
				inputPost.setText("");
			}
		});

		pubTitle.setShowLeftOrRight(true, false, false)
				.setOnTitleClickListener(new OnTitleClickListener() {

					@Override
					public void onRightClick() {
					}

					@Override
					public void onLeftClick() {
						if (null == listJob) {
							listJob = new ArrayList<JobPojo>();
						} else {
							listJob.clear();
						}
						for (int i = 0; i < list.size(); i++) {
							if ("2".equals(list.get(i).get("select"))) {
								JobPojo jobPojo = new JobPojo();
								jobPojo.setPositionName(list.get(i).get("job"));
								changeJob.append(list.get(i).get("job") + ",");
								listJob.add(jobPojo);
							}
						}
						if (list.size() > 0) {
							if (listJob.size() > 3) {
								DialogUtil.showSystemToast(mContext,
										"最多只能选择三个职位");
								inputPost.setText("");
								return;
							}
						} else {
							DialogUtil.showSystemToast(mContext, "请选择或输入你的职位");
							return;
						}
						content = changeJob.toString();
						if (null == progressShow) {
							progressShow = new ProgressShow(mContext, "提交中，请稍后");
						}
						progressShow.show();
						mHandler.sendEmptyMessageDelayed(UPLOAD, 1000);
					}
				});

	}

	private Handler mHandler = new Handler() {
		public void handleMessage(final android.os.Message msg) {
			switch (msg.what) {
//			case JOB_DETAIL:
//				runOnUiThread(new Runnable() {
//					public void run() {
//						Bundle bundle = msg.getData();
//						count = bundle.getInt("count");
//						if (GlobalConstants.isDebug) {
//							Log.i("xx", count + "");
//						}
//						String result = bundle.getString("job");
//						changeJob = new StringBuilder(result);
//						int position = bundle.getInt("position");
//						String type = bundle.getString("type");
//						list.get(position).put("select", type);
//						if (count > 3) {
//							DialogUtil.showSystemToast(mContext, "最多只能选择三个职位");
//							return;
//						}
//					}
//				});
//
//				break;

			case INIT_DATA:
				initData();
				break;

			case UPLOAD:
				upload();
				break;

			default:
				break;
			}
		};
	};

	private void upload() {
		PerfectDataHandler.getInstance(mContext).updatePost(content,
				new AXCallBack() {
					public void onCallBackString(final String retStr) {
						runOnUiThread(new Runnable() {
							public void run() {
								// 返回成功
								if (StringUtils.isNotEmpty(retStr)) {
									// 返回成功
									if (GlobalConstants.HTTP_SUCCESS_CODE
											.equals(GsonUtils.getJsonValue(
													retStr,
													GlobalConstants.HTTP_CODE))) {
										DialogUtil.showSystemToast(mContext,
												"修改成功");
										setMapValue("job", listJob);
									} else {
										DialogUtil
												.showSystemToast(
														mContext,
														GsonUtils
																.getJsonValue(
																		retStr,
																		GlobalConstants.HTTP_MSG));

										DialogUtil.showSystemToast(mContext,
												"网络异常，请稍后再试");
									}
								} else {
									DialogUtil.showSystemToast(mContext,
											"网络异常，请稍后再试");
								}
								finish();
							}
						});
					};
				});
	}

	@Override
	public void finish() {
		super.finish();
		if (null != progressShow) {
			progressShow.dismiss();
		}
	}

	private class PostAdapter extends BaseAdapter {

		private Context mContext;

		private LayoutInflater layoutInflater;

		private String post = "";

		public PostAdapter(Context context) {
			mContext = context;
			layoutInflater = LayoutInflater.from(mContext);
			count = 0;
		}

		@Override
		public int getCount() {
			if (null != list) {
				return list.size();
			}
			return 0;
		}

		@Override
		public Object getItem(int arg0) {
			if (null != list) {
				return list.get(arg0);
			}
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View contentView, ViewGroup arg2) {
			ViewHolder holder = null;
			if (null == contentView) {
				contentView = layoutInflater.inflate(R.layout.person_post_item,
						null);
				holder = initViewHolder(holder, contentView);
				contentView.setTag(holder);
			} else {
				holder = (ViewHolder) contentView.getTag();
			}
			initData(holder, position);
			return contentView;
		}

		/**
		 * @Auther: zhuwt
		 * @Description: 初始化数据
		 * @Date:2015年6月19日下午3:03:03
		 * @param holder
		 * @param position
		 * @return void
		 * @说明 代码版权归 杭州反盗版中心有限公司 所有
		 */
		private void initData(final ViewHolder holder, final int position) {
			final Map<String, String> map = list.get(position);
			holder.name.setText(map.get("job"));
			// 已选中
			if ("2".equals(map.get("select"))) {
				holder.name.setSelected(true);
				if (list.get(position).get("job").equals(post)) {
					return;
				} else {
					post = list.get(position).get("job");
				}
				count++;
			}
			if ("1".equals(map.get("select"))) {
				holder.name.setSelected(false);
			}

			holder.name.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View arg0, MotionEvent arg1) {
					// 已经被选中
					if ("2".equals(map.get("select"))) {
						holder.name.setSelected(false);
						list.get(position).put("select", "1");
						count--;
						return false;
					} else {
						count++;
						if (count > 3) {
							DialogUtil.showSystemToast(mContext, "最多只能选择三个职位");
							count--;
							return false;
						}
						holder.name.setSelected(true);
						list.get(position).put("select", "2");
					}
					return false;
				}
			});
		}

		/**
		 * @Auther: zhuwt
		 * @Description:初始化
		 * @Date:2015年6月8日上午10:06:20
		 * @param holder
		 * @param view
		 * @return
		 * @return ViewHolder
		 * @说明 代码版权归 杭州反盗版中心有限公司 所有
		 */
		private ViewHolder initViewHolder(ViewHolder holder, View view) {
			holder = new ViewHolder();
			holder.name = (TextView) view.findViewById(R.id.person_post_item);
			return holder;
		}

		private class ViewHolder {
			private TextView name;
		}
	}
}

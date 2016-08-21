package com.ciapc.anxin.modules.exchange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import u.aly.ah;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.AXSharedPreferences;
import com.ciapc.anxin.common.BaseActivity;
import com.ciapc.anxin.common.GlobalConstants;
import com.ciapc.anxin.common.callback.AXCallBack;
import com.ciapc.anxin.common.pojo.JobPojo;
import com.ciapc.anxin.common.view.CustomEditText;
import com.ciapc.anxin.common.view.ProgressShow;
import com.ciapc.anxin.common.view.PubTitle;
import com.ciapc.anxin.common.view.RoundView;
import com.ciapc.anxin.common.view.PubTitle.OnTitleClickListener;
import com.master.util.common.DialogUtil;
import com.master.util.common.GsonUtils;
import com.master.util.common.StringUtils;
import com.master.util.image.MasterImg;

/**
 * @author zhuwt
 * @Description: 递交名片时 添加验证
 * @ClassName: ExchangeVerifyActivity.java
 * @date 2015年6月21日 下午4:39:24
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */

public class ExchangeVerifyActivity extends BaseActivity {

	private Context mContext;

	private PubTitle pubTitle;

	private GridView gridView;
	private CompanyPeoAdapter adapter;
	private List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

	private CustomEditText verify;

	private String[] strs;

	// 类型 说明 要交换的名片
	private String change, note, cardId;

	private ProgressShow mProgressShow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exchange_verify);
		mContext = this;
		initView();
		initClick();
		initData();
	}

	private void initData() {
		cardId = getIntent().getStringExtra("cardId");
		strs = getResources().getStringArray(R.array.exchange);
		for (int i = 0; i < strs.length; i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			if (strs[i].equals("交友")) {
				map.put("flag", "1");
				change = "交友";
			} else {
				map.put("flag", "2");
			}
			map.put("name", strs[i]);
			list.add(map);
		}
		adapter = new CompanyPeoAdapter(mContext, list);
		gridView.setAdapter(adapter);
	}

	private void initClick() {
		pubTitle.setShowLeftOrRight(true, false, true).setOnTitleClickListener(
				new OnTitleClickListener() {

					@Override
					public void onRightClick() {
						// 开始递交名片
						if (null == mProgressShow
								&& !ExchangeVerifyActivity.this.isFinishing()) {
							mProgressShow = new ProgressShow(mContext);
							mProgressShow.show();
						} else {
							mProgressShow.show();
						}
						new Handler().postDelayed(new Runnable() {

							@Override
							public void run() {
								sendCard();
							}
						}, 1000);
					}

					@Override
					public void onLeftClick() {
						finish();
					}
				});

		// gridView.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
		// long arg3) {
		// if ("2".equals(list.get(arg2).get("flag"))) {
		// change = strs[arg2];
		// arg1.setSelected(true);
		// list.get(arg2).put("flag", "1");
		// return;
		// } else {
		// change = "";
		// arg1.setSelected(false);
		// list.get(arg2).put("flag", "2");
		// }
		// }
		// });
	}

	private void initView() {
		gridView = (GridView) findViewById(R.id.grid);
		verify = (CustomEditText) findViewById(R.id.exchange_verify_input);
		pubTitle = (PubTitle) findViewById(R.id.send_verify_title);
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 递交名片
	 * @Date:2015年6月25日下午6:26:03
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void sendCard() {
		note = verify.getText().toString();
		ExchangeDataHandler.getInstance(mContext).sendCard(cardId,
				AXSharedPreferences.getCardId(mContext), change, note,
				new AXCallBack() {

					@Override
					public void onCallBackString(String retStr) {
						super.onCallBackString(retStr);
						if (StringUtils.isNotEmpty(retStr)) {
							if (GlobalConstants.HTTP_SUCCESS_CODE
									.equals(GsonUtils.getJsonValue(retStr,
											GlobalConstants.HTTP_CODE))) {
								DialogUtil.showSystemToast(mContext,
										"发送成功，请等待对方验证");
								finish();
							} else if ("120007".equals(GsonUtils.getJsonValue(
									retStr, GlobalConstants.HTTP_CODE))) {
								DialogUtil.showSystemToast(mContext, GsonUtils
										.getJsonValue(retStr,
												GlobalConstants.HTTP_MSG));
								mProgressShow.dismiss();
							} else if ("120008".equals(GsonUtils.getJsonValue(
									retStr, GlobalConstants.HTTP_CODE))) {
								DialogUtil.showSystemToast(mContext, GsonUtils
										.getJsonValue(retStr,
												GlobalConstants.HTTP_MSG));
								mProgressShow.dismiss();
							}
						} else {
							DialogUtil.showSystemToast(mContext, "网络异常,请稍后再试");
							mProgressShow.dismiss();
						}
					}
				});

	}

	@Override
	public void finish() {
		if (null != mProgressShow) {
			mProgressShow.dismiss();
		}
		super.finish();
	}

	private class CompanyPeoAdapter extends BaseAdapter {

		private Context context;
		private List<HashMap<String, String>> data;
		private int count;

		public CompanyPeoAdapter(Context context,
				List<HashMap<String, String>> data) {
			this.context = context;
			this.data = data;
			count = 0;
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
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.person_post_item, null);
			}
			final TextView head = (TextView) convertView
					.findViewById(R.id.person_post_item);
			final HashMap<String, String> map = list.get(position);
			if (map.get("flag").equals("1")) {
				head.setSelected(true);
				count++;
			} else {
				head.setSelected(false);
			}
			head.setText(map.get("name"));
			head.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if (head.isSelected()) {
						DialogUtil.showSystemToast(context, "至少选择一个哦");
					} else {
						map.put("flag", "1");
						head.setSelected(true);
						change = map.get("name");
						for (HashMap<String, String> str : list) {
							if (!str.get("name").equals(map.get("name"))) {
								str.put("flag", "2");
							}
						}
						notifyDataSetChanged();
					}
				}
			});
			return convertView;
		}

	}
}

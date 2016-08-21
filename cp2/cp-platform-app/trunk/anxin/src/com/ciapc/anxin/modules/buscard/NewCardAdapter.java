package com.ciapc.anxin.modules.buscard;

import java.util.List;
import java.util.Map;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.AXSharedPreferences;
import com.ciapc.anxin.common.GlobalConstants;
import com.ciapc.anxin.common.callback.AXCallBack;
import com.ciapc.anxin.common.pojo.ExchangePojo;
import com.ciapc.anxin.common.view.RoundView;
import com.ciapc.anxin.modules.details.PersonDetails;
import com.ciapc.anxin.modules.exchange.ExchangeDataHandler;
import com.ciapc.anxin.modules.home.HomeDataHandler;
import com.ciapc.anxin.utils.ListView.XListView;
import com.master.util.common.DialogUtil;
import com.master.util.common.GsonUtils;
import com.master.util.common.StringUtils;
import com.master.util.image.MasterImg;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NewCardAdapter extends BaseAdapter {

	private Context mContext;

	private LayoutInflater layoutInflater;

	private List<ExchangePojo> list;

	private final int INSERT = 1;

	public NewCardAdapter(Context context, List<ExchangePojo> data) {
		mContext = context;
		layoutInflater = LayoutInflater.from(mContext);
		list = data;
	}

	public void setDate(List<ExchangePojo> data) {
		if (null != data) {
			list = data;
		}
		notifyDataSetChanged();
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
	
	/**
	 * @Auther: zhuwt
	 * @Description: 单条刷新
	 * @Date:2015年3月20日下午3:12:30
	 * @param mListView
	 * @param id
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void RefreshIte(XListView mListView, Map<String, String> map) {
		if (mListView != null) {
			int start = mListView.getFirstVisiblePosition() + 1;
			for (int i = start, j = mListView.getLastVisiblePosition(); i <= j; i++)
				if (map == mListView.getItemAtPosition(i)) {
					View view = mListView.getChildAt(i);
					getView(i - 1, view, mListView);
					break;
				}
		}
	}

	@Override
	public View getView(int position, View contentView, ViewGroup arg2) {
		ViewHolder holder = null;
		if (null == contentView) {
			contentView = layoutInflater.inflate(R.layout.new_card_item, null);
			holder = initViewHolder(holder, contentView);
			contentView.setTag(holder);
		} else {
			holder = (ViewHolder) contentView.getTag();
		}
		initClick(holder, position);
		return contentView;
	}

	/**
	 * @Auther: zhuwt
	 * @Description:点击事件
	 * @Date:2015年6月23日上午10:50:41
	 * @param holder
	 * @param position
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initClick(final ViewHolder holder, final int position) {
		final ExchangePojo pojo = list.get(position);
		String companyName = pojo.getOrgName();
		if(StringUtils.isNotEmpty(companyName)){
			holder.company.setText(companyName);
		}else{
			holder.company.setText("未填写");
		}
		holder.applyType.setText(pojo.getApplyType());
		holder.applyNote.setText(pojo.getApplyNote());
		String path = pojo.getIconUrl();
		if (StringUtils.isNotEmpty(path)) {
			holder.head.setTag(path);
			MasterImg.getInstance(mContext).loadImg(path, holder.head, true,
					true, 0, R.drawable.toux_default);
		} else {
			holder.head.setTag("");
			holder.head.setImageResource(R.drawable.toux_default);
		}
		// 未接受状态
		String trueName = pojo.getTrueName();
		if ("0".equals(pojo.getIsExchange())) {
			if (StringUtils.isNotEmpty(trueName)) {
				if (trueName.length() > 1) {
					trueName = trueName.substring(1, trueName.length());
					holder.name.setText(Html.fromHtml("*" + trueName));
				} else {
					holder.name.setText(Html.fromHtml("*"));
				}
			} else {
				holder.name.setText("无名氏");
			}
			holder.accept.setText("收名片");
			holder.accept.setTextColor(mContext.getResources().getColor(
					R.color.white));
			holder.accept
					.setBackgroundResource(R.drawable.accept_new_card_style);
		}
		if ("1".equals(pojo.getIsExchange())) {
			if (StringUtils.isNotEmpty(trueName)) {
				holder.name.setText(trueName);
			} else {
				holder.name.setText("无名氏");
			}
			holder.accept.setText("已交换");
			holder.accept
					.setBackgroundResource(R.drawable.accept_alread_card_style);
			holder.accept.setTextColor(mContext.getResources().getColor(
					R.color.C3));
		}
		if ("2".equals(pojo.getIsExchange())) {
			if (StringUtils.isNotEmpty(trueName)) {
				if (trueName.length() > 1) {
					trueName = trueName.substring(1, trueName.length());
					holder.name.setText(Html.fromHtml("*" + trueName));
				} else {
					holder.name.setText(Html.fromHtml("*"));
				}
			} else {
				holder.name.setText("无名氏");
			}
			holder.accept.setText("等待验证");
			holder.accept.setTextColor(mContext.getResources().getColor(
					R.color.C8));
			holder.accept
					.setBackgroundResource(R.drawable.accept_verify_card_style);
		}
		holder.accept.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 已经交换过和等待验证 则不允许操作
				if (pojo.getIsExchange().equals("1")
						|| pojo.getIsExchange().equals("2")) {
					return;
				}
				// 添加好友
				ExchangeDataHandler.getInstance(mContext).acceptCard(
						pojo.getApplyId(), new AXCallBack() {
							@Override
							public void onCallBackString(String retStr) {
								super.onCallBackString(retStr);
								if (StringUtils.isNotEmpty(retStr)) {
									// 接收成功
									if (GlobalConstants.HTTP_SUCCESS_CODE
											.equals(GsonUtils.getJsonValue(
													retStr,
													GlobalConstants.HTTP_CODE))) {
										Message msg = new Message();
										msg.what = INSERT;
										msg.obj = pojo;
										mHandler.sendMessage(msg);
										holder.accept.setText("已交换");
										holder.accept
												.setBackgroundResource(R.drawable.accept_alread_card_style);
										holder.accept.setTextColor(mContext
												.getResources().getColor(
														R.color.C3));
									}
								} else {
									DialogUtil.showSystemToast(mContext,
											"出错了，请稍后再试!");
								}

							}
						});
			}
		});

		holder.item.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 已经交换过和等待验证 则不允许操作
				if (pojo.getIsExchange().equals("1")) {
					Intent mIntent = new Intent(mContext, PersonDetails.class);
					mIntent.putExtra("cardId", pojo.getCardId() + "");
					mContext.startActivity(mIntent);
				}
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
		holder.name = (TextView) view.findViewById(R.id.new_card_accept_name);
		holder.company = (TextView) view
				.findViewById(R.id.new_card_accept_company);
		holder.accept = (Button) view.findViewById(R.id.new_card_accept);
		holder.applyType = (Button) view.findViewById(R.id.apply_type);
		holder.applyNote = (TextView) view.findViewById(R.id.apply_note);
		holder.head = (RoundView) view.findViewById(R.id.new_card_accept_head);
		holder.item = (LinearLayout) view.findViewById(R.id.item);
		return holder;
	}

	private class ViewHolder {
		private TextView name;
		private TextView company;
		private Button accept;
		private Button applyType;// 申请类型
		private TextView applyNote;// 申请备注
		private RoundView head;
		private LinearLayout item;
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case INSERT:
				final ExchangePojo pojo = (ExchangePojo) msg.obj;
				pojo.setGmtExchange(System.currentTimeMillis()+"");
				pojo.setUserId(Integer.parseInt(AXSharedPreferences
						.getCardId(mContext)));
				new Thread(new Runnable() {

					@Override
					public void run() {

						HomeDataHandler.getInstance().insertCard(pojo);
					}
				}).start();
				break;

			default:
				break;
			}
		};
	};
}

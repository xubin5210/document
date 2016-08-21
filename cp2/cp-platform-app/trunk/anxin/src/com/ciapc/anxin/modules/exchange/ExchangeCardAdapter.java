package com.ciapc.anxin.modules.exchange;

import java.util.List;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.AXSharedPreferences;
import com.ciapc.anxin.common.pojo.ExchangePojo;
import com.ciapc.anxin.common.view.AxinDialog;
import com.ciapc.anxin.common.view.RoundView;
import com.ciapc.anxin.common.view.AxinDialog.AxDialogClickListen;
import com.ciapc.anxin.modules.details.PersonDetails;
import com.ciapc.anxin.modules.perfect.PerfectInfoActivity;
import com.master.util.common.DialogUtil;
import com.master.util.common.StringUtils;
import com.master.util.image.MasterImg;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ExchangeCardAdapter extends BaseAdapter {

	private Context mContext;

	private LayoutInflater layoutInflater;

	private List<ExchangePojo> list;

	private int type;

	// type = 1 交换名片 type = 2 本地搜索
	public ExchangeCardAdapter(Context context, List<ExchangePojo> data,
			int type) {
		mContext = context;
		layoutInflater = LayoutInflater.from(mContext);
		list = data;
		this.type = type;
	}

	public void setData(List<ExchangePojo> data, int type) {
		list = data;
		this.type = type;
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

	@Override
	public View getView(final int position, View contentView, ViewGroup arg2) {
		ViewHolder holder = null;
		if (null == contentView) {
			contentView = layoutInflater.inflate(
					R.layout.exchange_card_srarch_item, null);
			holder = initViewHolder(holder, contentView);
			contentView.setTag(holder);
		} else {
			holder = (ViewHolder) contentView.getTag();
			holder.name.setText("");
			holder.company.setText("");
		}
		final ExchangePojo pojo = list.get(position);
		String trueName = pojo.getTrueName();
		String companyName = pojo.getOrgName();
		if (StringUtils.isNotEmpty(companyName)) {
			// if (companyName.length() > 10) {
			// companyName = companyName.substring(1, companyName.length());
			// holder.company.setText(companyName+"...");
			// } else {
			holder.company.setText(companyName);
			// }
		} else {
			holder.company.setText("未填写");
		}
		if (1 == type
				|| ((pojo.getCardId() + "").equals(AXSharedPreferences
						.getCardId(mContext)))) {
			holder.name.setText(trueName);
			holder.accept.setVisibility(View.GONE);
		} else {
			holder.accept.setVisibility(View.VISIBLE);
			// 未接受状态
			if ("0".equals(pojo.getIsExchange())) {
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
					holder.accept.setTextColor(mContext.getResources()
							.getColor(R.color.white));
					holder.accept
							.setBackgroundResource(R.drawable.accept_new_card_style);
				}
				holder.accept.setText("递名片");
				holder.accept.setTextColor(mContext.getResources().getColor(
						R.color.white));
				holder.accept
						.setBackgroundResource(R.drawable.add_new_card_style);
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
		}
		String path = pojo.getIconUrl();
		if (StringUtils.isNotEmpty(path)) {
			holder.head.setTag(path);
			MasterImg.getInstance(mContext).loadImg(path, holder.head, true,
					true, 0, R.drawable.toux_default);
		} else {
			holder.head.setTag("");
			holder.head.setImageResource(R.drawable.toux_default);
		}

		holder.accept.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String cardId = pojo.getCardId() + "";
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
				if (!"0".equals(pojo.getIsExchange())) {
					return;
				}
				Intent mIntent = new Intent(mContext,
						ExchangeVerifyActivity.class);
				mIntent.putExtra("cardId", cardId);
				mContext.startActivity(mIntent);
			}
		});

		holder.item.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (type != 1) {
					return;
				}
				Intent mIntent = new Intent(mContext, PersonDetails.class);
				mIntent.putExtra("cardId", list.get(position).getCardId() + "");
				mContext.startActivity(mIntent);
			}
		});
		return contentView;
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
		holder.head = (RoundView) view.findViewById(R.id.new_card_accept_head);
		holder.item = (LinearLayout) view.findViewById(R.id.item);
		return holder;
	}

	private class ViewHolder {
		private TextView name;
		private TextView company;
		private Button accept;
		private RoundView head;
		private LinearLayout item;
		private int flag;// 接受状态 1 接受 2 等待验证 3 已接受
	}
}

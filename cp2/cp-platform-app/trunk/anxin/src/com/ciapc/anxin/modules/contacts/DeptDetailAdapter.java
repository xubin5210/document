package com.ciapc.anxin.modules.contacts;

import java.util.List;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.pojo.ContactPojo;
import com.ciapc.anxin.common.view.RoundView;
import com.master.util.common.StringUtils;
import com.master.util.image.MasterImg;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

public class DeptDetailAdapter extends BaseAdapter implements SectionIndexer {

	private Context mContext;

	private LayoutInflater layoutInflater;

	private List<ContactPojo> list;

	public DeptDetailAdapter(Context context, List<ContactPojo> data) {
		mContext = context;
		layoutInflater = LayoutInflater.from(mContext);
		list = data;
	}

	public void setData(List<ContactPojo> data) {
		list = data;
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
			contentView = layoutInflater.inflate(R.layout.dept_card_item,
					null);
			holder = initViewHolder(holder, contentView);
			contentView.setTag(holder);
		} else {
			holder = (ViewHolder) contentView.getTag();
		}
		final ContactPojo pojo = list.get(position);
		String path = pojo.getIconUrl();
		if (StringUtils.isNotEmpty(path)) {
			holder.head.setTag(path);
			MasterImg.getInstance(mContext).loadImg(path, holder.head, true,
					true, 0, R.drawable.toux_default);
		} else {
			holder.head.setTag("");
			holder.head.setImageResource(R.drawable.toux_default);
		}
		// 当前项在分类索引的位置
		int section = getSectionForPosition(position);
		// 如果当前项位置跟索引位置一样 说明是第一个 则显示inital
		if (position == getPositionForSection(section)) {
			holder.initial.setVisibility(View.VISIBLE);
			holder.initial.setText(pojo.getTnFirstSpell());
		} else {
			holder.initial.setVisibility(View.GONE);
		}
		String trueName = pojo.getTrueName();
		if(StringUtils.isNotEmpty(trueName)){
			holder.name.setText(trueName);
		}else{
			holder.name.setText("未填写");
		}
		String companyName = pojo.getDeptName();
		if(StringUtils.isNotEmpty(companyName)){
			holder.company.setText(companyName);
		}else{
			holder.company.setText("未填写");
		}
//		if (pojo.getCardId().equals(AXSharedPreferences.getCardId(mContext))) {
//			holder.accept.setVisibility(View.INVISIBLE);
//		} else {
//			holder.accept.setVisibility(View.VISIBLE);
//			if ("0".equals(pojo.getIsExchange())) {
//				holder.accept.setText("递交名片");
//				holder.accept
//						.setBackgroundResource(R.drawable.add_new_card_style);
//			}
//			if ("1".equals(pojo.getIsExchange())) {
//				holder.accept.setText("已交换");
//				holder.accept
//						.setBackgroundResource(R.drawable.accept_alread_card_style);
//				holder.accept.setTextColor(mContext.getResources().getColor(
//						R.color.C3));
//			}
//			if ("2".equals(pojo.getIsExchange())) {
//				holder.accept.setText("等待验证");
//				holder.accept.setTextColor(mContext.getResources().getColor(
//						R.color.C8));
//				holder.accept
//						.setBackgroundResource(R.drawable.accept_verify_card_style);
//			}
//		}
//		holder.accept.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				if ("1".equals(pojo.getIsExchange())
//						|| "2".equals(pojo.getIsExchange())) {
//					return;
//				}
//				String cardId = list.get(position).getCardId() + "";
//				if (AXSharedPreferences.getCardId(mContext).equals(cardId)) {
//					DialogUtil.showSystemToast(mContext, "无法递交自己的名片");
//					return;
//				}
//				if ("2".equals(AXSharedPreferences.getUserInfoStatus(mContext))) {
//					new AxinDialog(mContext, AxinDialog.DIALOG_CHANGE)
//							.setConfirmStr("立即完善").setCancelStr("稍后再说")
//							.setTitle("完善个人资料，挂靠实名单位，权威认证，将拓展你的人脉！")
//							.setCancelClick(new AxDialogClickListen() {
//
//								@Override
//								public void onClick(AxinDialog axinDialog) {
//									axinDialog.dismiss();
//
//								}
//							}).setConfirmClick(new AxDialogClickListen() {
//
//								@Override
//								public void onClick(AxinDialog axinDialog) {
//									mContext.startActivity(new Intent(mContext,
//											PerfectInfoActivity.class));
//									axinDialog.dismiss();
//								}
//							}).show();
//					return;
//				}
//				Intent mIntent = new Intent(mContext,
//						ExchangeVerifyActivity.class);
//				mIntent.putExtra("cardId", cardId);
//				mContext.startActivity(mIntent);
//			}
//		});

		holder.item.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent mIntent = new Intent(mContext,ContactDetailActivity.class);
				mIntent.putExtra("cardId", pojo.getCardId()+"");
				mIntent.putExtra("isExchange", pojo.getIsExchange());
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
		holder.head = (RoundView) view.findViewById(R.id.new_card_accept_head);
		holder.initial = (TextView) view.findViewById(R.id.contact_group);
		holder.item = (LinearLayout) view.findViewById(R.id.item);
		return holder;
	}

	private class ViewHolder {
		private TextView name;
		private TextView company;
		private TextView initial;// 首字母
		private RoundView head;
		private LinearLayout item;
	}

	@Override
	public int getPositionForSection(int arg0) {
		for (int i = 0; i < list.size(); i++) {
			char str = list.get(i).getTnFirstSpell().toUpperCase().charAt(0);
			if (str == arg0) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public int getSectionForPosition(int arg0) {
		return list.get(arg0).getTnFirstSpell().charAt(0);
	}

	@Override
	public Object[] getSections() {
		return null;
	}
}

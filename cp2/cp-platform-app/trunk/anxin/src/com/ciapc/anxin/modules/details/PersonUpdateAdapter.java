package com.ciapc.anxin.modules.details;

import java.util.List;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.pojo.PersonUpdatePojo;
import com.master.util.common.StringUtils;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author zhuwt
 * @Description: 跟新名片
 * @ClassName: UpdateCardAdapter.java
 * @date 2015年6月15日 下午4:44:26
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */

public class PersonUpdateAdapter extends BaseAdapter {
	private Context mContext;

	private LayoutInflater layoutInflater;

	private List<PersonUpdatePojo> list;

	public PersonUpdateAdapter(Context context, List<PersonUpdatePojo> data) {
		mContext = context;
		layoutInflater = LayoutInflater.from(mContext);
		list = data;
	}

	public void setDate(List<PersonUpdatePojo> data) {
		if (data != null) {
			list = data;
			notifyDataSetChanged();
		}
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
			contentView = layoutInflater.inflate(R.layout.person_update_item,
					null);
			holder = initViewHolder(holder, contentView);
			contentView.setTag(holder);
		} else {
			holder = (ViewHolder) contentView.getTag();
		}
		final PersonUpdatePojo pojo = list.get(position);
		// 设置时间
		String time = pojo.getDynamicTime();
		if (StringUtils.isNotEmpty(time)) {
			String str = time.substring(0, 10);
			holder.time.setText(str);
		}
		// 设置号码
		String moblie = pojo.getDynamicMobile();
		if (StringUtils.isNotEmpty(moblie)) {
			holder.phone.setText(Html.fromHtml(moblie));
		}

		// 设置职位
		String job = pojo.getDynamicPosition();
		if (StringUtils.isNotEmpty(job)) {
			holder.job.setText(Html.fromHtml(job));
		}

		// 设置部门
		String dep = pojo.getDynamicDepartment();
		if (StringUtils.isNotEmpty(dep)) {
			holder.department.setText(Html.fromHtml(dep));
		}
		holder.type.setBackgroundColor(mContext.getResources().getColor(
				R.color.C8));
		holder.type.setText("在职");
		holder.type.setTextColor(mContext.getResources()
				.getColor(R.color.white));
		// // 设置在职离职状态 0 离职 1在职
		// String status = pojo.getDynamicActiveStatus() + "";
		// if (StringUtils.isNotEmpty(status)) {
		// if ("0".equals(status)) {
		// holder.type.setBackgroundColor(mContext.getResources()
		// .getColor(R.color.C4));
		// holder.type.setText("离职");
		// holder.type.setTextColor(mContext.getResources().getColor(
		// R.color.white));
		// }
		// if ("1".equals(status)) {
		// holder.type.setText("在职");
		// holder.type.setBackgroundColor(mContext.getResources()
		// .getColor(R.color.C8));
		// holder.type.setTextColor(mContext.getResources().getColor(
		// R.color.white));
		// }
		// }

		// holder.item.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// Intent mIntent = new Intent(mContext, PersonDetails.class);
		// mIntent.putExtra("cardId", pojo.getCardId());
		// mContext.startActivity(mIntent);
		// }
		// });
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
		holder.name = (TextView) view.findViewById(R.id.update_name);
		holder.company = (TextView) view.findViewById(R.id.update_company);
		holder.icon = (ImageView) view.findViewById(R.id.update_card_head);
		holder.time = (TextView) view.findViewById(R.id.update_time);
		holder.department = (TextView) view
				.findViewById(R.id.update_department);
		holder.job = (TextView) view.findViewById(R.id.update_job);
		holder.phone = (TextView) view.findViewById(R.id.update_mobile);
		holder.type = (TextView) view.findViewById(R.id.update_status);
		holder.item = (LinearLayout) view.findViewById(R.id.update_item);
		return holder;
	}

	private class ViewHolder {
		private TextView name;
		private TextView company;
		private TextView time;
		private TextView department;
		private TextView job;
		private TextView phone;
		private TextView type;
		private ImageView icon;
		private LinearLayout item;
	}

}

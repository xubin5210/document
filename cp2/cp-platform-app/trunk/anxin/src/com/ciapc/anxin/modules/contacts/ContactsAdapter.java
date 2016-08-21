package com.ciapc.anxin.modules.contacts;

import java.util.ArrayList;
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
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

/**
 * 
 * @author zhuwt
 * @Description: 企业通讯录adapter
 * @ClassName: ContactsAdapter.java
 * @date 2015年5月27日 下午4:41:55
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */
public class ContactsAdapter extends BaseAdapter implements SectionIndexer {

	private Context mContext;
	private LayoutInflater mInflater;

	private List<ContactPojo> list = new ArrayList<ContactPojo>();

	public ContactsAdapter(Context context, List<ContactPojo> data) {
		mContext = context;
		list = data;
		mInflater = LayoutInflater.from(mContext);
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 重新设置数据源
	 * @Date:2015年6月8日下午3:25:40
	 * @param data
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void setData(List<ContactPojo> data) {
		if (null != data) {
			list = data;
			notifyDataSetChanged();
		}
	}

	@Override
	public int getCount() {
		if (null != list)
			return list.size();
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		if (null != list)
			return list.get(arg0);
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
			contentView = mInflater.inflate(R.layout.contact_item_layout, null);
			holder = initViewHolder(holder, contentView);
			contentView.setTag(holder);
		} else {
			holder = (ViewHolder) contentView.getTag();
		}
		String path = list.get(position).getIconUrl();
		if(StringUtils.isNotEmpty(path)){
			holder.head.setTag(path);
			MasterImg.getInstance(mContext).loadImg(path, holder.head, true, true, 0, R.drawable.toux_default);
		} else {
			holder.head.setTag("");
			holder.head.setImageResource(R.drawable.toux_default);
		}
		// 当前项在分类索引的位置
		int section = getSectionForPosition(position);
		// 如果当前项位置跟索引位置一样 说明是第一个 则显示inital
		if (position == getPositionForSection(section)) {
			holder.initial.setVisibility(View.VISIBLE);
			holder.initial.setText(list.get(position).getTnFirstSpell());
		} else {
			holder.initial.setVisibility(View.GONE);
		}
		holder.label.setText(list.get(position).getTrueName());
		holder.department.setText(list.get(position).getDeptName());

		holder.item.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent mIntent = new Intent(mContext,
						ContactDetailActivity.class);
				mIntent.putExtra("cardId", list.get(position).getCardId() + "");
				mIntent.putExtra("isExchange", list.get(position).getIsExchange());
				mContext.startActivity(mIntent);
			}
		});
		return contentView;
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 初始化
	 * @Date:2015年6月8日下午2:55:49
	 * @param holder
	 * @param view
	 * @return
	 * @return ViewHolder
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private ViewHolder initViewHolder(ViewHolder holder, View view) {
		holder = new ViewHolder();
		holder.initial = (TextView) view.findViewById(R.id.contact_group);
		holder.label = (TextView) view.findViewById(R.id.contact_name);
		holder.department = (TextView) view.findViewById(R.id.contact_company);
		holder.item = (RelativeLayout) view.findViewById(R.id.contact);
		holder.head = (RoundView) view.findViewById(R.id.contact_card_head);
		return holder;
	}

	private class ViewHolder {
		private TextView initial;// 首字母
		private TextView label;// 标签
		private TextView department;// 部门
		private RelativeLayout item;
		private RoundView head;
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

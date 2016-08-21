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
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author zhuwt
 * @Description: 企业通讯录同事搜索
 * @ClassName: SearchCompanyAdapter.java
 * @date 2015年6月9日 下午7:30:31
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */

public class ContactColleagueAdapter extends BaseAdapter {

	private Context mContext;

	private List<ContactPojo> list;

	public ContactColleagueAdapter(Context context, List<ContactPojo> data) {
		mContext = context;
		list = data;
	}

	public void setData(List<ContactPojo> data) {
		list = data;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		if (null != list) {
//			if (list.size() > 4) {
//				return 4;
//			}
			return list.size();
		}
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
	public View getView(int position, View contentView, ViewGroup arg2) {
		ViewHolder holder = null;
		if (null == contentView) {
			contentView = LayoutInflater.from(mContext).inflate(
					R.layout.contect_person_item, null);
			holder = initView(holder, contentView);
			contentView.setTag(holder);
		} else {
			holder = (ViewHolder) contentView.getTag();
		}
		initData(holder, position);
		return contentView;
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 初始化数据和点击事件
	 * @Date:2015年6月9日下午7:42:17
	 * @param holder
	 * @param position
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initData(ViewHolder holder, int position) {
		final ContactPojo contactPojo = list.get(position);
		String path = contactPojo.getIconUrl();
		if(StringUtils.isNotEmpty(path)){
			holder.head.setTag(path);
			MasterImg.getInstance(mContext).loadImg(path, holder.head, true, true, 0, R.drawable.toux_default);
		}else{
			holder.head.setTag("");
			holder.head.setImageResource(R.drawable.toux_default);
		}
		String trueName = contactPojo.getTrueName();
		if(StringUtils.isNotEmpty(trueName)){
			holder.name.setText(trueName);
		}else{
			holder.name.setText("无名氏");
		}
		String deptName = contactPojo.getDeptName();
		if(StringUtils.isNotEmpty(deptName)){
			holder.dept.setText(deptName);
		}else{
			holder.dept.setText("无名氏");
		}
		holder.item.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent mIntent = new Intent(mContext,ContactDetailActivity.class);
				mIntent.putExtra("cardId", contactPojo.getCardId()+"");
				mIntent.putExtra("isExchange", contactPojo.getIsExchange());
				mContext.startActivity(mIntent);
			}
		});
		
	}

	/**
	 * @Auther: zhuwt
	 * @Description:初始化控件
	 * @Date:2015年6月9日下午7:37:04
	 * @param holder
	 * @param view
	 * @return
	 * @return ViewHolder
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private ViewHolder initView(ViewHolder holder, View view) {
		holder = new ViewHolder();
		holder.name = (TextView) view.findViewById(R.id.contact_name);
		holder.dept = (TextView) view.findViewById(R.id.contact_dept);
		holder.head = (RoundView) view.findViewById(R.id.contact_card_head);
		holder.item = (LinearLayout) view.findViewById(R.id.contatc_person);
		return holder;
	}

	private class ViewHolder {
		private TextView name;
		private TextView dept;
		private RoundView head;
		private LinearLayout item;
	}

}

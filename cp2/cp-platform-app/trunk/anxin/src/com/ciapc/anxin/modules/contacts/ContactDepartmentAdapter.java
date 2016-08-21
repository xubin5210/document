package com.ciapc.anxin.modules.contacts;

import java.util.List;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.pojo.ContactPojo;

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
 * @Description: 企业通讯录部门搜索
 * @ClassName: SearchCompanyAdapter.java
 * @date 2015年6月9日 下午7:30:31
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */

public class ContactDepartmentAdapter extends BaseAdapter {

	private Context mContext;

	private List<ContactPojo> list;

	public ContactDepartmentAdapter(Context context, List<ContactPojo> data) {
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
					R.layout.contact_department_item, null);
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
	private void initData(ViewHolder holder, final int position) {
		ContactPojo contactPojo = list.get(position);
		holder.content.setText(contactPojo.getDeptName()+"("+contactPojo.getDeptStaffNum()+"人)");
		holder.item.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent mIntent = new Intent(mContext,DeptDetailActivity.class);
				mIntent.putExtra("deptName", list.get(position).getDeptName());
				mIntent.putExtra("deptId", list.get(position).getDeptId());
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
		holder.content = (TextView) view
				.findViewById(R.id.contact_department);
		holder.item = (LinearLayout) view.findViewById(R.id.contact_item);
		return holder;
	}

	private class ViewHolder {
		private TextView content;
		private LinearLayout item;
	}

}

package com.ciapc.anxin.modules.search;

import java.util.List;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.pojo.ExchangePojo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author zhuwt
 * @Description: 搜索结果为个人
 * @ClassName: SearchCompanyAdapter.java
 * @date 2015年6月9日 下午7:30:31
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */

public class SearchPersonAdapter extends BaseAdapter {

	private Context mContext;

	private List<ExchangePojo> list;

	private LayoutInflater inflater;

	public SearchPersonAdapter(Context context, List<ExchangePojo> data) {
		mContext = context;
		list = data;
		inflater = LayoutInflater.from(mContext);
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 重新加载数据源并刷新
	 * @Date:2015年6月9日下午7:56:06
	 * @param data
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void setDate(List<ExchangePojo> data) {
		list = data;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		if (null != list) {
			if (list.size()>0) {
				return list.size();
			}
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
			contentView = inflater.inflate(R.layout.home_search_person, null);
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
		ExchangePojo cardPojo = list.get(position);
		holder.contentA.setText(cardPojo.getTrueName());
		holder.contentB.setText(cardPojo.getOrgName());
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
		holder.contentA = (TextView) view
				.findViewById(R.id.home_search_content_a);
		holder.contentB = (TextView) view
				.findViewById(R.id.home_search_content_b);
		return holder;
	}

	private class ViewHolder {
		private TextView contentA;
		private TextView contentB;
	}

}

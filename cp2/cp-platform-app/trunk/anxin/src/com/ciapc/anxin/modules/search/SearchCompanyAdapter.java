package com.ciapc.anxin.modules.search;

import java.util.List;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.pojo.CompanyPojo;
import com.ciapc.anxin.common.view.RoundView;
import com.ciapc.anxin.modules.details.CompanyDetails;
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
 * @Description: 搜索结果为公司
 * @ClassName: SearchCompanyAdapter.java   
 * @date 2015年6月9日 下午7:30:31      
 * @说明  代码版权归 杭州反盗版中心有限公司 所有 
 */

public class SearchCompanyAdapter extends BaseAdapter{
	
	private Context mContext;
	
	private List<CompanyPojo> list;
	
	public SearchCompanyAdapter(Context context,List<CompanyPojo> data){
		mContext = context;
		list = data;
	}
	
	/**
	 * @Auther: zhuwt
	 * @Description: 重新加载数据源并刷新
	 * @Date:2015年6月9日下午7:56:06
	 * @param data
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void setDate(List<CompanyPojo> data) {
		list = data;
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		if (null != list) {
			if (list.size() > 4) {
				return 4;
			}
		}
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		if(null != list)
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
		if(null == contentView){
			contentView = LayoutInflater.from(mContext).inflate(R.layout.home_search_company, null);
			holder = initView(holder, contentView);
			contentView.setTag(holder);
		}else{
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
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initData(ViewHolder holder,int position){
		final CompanyPojo pojo = list.get(position);
		holder.content.setText(pojo.getOrgName());
		String path = pojo.getLogoUrl();
		if(StringUtils.isNotEmpty(path)){
			holder.icon.setTag(path);
			MasterImg.getInstance(mContext).loadImg(path, holder.icon, true, true, 0, R.drawable.toux_default);
		} else {
			holder.icon.setTag("");
			holder.icon.setImageResource(R.drawable.toux_default);
		}
		
		holder.item.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String entId  = pojo.getEntId();
				Intent mIntent = new Intent(mContext,CompanyDetails.class);
				mIntent.putExtra("entId", entId);
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
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	private ViewHolder initView(ViewHolder holder,View view){
		holder = new ViewHolder();
		holder.content = (TextView) view.findViewById(R.id.home_search_company_content);
		holder.icon = (RoundView) view.findViewById(R.id.home_search_company_head);
		holder.item = (LinearLayout) view.findViewById(R.id.item);
		return holder;
	}
	
	private class ViewHolder{
		private TextView content;
		private RoundView icon;
		private LinearLayout item;
	}
	
}

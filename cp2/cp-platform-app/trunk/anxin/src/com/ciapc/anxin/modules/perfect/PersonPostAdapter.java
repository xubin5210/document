package com.ciapc.anxin.modules.perfect;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ciapc.anxin.R;

/**
 * 
 * @author zhuwt
 * @Description:
 * @ClassName: HomeAdapter.java
 * @date 2015年6月3日 上午11:10:44
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */
public class PersonPostAdapter extends BaseAdapter {

	private Context mContext;

	private LayoutInflater layoutInflater;

	private List<HashMap<String, String>> list;

	private StringBuilder sb;

	private int count = 0;

	private Handler handler;
	
	private String post;

	public PersonPostAdapter(Context context,
			List<HashMap<String, String>> data, Handler handler) {
		mContext = context;
		layoutInflater = LayoutInflater.from(mContext);
		list = data;
		sb = new StringBuilder();
		this.handler = handler;
		post = "";
	}

	public void setDate(List<HashMap<String, String>> data){
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
	public View getView(int position, View contentView, ViewGroup arg2) {
		ViewHolder holder = null;
		if (null == contentView) {
			contentView = layoutInflater.inflate(R.layout.person_post_item,
					null);
			holder = initViewHolder(holder, contentView);
			contentView.setTag(holder);
		} else {
			holder = (ViewHolder) contentView.getTag();
		}
		initData(holder, position);
		return contentView;
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 初始化数据
	 * @Date:2015年6月19日下午3:03:03
	 * @param holder
	 * @param position
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initData(final ViewHolder holder, final int position) {
		holder.name.setText(list.get(position).get("job"));
		holder.flag = list.get(position).get("select");
		// 已选中
		if ("2".equals(holder.flag)) {
			holder.name.setSelected(true);
			if(list.get(position).get("job").equals(post)){
				return;
			}else{
				post = list.get(position).get("job");
			}
			holder.flag = "2";
			count++;
			sb.append(list.get(position).get("job")+",");
			Message message = new Message();
			message.what = PersonPostActivity.JOB_DETAIL;
			Bundle bundle = new Bundle();
			bundle.putInt("count", count);
			bundle.putString("job", sb.toString());
			bundle.putInt("position", position);
			bundle.putString("type", "2");
			message.setData(bundle);
			handler.sendMessage(message);
		}
		if ("1".equals(holder.flag)) {
			holder.name.setSelected(false);
			holder.flag = "1";
		}

		holder.name.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				Message message = new Message();
				message.what = PersonPostActivity.JOB_DETAIL;
				Bundle bundle = new Bundle();
				// 已经被选中
				if ("2".equals(holder.flag)) {
					sb.setLength(sb.length()
							- (list.get(position).get("job") + ",").length());
					holder.name.setSelected(false);
					holder.flag = "1";
					count--;
					bundle.putInt("count", count);
					bundle.putString("job", sb.toString());
					bundle.putInt("position",position);
					bundle.putString("type", "1");
					message.setData(bundle);
					handler.sendMessage(message);
					return false;
				} else {
					count++;
					if (count > 3) {
						bundle.putInt("count", count);
						bundle.putString("job", sb.toString());
						message.setData(bundle);
						handler.sendMessage(message);
						count--;
						return false;
					}
					sb.append(list.get(position).get("job") + ",");
					holder.name.setSelected(true);
					holder.flag = "2";
					bundle.putInt("count", count);
					bundle.putString("job", sb.toString());
					bundle.putInt("position", position);
					bundle.putString("type", "2");
					message.setData(bundle);
					handler.sendMessage(message);
				}
			
				return false;
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
		holder.name = (TextView) view.findViewById(R.id.person_post_item);
		return holder;
	}

	private class ViewHolder {
		private TextView name;
		private String flag;
	}
}

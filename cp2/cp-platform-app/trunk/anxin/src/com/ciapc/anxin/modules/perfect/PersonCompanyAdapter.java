package com.ciapc.anxin.modules.perfect;

import java.util.List;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.AXSharedPreferences;
import com.ciapc.anxin.common.GlobalConstants;
import com.ciapc.anxin.common.callback.AXCallBack;
import com.ciapc.anxin.common.pojo.CompanyPojo;
import com.ciapc.anxin.common.view.RoundView;
import com.master.util.common.DialogUtil;
import com.master.util.common.GsonUtils;
import com.master.util.common.StringUtils;
import com.master.util.image.MasterImg;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author zhuwt
 * @Description: 搜索结果为企业
 * @ClassName: PersonCompanyAdapter.java
 * @date 2015年6月10日 下午3:25:28
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */

public class PersonCompanyAdapter extends BaseAdapter {

	private Context mContext;

	private List<CompanyPojo> list;

	private LayoutInflater inflater;

	private Handler mHandler;

	public PersonCompanyAdapter(Context context, List<CompanyPojo> data,
			Handler mHandler) {
		list = data;
		mContext = context;
		inflater = LayoutInflater.from(mContext);
		this.mHandler = mHandler;
	}

	public void setData(List<CompanyPojo> data) {
		list = data;
		notifyDataSetChanged();
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
			contentView = inflater.inflate(R.layout.search_company_item, null);
			holder = initViewHolder(holder, contentView);
			contentView.setTag(holder);
		} else {
			holder = (ViewHolder) contentView.getTag();
		}
		initDate(holder, position);
		return contentView;
	}

	private void initDate(final ViewHolder holder, final int position) {
		String name = list.get(position).getOrgName();
		if (StringUtils.isNotEmpty(name)) {
			if (name.length() > 9) {
				name = name.substring(0, 9);
				holder.name.setText(name + "...");
			}
			holder.name.setText(name);
		} else {
			holder.name.setText(name);
		}
		String id = AXSharedPreferences.getEntId(mContext) + "";
		String path = list.get(position).getLogoUrl();
		if (StringUtils.isNotEmpty(path)) {
			holder.head.setTag(path);
			MasterImg.getInstance(mContext).loadImg(path, holder.head, true,
					true, 0, R.drawable.toux_default);
		} else {
			holder.head.setTag("");
			holder.head.setImageResource(R.drawable.toux_default);
		}
		if (id.equals(list.get(position).getEntId())) {
			holder.accept.setText("等待验证");
			holder.accept.setTextColor(mContext.getResources().getColor(
					R.color.C8));
			holder.accept
					.setBackgroundResource(R.drawable.accept_verify_card_style);
		} else {
			holder.accept.setText("申请绑定");
			holder.accept.setTextColor(mContext.getResources().getColor(
					R.color.white));
			holder.accept
					.setBackgroundResource(R.drawable.accept_new_card_style);
		}
		holder.accept.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (AXSharedPreferences.getEntId(mContext).equals(
						list.get(position).getEntId())) {
					return;
				}
				PerfectDataHandler.getInstance(mContext).bindCompany(
						AXSharedPreferences.getCardId(mContext),
						list.get(position).getEntId(), new AXCallBack() {
							@Override
							public void onCallBackString(String retStr) {
								super.onCallBackString(retStr);
								if (null != retStr) {
									if (GlobalConstants.HTTP_SUCCESS_CODE
											.equals(GsonUtils.getJsonValue(
													retStr,
													GlobalConstants.HTTP_CODE))) {
										DialogUtil.showSystemToast(mContext,
												"申请成功，等待验证");
										// 状态改为等待审核
										AXSharedPreferences.setEntStatus(
												mContext, "0");
										Message message = new Message();
										message.what = 5;
										message.obj = list.get(position)
												.getOrgName();
										mHandler.sendMessage(message);
										AXSharedPreferences.setEntId(mContext,
												list.get(position).getEntId());
										holder.accept.setText("等待验证");
										holder.accept.setTextColor(mContext
												.getResources().getColor(
														R.color.C8));
										holder.accept
												.setBackgroundResource(R.drawable.accept_verify_card_style);
									} else {
										DialogUtil
												.showSystemToast(
														mContext,
														GsonUtils
																.getJsonValue(
																		retStr,
																		GlobalConstants.HTTP_MSG));
									}
								} else {
									DialogUtil.showSystemToast(mContext,
											"网络异常，请稍后再试");
								}
							}
						});
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
		holder.name = (TextView) view
				.findViewById(R.id.person_search_company_name);
		holder.accept = (Button) view
				.findViewById(R.id.person_search_company_btn);
		holder.head = (RoundView) view
				.findViewById(R.id.person_search_company_head);
		return holder;
	}

	private class ViewHolder {
		private TextView name;
		private Button accept;
		private RoundView head;
	}
}

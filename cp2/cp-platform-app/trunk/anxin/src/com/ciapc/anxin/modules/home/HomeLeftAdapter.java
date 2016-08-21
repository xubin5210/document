package com.ciapc.anxin.modules.home;

import java.util.List;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.AXSharedPreferences;
import com.ciapc.anxin.common.view.AxinDialog;
import com.ciapc.anxin.common.view.AxinDialog.AxDialogClickListen;
import com.ciapc.anxin.modules.buscard.NewCardActivity;
import com.ciapc.anxin.modules.contacts.ContactsActivity;
import com.ciapc.anxin.modules.perfect.BindCompanyActivity;
import com.ciapc.anxin.modules.perfect.PerfectInfoActivity;
import com.ciapc.anxin.modules.perfect.PersonActivity;
import com.ciapc.anxin.modules.setting.SettingMainActivity;
import com.ciapc.anxin.modules.share.ShareActivity;
import com.ciapc.anxin.modules.statistics.StatisticsActivity;
import com.master.util.common.DialogUtil;
import com.master.util.common.StringUtils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HomeLeftAdapter extends BaseAdapter {

	private final String TAG = "HomeLeftAdapter";

	private Context mContext;

	private List<String> list;

	public HomeLeftAdapter(Context context, List<String> data) {
		mContext = context;
		list = data;
	}

	@Override
	public int getCount() {
		return 1;
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View contentView, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		if (null == contentView) {
			contentView = LayoutInflater.from(mContext).inflate(
					R.layout.home_left_menu, null);
			viewHolder = initViewHolder(viewHolder, contentView);
			contentView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) contentView.getTag();
		}
		initClick(viewHolder);
		return contentView;
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 点击事件
	 * @Date:2015年6月7日下午1:40:25
	 * @param holder
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initClick(ViewHolder holder) {
		if (!"0".equals(HomeSharedPreferences.getNewCardUnRead(mContext))) {
			holder.red.setVisibility(View.VISIBLE);
			holder.red
					.setText(HomeSharedPreferences.getNewCardUnRead(mContext));
		} else {
			holder.red.setVisibility(View.GONE);
		}

		if (StringUtils.isNotEmpty(AXSharedPreferences.getEntId(mContext))) {
			holder.bingCompany.setVisibility(View.GONE);
		} else {
			holder.bingCompany.setVisibility(View.VISIBLE);
		}

		holder.bingCompany.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				mContext.startActivity(new Intent(mContext,
						BindCompanyActivity.class));
			}
		});

		// 设置
		holder.set.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				mContext.startActivity(new Intent(mContext,
						SettingMainActivity.class));
			}
		});

		// 企业通讯录
		holder.companyContactItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 企业ID
				String entId = AXSharedPreferences.getEntId(mContext);
				 if (StringUtils.isNotEmpty(entId)
				 && "1".equals(AXSharedPreferences
				 .getEntStatus(mContext))) {
					mContext.startActivity(new Intent(mContext,
							ContactsActivity.class));
				} else {
					String content = null;
					if ("0".equals(AXSharedPreferences.getEntStatus(mContext))) {
						content = "您申请绑定的企业正在审核中，暂无法查看。";
						DialogUtil.showSystemToast(mContext, content);
					}else {
						content = "完善个人资料，挂靠实名单位，权威认证，将拓展你的人脉！";
						// 未绑定企业 引导去绑定企业
						new AxinDialog(mContext, AxinDialog.DIALOG_CHANGE)
								.setConfirmStr("立即完善").setCancelStr("稍后再说")
								.setTitle(content)
								.setCancelClick(new AxDialogClickListen() {

									@Override
									public void onClick(AxinDialog axinDialog) {
										axinDialog.dismiss();

									}
								}).setConfirmClick(new AxDialogClickListen() {

									@Override
									public void onClick(AxinDialog axinDialog) {
										mContext.startActivity(new Intent(
												mContext,
												PerfectInfoActivity.class));
										axinDialog.dismiss();
									}
								}).show();
					}
				}
			}
		});

		// 人脉
		holder.contacts.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				mContext.startActivity(new Intent(mContext,
						StatisticsActivity.class));
			}
		});

		// 新名片
		holder.newCard.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				mContext.startActivity(new Intent(mContext,
						NewCardActivity.class));

			}
		});

		// 个人信息
		holder.person.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 已经完善资料
				if ("1".equals(AXSharedPreferences.getUserInfoStatus(mContext))) {
					mContext.startActivity(new Intent(mContext,
							PersonActivity.class));
				} else {
					mContext.startActivity(new Intent(mContext,
							PerfectInfoActivity.class));
				}

			}
		});

		holder.share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				mContext.startActivity(new Intent(mContext, ShareActivity.class));

			}
		});
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 初始化
	 * @Date:2015年6月7日下午1:38:12
	 * @param viewHolder
	 * @param view
	 * @return
	 * @return ViewHolder
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private ViewHolder initViewHolder(ViewHolder viewHolder, View view) {
		viewHolder = new ViewHolder();
		viewHolder.companyContact = (TextView) view
				.findViewById(R.id.home_left_company_contacts);
		viewHolder.contacts = (TextView) view
				.findViewById(R.id.home_left_contacts);
		viewHolder.newCard = (RelativeLayout) view
				.findViewById(R.id.home_left_new_card_layout);
		viewHolder.person = (TextView) view.findViewById(R.id.home_left_person);
		viewHolder.red = (Button) view.findViewById(R.id.home_left_red_point);
		viewHolder.set = (TextView) view.findViewById(R.id.home_left_set);
		viewHolder.share = (TextView) view.findViewById(R.id.home_left_share);
		viewHolder.companyContactItem = (RelativeLayout) view
				.findViewById(R.id.home_left_company_contacts_item);
		viewHolder.bingCompany = (Button) view
				.findViewById(R.id.new_card_accept);
		return viewHolder;
	}

	private class ViewHolder {
		private TextView person;// 个人
		private RelativeLayout newCard;// 新名片
		private Button red;// 小红点 新名片未读
		private TextView contacts;// 人脉
		private TextView companyContact;// 企业通讯录
		private TextView set;// 设置
		private TextView share;// 分享有礼
		private RelativeLayout companyContactItem;
		private Button bingCompany;
	}

}

package com.ciapc.anxin.modules.home;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.callback.AXCallBack;
import com.ciapc.anxin.common.pojo.ExchangePojo;
import com.ciapc.anxin.common.view.AxinDialog;
import com.ciapc.anxin.common.view.AxinDialog.AxDialogClickListen;
import com.ciapc.anxin.common.view.RoundView;
import com.ciapc.anxin.modules.details.DetailsDataHandler;
import com.ciapc.anxin.modules.details.PersonDetails;
import com.ciapc.anxin.utils.CheckStringUtils;
import com.ciapc.anxin.utils.ListView.ZSwipeItem;
import com.ciapc.anxin.utils.ListView.adapter.BaseSwipeAdapter;
import com.ciapc.anxin.utils.ListView.enums.DragEdge;
import com.ciapc.anxin.utils.ListView.enums.ShowMode;
import com.ciapc.anxin.utils.ListView.listener.SimpleSwipeListener;
import com.master.util.common.DialogUtil;
import com.master.util.common.StringUtils;
import com.master.util.image.MasterImg;

/**
 * 
 * @author zhuwt
 * @Description: 主页的adapter
 * @ClassName: HomeAdapter.java
 * @date 2015年6月3日 上午11:10:44
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */
public class HomeAdapter extends BaseSwipeAdapter {

	private Context mContext;

	private List<ExchangePojo> list = new ArrayList<ExchangePojo>();

	private LayoutInflater mInflater;

	private ViewHolder viewHolder;

	private final int DEL = 1;

	public HomeAdapter(Context context, List<ExchangePojo> data) {
		mContext = context;
		list = data;
		mInflater = LayoutInflater.from(mContext);
	}

	public void setData(List<ExchangePojo> data) {
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
	public int getSwipeLayoutResourceId(int position) {
		return R.id.swipe_item;
	}

	// 设置ITEM的布局
	@Override
	public View generateView(int position, ViewGroup parent) {
		return mInflater.inflate(R.layout.home_business_card_item, parent,
				false);
	}

	// 初始化控件
	@Override
	public void fillValues(final int position, View convertView) {
		ExchangePojo cardPojo = list.get(position);
		if (null == convertView.getTag(R.id.tag_myadapter)) {
			viewHolder = initViewHolder(viewHolder, convertView);
			convertView.setTag(R.id.tag_myadapter, viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag(R.id.tag_myadapter);
		}
		// 赋值
		String trueName = cardPojo.getTrueName();
		if (StringUtils.isNotEmpty(trueName)) {
			viewHolder.nickName.setText(trueName);
		} else {
			viewHolder.nickName.setText("无名氏");
		}
		String companyText = cardPojo.getOrgName();
		if (StringUtils.isNotEmpty(companyText)) {
			if(companyText.length() > 14){
				viewHolder.company.setText(companyText.subSequence(0, 14)+"...");
			}else{
				viewHolder.company.setText(companyText);
			}
		} else {
			viewHolder.company.setText("未填写");
		}
		String time = cardPojo.getGmtExchange();
		if (StringUtils.isNotEmpty(time)) {
			long timeShow = Long.parseLong(cardPojo.getGmtExchange());
			String str = CheckStringUtils.getTime(timeShow);
			viewHolder.ecvhangeTime.setText(str);
		} else {
			viewHolder.ecvhangeTime.setText("");
		}

		String path = cardPojo.getIconUrl();
		if (StringUtils.isNotEmpty(path)) {
			viewHolder.head.setTag(path);
			MasterImg.getInstance(mContext).loadImg(path, viewHolder.head,
					true, true, 0, R.drawable.toux_default);
		} else {
			viewHolder.head.setTag("");
			viewHolder.head.setImageResource(R.drawable.toux_default);

		}
		if (position % 4 == 1) {
			viewHolder.swipeItem.setShowMode(ShowMode.PullOut);
			viewHolder.swipeItem.setDragEdge(DragEdge.Right);
		} else if (position % 4 == 2) {
			viewHolder.swipeItem.setShowMode(ShowMode.LayDown);
			viewHolder.swipeItem.setDragEdge(DragEdge.Right);
		}

		initClick(position, viewHolder);

	}

	/**
	 * @Auther: zhuwt
	 * @Description:初始化点击事件
	 * @Date:2015年6月3日下午6:14:29
	 * @param position
	 * @param mHolder
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initClick(final int position, final ViewHolder mHolder) {
		// 滑动监听
		mHolder.swipeItem.addSwipeListener(new SimpleSwipeListener() {
			@Override
			public void onOpen(ZSwipeItem layout) {
				Log.d(TAG, "打开:" + position);
			}

			@Override
			public void onClose(ZSwipeItem layout) {
				Log.d(TAG, "关闭:" + position);
			}

			@Override
			public void onStartOpen(ZSwipeItem layout) {
				Log.d(TAG, "准备打开:" + position);
			}

			@Override
			public void onStartClose(ZSwipeItem layout) {
				Log.d(TAG, "准备关闭:" + position);
			}

			@Override
			public void onHandRelease(ZSwipeItem layout, float xvel, float yvel) {
				Log.d(TAG, "手势释放");
			}

			@Override
			public void onUpdate(ZSwipeItem layout, int leftOffset,
					int topOffset) {
			}
		});

		// 删除
		mHolder.del.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				new AxinDialog(mContext, AxinDialog.DIALOG_CHANGE)
						.setTitle("删除该名片时，你的名片也会在对方名片里删除").setCancelStr("取消")
						.setConfirmStr("确认删除")
						.setCancelClick(new AxDialogClickListen() {

							@Override
							public void onClick(AxinDialog axinDialog) {
								axinDialog.dismiss();
							}
						}).setConfirmClick(new AxDialogClickListen() {

							@Override
							public void onClick(final AxinDialog axinDialog) {
								axinDialog.dismiss();
								DetailsDataHandler.getInstance(mContext)
										.delCard(
												list.get(position).getCardId()
														+ "", new AXCallBack() {
													@Override
													public void onCallBackString(
															String retStr) {
														super.onCallBackString(retStr);
														if (null != retStr) {
															DialogUtil
																	.showSystemToast(
																			mContext,
																			"删除成功");
															Message msg = new Message();
															msg.what = DEL;
															String cardId = list
																	.get(position)
																	.getCardId()
																	+ "";
															msg.obj = cardId;
															mHandler.sendMessage(msg);
															list.remove(position);
															notifyDataSetChanged();
														} else {
															DialogUtil
																	.showSystemToast(
																			mContext,
																			"网络异常，请稍后再试");
														}
													}
												});
							}
						}).show();
				mHolder.swipeItem.close();
			}
		});
		
		mHolder.item.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent mIntent = new Intent(mContext, PersonDetails.class);
				mIntent.putExtra("cardId", list.get(position).getCardId() + "");
				mContext.startActivity(mIntent);
			}
		});
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 初始化
	 * @Date:2015年6月3日下午6:11:31
	 * @param mHolder
	 * @param convertView
	 * @return
	 * @return ViewHolder
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private ViewHolder initViewHolder(ViewHolder mHolder, View convertView) {
		mHolder = new ViewHolder();
		// 初始化
		mHolder.swipeItem = (ZSwipeItem) convertView
				.findViewById(R.id.swipe_item);
		mHolder.nickName = (TextView) convertView
				.findViewById(R.id.business_name);
		mHolder.company = (TextView) convertView
				.findViewById(R.id.business_company);
		mHolder.del = (LinearLayout) convertView
				.findViewById(R.id.home_business_card_del);
		mHolder.head = (RoundView) convertView
				.findViewById(R.id.business_card_head);
		mHolder.ecvhangeTime = (TextView) convertView
				.findViewById(R.id.business_time);
		mHolder.item = (LinearLayout) convertView.findViewById(R.id.home_item);
		return mHolder;
	}

	private class ViewHolder {
		private TextView nickName;
		private TextView company;
		private ZSwipeItem swipeItem;
		private LinearLayout del;
		private RoundView head;
		private TextView ecvhangeTime;
		private LinearLayout item;
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case DEL:
				final String pojo = (String) msg.obj;
				new Thread(new Runnable() {

					@Override
					public void run() {
						HomeDataHandler.getInstance().delCard(pojo);
					}
				}).start();
				break;

			default:
				break;
			}
		};
	};

}

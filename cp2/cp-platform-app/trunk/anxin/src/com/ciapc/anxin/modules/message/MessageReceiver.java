package com.ciapc.anxin.modules.message;

import java.util.HashMap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ciapc.anxin.common.AXSharedPreferences;
import com.ciapc.anxin.common.GlobalConstants;
import com.ciapc.anxin.common.callback.AXCallBack;
import com.ciapc.anxin.common.pojo.ExchangePojo;
import com.ciapc.anxin.common.view.AxinDialog;
import com.ciapc.anxin.common.view.AxinDialog.AxDialogClickListen;
import com.ciapc.anxin.modules.home.HomeDataHandler;
import com.ciapc.anxin.modules.home.HomeSharedPreferences;
import com.ciapc.anxin.modules.login.LoginActivity;
import com.ciapc.anxin.modules.perfect.PerfectInfoActivity;
import com.ciapc.anxin.modules.setting.SettingMainActivity;
import com.ciapc.anxin.utils.NotificationUtils;
import com.igexin.sdk.PushConsts;
import com.master.util.common.DialogUtil;
import com.master.util.common.GsonUtils;
import com.master.util.common.StringUtils;

public class MessageReceiver extends BroadcastReceiver {

	private final String TAG = "MessageReceiver";

	private final int SEND_READ = 1;

	private Context mContext;

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		Log.d("GetuiSdkDemo", "onReceive() action=" + bundle.getInt("action"));
		mContext = context;
		switch (bundle.getInt(PushConsts.CMD_ACTION)) {
		case PushConsts.GET_MSG_DATA:
			// 获取透传数据
			byte[] payload = bundle.getByteArray("payload");
			if (payload != null) {
				String data = new String(payload);
				if (GlobalConstants.isDebug) {
					Log.i(TAG, "内容:" + data);
				}
				if (AXSharedPreferences.getLoginType(mContext)) {
					getMessage(data, context);
				}
			}
			break;

		case PushConsts.GET_CLIENTID:
			// 获取ClientID(CID)
			// 第三方应用需要将CID上传到第三方服务器，并且将当前用户帐号和CID进行关联，以便日后通过用户帐号查找CID进行消息推送
			String cid = bundle.getString("clientid");
			if (StringUtils.isNotEmpty(cid)) {
				uploadCid(cid, context);
			}
			break;

		case PushConsts.THIRDPART_FEEDBACK:
			break;

		default:
			break;
		}
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 获取参数
	 * @Date:2015年6月17日下午4:45:16
	 * @param msg
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void getMessage(final String msg, final Context context) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				if (!StringUtils.isNotEmpty(AXSharedPreferences
						.getCardId(mContext))) {
					return;
				}
				String code = GsonUtils.getJsonValue(msg, "code");
				String id = GsonUtils.getJsonValue(msg, "messageId");
				Message message = new Message();
				message.what = SEND_READ;
				message.obj = id;
				mHandler.sendMessage(message);
				// 2 名片验证通过
				if ("2".equals(code)) {
					String data = GsonUtils.getJsonValue(msg, "data");
					if (StringUtils.isNotEmpty(data)) {
						ExchangePojo pojo = GsonUtils.toObject(data,
								ExchangePojo.class);
						pojo.setUserId(Integer.parseInt(AXSharedPreferences
								.getCardId(context)));
						HomeDataHandler.getInstance().insertCard(pojo);
					}
					return;
				}

				// 有新名片申请
				if ("1".equals(code)) {
					String content = GsonUtils.getJsonValue(msg, "data");
					// 获取已经保存的更新信息
					String name = HomeSharedPreferences
							.getNewCardUnReadContent(context);
					if (StringUtils.isNotEmpty(name)) {
						if (name.indexOf(content) > -1) {
							// 重复
							return;
						}
					}
					// 不一样 做更新提示 并保存
					if (StringUtils.isNotEmpty(name)) {
						String[] str = name.split(",");
						if (str.length > 2) {
							name = content + "," + str[0];
							HomeSharedPreferences.setNewCardUnReadContent(
									context, name);
						} else {
							HomeSharedPreferences.setNewCardUnReadContent(
									context, content + "," + name);
						}
					} else {
						HomeSharedPreferences.setNewCardUnReadContent(context,
								content);
					}
					// 增加未读消息记录
					int count = Integer.parseInt(HomeSharedPreferences
							.getNewCardUnRead(context));
					count++;
					HomeSharedPreferences.setNewCardUnRead(context, count + "");
					String showMsg = content + "发来名片申请";
					NotificationUtils.showNotificationMsg(
							NotificationUtils.MESSAGE_ID, showMsg, null,
							mContext);
				}

				// 获取更新信息
				if ("3".equals(code)) {
					String content = GsonUtils.getJsonValue(msg, "data");
					ExchangePojo pojo = GsonUtils.toObject(content,
							ExchangePojo.class);
					pojo.setUserId(Integer.parseInt(AXSharedPreferences
							.getCardId(mContext)));
					HomeDataHandler.getInstance().updateCard(pojo);
					// 获取已经保存的更新信息
					String name = HomeSharedPreferences
							.getUpdateUnReadContent(context);
					String str = null;
					if (StringUtils.isNotEmpty(pojo.getTrueName())) {
						str = pojo.getTrueName();
					} else {
						str = pojo.getMobile();
					}
					if (StringUtils.isNotEmpty(name)) {
						if (name.indexOf(str) > -1) {
							// 重复
							return;
						}
					}
					// 不一样 做更新提示 并保存
					if (StringUtils.isNotEmpty(name)) {
						String[] strs = name.split(",");
						if (strs.length > 2) {
							name = str + "," + strs[0];
							HomeSharedPreferences.setUpdateUnReadContent(
									context, name);
						} else {
							HomeSharedPreferences.setUpdateUnReadContent(
									context, str + "," + name);
						}

					} else {
						HomeSharedPreferences.setUpdateUnReadContent(context,
								str);
					}
					// 增加未读消息记录
					int count = Integer.parseInt(HomeSharedPreferences
							.getUpdateUnRead(context));
					count++;
					HomeSharedPreferences.setUpdateUnRead(context, count + "");
				}

				// 删除名片
				if ("4".equals(code)) {
					String cardId = GsonUtils.getJsonValue(msg, "data");
					HomeDataHandler.getInstance().delCard(cardId);
				}

				// 申请绑定企业 审核不通过
				if ("5".equals(code)) {
					// 设置资料为未完善
					AXSharedPreferences.setUserInfoStatus(context, "2");
					AXSharedPreferences.setEntId(mContext, "");
					AXSharedPreferences.setEntStatus(mContext, "-1");
					String showMsg = GsonUtils.getJsonValue(msg, "data");
					NotificationUtils.showNotificationBind(
							NotificationUtils.MESSAGE_ID, showMsg, null,
							mContext);
				}
				// 申请绑定企业通过
				if ("6".equals(code)) {
					String showMsg = GsonUtils.getJsonValue(msg, "data");
					AXSharedPreferences.setEntStatus(mContext, "1");
					NotificationUtils.showNotificationInfo(
							NotificationUtils.MESSAGE_ID, showMsg, null,
							mContext);
				}
				// 在第二台设备登录 当前账号自动下线
				if ("7".equals(code)) {
					SharedPreferences spf = mContext.getSharedPreferences(
							AXSharedPreferences.SHARED_PREFERENCEK_KEY, 0);
					Editor editor = spf.edit();
					editor.clear();
					editor.commit();
					// setMap(new HashMap<String,Object>());
					Intent mIntent = new Intent(mContext, LoginActivity.class);
					mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					mIntent.putExtra("login", "1");
					mContext.startActivity(mIntent);
				}

			}
		}).start();
	}

	/**
	 * @Auther: zhuwt
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @Date:2015年7月2日上午9:13:06
	 * @param cid
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void uploadCid(String cid, Context context) {
		MessageDateHandler.getInstance(context).uploadClientId(cid,
				new AXCallBack() {
					@Override
					public void onCallBackString(String retStr) {
						super.onCallBackString(retStr);
						if (StringUtils.isNotEmpty(retStr)) {
							if (GlobalConstants.HTTP_SUCCESS_CODE
									.equals(GsonUtils.getJsonValue(retStr,
											GlobalConstants.HTTP_CODE))) {

							} else {
								DialogUtil.showSystemToast(mContext, GsonUtils
										.getJsonValue(retStr,
												GlobalConstants.HTTP_MSG));
							}
						} else {
							DialogUtil.showSystemToast(mContext, "网络异常,请稍后再试!");
						}
					}
				});
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SEND_READ:
				String id = (String) msg.obj;
				MessageDateHandler.getInstance(mContext).sendRead(id,
						new AXCallBack() {
							@Override
							public void onCallBackString(String retStr) {
								super.onCallBackString(retStr);
								if (null != retStr) {

								} else {

								}
							}
						});
				break;

			default:
				break;
			}
		};
	};

}

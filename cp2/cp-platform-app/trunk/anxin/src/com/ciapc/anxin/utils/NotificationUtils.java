/**    
 * @author maomy  
 * @Description: TODO(用一句话描述该文件做什么)   
 * @Package com.ciapc.tzd.modules.common.utils   
 * @Title: NotificationUtils.java   
 * @date 2013-12-2 下午2:13:17   
 * @version V1.0 
 * @说明  代码版权归 杭州反盗版中心有限公司 所有
 */
package com.ciapc.anxin.utils;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.AXSharedPreferences;
import com.ciapc.anxin.modules.buscard.NewCardActivity;
import com.ciapc.anxin.modules.perfect.PerfectInfoActivity;
import com.ciapc.anxin.modules.perfect.PersonActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class NotificationUtils {

	public final static int NOTIFICATION_RECORDER_ID = 123432895;

	public final static int RECORDER_RUNNING_ID = 100020939;

	public final static int MESSAGE_ID = 102320955;

	private static NotificationManager mNotificationManager;

	private static Notification mNotification;

	private static long firstTime = 0;

	/**
	 * @Auther: maomy
	 * @Description: 新消息提示
	 * @Date:2014-1-13下午1:50:25
	 * @param NOTIFICATION_ID
	 * @param showMsg
	 * @param pIntent
	 * @param mCt
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	@SuppressWarnings("deprecation")
	public static void showNotificationMsg(int NOTIFICATION_ID, String showMsg,
			PendingIntent pIntent, Context mCt) {
		mNotificationManager = (NotificationManager) mCt
				.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotification = new Notification();
		mNotification.icon = R.drawable.icon;
		mNotification.tickerText = mCt.getString(R.string.app_name) + "新消息提示";
		mNotification.when = System.currentTimeMillis();
		// 将使用默认的声音来提醒用户
		if (AXSharedPreferences.getNewInfoRemind(mCt)) {
			if (AXSharedPreferences.getSound(mCt)) {
				mNotification.defaults = Notification.DEFAULT_SOUND;
			}
			if (AXSharedPreferences.getShake(mCt))
				// 100ms延迟后，振动250ms，停止100ms后振动500ms
				mNotification.vibrate = new long[] { 100, 250, 100, 500 };
		}
		mNotification.flags = Notification.FLAG_AUTO_CANCEL;
		mNotification.flags |= Notification.FLAG_ONGOING_EVENT;
		if (null == pIntent) {
			Intent intent = new Intent(mCt, NewCardActivity.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(mCt, 0,
					intent, 0);
			mNotification.setLatestEventInfo(mCt,
					"您有来自" + mCt.getString(R.string.app_name) + "的消息", showMsg,
					pendingIntent);
		} else {
			mNotification.setLatestEventInfo(mCt,
					"您有来自" + mCt.getString(R.string.app_name) + "的消息", showMsg,
					pIntent);
		}

		mNotificationManager.notify(NOTIFICATION_ID, mNotification);
	}
	
	/*
	 * *
	 * @Auther: maomy
	 * @Description: 新消息提示
	 * @Date:2014-1-13下午1:50:25
	 * @param NOTIFICATION_ID
	 * @param showMsg
	 * @param pIntent
	 * @param mCt
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	@SuppressWarnings("deprecation")
	public static void showNotificationBind(int NOTIFICATION_ID, String showMsg,
			PendingIntent pIntent, Context mCt) {
		mNotificationManager = (NotificationManager) mCt
				.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotification = new Notification();
		mNotification.icon = R.drawable.icon;
		mNotification.tickerText = mCt.getString(R.string.app_name) + "新消息提示";
		mNotification.when = System.currentTimeMillis();
		// 将使用默认的声音来提醒用户
		if (AXSharedPreferences.getNewInfoRemind(mCt)) {
			if (AXSharedPreferences.getSound(mCt)) {
				mNotification.defaults = Notification.DEFAULT_SOUND;
			}
			if (AXSharedPreferences.getShake(mCt))
				// 100ms延迟后，振动250ms，停止100ms后振动500ms
				mNotification.vibrate = new long[] { 100, 250, 100, 500 };
		}
		mNotification.flags = Notification.FLAG_AUTO_CANCEL;
		mNotification.flags |= Notification.FLAG_ONGOING_EVENT;
		if (null == pIntent) {
			Intent intent = new Intent(mCt, PerfectInfoActivity.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(mCt, 0,
					intent, 0);
			mNotification.setLatestEventInfo(mCt,
					"您有来自" + mCt.getString(R.string.app_name) + "的消息", showMsg,
					pendingIntent);
		} else {
			mNotification.setLatestEventInfo(mCt,
					"您有来自" + mCt.getString(R.string.app_name) + "的消息", showMsg,
					pIntent);
		}

		mNotificationManager.notify(NOTIFICATION_ID, mNotification);
	}
	
	public static void showNotificationInfo(int NOTIFICATION_ID, String showMsg,
			PendingIntent pIntent, Context mCt) {
		mNotificationManager = (NotificationManager) mCt
				.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotification = new Notification();
		mNotification.icon = R.drawable.icon;
		mNotification.tickerText = mCt.getString(R.string.app_name) + "新消息提示";
		mNotification.when = System.currentTimeMillis();
		// 将使用默认的声音来提醒用户
		if (AXSharedPreferences.getNewInfoRemind(mCt)) {
			if (AXSharedPreferences.getSound(mCt)) {
				mNotification.defaults = Notification.DEFAULT_SOUND;
			}
			if (AXSharedPreferences.getShake(mCt))
				// 100ms延迟后，振动250ms，停止100ms后振动500ms
				mNotification.vibrate = new long[] { 100, 250, 100, 500 };
		}
		mNotification.flags = Notification.FLAG_AUTO_CANCEL;
		mNotification.flags |= Notification.FLAG_ONGOING_EVENT;
		if (null == pIntent) {
			Intent intent = new Intent(mCt, PersonActivity.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(mCt, 0,
					intent, 0);
			mNotification.setLatestEventInfo(mCt,
					"您有来自" + mCt.getString(R.string.app_name) + "的消息", showMsg,
					pendingIntent);
		} else {
			mNotification.setLatestEventInfo(mCt,
					"您有来自" + mCt.getString(R.string.app_name) + "的消息", showMsg,
					pIntent);
		}

		mNotificationManager.notify(NOTIFICATION_ID, mNotification);
	}

	// /**
	// * @Auther: maomy
	// * @Description: 新消息提示
	// * @Date:2014-1-13下午1:50:25
	// * @param NOTIFICATION_ID
	// * @param showMsg
	// * @param pIntent
	// * @param mCt
	// * @return void
	// * @说明 代码版权归 杭州反盗版中心有限公司 所有
	// */
	// @SuppressWarnings("deprecation")
	// public static void showChatNotificationMsg(int
	// NOTIFICATION_ID,PendingIntent pIntent,Context mCt) {
	// long time=System.currentTimeMillis();
	// if(time>firstTime &&firstTime!=0){
	// mNotificationManager.cancel(Integer.valueOf((int)
	// (NOTIFICATION_ID+firstTime)));
	// }
	// firstTime=time;
	// mNotificationManager = (NotificationManager)
	// mCt.getSystemService(Context.NOTIFICATION_SERVICE);
	// mNotification = new Notification();
	// mNotification.icon = R.drawable.ic_launcher;
	// mNotification.tickerText = mCt.getString(R.string.app_name)+"有好友发来消息";
	// mNotification.when = System.currentTimeMillis();
	// //将使用默认的声音来提醒用户
	// mNotification.defaults = Notification.DEFAULT_SOUND;
	// // 100ms延迟后，振动250ms，停止100ms后振动500ms
	// // mNotification.vibrate = new long[] { 100, 250, 100, 500 };
	// mNotification.flags = Notification.FLAG_AUTO_CANCEL;
	// mNotification.flags |= Notification.FLAG_ONGOING_EVENT;
	// if(null == pIntent){
	// Intent intent = new Intent(mCt, MainActivity.class);
	// PendingIntent pendingIntent = PendingIntent.getActivity(mCt, 0,
	// intent,0);
	// mNotification.setLatestEventInfo(
	// mCt,
	// "果冻社区",
	// "你的好友发来了消息。",
	// pendingIntent);
	// }else{
	// mNotification.setLatestEventInfo(
	// mCt,
	// "果冻社区",
	// "你的好友发来了消息。",
	// pIntent);
	// }
	//
	// mNotificationManager.notify(Integer.valueOf((int)
	// (NOTIFICATION_ID+time)), mNotification);
	// }

	/**
	 * @Auther: maomy
	 * @Description: 清除图标
	 * @Date:2013-12-2下午2:26:28
	 * @param notify_id
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static void clearNotifycation(int notify_id) {
		if (null != mNotificationManager)
			mNotificationManager.cancel(notify_id);
	}
}

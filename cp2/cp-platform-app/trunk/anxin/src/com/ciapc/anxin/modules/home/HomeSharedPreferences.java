package com.ciapc.anxin.modules.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.ciapc.anxin.common.AXSharedPreferences;
import com.ciapc.anxin.common.GlobalConstants;

public class HomeSharedPreferences extends AXSharedPreferences {

	/**
	 * @Auther: zhuwt
	 * @Description: 新名片未读
	 * @Date:2015年6月18日上午10:06:51
	 * @param context
	 * @return
	 * @return String
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static String getNewCardUnRead(Context context) {
		SharedPreferences spf = context.getSharedPreferences(
				SHARED_PREFERENCEK_KEY, 0);
		return spf.getString(HomeConstants.CARD_NOT_READ, "0");
	}

	public static void setNewCardUnRead(Context context, String val) {
		Editor data = context.getSharedPreferences(SHARED_PREFERENCEK_KEY, 0)
				.edit();
		data.putString(HomeConstants.CARD_NOT_READ, val);
		data.commit();
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 更新消息未读
	 * @Date:2015年7月2日上午11:49:38
	 * @param context
	 * @return
	 * @return String
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static String getUpdateUnRead(Context context) {
		SharedPreferences spf = context.getSharedPreferences(
				SHARED_PREFERENCEK_KEY, 0);
		return spf.getString(HomeConstants.UPDATE_READ, "0");
	}

	public static void setUpdateUnRead(Context context, String val) {
		Editor data = context.getSharedPreferences(SHARED_PREFERENCEK_KEY, 0)
				.edit();
		data.putString(HomeConstants.UPDATE_READ, val);
		data.commit();
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 新名片 未读消息 内容（名字)
	 * @Date:2015年7月2日上午11:51:28
	 * @param context
	 * @return
	 * @return String
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static String getNewCardUnReadContent(Context context) {
		SharedPreferences spf = context.getSharedPreferences(
				SHARED_PREFERENCEK_KEY, 0);
		return spf.getString(HomeConstants.CARD_NOT_READ_CONTENT, "");
	}

	public static void setNewCardUnReadContent(Context context, String val) {
		Editor data = context.getSharedPreferences(SHARED_PREFERENCEK_KEY, 0)
				.edit();
		data.putString(HomeConstants.CARD_NOT_READ_CONTENT, val);
		data.commit();
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 更新消息未读内容(名字）
	 * @Date:2015年7月2日上午11:49:38
	 * @param context
	 * @return
	 * @return String
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static String getUpdateUnReadContent(Context context) {
		SharedPreferences spf = context.getSharedPreferences(
				SHARED_PREFERENCEK_KEY, 0);
		return spf.getString(HomeConstants.UPDATE_READ_CONTENT, "null");
	}

	public static void setUpdateUnReadContent(Context context, String val) {
		Editor data = context.getSharedPreferences(SHARED_PREFERENCEK_KEY, 0)
				.edit();
		data.putString(HomeConstants.UPDATE_READ_CONTENT, val);
		data.commit();
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 昵称
	 * @Date:2015年6月18日上午10:06:51
	 * @param context
	 * @return
	 * @return String
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static String getNickName(Context context) {
		SharedPreferences spf = context.getSharedPreferences(
				SHARED_PREFERENCEK_KEY, 0);
		return spf.getString(HomeConstants.NICK_NAME, "");
	}

	public static void setNickName(Context context, String val) {
		Editor data = context.getSharedPreferences(SHARED_PREFERENCEK_KEY, 0)
				.edit();
		data.putString(HomeConstants.NICK_NAME, val);
		data.commit();
	}
	
	/**
	 * @Auther: zhuwt
	 * @Description: 真实姓名
	 * @Date:2015年6月18日上午10:06:51
	 * @param context
	 * @return
	 * @return String
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static String getTrueName(Context context) {
		SharedPreferences spf = context.getSharedPreferences(
				SHARED_PREFERENCEK_KEY, 0);
		return spf.getString(HomeConstants.TRUE_NAME, "");
	}

	public static void setTrueName(Context context, String val) {
		Editor data = context.getSharedPreferences(SHARED_PREFERENCEK_KEY, 0)
				.edit();
		data.putString(HomeConstants.TRUE_NAME, val);
		data.commit();
	}


	/**
	 * @Auther: zhuwt
	 * @Description: 个性签名
	 * @Date:2015年6月18日上午10:12:32
	 * @param context
	 * @return
	 * @return String
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static String getSignature(Context context) {
		SharedPreferences spf = context.getSharedPreferences(
				SHARED_PREFERENCEK_KEY, 0);
		return spf.getString(HomeConstants.SIGNATURE, "这个家伙太懒了，什么都没写");
	}

	public static void setSignature(Context context, String val) {
		Editor data = context.getSharedPreferences(SHARED_PREFERENCEK_KEY, 0)
				.edit();
		data.putString(HomeConstants.SIGNATURE, val);
		data.commit();
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 获取头像地址
	 * @Date:2015年6月18日上午10:44:52
	 * @param context
	 * @return
	 * @return String
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static String getHeadPath(Context context) {
		SharedPreferences spf = context.getSharedPreferences(
				SHARED_PREFERENCEK_KEY, 0);
		return spf.getString(HomeConstants.HEAD_PATH, "");
	}

	public static void setHeadPath(Context context, String val) {
		Editor data = context.getSharedPreferences(SHARED_PREFERENCEK_KEY, 0)
				.edit();
		data.putString(HomeConstants.HEAD_PATH, val);
		data.commit();
	}

	/**
	 * @Auther: zhuwt 
	 * @Description: 是否加入新名片提醒
	 * @Date:2015年7月22日下午2:19:33
	 * @param context
	 * @return  
	 * @return boolean 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static boolean getNewRemind(Context context) {
		SharedPreferences spf = context.getSharedPreferences(
				SHARED_PREFERENCEK_KEY, 0);
		return spf.getBoolean(GlobalConstants.NEW_REMIND, false);
	}

	public static void setNewRemind(Context context, boolean str) {
		Editor data = context.getSharedPreferences(SHARED_PREFERENCEK_KEY, 0)
				.edit();
		data.putBoolean(GlobalConstants.NEW_REMIND, str);
		data.commit();
	}
	
	/**
	 * @Auther: zhuwt 
	 * @Description: 是否加入更新名片提醒
	 * @Date:2015年7月22日下午2:19:33
	 * @param context
	 * @return  
	 * @return boolean 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static boolean getUpdateRemind(Context context) {
		SharedPreferences spf = context.getSharedPreferences(
				SHARED_PREFERENCEK_KEY, 0);
		return spf.getBoolean(GlobalConstants.UPDATE_REMIND, false);
	}

	public static void setUpdateRemind(Context context, boolean str) {
		Editor data = context.getSharedPreferences(SHARED_PREFERENCEK_KEY, 0)
				.edit();
		data.putBoolean(GlobalConstants.UPDATE_REMIND, str);
		data.commit();
	}
}

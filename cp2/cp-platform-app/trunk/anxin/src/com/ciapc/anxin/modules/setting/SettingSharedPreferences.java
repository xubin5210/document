package com.ciapc.anxin.modules.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.ciapc.anxin.common.AXSharedPreferences;


public class SettingSharedPreferences extends AXSharedPreferences {
        
      /**
       *     @Auther: zhoumc  
       * @Description: 是否接受新名片通知
       * @Date:2015-6-18上午9:01:22
       * @param context
       * @return  
       * @return String 
       * @说明  代码版权归 杭州安存网络科技有限公司 所有
       */
	public static String getIsNotice(Context context) {
		SharedPreferences spf = context.getSharedPreferences(
				SHARED_PREFERENCEK_KEY, 0);
		return spf.getString(SettingConstants.IS_NOTICE, "");
	}

	public static void setIsNoticeE(Context context, Boolean val) {
		Editor data = context.getSharedPreferences(SHARED_PREFERENCEK_KEY, 0)
				.edit();
		data.putBoolean(SettingConstants.IS_NOTICE, val);
		data.commit();
	}
	
	/**
	 * @Auther: zhoumc  
	 * @Description: 是否开启声音 
	 * @Date:2015-6-18上午9:48:32
	 * @param context
	 * @return  
	 * @return String 
	 * @说明  代码版权归 杭州安存网络科技有限公司 所有
	 */
	public static String getIsSound(Context context) {
		SharedPreferences spf = context.getSharedPreferences(
				SHARED_PREFERENCEK_KEY, 0);
		return spf.getString(SettingConstants.IS_SOUND, "");
	}

	public static void setIsSound(Context context, Boolean val) {
		Editor data = context.getSharedPreferences(SHARED_PREFERENCEK_KEY, 0)
				.edit();
		data.putBoolean(SettingConstants.IS_SOUND, val);
		data.commit();
	}
	
	/**
	 * @Auther: zhoumc  
	 * @Description: 是否开启震动
	 * @Date:2015-6-18上午9:52:18
	 * @param context
	 * @return  
	 * @return String 
	 * @说明  代码版权归 杭州安存网络科技有限公司 所有
	 */
	public static String getIsShake(Context context) {
		SharedPreferences spf = context.getSharedPreferences(
				SHARED_PREFERENCEK_KEY, 0);
		return spf.getString(SettingConstants.IS_SHAKE, "");
	}

	public static void setIsShake(Context context, Boolean val) {
		Editor data = context.getSharedPreferences(SHARED_PREFERENCEK_KEY, 0)
				.edit();
		data.putBoolean(SettingConstants.IS_SHAKE, val);
		data.commit();
	}
	
	/**
	 * @Auther: zhoumc  
	 * @Description: 名片内容自动更新
	 * @Date:2015-6-18上午9:54:28
	 * @param context
	 * @return  
	 * @return String 
	 * @说明  代码版权归 杭州安存网络科技有限公司 所有
	 */
	public static String getCardUpdate(Context context) {
		SharedPreferences spf = context.getSharedPreferences(
				SHARED_PREFERENCEK_KEY, 0);
		return spf.getString(SettingConstants.CARD_UPDATE, "");
	}

	public static void setCardUpdate(Context context, Boolean val) {
		Editor data = context.getSharedPreferences(SHARED_PREFERENCEK_KEY, 0)
				.edit();
		data.putBoolean(SettingConstants.CARD_UPDATE, val);
		data.commit();
	}
	/**
	 * @Auther: zhoumc  
	 * @Description: 新密码
	 * @Date:2015-6-19下午2:38:49
	 * @param context
	 * @return  
	 * @return String 
	 * @说明  代码版权归 杭州安存网络科技有限公司 所有
	 */
	public static String getNewPwd(Context context) {
		SharedPreferences spf = context.getSharedPreferences(
				SHARED_PREFERENCEK_KEY, 0);
		return spf.getString(SettingConstants.NEW_PWD, "");
	}

	public static void setNewPwd(Context context, String val) {
		Editor data = context.getSharedPreferences(SHARED_PREFERENCEK_KEY, 0)
				.edit();
		data.putString(SettingConstants.NEW_PWD, val);
		data.commit();
	}
	
}

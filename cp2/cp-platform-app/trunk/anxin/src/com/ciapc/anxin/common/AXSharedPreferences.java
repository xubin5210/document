/**    
 * @author maomy  
 * @Description: SharedPreferences 
 * @Package com.ciapc.anxin.common   
 * @Title: SharedPreferencesUtil.java   
 * @date 2015-05-25 下午4:03:14   
 * @version V1.0 
 * @说明  代码版权归 杭州安存网络科技有限公司 所有
 */
package com.ciapc.anxin.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AXSharedPreferences {

	public final static String SHARED_PREFERENCEK_KEY = "ciapc_anxin";

	/**
	 * @Auther: zhuwt
	 * @Description: 用户的手机号
	 * @Date:2015年6月17日下午2:21:22
	 * @param context
	 * @return
	 * @return String
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static String getUserPhone(Context context) {
		SharedPreferences spf = context.getSharedPreferences(
				SHARED_PREFERENCEK_KEY, 0);
		return spf.getString(GlobalConstants.PHONE, "");
	}

	public static void setUserPhone(Context context, String val) {
		Editor data = context.getSharedPreferences(SHARED_PREFERENCEK_KEY, 0)
				.edit();
		data.putString(GlobalConstants.PHONE, val);
		data.commit();
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 用户的密码
	 * @Date:2015年6月17日下午2:21:22
	 * @param context
	 * @return
	 * @return String
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static String getUserPwd(Context context) {
		SharedPreferences spf = context.getSharedPreferences(
				SHARED_PREFERENCEK_KEY, 0);
		return spf.getString(GlobalConstants.PWD, "");
	}

	public static void setUserPwd(Context context, String val) {
		Editor data = context.getSharedPreferences(SHARED_PREFERENCEK_KEY, 0)
				.edit();
		data.putString(GlobalConstants.PWD, val);
		data.commit();
	}

	/**
	 * @Auther: zhuwt
	 * @Description:登录令牌
	 * @Date:2015年6月17日下午2:27:31
	 * @param context
	 * @return
	 * @return String
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static String getTokenId(Context context) {
		SharedPreferences spf = context.getSharedPreferences(
				SHARED_PREFERENCEK_KEY, 0);
		return spf.getString(GlobalConstants.TOKEN_ID, "");
	}

	public static void setTokenId(Context context, String str) {
		Editor data = context.getSharedPreferences(SHARED_PREFERENCEK_KEY, 0)
				.edit();
		data.putString(GlobalConstants.TOKEN_ID, str);
		data.commit();
	}

	/**
	 * @Auther: zhoumc
	 * @Description: 新消息提醒
	 * @Date:2015-7-6下午2:09:20
	 * @param context
	 * @return
	 * @return String
	 * @说明 代码版权归 杭州安存网络科技有限公司 所有
	 */
	public static boolean getNewInfoRemind(Context context) {
		SharedPreferences spf = context.getSharedPreferences(
				SHARED_PREFERENCEK_KEY, 0);
		return spf.getBoolean(GlobalConstants.NEWINFO_REMIND, true);
	}

	public static void setNewInfoRemind(Context context, boolean str) {
		Editor data = context.getSharedPreferences(SHARED_PREFERENCEK_KEY, 0)
				.edit();
		data.putBoolean(GlobalConstants.NEWINFO_REMIND, str);
		data.commit();
	}

	/**
	 * @Auther: zhoumc
	 * @Description: 声音
	 * @Date:2015-7-6下午2:09:20
	 * @param context
	 * @return
	 * @return String
	 * @说明 代码版权归 杭州安存网络科技有限公司 所有
	 */
	public static boolean getSound(Context context) {
		SharedPreferences spf = context.getSharedPreferences(
				SHARED_PREFERENCEK_KEY, 0);
		return spf.getBoolean(GlobalConstants.SOUND, true);
	}

	public static void setSound(Context context, boolean str) {
		Editor data = context.getSharedPreferences(SHARED_PREFERENCEK_KEY, 0)
				.edit();
		data.putBoolean(GlobalConstants.SOUND, str);
		data.commit();
	}

	/**
	 * @Auther: zhoumc
	 * @Description: 震动
	 * @Date:2015-7-6下午2:09:20
	 * @param context
	 * @return
	 * @return String
	 * @说明 代码版权归 杭州安存网络科技有限公司 所有
	 */
	public static boolean getShake(Context context) {
		SharedPreferences spf = context.getSharedPreferences(
				SHARED_PREFERENCEK_KEY, 0);
		return spf.getBoolean(GlobalConstants.SHAKE, true);
	}

	public static void setShake(Context context, boolean str) {
		Editor data = context.getSharedPreferences(SHARED_PREFERENCEK_KEY, 0)
				.edit();
		data.putBoolean(GlobalConstants.SHAKE, str);
		data.commit();
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 是否登录
	 * @Date:2015年6月17日下午2:30:10
	 * @param context
	 * @return
	 * @return String
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static boolean getLoginType(Context context) {
		SharedPreferences spf = context.getSharedPreferences(
				SHARED_PREFERENCEK_KEY, 0);
		return spf.getBoolean(GlobalConstants.LOGIN_TYPE, false);
	}

	public static void setLoginType(Context context, boolean str) {
		Editor data = context.getSharedPreferences(SHARED_PREFERENCEK_KEY, 0)
				.edit();
		data.putBoolean(GlobalConstants.LOGIN_TYPE, str);
		data.commit();
	}

	/**
	 * @Auther: zhuwt
	 * @Description:获取名片ID
	 * @Date:2015年6月17日下午2:27:31
	 * @param context
	 * @return
	 * @return String
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static String getCardId(Context context) {
		SharedPreferences spf = context.getSharedPreferences(
				SHARED_PREFERENCEK_KEY, 0);
		return spf.getString(GlobalConstants.CARD_ID, "");
	}

	public static void setCardIdId(Context context, String str) {
		Editor data = context.getSharedPreferences(SHARED_PREFERENCEK_KEY, 0)
				.edit();
		data.putString(GlobalConstants.CARD_ID, str);
		data.commit();
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 企业名片号
	 * @Date:2015年6月30日下午2:21:29
	 * @param context
	 * @return
	 * @return String
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static String getEntId(Context context) {
		SharedPreferences spf = context.getSharedPreferences(
				SHARED_PREFERENCEK_KEY, 0);
		return spf.getString(GlobalConstants.ENT_ID, "");
	}

	public static void setEntId(Context context, String str) {
		Editor data = context.getSharedPreferences(SHARED_PREFERENCEK_KEY, 0)
				.edit();
		data.putString(GlobalConstants.ENT_ID, str);
		data.commit();
	}

	/**
	 * @Auther: zhuwt
	 * @Description:验证绑定企业状态
	 * @Date:2015年7月7日下午7:39:48
	 * @param context
	 * @return
	 * @return String
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static String getEntStatus(Context context) {
		SharedPreferences spf = context.getSharedPreferences(
				SHARED_PREFERENCEK_KEY, 0);
		return spf.getString(GlobalConstants.ENT_STATUS, "0");
	}

	public static void setEntStatus(Context context, String str) {
		Editor data = context.getSharedPreferences(SHARED_PREFERENCEK_KEY, 0)
				.edit();
		data.putString(GlobalConstants.ENT_STATUS, str);
		data.commit();
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 个人资料状态 1 完善资料 2未完善资料 3 完善资料 但是公司审核未通过
	 * @Date:2015年6月18日下午3:37:57
	 * @param context
	 * @return
	 * @return int
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static String getUserInfoStatus(Context context) {
		SharedPreferences spf = context.getSharedPreferences(
				SHARED_PREFERENCEK_KEY, 0);
		return spf.getString(GlobalConstants.USER_STATUS, "");
	}

	public static void setUserInfoStatus(Context context, String str) {
		Editor data = context.getSharedPreferences(SHARED_PREFERENCEK_KEY, 0)
				.edit();
		data.putString(GlobalConstants.USER_STATUS, str);
		data.commit();
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 隐私设置
	 * @Date:2015年7月1日上午10:24:10
	 * @param context
	 * @return
	 * @return String
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static String getPrivacy(String type, Context context) {
		SharedPreferences spf = context.getSharedPreferences(
				SHARED_PREFERENCEK_KEY, 0);
		return spf.getString(type, "0");
	}

	public static void setPrivacy(Context context, String str, String type) {
		Editor data = context.getSharedPreferences(SHARED_PREFERENCEK_KEY, 0)
				.edit();
		data.putString(type, str);
		data.commit();
	}

	/**
	 * @Auther: zhoumc
	 * @Description: 新消息提醒 //0 不接受 1 接受
	 * @Date:2015-7-3下午5:22:58
	 * @param type
	 * @param context
	 * @return
	 * @return String
	 * @说明 代码版权归 杭州安存网络科技有限公司 所有
	 */
	public static String getNewInfo(Context context) {
		SharedPreferences spf = context.getSharedPreferences(
				SHARED_PREFERENCEK_KEY, 0);
		return spf.getString(GlobalConstants.USER_STATUS, "0");
	}

	public static void setNewInfo(Context context, String str) {
		Editor data = context.getSharedPreferences(SHARED_PREFERENCEK_KEY, 0)
				.edit();
		data.putString(GlobalConstants.USER_STATUS, str);
		data.commit();
	}

}

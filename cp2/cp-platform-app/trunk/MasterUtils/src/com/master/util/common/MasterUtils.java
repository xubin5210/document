/**    
 * @author maomy  
 * @Description: 工具类 
 * @Package com.master.util.common   
 * @Title: MasterUtils.java   
 * @date 2014年8月8日 下午1:56:19   
 * @version V2.0 
 * @说明  代码版权归 杭州反盗版中心有限公司 所有
 */
package com.master.util.common;

import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;

public class MasterUtils {

	/**
	 * @Auther: maomy
	 * @Description: 获取meta data 里的数据
	 * @Date:2014年6月23日上午9:48:30
	 * @param context
	 * @param metaKey
	 * @return
	 * @return String
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static String getMetaValue(Context context, String metaKey) {
		Bundle metaData = null;
		String apiKey = null;
		if (context == null || metaKey == null) {
			return null;
		}
		try {
			ApplicationInfo ai = context.getPackageManager()
					.getApplicationInfo(context.getPackageName(),
							PackageManager.GET_META_DATA);
			if (null != ai) {
				metaData = ai.metaData;
			}
			if (null != metaData) {
				apiKey = metaData.getString(metaKey);
			}
		} catch (NameNotFoundException e) {

		}
		return apiKey;
	}

	
	/**
	 * @Auther: maomy  
	 * @Description: 获取手机网卡的mac地址
	 * @Date:2014年10月22日下午6:02:51
	 * @return  
	 * @return String 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static String getLocalMacAddress(Context context) {  
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);  
        WifiInfo info = wifi.getConnectionInfo();  
        return info.getMacAddress();  
    } 
	
	// 软件版本号
	@SuppressWarnings("unused")
	public static String getTelAppVer(Context context) {
		PackageInfo info;
		int versionCode = 0;
		String versionName = null;
		try {
			info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			// 当前应用的版本名称
			versionName = info.versionName;
			// 当前版本的版本号
			versionCode = info.versionCode;
			// 当前版本的包名
			String packageNames = info.packageName;

		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return versionName;
	}

	/**
	 * 判断当前手机是否已经安装过该应用
	 * 
	 * @param context
	 * @param packName
	 * @return
	 */
	public static boolean isInstall(Context context, String packName) {
		PackageManager packageManager = context.getPackageManager();
		List<PackageInfo> packageInfoList = packageManager
				.getInstalledPackages(0);
		for (int i = 0; i < packageInfoList.size(); i++) {
			PackageInfo pinfo = packageInfoList.get(i);
			if (packName.equals(pinfo.applicationInfo.packageName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 检测Sdcard是否存在
	 * 
	 * @return
	 */
	public static boolean isExitsSdcard() {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
			return true;
		else
			return false;
	}
	
	/**
	 * @Auther: zhuwt  
	 * @Description:获取手机运营商
	 * @Date:2014-9-23下午5:28:47
	 * @param mContext
	 * @return  
	 * @return String 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static String getIMSI(Context mContext) {
		TelephonyManager tm = (TelephonyManager) mContext
				.getSystemService(Context.TELEPHONY_SERVICE);
		String imsi = tm.getSubscriberId();
		if (imsi != null) {
			if (imsi.startsWith("46000") || imsi.startsWith("46002")) {
				// 移动
				return "移动";
			} else if (imsi.startsWith("46001")) {
				// 中国联通
				return "联通";
			} else if (imsi.startsWith("46003")) {
				// 中国电信
				return "电信";
			}else{
				return "电信";
			}
		}
		return "电信.移动.联通";
	}
}

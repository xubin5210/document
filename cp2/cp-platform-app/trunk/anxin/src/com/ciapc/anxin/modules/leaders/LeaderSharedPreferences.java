/**    
 * @author maomy  
 * @Description: 当前模块下的 SharedPreferences 
 * @Package com.ciapc.anxin.modules.sample   
 * @Title: SampleSharefpre.java   
 * @date 2015年5月25日 上午10:16:31   
 * @version V1.0 
 * @说明  代码版权归 杭州安存网络科技有限公司 所有
 */
package com.ciapc.anxin.modules.leaders;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.ciapc.anxin.common.AXSharedPreferences;
import com.ciapc.anxin.common.GlobalConstants;

public class LeaderSharedPreferences extends AXSharedPreferences {

	/**
	 * @Auther: zhuwt 
	 * @Description: 是否是第一次启动
	 * @Date:2015年6月2日上午9:53:18
	 * @param context
	 * @return  
	 * @return boolean 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static boolean getInitialize(Context context) {
		SharedPreferences spf = context.getSharedPreferences(
				SHARED_PREFERENCEK_KEY, 0);
		return spf.getBoolean(GlobalConstants.INITIALIZE, false);
	}

	public static void setInitialize(Context context, boolean val) {
		Editor data = context.getSharedPreferences(SHARED_PREFERENCEK_KEY, 0)
				.edit();
		data.putBoolean(GlobalConstants.INITIALIZE, val);
		data.commit();
	}

}

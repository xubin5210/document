/**    
 * @author maomy  
 * @Description: 加载等待/简单提示dialog
 * @Package com.ciapc.tzd.modules.common.dialog   
 * @Title: DialogUtil.java   
 * @date 2013-10-12 上午10:01:26   
 * @version V1.0 
 * @说明  代码版权归 杭州反盗版中心有限公司 所有
 */
package com.master.util.common;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;


public class DialogUtil {


	public static Dialog showWaitingDialog(Context context, int iResID) {
		ProgressDialog pd = new ProgressDialog(context);
		pd.setMessage(context.getString(iResID));
		pd.show();
		return pd;
	}


	public static void showSystemToast(Context context, int iResID) {
		Toast toast = Toast.makeText(context, iResID, Toast.LENGTH_SHORT);
		// toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 0);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	public static void showSystemToast(Context context, String str) {
		Toast toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
		// toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 0);
		// toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	public static void showSystemToastL(Context context, int iResID) {
		Toast toast = Toast.makeText(context, iResID, Toast.LENGTH_LONG);
		// toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 0);
		// toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	public static void showSystemToastL(Context context, String str) {
		Toast toast = Toast.makeText(context, str, Toast.LENGTH_LONG);
		// toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 0);
		// toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}


}

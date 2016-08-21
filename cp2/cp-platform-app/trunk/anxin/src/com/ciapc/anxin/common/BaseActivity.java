/**    
 * @author maomy  
 * @Description: 所有新建的activity   都要继承该类
 * @Package com.ciapc.anxin.common   
 * @Title: BaseActivity.java   
 * @date 2015-5-25 下午2:44:18   
 * @version V1.0  
   @说明  代码版权归  杭州安存网络科技有限公司 所有
 */
package com.ciapc.anxin.common;

import java.util.ArrayList;
import java.util.HashMap;

import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class BaseActivity extends Activity {

	/**
	 * activity 界面栈
	 */
	public static final ArrayList<Activity> activities = new ArrayList<Activity>();

	public static HashMap<String, Object> map = new HashMap<String, Object>();

	public static HashMap<String, Object> getMap() {
		return map;
	}

	public static void setMap(HashMap<String, Object> map) {
		BaseActivity.map = map;
	}

	/**
	 * @Auther: zhuwt
	 * @Description:添加数据
	 * @Date:2015年6月18日下午9:49:18
	 * @param key
	 * @param value
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void setMapValue(String key, Object value) {
		getMap().put(key, value);
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 获取数据
	 * @Date:2015年6月18日下午9:54:22
	 * @param key
	 * @return
	 * @return String
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public Object getMapValue(String key) {
		return getMap().get(key);
	}

	/**
	 * @Auther: maomy
	 * @Description: 将返回按钮初始化
	 * @Date:2015年5月25日上午9:43:35
	 * @param activity
	 * @param backHome
	 * @return void
	 * @说明 代码版权归 杭州安存网络科技有限公司 所有
	 */
	protected void initBackButton(final Activity activity, View backHome) {

		activities.add(activity);
		if (GlobalConstants.isDebug)
			Log.i("BaseActivity", activity.toString());
		backHome.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				activities.remove(activity);
				activity.finish();
			}
		});

	}
	

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

}

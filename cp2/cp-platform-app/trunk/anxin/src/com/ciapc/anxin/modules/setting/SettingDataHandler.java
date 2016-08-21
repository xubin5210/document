package com.ciapc.anxin.modules.setting;

import java.util.HashMap;

import org.apache.http.Header;

import com.ciapc.anxin.common.AXSharedPreferences;
import com.ciapc.anxin.common.GlobalConstants;
import com.ciapc.anxin.common.InterfaceConstants;
import com.ciapc.anxin.common.callback.AXCallBack;
import com.ciapc.anxin.modules.perfect.PerfectDataHandler;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.master.util.common.DialogUtil;
import com.master.util.common.GsonUtils;
import com.master.util.common.Md5Util;
import com.master.util.common.StringUtils;
import com.master.util.http.MasterHttpClient;

import android.content.Context;
import android.util.Log;

public class SettingDataHandler {
	
	private final String TAG = "SettingDataHandler";
	
	public static Context mContext;
	
	private static SettingDataHandler settingDataHandler;
	
	public SettingDataHandler(Context context) {
		mContext=context;
	}
	
	public static SettingDataHandler getInstance(Context context){
		if (null == settingDataHandler) {
			settingDataHandler = new SettingDataHandler(context);
		}
		return settingDataHandler;
	}
	
	/**
	 * @Auther: zhoumc  
	 * @Description: 修改密码
	 * @Date:2015-6-23下午4:30:04
	 * @param pwd     密码
	 * @param newpwd  新密码
	 * @param back    回滚
	 * @return void 
	 * @说明  代码版权归 杭州安存网络科技有限公司 所有
	 */
	public void toRevisePwd(String pwd,final String newpwd,final AXCallBack back){
		HashMap<String, String>  map=new HashMap<String, String>();
		map.put("tokenid", AXSharedPreferences.getTokenId(mContext));
		map.put("pwd", Md5Util.encode(pwd));
		map.put("newpwd", Md5Util.encode(newpwd));
		map.put(InterfaceConstants.INTERFACE_NAME, InterfaceConstants.UPDATE_PWD_CODE);
		MasterHttpClient.postForJava(mContext, map, new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
			     String result=new String(arg2);
			     if (StringUtils.isNotEmpty(result)) {
			    	 back.onCallBackString(result);
					if (GlobalConstants.isDebug) {
						Log.i(TAG, result);
					}
					
			     }else {
					DialogUtil.showSystemToast(mContext, GsonUtils.getJsonValue(result,GlobalConstants.HTTP_MSG));
				}
			     
				
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				if (GlobalConstants.isDebug) {
					Log.i(TAG, arg0 + "");
				}
				DialogUtil.showSystemToast(mContext, "连接异常，请稍后再试！");
				
			}
		});
		
	}
	
}

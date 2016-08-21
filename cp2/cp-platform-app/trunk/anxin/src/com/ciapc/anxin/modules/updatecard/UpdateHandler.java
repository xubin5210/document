package com.ciapc.anxin.modules.updatecard;

import java.util.HashMap;

import org.apache.http.Header;

import com.ciapc.anxin.common.AXSharedPreferences;
import com.ciapc.anxin.common.GlobalConstants;
import com.ciapc.anxin.common.InterfaceConstants;
import com.ciapc.anxin.common.callback.AXCallBack;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.master.util.common.StringUtils;
import com.master.util.http.MasterHttpClient;

import android.content.Context;
import android.util.Log;

public class UpdateHandler {
	
	private final String TAG = "UpdateHandler";
	
	public static UpdateHandler mHandler;
	
	private Context mContext;
	
	public UpdateHandler(Context context){
		mContext = context;
	}
	
	public static UpdateHandler getInstance(Context context){
		if(null == mHandler){
			mHandler = new UpdateHandler(context);
		}
		return mHandler;
	}
	
	/**
	 * @Auther: zhuwt 
	 * @Description: 获取所有名片更新
	 * @Date:2015年6月23日下午6:18:24  
	 * @return void 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void getUpdate(int start,String tine,final AXCallBack back){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("tokenid", AXSharedPreferences.getTokenId(mContext));
		map.put("pageNo", start+"");
		map.put("searchTime", start+"");
		map.put("pageSize", InterfaceConstants.PAGE_SIZE);
		map.put(InterfaceConstants.INTERFACE_NAME, InterfaceConstants.UPDATE_CARD_CODE);
		MasterHttpClient.postForJava(mContext, map, new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				String result = new String(arg2);
				if (StringUtils.isNotEmpty(result)) {
					if (GlobalConstants.isDebug) {
						Log.i(TAG, "code = " + arg0 + " , msg = "
								+ new String(arg2));
					}
					back.onCallBackString(result);
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
					if (GlobalConstants.isDebug) {
						Log.i(TAG, "code = " + arg0);
					}
					back.onCallBackString(null);
			}
		});
	}
}

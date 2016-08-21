package com.ciapc.anxin.modules.message;

import java.util.HashMap;

import org.apache.http.Header;

import com.ciapc.anxin.common.AXSharedPreferences;
import com.ciapc.anxin.common.GlobalConstants;
import com.ciapc.anxin.common.InterfaceConstants;
import com.ciapc.anxin.common.callback.AXCallBack;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.master.util.common.StringUtils;
import com.master.util.common.SystemUtils;
import com.master.util.http.MasterHttpClient;

import android.content.Context;
import android.util.Log;

/**
 * 
 * @author zhuwt
 * @Description: 消息推送
 * @ClassName: MessageDateHandler.java
 * @date 2015年7月1日 下午6:14:23
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */
public class MessageDateHandler {

	private Context mContext;

	public static MessageDateHandler mDateHandler;

	private final String TAG = "MessageDateHandler";

	public MessageDateHandler(Context context) {
		mContext = context;
	}

	public static MessageDateHandler getInstance(Context context) {
		if (mDateHandler == null) {
			mDateHandler = new MessageDateHandler(context);
		}
		return mDateHandler;
	}

	/**
	 * @Auther: zhuwt
	 * @Description:上报clientId
	 * @Date:2015年6月17日下午5:50:13
	 * @param cid
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void uploadClientId(String cid, final AXCallBack back) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("channel ", SystemUtils.getMetaData(mContext, "UMENG_CHANNEL"));
		map.put("tokenid", AXSharedPreferences.getTokenId(mContext));
		map.put("versions", SystemUtils.getAppVersionName(mContext));
		map.put("platform", "测试");
		map.put("clientId", cid);
		map.put(InterfaceConstants.INTERFACE_NAME,
				InterfaceConstants.UPLOAD_CLIENTID_CODE);
		MasterHttpClient.postForJava(mContext, map,
				new AsyncHttpResponseHandler() {

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
	
	/**
	 * @Auther: zhuwt 
	 * @Description:名片已读
	 * @Date:2015年7月5日下午4:04:07
	 * @param id
	 * @param back  
	 * @return void 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void sendRead(String id, final AXCallBack back) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("tokenid", AXSharedPreferences.getTokenId(mContext));
		map.put("messageId", id);
		map.put(InterfaceConstants.INTERFACE_NAME,
				InterfaceConstants.SEND_READ);
		MasterHttpClient.postForJava(mContext, map,
				new AsyncHttpResponseHandler() {

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

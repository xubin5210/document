package com.ciapc.anxin.modules.buscard;

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

/**
 * 
 * @author zhuwt
 * @Description: 已经交换过的名片的历史数据
 * @ClassName: NewCardDateHandler.java
 * @date 2015年6月23日 上午10:15:29
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */
public class NewCardDateHandler {
	
	private final String TAG = "NewCardDateHandler";
	
	private static NewCardDateHandler mCardDateHandler;

	private Context mContext;

	public NewCardDateHandler(Context context) {
		mContext = context;
	}

	public static NewCardDateHandler getInstance(Context context) {
		if (null == mCardDateHandler) {
			mCardDateHandler = new NewCardDateHandler(context);
		}
		return mCardDateHandler;
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 获取已经交换过的名片的数据 all：所有状态 exchange:以交换
	 * @Date:2015年6月23日上午10:25:06
	 * @param start
	 * @param back
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void getData(String start,String type, final AXCallBack back) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("tokenid", AXSharedPreferences.getTokenId(mContext));
		map.put("searchType", type);
		map.put("pageNo", start);
		map.put("pageSize", InterfaceConstants.PAGE_SIZE);
		map.put(InterfaceConstants.INTERFACE_NAME,
				InterfaceConstants.EXCHANGE_RECORD_CODE);
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
						Log.i(TAG, "code = " + arg0);
						back.onCallBackString(null);

					}
				});
	}
}

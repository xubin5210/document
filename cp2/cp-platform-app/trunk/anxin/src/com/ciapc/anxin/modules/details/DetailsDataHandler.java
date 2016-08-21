package com.ciapc.anxin.modules.details;

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
 * @author zhuwt
 * @Description: 详情
 * @ClassName: DetailsDataHandler.java
 * @date 2015年6月25日 上午10:41:37
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */

public class DetailsDataHandler {

	private final String TAG = "DetailsDataHandler";

	public static DetailsDataHandler mDataHandler;

	public Context mContext;

	public DetailsDataHandler(Context context) {
		mContext = context;
	}

	public static DetailsDataHandler getInstance(Context context) {
		if (null == mDataHandler) {
			mDataHandler = new DetailsDataHandler(context);
		}
		return mDataHandler;
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 举报
	 * @Date:2015年6月25日上午10:50:26
	 * @param cardId
	 * @param reportType
	 *            举报类型对象（1：个人,2:企业）
	 * @param reportReason
	 *            举报原因
	 * @param reportComments
	 *            补充说明
	 * @param back
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void report(String cardId, String reportType, String reportReason,
			String reportComments, final AXCallBack back) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("tokenid", AXSharedPreferences.getTokenId(mContext));
		map.put("cardId", cardId);
		map.put("reportType", reportType);
		map.put("reportReason", reportReason);
		map.put("reportComments", reportComments);
		map.put(InterfaceConstants.INTERFACE_NAME,
				InterfaceConstants.REPORT_CODE);
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
	 * @Description:获取企业名片详情
	 * @Date:2015年6月29日上午11:51:20
	 * @param id
	 * @param back  
	 * @return void 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void getCompanyInfo(String id, final AXCallBack back) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("tokenid", AXSharedPreferences.getTokenId(mContext));
		map.put("entId", id);
		map.put(InterfaceConstants.INTERFACE_NAME,
				InterfaceConstants.COMPANY_DETAIL_CODE);
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
	 * @Description: 删除名片 
	 * @Date:2015年6月26日下午4:51:02
	 * @param cardId
	 * @param back  
	 * @return void 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void delCard(String  cardId,final AXCallBack back){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("tokenid", AXSharedPreferences.getTokenId(mContext));
		map.put("cardId", cardId);
		map.put(InterfaceConstants.INTERFACE_NAME, InterfaceConstants.DEE_CARD_CODE);
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
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				if (GlobalConstants.isDebug) {
					Log.i(TAG, "code = " + arg0);
				}
				back.onCallBackString(null);
			}
		});
	}
	
	/**
	 * @Auther: zhuwt 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @Date:2015年7月5日下午2:52:54
	 * @param cardId  
	 * @return void 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void getPersonDynamci(String cardId,final AXCallBack back){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("tokenid", AXSharedPreferences.getTokenId(mContext));
		map.put("cardId", cardId);
		map.put(InterfaceConstants.INTERFACE_NAME, InterfaceConstants.UPDATE_PERSON_CARD_CODE);
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
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				if (GlobalConstants.isDebug) {
					Log.i(TAG, "code = " + arg0);
				}
				back.onCallBackString(null);
			}
		});
	}
	
	/**
	 * @Auther: zhuwt 
	 * @Description:获取企业下 已经交换的名片
	 * @Date:2015年7月5日下午2:52:54
	 * @param cardId  
	 * @return void 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void getEntContact(String entId,final AXCallBack back){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("tokenid", AXSharedPreferences.getTokenId(mContext));
		map.put("entId", entId);
		map.put(InterfaceConstants.INTERFACE_NAME, InterfaceConstants.COMPANY_BIND_CODE);
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
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				if (GlobalConstants.isDebug) {
					Log.i(TAG, "code = " + arg0);
				}
				back.onCallBackString(null);
			}
		});
	}
}

package com.ciapc.anxin.modules.contacts;

import java.util.HashMap;

import org.apache.http.Header;

import com.ciapc.anxin.common.AXSharedPreferences;
import com.ciapc.anxin.common.GlobalConstants;
import com.ciapc.anxin.common.InterfaceConstants;
import com.ciapc.anxin.common.callback.AXCallBack;
import com.ciapc.anxin.common.view.AxinDialog;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.master.util.common.StringUtils;
import com.master.util.http.MasterHttpClient;

import android.content.Context;
import android.util.Log;

/**
 * @author zhuwt
 * @Description: 企业通讯录
 * @ClassName: ContactDateHandler.java
 * @date 2015年6月29日 下午6:23:02
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */

public class ContactDateHandler {

	private Context mContext;

	private final String TAG = "ContactDateHandler";

	public static ContactDateHandler mHandler;

	public ContactDateHandler(Context context) {
		mContext = context;
	}

	public static ContactDateHandler getInstance(Context context) {
		if (null == mHandler) {
			mHandler = new ContactDateHandler(context);
		}
		return mHandler;
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 获取企业成员
	 * @Date:2015年6月30日下午4:36:14
	 * @param entId
	 * @param back
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void getContactData(String type, String content,final AXCallBack back) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("tokenid", AXSharedPreferences.getTokenId(mContext));
		map.put("entId", AXSharedPreferences.getEntId(mContext));
		map.put("searchType", type);
		map.put("content", content);
		map.put(InterfaceConstants.INTERFACE_NAME,
				InterfaceConstants.SEARCH_COMPANY_PERSON_CODE);
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

	/**
	 * @Auther: zhuwt
	 * @Description: 获取企业通讯录 名片详情
	 * @Date:2015年7月6日下午2:16:58
	 * @param cardId
	 * @param back
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void getContectDetail(String cardId, final AXCallBack back) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("tokenid", AXSharedPreferences.getTokenId(mContext));
		map.put("cardId", cardId);
		map.put(InterfaceConstants.INTERFACE_NAME,
				InterfaceConstants.COMPANY_PERSON_DETAIL_CODE);
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

	/**
	 * @Auther: zhuwt
	 * @Description:搜索企业通讯录 个人和部门
	 * @Date:2015年6月30日下午6:00:44
	 * @param content
	 * @param back
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void searchContact(String content, final AXCallBack back) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("tokenid", AXSharedPreferences.getTokenId(mContext));
		map.put("content", content);
		map.put("entId", AXSharedPreferences.getEntId(mContext));
		map.put(InterfaceConstants.INTERFACE_NAME,
				InterfaceConstants.SEARCH_COMPANY_P_D_CODE);
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
	
	/**
	 * @Auther: zhuwt 
	 * @Description: 获取企业搜索部门信息
	 * @Date:2015年7月7日上午11:46:16  
	 * @return void 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void getDeptName(String content,final AXCallBack back){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("tokenid", AXSharedPreferences.getTokenId(mContext));
		map.put("content", content);
		map.put("entId", AXSharedPreferences.getEntId(mContext));
		map.put(InterfaceConstants.INTERFACE_NAME,
				InterfaceConstants.SEARCH_COMPANY_DEPARTMENT_CODE);
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
				Log.i(TAG, "code = " + arg0);
				back.onCallBackString(null);
			}
		});
	}
}

package com.ciapc.anxin.modules.perfect;

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

public class PerfectDataHandler {

	public static Context mContext;

	public static PerfectDataHandler dataHandler;

	private final String TAG = "PerfectDataHandler";

	public PerfectDataHandler(Context context) {
		mContext = context;
	}

	public static PerfectDataHandler getInstance(Context context) {
		if (null == dataHandler) {
			dataHandler = new PerfectDataHandler(context);
		}
		return dataHandler;
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 完善资料
	 * @Date:2015年6月20日下午1:56:42
	 * @param map
	 * @param back
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void updatePerfect(String cardId, String trueName, String email,
			String positionName, final AXCallBack back) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("tokenid", AXSharedPreferences.getTokenId(mContext));
		map.put("cardId", cardId);
		map.put("trueName", trueName);
		map.put("email", email);
		map.put("positionName", positionName);
		map.put(InterfaceConstants.INTERFACE_NAME,
				InterfaceConstants.UPDATE_PERSON_CODE);
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
	 * @Auther: zhoumc
	 * @Description:
	 * @Date:2015-6-30下午6:23:20
	 * @param cardId
	 *            卡片号
	 * @param nickName
	 *            昵称
	 * @param ename
	 *            英文名
	 * @param jobs
	 *            职务
	 * @param deptNames
	 *            部门
	 * @param mobile
	 *            手机号
	 * @param phone
	 *            座机
	 * @param fax
	 *            传真
	 * @param email
	 *            邮箱
	 * @param qq
	 *            QQ
	 * @param wx
	 *            微信
	 * @param company
	 *            企业
	 * @return void
	 * @说明 代码版权归 杭州安存网络科技有限公司 所有
	 */
	public void updatePersonInfo(String cardId, String nickName,
			String trueName, String ename, String jobs, String deptNames,
			String mobile, String phone, String fax, String email, String qq,
			String wx, String nameId, final AXCallBack back) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("tokenid", AXSharedPreferences.getTokenId(mContext));
		map.put("cardId", AXSharedPreferences.getCardId(mContext));
			map.put("nickName", nickName);
			map.put("trueName", trueName);
			map.put("ename", ename);
			map.put("positionName", jobs);
			map.put("deptName", deptNames);
			map.put("mobile", mobile);
			map.put("phone", phone);
			map.put("fax", fax);
			map.put("email", email);
			map.put("qq", qq);
			map.put("weiXin", wx);
			map.put("userIdcard", nameId);
		map.put(InterfaceConstants.INTERFACE_NAME,
				InterfaceConstants.UPDATE_PERSON_CODE);
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
	 * @Description: 修改座右铭
	 * @Date:2015年7月17日下午5:57:33
	 * @param motto
	 * @param back
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void updateMotto(String motto, final AXCallBack back) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("tokenid", AXSharedPreferences.getTokenId(mContext));
		map.put("cardId", AXSharedPreferences.getCardId(mContext));
		map.put("motto", motto);
		map.put(InterfaceConstants.INTERFACE_NAME,
				InterfaceConstants.UPDATE_PERSON_CODE);
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
	 * @Description: 修改职位 
	 * @Date:2015年7月17日下午6:00:31
	 * @param motto
	 * @param back  
	 * @return void 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void updatePost(String motto, final AXCallBack back) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("tokenid", AXSharedPreferences.getTokenId(mContext));
		map.put("cardId", AXSharedPreferences.getCardId(mContext));
		map.put("positionName", motto);
		map.put(InterfaceConstants.INTERFACE_NAME,
				InterfaceConstants.UPDATE_PERSON_CODE);
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
	 * @Auther: zhoumc
	 * @Description: 修改开关状态
	 * @Date:2015-7-3下午2:57:01
	 * @param cardId
	 *            名片开关
	 * @param mobile
	 *            手机号开关
	 * @param email
	 *            邮箱开关
	 * @param position
	 *            职位开关
	 * @param nickName
	 *            昵称开关
	 * @param status
	 *            获取状态
	 * @return void
	 * @说明 代码版权归 杭州安存网络科技有限公司 所有
	 */
	public void updateSwithStatus(String cardId, String mobile, String email,
			String position, String nickName, final AXCallBack back) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("tokenid", AXSharedPreferences.getTokenId(mContext));
		map.put("cardId", cardId);
		map.put("mobile", mobile);
		map.put("email", email);
		map.put("position", position);
		map.put("nickName", nickName);
		map.put(InterfaceConstants.INTERFACE_NAME,
				InterfaceConstants.PRIVACY_CODE);
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
	 * @Description: 获取个人资料
	 * @Date:2015年6月20日下午1:57:38
	 * @param map
	 * @param back
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void getPersonData(String cardId, final AXCallBack back) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("tokenid", AXSharedPreferences.getTokenId(mContext));
		map.put("cardId", cardId);
		map.put(InterfaceConstants.INTERFACE_NAME,
				InterfaceConstants.CARD_DETAILS_CODE);
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
	 * @Description: 获取自己的资料
	 * @Date:2015年6月20日下午1:57:38
	 * @param map
	 * @param back
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void getMyData(final AXCallBack back) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("tokenid", AXSharedPreferences.getTokenId(mContext));
		map.put(InterfaceConstants.INTERFACE_NAME,
				InterfaceConstants.GET_MAY_DATE);
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
	 * @Description: 搜已经注册的企业
	 * @Date:2015年6月20日下午3:34:29
	 * @param searchType
	 * @param content
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void searchCompany(String content, final AXCallBack back) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("tokenid", AXSharedPreferences.getTokenId(mContext));
		map.put("content", content);
		map.put(InterfaceConstants.INTERFACE_NAME,
				InterfaceConstants.SEARCH_COMPANY_CODE);
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
	 * @Description: 绑定企业接口
	 * @Date:2015年7月1日上午9:46:21
	 * @param cardId
	 * @param entId
	 * @param back
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void bindCompany(String cardId, String entId, final AXCallBack back) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("tokenid", AXSharedPreferences.getTokenId(mContext));
		map.put("cardId", cardId);
		map.put("entId", entId);
		map.put(InterfaceConstants.INTERFACE_NAME,
				InterfaceConstants.BIND_COMPANY_CODE);
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
	 * @Description:获取职位信息
	 * @Date:2015年7月5日上午11:17:17
	 * @param entId
	 * @param back
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void getJob(final AXCallBack back) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("tokenid", AXSharedPreferences.getTokenId(mContext));
		map.put("entId", AXSharedPreferences.getEntId(mContext));
		map.put(InterfaceConstants.INTERFACE_NAME, InterfaceConstants.GET_JOB);
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

package com.ciapc.anxin.modules.exchange;

import java.util.HashMap;

import org.apache.http.Header;

import com.ciapc.anxin.common.AXSharedPreferences;
import com.ciapc.anxin.common.GlobalConstants;
import com.ciapc.anxin.common.InterfaceConstants;
import com.ciapc.anxin.common.callback.AXCallBack;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.master.util.common.StringUtils;
import com.master.util.http.MasterHttpClient;

import android.content.Context;
import android.util.Log;

public class ExchangeDataHandler {

	private final String TAG = "ExchangeDataHandler";

	private static ExchangeDataHandler mDataHandler;

	private Context mContext;

	public ExchangeDataHandler(Context context) {
		mContext = context;
	}

	public static ExchangeDataHandler getInstance(Context context) {
		if (null == mDataHandler) {
			mDataHandler = new ExchangeDataHandler(context);
		}
		return mDataHandler;
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 添加新名片
	 * @Date:2015年6月23日上午11:01:03
	 * @param cardId
	 * @param back
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void exchangeCard(String cardId, final AXCallBack back) {
		RequestParams map = new RequestParams();
		map.add("tokenid", AXSharedPreferences.getTokenId(mContext));
		map.add("cardId", cardId);
		map.add(InterfaceConstants.INTERFACE_NAME,
				InterfaceConstants.ADD_CARD_CODE);
		MasterHttpClient.get(map, new AsyncHttpResponseHandler() {

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
	 * @Description: 接受新名片
	 * @Date:2015年7月1日下午2:07:54
	 * @param applyId
	 * @param back
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void acceptCard(String applyId, final AXCallBack back) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("tokenid", AXSharedPreferences.getTokenId(mContext));
		map.put("applyId", applyId);
		map.put(InterfaceConstants.INTERFACE_NAME,
				InterfaceConstants.APPECT_CODE);
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
	 * @Description: 精确搜索名片
	 * @Date:2015年6月23日下午4:21:19
	 * @param searchType
	 * @param content
	 * @param start
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void exchangeSerch(int start, String searchType, String content,
			final AXCallBack back) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("searchType", searchType);
		map.put("tokenid", AXSharedPreferences.getTokenId(mContext));
		map.put("content", content);
		map.put("pageNo", start+"");
		map.put("pageSize", InterfaceConstants.PAGE_SIZE);
		map.put(InterfaceConstants.INTERFACE_NAME,
				InterfaceConstants.EXACT_SEARCH_CODE);
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
						back.onCallBackString(null);

					}
				});
	}

	/**
	 * @Auther: zhuwt
	 * @Description:递交名片
	 * @Date:2015年6月25日下午6:20:18
	 * @param cardId
	 *            对方的名片id
	 * @param cardIdFrom
	 *            自己的名片id
	 * @param applyType
	 *            申请类型
	 * @param applyNote
	 *            申请说明
	 * @param back
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void sendCard(String cardId, String cardIdFrom, String applyType,
			String applyNote, final AXCallBack back) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("cardId", cardId);
		map.put("tokenid", AXSharedPreferences.getTokenId(mContext));
		map.put("cardIdFrom", cardIdFrom);
		map.put("applyType", applyType);
		map.put("applyNote", applyNote);
		map.put(InterfaceConstants.INTERFACE_NAME,
				InterfaceConstants.APPLY_CARD_CODE);
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
	 * @Description: 推荐名片
	 * @Date:2015年7月1日上午11:47:02
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void commentCard(final AXCallBack back) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("tokenid", AXSharedPreferences.getTokenId(mContext));
		map.put(InterfaceConstants.INTERFACE_NAME,
				InterfaceConstants.RECOMMEND_CARD_CODE);
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

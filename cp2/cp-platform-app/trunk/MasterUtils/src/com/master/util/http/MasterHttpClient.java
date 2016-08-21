/**    
 * @author maomy  
 * @Description: HTTP 处理类
 * @Package com.master.util.http   
 * @Title: MasterHttpClient.java   
 * @date 2014年8月7日 上午10:47:21   
 * @version V2.0 
 * @说明  代码版权归 杭州反盗版中心有限公司 所有
 */
package com.master.util.http;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.master.util.common.GsonUtils;
import com.master.util.common.MasterConstants;
import com.master.util.common.SystemUtils;
import com.master.util.gson.GsonResult;

public class MasterHttpClient {

	/**
	 * host 地址
	 */

	// private static final String BASE_URL =
	// "http://192.168.0.52:8080/ipush/ipush.htm";

	// private static final String BASE_URL =
	// "http://115.29.164.153/ipush/ipush.htm";
	// private static final String BASE_URL =
	// "http://192.168.0.249:8080/ipush/ipush.htm";
	// private static final String BASE_URL =
	// "http://192.168.0.26:8080/ipush/ipush.htm";

	// private static final String BASE_URL =
	// "http://192.168.0.231:8342/api/api.php?";

	private static String BASE_URL = "";// "http://10.0.17.73:8081/xinhuapi?";

	/**
	 * TAG
	 */
	private static String TAG = "MasterHttpClient";

	/**
	 * Http异步处理类(版本 1.4.7)
	 */
	private static AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

	static {
		asyncHttpClient.setTimeout(1000 * 20);// 设置超时时间 单位毫秒 默认是10s
	}

	/**
	 * @Auther: maomy
	 * @Description: post 请求(java 服务端 才可以用这个方法)
	 * @Date:2014年9月4日下午3:04:25
	 * @param paramsMap
	 * @param httpCallBack
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static void postIpush(Context context,
			final HashMap<String, String> paramsMap,
			final IHttpCallBack httpCallBack) {
		// 加入签名串
		// paramsMap.put(Sign.SIGN_KEY, Sign.sign(paramsMap,
		// MasterUtils.getMetaValue(context, Sign.MAD5_KEY)));
		// 请求地址
		HttpPost httpost = new HttpPost(getAbsoluteUrlForJava(null));
		final HttpPost postUrl = httpost;
		new Thread(new Runnable() {
			@Override
			public void run() {
				String result = doPostExcetor(postUrl, buildPairList(paramsMap));
				if (result.equals(HttpConstants.FALID)) {
					GsonResult gr = new GsonResult();
					gr.setReason("请求错误");
					gr.setResult(HttpConstants.FALID);
					gr.setRetCode(HttpConstants.RESULT_ERROR);
					String message = GsonUtils.toJson(gr);// "{'retCode':'"+HttpConstants.RESULT_ERROR+"','result':'"+HttpConstants.FALID+"','reason':'请求错误'}";
					httpCallBack.onFailed(HttpConstants.RESULT_ERROR, message);
				} else {
					if (MasterConstants.isDebug())
						Log.d(TAG, "post 请求成功返回结果   ...result = " + result);
					httpCallBack
							.onSuccess(HttpConstants.RESULT_SUCCESS, result);
				}
			}
		}).start();
		if (MasterConstants.isDebug())
			Log.d(TAG, "post 请求已经发出   ...");
	}

	/***
	 * @Auther: maomy
	 * @Description: http get 请求
	 * @Date:2015年5月13日下午2:00:59
	 * @param params
	 * @param responseHandler
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static void get(RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		params.add("httpTime", (System.currentTimeMillis() / 1000) + "");
		params.add("sign", Sign.getSign(params));
		if (MasterConstants.isDebug())
			Log.e(TAG,
					"get 请求发出 url = " + BASE_URL
							+ (null != params ? params.toString() : ""));
		asyncHttpClient.get(getAbsoluteUrlForPhp(null, null), params,
				responseHandler);
	}

	/**
	 * @Auther: ageng
	 * @Description: http get url 直接 请求
	 * @Date:2015-6-2下午3:12:55
	 * @param url
	 * @param responseHandler
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static void get(String url, AsyncHttpResponseHandler responseHandler) {
		asyncHttpClient.get(url, responseHandler);
	}

	/**
	 * @Auther: maomy
	 * @Description: http post 请求（PHP）
	 * @Date:2015年5月13日下午2:08:46
	 * @param context
	 * @param actUrl
	 * @param entity
	 * @param contentType
	 * @param responseHandler
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static void post(Context context, String actUrl, HttpEntity entity,
			String contentType, AsyncHttpResponseHandler responseHandler) {
		if (MasterConstants.isDebug())
			Log.d(TAG, "post 请求发出 url = " + BASE_URL + actUrl + "/n"
					+ " param = " + entity.toString());
		asyncHttpClient.post(context, getAbsoluteUrlForPhp(actUrl, context),
				entity, contentType, responseHandler);
	}

	/**
	 * @Auther: zhuwt
	 * @Description: http post 请求（JAVA）
	 * @Date:2015年6月16日下午2:23:38
	 * @param context
	 * @param params
	 * @param responseHandler
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static void postForJava(Context context,
			HashMap<String, String> map,
			AsyncHttpResponseHandler responseHandler) {
		try {
			if (MasterConstants.isDebug())
				Log.d(TAG, "post 请求发出 url = " + BASE_URL);
			List<NameValuePair> nvp = buildPairList(map);
			HttpEntity ent = new UrlEncodedFormEntity(nvp,
					HttpConstants.DEFAULT_CHARSET);
			asyncHttpClient.post(context, BASE_URL, ent, null, responseHandler);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// /**
	// * @Auther: ageng
	// * @Description: http get url 直接 请求
	// * @Date:2015-6-2下午3:12:55
	// * @param url
	// * @param responseHandler
	// * @return void
	// * @说明 代码版权归 杭州反盗版中心有限公司 所有
	// */
	// public static void get(String url,AsyncHttpResponseHandler
	// responseHandler) {
	// asyncHttpClient.get(url, responseHandler);
	// }

//	/**
//	 * @Auther: maomy
//	 * @Description: http post 请求
//	 * @Date:2015年5月13日下午2:08:46
//	 * @param context
//	 * @param actUrl
//	 * @param entity
//	 * @param contentType
//	 * @param responseHandler
//	 * @return void
//	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
//	 */
//	public static void post(Context context, String actUrl, HttpEntity entity,
//			String contentType, AsyncHttpResponseHandler responseHandler) {
//		if (MasterConstants.isDebug())
//			Log.d(TAG,
//					"post 请求发出 url = " + getAbsoluteUrlForPhp(actUrl, context)
//							+ "/n" + " param = " + entity.toString());
//		asyncHttpClient.post(context, getAbsoluteUrlForPhp(actUrl, context),
//				entity, contentType, responseHandler);
//	}

	/**
	 * @Auther: zhuwt
	 * @Description: 上传文件
	 * @Date:2015年6月18日下午2:41:29
	 * @param context
	 * @param params
	 * @param responseHandler
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static void postUploadFile(Context context, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		asyncHttpClient.post(context, BASE_URL, params, responseHandler);

	}
	
	/**
	 * @Auther: zhuwt 
	 * @Description:上传文件 用地址
	 * @Date:2015年7月1日下午2:55:58
	 * @param context
	 * @param url
	 * @param params
	 * @param responseHandler  
	 * @return void 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static void postUploadFileByUrl(Context context, String url,RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		asyncHttpClient.post(context, url, params, responseHandler);

	}
	
	/**
	 * @Auther: maomy
	 * @Description: http post 上传 文件
	 * @Date:2015年5月13日下午2:08:46
	 * @param context
	 * @param actUrl
	 * @param responseHandler
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static void post(RequestParams params, File file,
			AsyncHttpResponseHandler responseHandler) {
		params.add("httpTime", (System.currentTimeMillis() / 1000) + "");
		params.add("sign", Sign.getSign(params));
		String url = BASE_URL + params.toString();

		RequestParams params2 = new RequestParams();
		try {
			params2.put("uploadfile", file);// 图片地址
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (MasterConstants.isDebug())
			if (MasterConstants.isDebug())
				Log.e(TAG,
						"get 请求发出 url = "
								+ BASE_URL
								+ (null != params ? params.toString() + "&"
										+ params2.toString() : ""));
		asyncHttpClient.post(url, params2, responseHandler);
	}

	/**
	 * @Auther: maomy
	 * @Description: 请求地址
	 * @Date:2014年8月7日下午1:56:40
	 * @param relativeUrl
	 * @return
	 * @return String
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private static String getAbsoluteUrlForJava(String relativeUrl) {
		if (null == relativeUrl)
			return BASE_URL;
		else
			return BASE_URL + relativeUrl;
	}

	/**
	 * @Auther: maomy
	 * @Description: http post 发出
	 * @Date:2014年9月4日下午2:40:28
	 * @param host
	 * @param nvps
	 * @return
	 * @return String
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private static String doPostExcetor(HttpEntityEnclosingRequestBase host,
			List<NameValuePair> nvps) {
		HttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams()
				.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
						HttpConstants.OUT_TIME);
		String res = null;
		try {
			HttpEntity ent = new UrlEncodedFormEntity(nvps,
					HttpConstants.DEFAULT_CHARSET);
			host.setEntity(ent);
			HttpResponse response = httpclient.execute(host);
			int status = response.getStatusLine().getStatusCode();
			if ((status != 200)) {// 请求失败
				return HttpConstants.FALID;
			}
			HttpEntity entity = response.getEntity();
			res = EntityUtils.toString(entity, HttpConstants.DEFAULT_CHARSET);
		} catch (Exception e) {
			e.printStackTrace();
			res = HttpConstants.FALID;
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return res;
	}

	/**
	 * @Auther: maomy
	 * @Description: 构建map 参数
	 * @Date:2014年9月4日下午2:42:28
	 * @param map
	 * @return
	 * @return List<NameValuePair>
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private static List<NameValuePair> buildPairList(Map<String, String> map) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			String val = map.get(key);
			nvps.add(new BasicNameValuePair(key, val));
		}
		return nvps;
	}

	private static String getAbsoluteUrlForPhp(String actUrl, Context mContext) {
		if (null != actUrl) {
			String httpTime = (System.currentTimeMillis() / 1000) + "";
			RequestParams params = new RequestParams();
			params.put("act", actUrl);
			params.put("appver", SystemUtils.getAppVersionName(mContext));
			params.put("httpTime", httpTime);
			String mSign = Sign.getSign(params);
			return BASE_URL + "act=" + actUrl + "&appver="
					+ SystemUtils.getAppVersionName(mContext) + "&httpTime="
					+ httpTime + "&sign=" + mSign;
		}
		return BASE_URL;
	}

	/**
	 * @Auther: maomy
	 * @Description: 设置初始化URL
	 * @Date:2015年6月17日上午11:25:01
	 * @param httpUrl
	 * @return void
	 * @说明 代码版权归 杭州安存网络科技有限公司 所有
	 */
	public static void setHttpUrl(String httpUrl) {
		BASE_URL = httpUrl;
	}

}

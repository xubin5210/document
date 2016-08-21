/**    
 * @author maomy  
 * @Description: 签名操作类
 * @Package com.master.util.http   
 * @Title: Sign.java   
 * @date 2014年8月8日 下午1:44:14   
 * @version V2.0 
 * @说明  代码版权归 杭州反盗版中心有限公司 所有
 */ 
package com.master.util.http;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

import android.util.Log;

import com.loopj.android.http.RequestParams;
import com.master.util.common.MasterConstants;
import com.master.util.common.Md5Util;

public class Sign {
	
	/**
	 * 配置文件里的 MD5 key
	 */
	public static final String MAD5_KEY = "MI_MI";


	private static String TAG = "Sign";
	
	
	/**
	 * 传过去的签名参数
	 */
	public static String SIGN_KEY = "sign";
	
	/**
	 * 编码
	 */
	private  static String DEFAULT_CHARSET = "UTF-8";
	
	/**
	 * @Auther: maomy  
	 * @Description: 签名
	 * @Date:2014年8月8日下午1:42:27
	 * @param map
	 * @param md5key
	 * @return  
	 * @return String 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static String sign(Map<String,String> map,String md5key){
		Map <String,String> treeMap = new TreeMap<String,String>();
		treeMap.putAll(map);
		Iterator<String> it = treeMap.keySet().iterator();
		StringBuffer sb = new StringBuffer();
		while (it.hasNext()) {
			String key = it.next();
			String value = treeMap.get(key);
			sb.append(value);
		}
		if(MasterConstants.isDebug())
			Log.d(TAG, "签名前   = "+sb.toString());
		String sign = authSgin(sb.toString(),md5key);
		if(MasterConstants.isDebug())
			Log.d(TAG, "签名结果  = "+sign);
		return sign;
	}


	
	/**
	 * @Auther: maomy  
	 * @Description: 开始签名
	 * @Date:2014年8月8日下午1:48:18
	 * @param signStr
	 * @param md5key
	 * @return  
	 * @return String 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	private static String authSgin(String signStr, String md5key) {
		 return Md5Util.encrypt(signStr+md5key,"UTF-8");
	}

	/**
	 * @Auther: wudd
	 * @Description: 签名串
	 * @Date:2015年3月18日上午11:16:17
	 * @param params
	 * @return
	 * @return String
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public  static String getSign(RequestParams params) {
		if (null == params)
			return "";
		StringBuffer sb = new StringBuffer();
		String pattern = "&";
		Pattern pat = Pattern.compile(pattern);
		String[] rs = pat.split(params.toString());
		String [] strs = new String[rs.length];
		for (int i = 0; i < rs.length; i++) {
			strs[i] = rs[i];
		}
		Arrays.sort(strs);
		for(int j=0;j<strs.length;j++){
			sb.append(strs[j]+"|");
		}
		String last = sb.substring(0, sb.length() - 1);
		Log.i(TAG, "sign : "+last);
		String sign = Md5Util.Md5(last,"UTF-8");
		return sign;
	}
}

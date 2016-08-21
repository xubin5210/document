/**    
 * @author maomy  
 * @Description: gson 工具类 
 * @Title: GsonUtils.java   
 * @date 2014年9月3日 上午9:27:33   
 * @version V1.0 
 * @说明  代码版权归 杭州反盗版中心有限公司 所有
 */ 
package com.master.util.common;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;



public class GsonUtils {
	
	/**
	 * success
	 */
	public static  String  SUCCESS = "success";
	  
	/**
	 * RESULT
	 */
	public static String RESULT = "result";	
	
	/**
	 * falid
	 */
	public static String FALID = "failed";
	
	/**
	 * reason
	 */
	public static String  REASON= "reason";
	
	
	/**
	 * Code
	 */
	public static String CODE= "retCode";
	
	
	
	
	
	/**
	 * @Auther: maomy  
	 * @Description: json数组转成 string   
	 * @Date:2014年6月30日上午9:39:06
	 * @param jsonArray
	 * @return  
	 * @return String 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static String jsonArrayToString(String str,String key){
		Gson gson = new Gson();
		JsonObject json = stringToGson(str);
		String string  = gson.toJson(json.getAsJsonArray(key));
		return string;
	}
	
	/**
	 * @Auther: maomy  
	 * @Description: 获取json 值
	 * @Date:2014年6月30日上午9:38:11
	 * @param str
	 * @return  
	 * @return String 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static String getJsonValue(String str,String key){
		 JsonObject json = stringToGson(str);
		 String re = json.get(key).toString();
		 return removeTips(re);
	}
	
	/**
	 * @Auther: ageng  
	 * @Description: 获取json 值
	 * @Date:2015-6-9下午3:25:30
	 * @param jsonObject
	 * @param key
	 * @return  
	 * @return String 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static String getJsonValue(JsonObject jsonObject,String key){
		try {
			String re = jsonObject.get(key).toString();
			//去掉前后双引号
			String result = removeTips(re);
			return result;
			
		} catch (NullPointerException e) {
			return "";
		}
	}
	
	
	/**
	 * @Auther: maomy  
	 * @Description: {[{1:2},{3:3}]}  -> list
	 * @Date:2014-3-4下午6:19:41
	 * @param json
	 * @param typeToken
	 * @return  
	 * @return ArrayList<T> 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static <T> ArrayList<T> toObjectList(String json, Type typeToken) {
		Gson gson = new Gson();
		return gson.fromJson(json, typeToken);
		// List<T> listT = gson.fromJson(json, new TypeToken<List<T>>() {
		// }.getType());
		// return listT;
	}

	
	/**
	 * @Auther: maomy  
	 * @Description: 获取 json object对象
	 * @Date:2014年6月30日上午9:39:24
	 * @param str
	 * @return  
	 * @return JsonObject 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static JsonObject stringToGson(String str){
		 JsonParser jsonParse = new JsonParser();
		 JsonObject json = jsonParse.parse(str).getAsJsonObject();
		 return json;
	}
	

	
	
	/**
	 * @Auther: maomy  
	 * @Description: 对象转成json
	 * @Date:2014年9月4日下午2:59:07
	 * @param t
	 * @return  
	 * @return String 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static <T> String toJson(T t) {
		Gson gson = new Gson();
		return gson.toJson(t);
	}

	
	/**
	 * @Auther: maomy  
	 * @Description: json 转成 对象  
	 * @Date:2014年9月4日下午2:58:40
	 * @param json
	 * @param cls
	 * @return  
	 * @return T 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static <T> T toObject(String json, Class<T> cls) {
		Gson gson = new Gson();
		return gson.fromJson(json, cls);
	}

	
	/**
	 * @Auther: maomy  
	 * @Description: json 转成 对象  
	 * @Date:2014年9月4日下午2:58:40
	 * @param json
	 * @param cls
	 * @param dateFormat
	 * @return  
	 * @return T 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static <T> T toObject(String json, Class<T> cls, int dateFormat) {
		Gson gson = new GsonBuilder().setDateFormat(dateFormat).create();
		return gson.fromJson(json, cls);
	}

	/**
	 * @Auther: maomy  
	 * @Description: 获取json返回的result结果  success  faild 
	 * @Date:2014年6月30日上午9:38:11
	 * @param str
	 * @return  
	 * @return String 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static String getJsonResult(String str){
		if(null ==str  || "".equals(str) || str.length() < 5){
			return GsonUtils.FALID;}
		String mKey = GsonUtils.RESULT;
		return getJsonValue(removeTips(str),mKey);
	}
	
	/**
	 * @Auther: maomy  
	 * @Description: json数组转成 string   
	 * @Date:2014年6月30日上午9:39:06
	 * @param jsonArray
	 * @return  
	 * @return String 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static String jsonArrayToString(String str){
		String mKey = "data";
		return jsonArrayToString(removeTips(str),mKey);
	}
	
	/**
	 * @Auther: maomy  
	 * @Description:去掉前后双引号
	 * @Date:2014年6月30日上午11:53:14
	 * @param str
	 * @return  
	 * @return String 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	private static String removeTips(String  str){
		 String result  = "";
		if(str.startsWith("\"") && str.endsWith("\""))
			result= str.substring(1, str.length()-1);
		else
			result = str;
		return result;
	}
	
	/**
	 * @Auther: maomy  
	 * @Description: 去掉php返回数据里的头部数据
	 * @Date:2013-9-27下午5:14:43
	 * @param in
	 * @return  
	 * @return String 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static String JSONTokener(String in) {
        // consume an optional byte order mark (BOM) if it exists
        if (!"".equals(in) && in != null && in.startsWith("\ufeff")) {
        	int index = in.indexOf("{");
        	if(index != -1)
        		in = in.substring(index).toString();
        }
        return in;
    }

}

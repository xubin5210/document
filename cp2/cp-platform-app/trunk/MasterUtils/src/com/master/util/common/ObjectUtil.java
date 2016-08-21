/**    
 * @author maomy  
 * @Description: object 工具类
 * @Package com.master.util.common   
 * @Title: ObjectUtil.java   
 * @date 2015年6月8日 上午11:52:41   
 * @version V1.0 
 * @说明  代码版权归 杭州安存网络科技有限公司 所有
 */ 
package com.master.util.common;

import java.util.ArrayList;



public class ObjectUtil {
	
	
	/**
	 * @Auther: maomy  
	 * @Description: object 转成 javabean
	 * @Date:2015年6月8日上午11:52:50
	 * @param list
	 * @return  
	 * @return ArrayList<T> 
	 * @说明  代码版权归 杭州安存网络科技有限公司 所有
	 */
	public static <T> ArrayList<T> convertObjToBeanList(ArrayList<Object> list){
		ArrayList<T> result = new ArrayList<T>();
		if(list.size() <= 0)
			return result;
		
		for (Object obj : list) {
			@SuppressWarnings("unchecked")
			T t = (T)obj;
			result.add(t);
		}
		return result;
	}

}

/**    
 * @author maomy  
 * @Description: 日期 工具类
 * @Package com.ciapc.tzd.modules.common.utils   
 * @Title: DateUtil.java   
 * @date 2013-10-29 下午3:05:16   
 * @version V1.0 
 * @说明  代码版权归 杭州反盗版中心有限公司 所有
 */ 
package com.master.util.common;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;



public class DateUtil {
	
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	
	public static final String YY_MM_DD_HH_MM = "yy-MM-dd HH:mm";
	
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	
	/**
	 * @Auther: maomy  
	 * @Description: 日期转换 long型 -> yy-MM-dd HH:mm
	 * @Date:2013-10-29下午3:14:11
	 * @param time
	 * @param formatType
	 * @return  
	 * @return String 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getDateYHDHMS(long time){
		return getDateYHDHMS(time,YY_MM_DD_HH_MM);			
	}

	/**
	 * @Auther: maomy  
	 * @Description: 日期转换 long型 -> yyyy-MM-dd
	 * @Date:2013-10-29下午3:14:11
	 * @param time
	 * @param formatType
	 * @return  
	 * @return String 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static String getDateYHD(long time){
		return getDateYHDHMS(time,YYYY_MM_DD);			
	}
	
	/**
	 * @Auther: maomy  
	 * @Description: 日期转换 
	 * @Date:2013-10-29下午3:14:11
	 * @param time
	 * @param formatType
	 * @return  
	 * @return String 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getDateYHDHMS(long time,String formatType){
		String retStr = "";
		SimpleDateFormat sf = new SimpleDateFormat(formatType);
		if(0 != time){
			Date date = new Date(time);
			retStr = sf.format(date);
			return retStr;
		}
		return retStr;			
	}
}

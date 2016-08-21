/**    
 * @author maomy  
 * @Description: 列名   映射到表里的列名
 * @Package com.master.util.db.annotation   
 * @Title: Column.java   
 * @date 2014年8月4日 上午10:10:44   
 * @version V2.0 
 * @说明  代码版权归 杭州反盗版中心有限公司 所有
 */ 
package com.master.util.db.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
	
	/**
	 * @Auther: maomy  
	 * @Description: 自定义列名 
	 * @Date:2014年8月4日上午10:11:49
	 * @return  
	 * @return String 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	String columnName() default "";
	
	/**
	 * @Auther: zhuwt  
	 * @Description: 长度 
	 * @Date:2014年8月5日下午2:25:44
	 * @return  
	 * @return int 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	String lengtn() default "0";
	
	/**
	 * @Auther: zhuwt  
	 * @Description: 是否大字符串 true的话对于数据库里的TEXT类型
	 * @Date:2014年8月6日下午3:13:07
	 * @return  
	 * @return boolean 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	boolean isBigString() default false;
	
}

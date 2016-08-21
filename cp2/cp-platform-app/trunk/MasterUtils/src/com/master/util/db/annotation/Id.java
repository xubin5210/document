/**    
 * @author maomy  
 * @Description: ID 映射到表里的主键字段 
 * @Package com.master.util.db.annotation   
 * @Title: Id.java   
 * @date 2014年8月4日 上午10:06:21   
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
public @interface Id {
	
	/**
	 * @Auther: zhuwt  
	 * @Description: 别名 
	 * @Date:2014年8月6日下午2:36:15
	 * @return  
	 * @return String 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	String idName() default "id";
	
	/**
	 * @Auther: maomy  
	 * @Description: 是否自增
	 * @Date:2014年8月4日上午10:07:57
	 * @return  
	 * @return boolean 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	boolean increment () default true;
	
	/**
	 * @Auther: zhuwt  
	 * @Description:长度 
	 * @Date:2014年8月6日下午1:51:24
	 * @return  
	 * @return String 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	String length() default "125";
	

}

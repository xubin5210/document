
package com.master.util.db.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author zhuwt   
 * @Description: 表名    
 * @ClassName: Table.java   
 * @date 2014年8月6日 上午10:33:34      
 * @说明  代码版权归 杭州反盗版中心有限公司 所有
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Table {
	
	
	/**
	 * 表名
	 * @return
	 */
	String tableName();

}

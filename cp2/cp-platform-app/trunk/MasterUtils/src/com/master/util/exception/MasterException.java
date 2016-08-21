/**    
 * @author maomy  
 * @Description: 异常处理类  
 * @Package com.master.util.Exception   
 * @Title: MasterException.java   
 * @date 2014年8月8日 上午9:58:26   
 * @version V2.0 
 * @说明  代码版权归 杭州反盗版中心有限公司 所有
 */ 
package com.master.util.exception;



public class MasterException extends Exception{

	/**  
	 * @Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = 6216681756502222798L;

	
	/**
	 * 
	 * <p>Title: </p>   
	 * <p>Description: </p>   
	 * @param detailMessage  异常描述
	 * @param throwable  异常信息
	 */
	public MasterException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	
	
	
	public MasterException(String detailMessage) {
		super(detailMessage);
	}

	
}

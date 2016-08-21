package com.ancun.xinhu.biz.service;

import com.ancun.xinhu.domain.model.UserClientInfo;

/**
 * 
 * 
 * <p>
 * create at 2015年7月23日 下午3:32:13
 * @author xyy
 * @version %I%, %G%
 * @see
 */
public interface UserClientInfoService {
	
	/**
	* APP上传 ClientId 时， 维护表 user_client_info
	* 该表中 client_id 和  user_id 是唯一值， 而且 client_id 值不为空， user_id 值可以为空 
	* <p>
	* author: xyy
	* create at: 2014年4月16日上午12:34:54
	 */
	public void hanlderUserClientInfo(UserClientInfo userClientInfo);
}

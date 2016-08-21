package com.ancun.xinhu.biz.service;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.ancun.xinhu.domain.model.UserInfo;
import com.ancun.xinhu.web.session.XinHuUserAgentSession;

public interface UserLoginService {
	
	/**
	 * 获取用户信息
	* @param userInfo
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月4日 下午4:38:21
	 */
	UserInfo getUserInfo(UserInfo userInfo);
	
	/**
	 * 获取用户登录结果Map， 主要是用户信息，不包含用户的好友名片信息
	* @param userInfo
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月18日 上午10:12:52
	 */
	HashMap<String,Object> getUserLoginResultMap(UserInfo userInfo,HttpServletRequest request);
	
	/**
	 * 设置用户登录信息
	* @param userInfo
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月5日 下午1:25:33
	 */
	void setUserLoginInfo(UserInfo userInfo);
	
	/**
	 * 保存用户登录日志信息
	* @param userInfo
	* @param ip
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月4日 下午5:49:05
	 */
	boolean saveUserLoginLog(String userName,String ip, Integer loginStatus);
	
	/**
	 * 获取手机随机验证码
	* @param mobile
	* @param type
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月5日 下午4:57:57
	 */
	String getCode(String mobile,String type);
	
	/**
	 * 验证手机验证码
	* @param mobile
	* @param code
	* @param codeTokenid
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月8日 下午1:37:19
	 */
	String validateCode(String mobile,String code);
	
	/**
	 * 验证手机是否注册
	* @param userInfo
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月8日 下午2:06:56
	 */
	boolean validateMobileIsRegister(UserInfo userInfo);
	
	/**
	 * 用户注册
	* @param userInfo
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月8日 下午1:31:56
	 */
	UserInfo register(UserInfo userInfo);
	
	/**
	 * 重置密码
	* @param userInfo
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月8日 下午5:00:34
	 */
	boolean resetPwd(UserInfo userInfo);

}

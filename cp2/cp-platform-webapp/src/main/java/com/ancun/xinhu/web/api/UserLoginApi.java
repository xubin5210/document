package com.ancun.xinhu.web.api;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ancun.products.core.api.AncunApiResponse;
import com.ancun.products.core.web.controller.ControllerSupport;
import com.ancun.products.core.web.utils.HttpUtils;
import com.ancun.xinhu.biz.service.UserLoginService;
import com.ancun.xinhu.domain.dto.XinHuResponseCode;
import com.ancun.xinhu.domain.model.UserInfo;

/**
 * 信乎用户登录接口
 * <p>
 * create at 2015年6月4日 下午3:16:21
 * @author <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a>
 * @version %I%, %G%
 * @see
 */
@Controller
@RequestMapping("userLogin")
public class UserLoginApi extends ControllerSupport{
	
	@Autowired
	private UserLoginService userLoginService;
	
	/**
	 * 用户登录
	* @param userInfo
	* @param request
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月5日 下午4:20:08
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse login(@ModelAttribute UserInfo userInfo,HttpServletRequest request) {
		String mobile = userInfo.getMobile();
		userInfo = userLoginService.getUserInfo(userInfo);
		String clientAddr = HttpUtils.getRemoteAddr(request);//登录ip地址
		if (userInfo != null) {
			userLoginService.setUserLoginInfo(userInfo);
			userLoginService.saveUserLoginLog(userInfo.getIdCard(), clientAddr, 1);
			Map<String,Object> resultMap = userLoginService.getUserLoginResultMap(userInfo,request);
			return AncunApiResponse.create(resultMap);
		} else {
			userLoginService.saveUserLoginLog(mobile, clientAddr, 0);
			return AncunApiResponse.create(XinHuResponseCode._120000.getCode(), XinHuResponseCode._120000.getMsg());
		}
	}
	
	/**
	 * 获取验证码
	* @param mobile
	* @param type
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月5日 下午4:35:04
	 */
	@RequestMapping(value = "/getCode", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse getCode(String mobile,String type){
		String code = userLoginService.getCode(mobile, type);
		System.out.println(code+"#########################");
		if(code==null){
			return AncunApiResponse.create(XinHuResponseCode._110031.getCode(),XinHuResponseCode._110031.getMsg());
		}else{
			return AncunApiResponse.create(code);
		}
	}
	
	/**
	 * 用户注册
	* @param mobile
	* @param type
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月8日 上午10:18:37
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse register(@ModelAttribute UserInfo userInfo,String code,HttpServletRequest request){
		//检验验证码
		String codeMsg = userLoginService.validateCode(userInfo.getMobile(), code);
		if(codeMsg!=null){
			return AncunApiResponse.create(XinHuResponseCode._120001.getCode(), codeMsg);
		}
		//验证手机号是否注册
		boolean result = userLoginService.validateMobileIsRegister(userInfo);
		if(result){
			return AncunApiResponse.create(XinHuResponseCode._120002.getCode(), XinHuResponseCode._120002.getMsg());
		}
		
		userLoginService.register(userInfo);
		
		String clientAddr = HttpUtils.getRemoteAddr(request);//登录ip地址
		
		userLoginService.setUserLoginInfo(userInfo);
		userLoginService.saveUserLoginLog(userInfo.getIdCard(), clientAddr, 1);
		
		return AncunApiResponse.create();
	}
	
	/**
	 * 用户激活
	* @param userInfo
	* @param request
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月8日 上午10:21:15
	 */
	@RequestMapping(value = "/activation", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse activation(@ModelAttribute UserInfo userInfo,HttpServletRequest request){
		//可能和用户注册合并，不需要用户激活了，因为两者比较相似
		return AncunApiResponse.create();
	}
	
	/**
	 * 重置密码
	* @param userInfo
	* @param request
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月8日 上午10:22:04
	 */
	@RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse resetPwd(@ModelAttribute UserInfo userInfo,String code){
		//检验验证码
//		boolean result = userLoginService.validateCode(userInfo.getMobile(), code);
		String codeMsg = userLoginService.validateCode(userInfo.getMobile(), code);
		if(codeMsg!=null){
			return AncunApiResponse.create(XinHuResponseCode._120001.getCode(), codeMsg);
		}
		
		userLoginService.resetPwd(userInfo);
		
		return AncunApiResponse.create();
	}
	
	/**
	 * 验证手机号是否已注册
	* @param userInfo
	* @param code
	* @param request
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年7月8日 上午11:31:04
	 */
	@RequestMapping(value = "/validateMobileIsRegister", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse validateMobileIsRegister(@ModelAttribute UserInfo userInfo){
		//验证手机号是否注册
		boolean result = userLoginService.validateMobileIsRegister(userInfo);
		return AncunApiResponse.create(result);
	}
	
}

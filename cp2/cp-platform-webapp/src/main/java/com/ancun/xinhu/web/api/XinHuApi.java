package com.ancun.xinhu.web.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ancun.products.core.api.AncunApiResponse;
import com.ancun.products.core.web.controller.ControllerSupport;

/**
 * 信乎统一接口响应方法，用来处理http 响应 status 404的情况 
 * 
 * <p>
 * create at 2015年6月16日 下午3:58:49
 * @author <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a>
 * @version %I%, %G%
 * @see
 */
@Controller
public class XinHuApi extends ControllerSupport {
	
	@RequestMapping(value = "/appApi", method = RequestMethod.GET)
	@ResponseBody
	public AncunApiResponse xinhuapi_get(){
		return null;
	}
	
	@RequestMapping(value = "/appApi", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse xinhuapi_post(){
		return null;
	}

}

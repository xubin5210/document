package com.ancun.xinhu.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ancun.products.core.config.AppPropertyPlaceholderConfigurer;

/**
 * <p>
 * create at 2014年10月28日 下午5:15:54
 * 
 * @author <a href="mailto:caozhenfei@ancun.com">Dim.Cao</a>
 * @version %I%, %G%
 * @see
 */
public class XinHuApiInterceptor implements HandlerInterceptor {
	
	@Autowired
	private AppPropertyPlaceholderConfigurer propertyConfigurer;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String mark=request.getParameter("sys_forward_mark");
		if("1".equals(mark)){
			return true;
		}
		String code=request.getParameter("sys_request_code");
		if(code!=null && !"".equals(code)){
			String path=propertyConfigurer.getProperty("xinhu_urlcode_"+code);
			if(path.indexOf("?")>0){
				request.getRequestDispatcher("/api/"+path+"&sys_forward_mark=1").forward(request, response);
			}else{
				request.getRequestDispatcher("/api/"+path+"?sys_forward_mark=1").forward(request, response);
			}
			return false;
		}else{
			return true;
		}
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	

}

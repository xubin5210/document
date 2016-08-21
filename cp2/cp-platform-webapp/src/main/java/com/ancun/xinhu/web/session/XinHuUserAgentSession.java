package com.ancun.xinhu.web.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.ancun.products.core.web.session.AbstractOperatorAgentSession;
import com.ancun.products.core.web.utils.CookieUtil;
import com.ancun.products.core.web.utils.WebContextUtils;
import com.ancun.xinhu.biz.mappers.UserLoginSessionMapper;
import com.ancun.xinhu.domain.dto.XinHuUserAgent;
import com.ancun.xinhu.domain.model.UserLoginSession;

@Component
public class XinHuUserAgentSession extends AbstractOperatorAgentSession<XinHuUserAgent>{

	private final static Logger log = LoggerFactory.getLogger(XinHuUserAgentSession.class);

	@Autowired
	private XinHuUserAgentCache cache;

	@Autowired
	private WebContextUtils webContextUtils;
	
	private  final static int TIME_OUT=7200;
	
	private final static String COOKIE_KEY = "xinhu_sessionId";
	
	private final static String ENCODING="UTF-8";
	
	@Autowired
	private UserLoginSessionMapper userLoginSessionMapper;

	
	public void save(HttpServletRequest request, XinHuUserAgent userAgent,HttpServletResponse response) {
		// Cookyjar cookyjar = (Cookyjar)
		// request.getAttribute(Cookyjar.CookyjarInRequest);
		// AdminCookie adminCookie = BeanUtils.quickMap(adminAgent,
		// AdminCookie.class);
		// cookyjar.set(adminCookie);
		String sessionId = request.getSession().getId();
		if (log.isDebugEnabled()) {
			log.debug("Servlet [" + webContextUtils.getServletUrl(request) + "], save sessionId:" + sessionId + " "
					+ userAgent);
		}
		cache.put(sessionId, userAgent);
		//存放到cookie里边
		CookieUtil.save(COOKIE_KEY, sessionId,TIME_OUT, response, ENCODING);
	}

	public XinHuUserAgent get(HttpServletRequest request) {
		 	String sessionId=CookieUtil.read(COOKIE_KEY, request, ENCODING);
			if(StringUtils.isBlank(sessionId)){
				return null;
			}
			//从缓存中获取user对象
			XinHuUserAgent userAgent = cache.get(sessionId);
			if(null == userAgent){
				return null;
			}
			return userAgent;
	}

	public void remove(HttpServletRequest request,HttpServletResponse response) {
		String sessionId = request.getSession().getId();

		if (log.isDebugEnabled()) {
			log.debug("remove session the sessionId: [" + sessionId + "]");
		}

		cache.remove(sessionId);
		CookieUtil.remove(COOKIE_KEY, request, response);
		
	}

//	public void setCache(XinHuUserAgentCache cache) {
//		this.cache = cache;
//	}
	
	//下面的是移动端使用的方法
	
	public void save(String tokenid, XinHuUserAgent userAgent,boolean isSaveDataBase) {
		if (log.isDebugEnabled()) {
			log.debug("save tokenid: [" + tokenid + "]"
					+ userAgent);
		}
		cache.put(tokenid, userAgent);
		
		if(isSaveDataBase){
			//放入数据库
			String sessionContent = JSONObject.toJSONString(userAgent);
			UserLoginSession userLoginSession = new UserLoginSession();
			userLoginSession.setTokenId(tokenid);
			userLoginSession.setUserId(userAgent.getId().intValue());
			userLoginSession.setSessionContent(sessionContent);
			userLoginSessionMapper.insert(userLoginSession);
		}
		
	}

	public XinHuUserAgent get(String tokenid) {
		if(StringUtils.isBlank(tokenid)){
			return null;
		}
		//从缓存中获取user对象
		XinHuUserAgent userAgent = cache.get(tokenid);
		if(null == userAgent){
			/*
			return null;
		}else{//缓存中没有时查询数据库，然后放入缓存
			*/
			UserLoginSession userLoginSession = userLoginSessionMapper.load(tokenid);
			if(userLoginSession!=null){
				if (log.isDebugEnabled()) {
					log.debug("save tokenid: [" + tokenid + "]"
							+ userAgent);
				}
				userAgent = JSONObject.parseObject(userLoginSession.getSessionContent(), XinHuUserAgent.class);
				cache.put(tokenid, userAgent);
				//更新时间
				userLoginSession = new UserLoginSession();
				userLoginSession.setTokenId(tokenid);
				userLoginSessionMapper.update(userLoginSession);
			}
		}
		return userAgent;
	}

	public void remove(String tokenid,boolean isSaveDataBase) {
		if (log.isDebugEnabled()) {
			log.debug("remove session the tokenid: [" + tokenid + "]");
		}
		cache.remove(tokenid);
		if(isSaveDataBase){
			userLoginSessionMapper.delete(tokenid);
		}
	}

	public void setCache(XinHuUserAgentCache cache) {
		this.cache = cache;
	}

}

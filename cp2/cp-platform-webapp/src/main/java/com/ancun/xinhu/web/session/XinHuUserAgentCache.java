package com.ancun.xinhu.web.session;

import javax.annotation.PostConstruct;

import net.sf.ehcache.CacheManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ancun.products.core.cache.CacheSupport;
import com.ancun.products.core.cache.EHCacheUtil;
import com.ancun.xinhu.domain.dto.XinHuUserAgent;
import com.ancun.xinhu.domain.model.UserInfo;

@Component
public class XinHuUserAgentCache extends CacheSupport<XinHuUserAgent> {

	public static final String CACHE_ID = XinHuUserAgentCache.class.getSimpleName();

	@Autowired
	@Qualifier("ehCacheManagerXinhu")
	private CacheManager ehCacheManager;

	@Override
	public String getCacheId() {
		return CACHE_ID;
	}

	@PostConstruct
	public void init() {
		ehCacheUtil = new EHCacheUtil(ehCacheManager, CACHE_ID);
	}

}

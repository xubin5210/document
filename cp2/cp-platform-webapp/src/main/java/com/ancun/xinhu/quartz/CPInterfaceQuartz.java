package com.ancun.xinhu.quartz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ancun.products.core.utils.JsonUtils;
import com.ancun.xinhu.biz.mappers.CpbMapper;
import com.ancun.xinhu.domain.model.Cpb;
import com.ancun.xinhu.domain.model.JxsscJsonBean;
import com.ancun.xinhu.util.NumUtils;

/**
 * 彩票接口调用定时器
 * 
 * @author <a href="mailto:taibai@ancun.com">xubin</a>
 * @create at 2015年8月25日 下午3:40:13
 */
public class CPInterfaceQuartz {
	private static Logger log = Logger.getLogger(CPInterfaceQuartz.class);
	public static final String GET_URL = "http://f.apiplus.cn/cqssc-1.json";
	
	
	@Autowired
	private CpbMapper cpbMapper;
	
	public void execute(){
		log.info("开始....");
		  URL getUrl;
		try {
			getUrl = new URL(GET_URL);
			 // 根据拼凑的URL，打开连接，URL.openConnection函数会根据URL的类型，
	        // 返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection
	        HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
	        // 进行连接，但是实际上get request要在下一句的connection.getInputStream()函数中才会真正发到
	        // 服务器
	        connection.connect();
	        // 取得输入流，并使用Reader读取
	        BufferedReader reader = new BufferedReader(new InputStreamReader(
	                connection.getInputStream(),"UTF-8"));
	        String lines;
	        JxsscJsonBean jxssc = null ;
	        while ((lines = reader.readLine()) != null) {
	        	jxssc = JsonUtils.jsonToBean(lines, JxsscJsonBean.class);
	            //System.out.println(lines);
	        }
	        
	        if(jxssc != null){
	        	String qhStr = jxssc.getData().get(0).getExpect();
	        	String code = jxssc.getData().get(0).getOpencode();
	        	
	        	//期号
	        	StringBuilder sb = new StringBuilder();
	        	sb.append(qhStr.substring(0, 8)).append("-").append(qhStr.substring(8));
	        	//开奖号
	        	String[] codes = code.split(",");
	        	
	        	Cpb c = new Cpb();
	        	c.setQh(sb.toString());
	        	c.setWws(Integer.valueOf(codes[0]));
	        	c.setQws(Integer.valueOf(codes[1]));
	        	c.setBws(Integer.valueOf(codes[2]));
	        	c.setSws(Integer.valueOf(codes[3]));
	        	c.setGws(Integer.valueOf(codes[4]));
	        	
	        	//计算和值
	        	NumUtils.init(c);
	        	
	        	cpbMapper.insert(c);
	        }
	        
	        reader.close();
	        // 断开连接
	        connection.disconnect();
		} catch (MalformedURLException e) {
			log.error(e.getMessage(),e);
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}	
		
		log.info("结束....");
	}
	
	
	
	
	public static void main(String[] args) {
		String ss = "{'rows':1,'code':'cqssc','info':'免费接口随机延迟3-5分钟，实时接口请访问opencai.net或QQ:9564384(注明彩票或API)','data':[{'expect':'20160616029','opencode':'1,2,8,6,8','opentime':'2016-06-16 10:50:40','opentimestamp':1466045440}]}";
		JxsscJsonBean jxssc = JsonUtils.jsonToBean(ss, JxsscJsonBean.class);
		String qhStr = jxssc.getData().get(0).getExpect();
    	String code = jxssc.getData().get(0).getOpencode();
    	
    	StringBuilder sb = new StringBuilder();
    	sb.append(qhStr.substring(0, 8)).append("-").append(qhStr.substring(8));
		
    	log.info(sb.toString());
		log.info(jxssc);
	}
}

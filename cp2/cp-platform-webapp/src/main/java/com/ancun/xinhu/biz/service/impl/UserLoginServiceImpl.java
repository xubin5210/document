package com.ancun.xinhu.biz.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ancun.products.basebiz.service.SMSService;
import com.ancun.products.basebiz.service.StoreService;
import com.ancun.products.core.config.SysConfig;
import com.ancun.products.core.utils.StringUtils;
import com.ancun.products.core.utils.UUIDGenerator;
import com.ancun.products.core.web.utils.WebContextUtils;
import com.ancun.xinhu.biz.mappers.CardApplyEntMapper;
import com.ancun.xinhu.biz.mappers.CardInfoMapper;
import com.ancun.xinhu.biz.mappers.CardInfoSnapshotMapper;
import com.ancun.xinhu.biz.mappers.CardPositionLinkMapper;
import com.ancun.xinhu.biz.mappers.SysLoginLogMapper;
import com.ancun.xinhu.biz.mappers.UserCardApplyMapper;
import com.ancun.xinhu.biz.mappers.UserCardLinkMapper;
import com.ancun.xinhu.biz.mappers.UserInfoMapper;
import com.ancun.xinhu.biz.service.UserLoginService;
import com.ancun.xinhu.config.XinHuConfig;
import com.ancun.xinhu.domain.dto.XinHuUserAgent;
import com.ancun.xinhu.domain.model.CardApplyEnt;
import com.ancun.xinhu.domain.model.CardInfo;
import com.ancun.xinhu.domain.model.CardInfoSnapshot;
import com.ancun.xinhu.domain.model.SysLoginLog;
import com.ancun.xinhu.domain.model.UserCardApply;
import com.ancun.xinhu.domain.model.UserCardLink;
import com.ancun.xinhu.domain.model.UserInfo;
import com.ancun.xinhu.web.session.XinHuUserAgentSession;

@Service
public class UserLoginServiceImpl implements UserLoginService {
	
	@Autowired
	private XinHuUserAgentSession xinHuUserAgentSession;
	
	@Autowired
	private UserInfoMapper userInfoMapper;
	
	@Autowired
	private CardInfoMapper cardInfoMapper;
	
	@Autowired
	private SysLoginLogMapper sysLoginLogMapper;
	
	@Autowired
	private SMSService smsService;
	
	@Autowired
	private CardInfoSnapshotMapper cardInfoSnapshotMapper;
	
	@Autowired
	private StoreService storeService;
	
	@Autowired
	private WebContextUtils webContextUtils;
	
	@Autowired
	private CardPositionLinkMapper cardPositionLinkMapper;
	
	@Autowired
	private CardApplyEntMapper cardApplyEntMapper;
	
	@Autowired
	private UserCardApplyMapper userCardApplyMapper;

	@Autowired
	private UserCardLinkMapper userCardLinkMapper;
	
	@Autowired
	private SysConfig sysConfig;
	
	/**
	 * 获取用户信息
	 */
	public UserInfo getUserInfo(UserInfo userInfo) {
		return userInfoMapper.getUserInfoByMobileAndPwd(userInfo);
	}
	
	/**
	 * 获取用户登录结果Map
	 */
	public HashMap<String,Object> getUserLoginResultMap(UserInfo userInfo,HttpServletRequest request){
		HashMap<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userInfo.getId());
		map.put("tokenid", userInfo.getTokenid());
		map.put("cardId", userInfo.getDefaultCardId());
		
		String cardApplyEntAuditStatus="-1";//申请绑定企业审核状态
		//XYY 2015-7-14
		CardApplyEnt cae1 = cardApplyEntMapper.loadByCardId(userInfo.getDefaultCardId());
		if(cae1 == null){
			cardApplyEntAuditStatus = "-1";
		}else if(cae1.getBindStatus()==1){
			cardApplyEntAuditStatus = "1";
		}else{
			cardApplyEntAuditStatus = "0";
		}
		
		CardInfoSnapshot cardInfo = cardInfoSnapshotMapper.getUserCardInfo(userInfo.getDefaultCardId());
		
		
		String infoCompleteStatus="2";
		if(cardInfo!=null){
			map.put("trueName", cardInfo.getTrueName());
			map.put("nickName", cardInfo.getNickName());
			map.put("iconUrl", cardInfo.getIconUrl());
			map.put("motto", cardInfo.getMotto());
			map.put("userIdcard", cardInfo.getUserIdcard());
			map.put("entId", cardInfo.getEntId());
			map.put("searchControlSwitch", cardInfo.getSearchControlSwitch());
			
			int cardPositionCount = cardPositionLinkMapper.getCardPositionCount(cardInfo.getId());
			
			if((cardInfo.getEntId()==null || cardInfo.getEntId()==0)
				|| (cardInfo.getTrueName()==null || "".equals(cardInfo.getTrueName().trim()))
				|| (cardInfo.getEmail()==null || "".equals(cardInfo.getEmail().trim()))
				|| cardPositionCount==0
					){
				
				if(cardInfo.getEntId()==null || cardInfo.getEntId()==0){
					HashMap<String,Object> caeMap = cardApplyEntMapper.getCardApplyEntNoAudit(cardInfo.getId());
					if(caeMap==null){
						infoCompleteStatus="2";						
					}else{
						map.put("entId", caeMap.get("ent_id"));
						map.put("orgName", caeMap.get("org_name"));
						//infoCompleteStatus="3";
						infoCompleteStatus="1";
						//cardApplyEntAuditStatus="0";
					}
				}else{
					if((cardInfo.getTrueName()==null || "".equals(cardInfo.getTrueName().trim()))
							|| (cardInfo.getEmail()==null || "".equals(cardInfo.getEmail().trim()))
							|| cardPositionCount==0){
						infoCompleteStatus="2";
					}else{
						infoCompleteStatus="1";
					}
					//cardApplyEntAuditStatus="1";
				}
				
			}else{
				infoCompleteStatus="1";
			}
		}else{
			map.put("trueName", "");
			map.put("nickName", "");
			map.put("iconUrl", "");
			map.put("motto", "");
			map.put("userIdcard", userInfo.getIdCard());
			map.put("entId", "");
			map.put("searchControlSwitch", "11111111111111");
		}
		
		
		//信息完整度
		infoCompleteStatus = isInfoComplete(cardInfo,userInfo.getDefaultCardId());
		
		map.put("infoCompleteStatus", infoCompleteStatus);
		map.put("cardApplyEntAuditStatus", cardApplyEntAuditStatus);
		
		map.put("iconUrl", getFileUrl((String)map.get("iconUrl"), request));
		
		//处理隐私开关
		String scs = map.get("searchControlSwitch").toString();
		String[][] scsArr=XinHuConfig.getSearchControlSwitchList(scs);
		for(int i=0;i<scsArr.length;i++){
			map.put(scsArr[i][0], scsArr[i][1]);
		}
		
		
		
		return map;
	}

	/**
	 * 保存用户登录信息日志
	 */
	public boolean saveUserLoginLog(String userName, String ip, Integer loginStatus) {
		SysLoginLog sysLoginLog = new SysLoginLog();
		
		sysLoginLog.setUserName(userName);
		sysLoginLog.setIp(ip);
		sysLoginLog.setLoginStatus(loginStatus);
		sysLoginLog.setUserType(0);
		
		sysLoginLogMapper.insert(sysLoginLog);
		return true;
	}

	/**
	 * 设置用户登录信息
	 */
	public void setUserLoginInfo(UserInfo userInfo) {
		String tokenid=UUIDGenerator.generate();
		
		userInfo.setTokenid(tokenid);
		
		XinHuUserAgent userAgent = new XinHuUserAgent();
		userAgent.setId(userInfo.getId());
		userAgent.setMobile(userInfo.getMobile());
		userAgent.setDefaultCardId(userInfo.getDefaultCardId());
		userAgent.setIdCard(userInfo.getIdCard());
		
		xinHuUserAgentSession.save(tokenid, userAgent,true);
		
//		XinHuUserAgent show = xinHuUserAgentSession.get(tokenid);
//		
//		System.out.println(show.getId()+" "+show.getMobile());
	}
	
	/**
	 * 获取手机随机验证码
	 * @param mobile
	 * @return
	 */
	public String getCode(String mobile,String type){
		String code=getFixLenthString(4);
		String msg=null;
		
		if("register".equals(type)){//注册
			msg="您正在注册信乎个人账号，验证码为："+code+",信乎将与您一起打造诚信的信息世界。";
		}else if("resetPwd".equals(type)){//重置密码
			msg="您正在重置密码，验证码："+code+",信乎将与您一起打造诚信的信息世界。";
		}else if("activation".equals(type)){//激活
			msg="您正在激活账号，验证码："+code+",信乎将与您一起打造诚信的信息世界。";
		}else if("bindNewMobile".equals(type)){//绑定新手机
			msg="您正在绑定新手机，验证码："+code+",信乎将与您一起打造诚信的信息世界。";
		}else{
			return null;
		}
		
		smsService.send(mobile, msg);
		
		/*HashMap<String,String> resMap=new HashMap<String, String>();
		resMap.put("code", code);*/
		
		XinHuUserAgent userAgent = new XinHuUserAgent();
		userAgent.setCode(code);
		userAgent.setGmtCode(System.currentTimeMillis());
		xinHuUserAgentSession.save(mobile, userAgent,false);
		
		return code;
	}
	
	/**
	 * 返回长度为【strLength】的随机数，在前面补0  
	 * @param strLength
	 * @return
	 */
	private static String getFixLenthString(int strLength) {  
	    Random rm = new Random();  
	    // 获得随机数  
	    double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);  
	    // 将获得的获得随机数转化为字符串  
	    String fixLenthString = String.valueOf(pross);  
	    // 返回固定的长度的随机数  
	    return fixLenthString.substring(1, strLength + 1);  
	}
	
	/**
	 * 验证手机验证码
	 */
	public String validateCode(String mobile,String code){
		XinHuUserAgent userAgent=xinHuUserAgentSession.get(mobile);
		if(userAgent==null){
			return "请先获取验证码";
		}
		//验证是否超时
		long ctms=System.currentTimeMillis();
		if((ctms-userAgent.getGmtCode())>(60000*5)){
			return "验证码超时";
		}
		
		if(code!=null && code.equals(userAgent.getCode())){
			xinHuUserAgentSession.remove(mobile,false);
			return null;
		}else{
			return "验证码错误";
		}
	}
	
	/**
	 * 验证手机是否注册
	 */
	public boolean validateMobileIsRegister(UserInfo userInfo){
		UserInfo userInfoExists = userInfoMapper.getUserInfoByMobile(userInfo);
		return userInfoExists==null?false:true;
	}
	
	/**
	 * 用户注册
	 */
	public UserInfo register(UserInfo userInfo){
		//通过手机号查询一下看看是否有对应的名片(未绑定企业)，没有名片的情况下需要插入一个默认的
		CardInfo cardInfo=new CardInfo();
		cardInfo.setMobile(userInfo.getMobile());
//		card.setUserIdcard(userInfo.getIdCard());
//		cardInfo.setUserId(userInfo.getId());
		Integer cardId = cardInfoMapper.getCardIdByMobileOrIdCardNotUserId(cardInfo);
		if(cardId == null || cardId <0){ //名片信息表中，没有这个手机号码，及该注册用户没有被企业维护过，自动给他新建一张空白 名片表
			cardInfoMapper.insert(cardInfo);
			cardId = cardInfoMapper.getCardIdByMobileOrIdCardNotUserId_new(cardInfo);
		}
		
		//插入用户信息
		userInfo.setDefaultCardId(cardId);
		userInfoMapper.insert(userInfo);
		userInfo=userInfoMapper.getUserInfoByMobileAndPwd(userInfo);
		
		//修改默认名片的用户id
		cardInfo=new CardInfo();
		cardInfo.setId(cardId);
		cardInfo.setUserId(userInfo.getId());
		cardInfoMapper.update(cardInfo);
		
		//增加客服人员
		String kefuMoble = sysConfig.get("kefu");
		CardInfo ci = cardInfoMapper.loadByMobile(kefuMoble);
		if(!kefuMoble.equals(userInfo.getMobile()) || ci==null){//如果是客服用户注册就不需要关联了
			int cardIdF = ci.getId();
			UserCardApply uca = new UserCardApply();
			uca.setCardIdFrom(cardId);
			uca.setCardId(cardIdF);
			uca.setIsDelete(0);
			uca.setIsExchange("1");
			uca.setGmtExchange(new Date());
			uca.setApplyType("交友");
			userCardApplyMapper.insert(uca);
			int applyId = uca.getId();
			UserCardLink ucl = new UserCardLink();
			ucl.setApplyId(applyId);
			ucl.setCardId(cardIdF);
			ucl.setCardIdFrom(cardId);
			userCardLinkMapper.insert(ucl);
		}
		return userInfo;
	}
	
	/**
	 * 重置密码
	 */
	public boolean resetPwd(UserInfo userInfo){
		userInfoMapper.resetPwd(userInfo);
		return true;
	}
	
	/**
	 * 获取文件url
	* @param fileUrl
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月24日 下午2:30:07
	 */
	private String getFileUrl(String fileUrl,HttpServletRequest request){
		if(fileUrl==null || "".equals(fileUrl.trim())){
			return fileUrl;
		}
		String root = webContextUtils.getStoreUrl(request);

		if (!storeService.isExistOnDisk(fileUrl)) {
			fileUrl = storeService.getAgentUrl()+ fileUrl; // 如果磁盘上的文件不存在，则路径指向OOS上
		} else {
			fileUrl = root + fileUrl;
		}
		return fileUrl;
	}
	
	/**
	 * 判断名片信息是否完整
	 * 1、有真实信息 trueName
	 * 2、已经绑定企业，或者已经申请绑定企业，（未申请，或者审核不通过不算）
	 * 3、有职位
	 * 4、有邮箱信息
	* @param cardInfo
	* @return
	* <p>
	* author: xyy
	* create at: 2014年4月16日上午12:34:54
	 */
	private String isInfoComplete(CardInfoSnapshot cardInfo, int cardId){
		String isInfoComplete = "";
		
		String trueName = cardInfo.getTrueName();
		String email = cardInfo.getEmail();
		String email2 = cardInfo.getEmail2();
		int cardPositionCount = cardPositionLinkMapper.getCardPositionCount(cardId);
		CardApplyEnt cae = cardApplyEntMapper.loadByCardId(cardId);
		
		if(StringUtils.isBlank(trueName) 
				|| (StringUtils.isBlank(email)&&StringUtils.isBlank(email2))
				|| cardPositionCount==0
				|| cae == null){
			isInfoComplete = "2";
		}else{
			isInfoComplete = "1";
		}
		
		//属于企业导入的员工刚注册
		if(cae==null && cardInfo.getEntId()>0){
			isInfoComplete = "3";
		}
		
		return isInfoComplete;
	}


}

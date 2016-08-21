package com.ancun.xinhu.web.api;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ancun.products.core.api.AncunApiResponse;
import com.ancun.products.core.model.PageResult;
import com.ancun.xinhu.biz.service.UserCardInfoService;
import com.ancun.xinhu.biz.service.UserClientInfoService;
import com.ancun.xinhu.config.XinHuConfig;
import com.ancun.xinhu.domain.dto.XinHuQuery;
import com.ancun.xinhu.domain.dto.XinHuResponseCode;
import com.ancun.xinhu.domain.dto.XinHuUserAgent;
import com.ancun.xinhu.domain.model.CardApplyEnt;
import com.ancun.xinhu.domain.model.CardInfo;
import com.ancun.xinhu.domain.model.CardInfoSnapshot;
import com.ancun.xinhu.domain.model.CardReport;
import com.ancun.xinhu.domain.model.EntInfo;
import com.ancun.xinhu.domain.model.GetuiMessageLog;
import com.ancun.xinhu.domain.model.UserCardApply;
import com.ancun.xinhu.domain.model.UserCardLink;
import com.ancun.xinhu.domain.model.UserClientInfo;
import com.ancun.xinhu.domain.model.UserInfo;
import com.ancun.xinhu.domain.query.CardInfoSnapshotQuery;
import com.ancun.xinhu.web.session.XinHuUserAgentSession;

/**
 * 名片信息接口
 * 
 * <p>
 * create at 2015年6月5日 下午4:15:15
 * @author <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a>
 * @version %I%, %G%
 * @see
 */
@Controller
@RequestMapping("cardInfo")
public class UserCardInfoApi {
	
	@Autowired
	private XinHuUserAgentSession xinHuUserAgentSession;
	
	@Autowired
	private UserCardInfoService userCardInfoService;
	
	@Autowired
	private UserClientInfoService userClientInfoService;
	
	
	@RequestMapping(value = "/test", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse test(String tokenid,HttpServletRequest request) {
		System.out.println(xinHuUserAgentSession.get(tokenid).getMobile());
		return null;
	}
	
	/**
	 * 保存客户端信息
	* @param userInfo
	* @param request
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月8日 上午10:23:17
	 */
	@RequestMapping(value = "/saveClientInfo", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse saveClientInfo(@ModelAttribute UserClientInfo userClientInfo,String tokenid){
		XinHuUserAgent xinHuUserAgent = xinHuUserAgentSession.get(tokenid);
		Long userId = xinHuUserAgent.getId();
		//Integer cardId = xinHuUserAgent.getDefaultCardId();
		userClientInfo.setUserId(userId.intValue());
		//userCardInfoService.saveClientInfo(userClientInfo);
		userClientInfoService.hanlderUserClientInfo(userClientInfo);
//		userCardInfoService.geTuiPushMessage(cardId);
		return AncunApiResponse.create();
	}
	
	/**
	 * 修改密码
	* @param userInfo
	* @param request
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月8日 上午10:23:58
	 */
	@RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse updatePwd(@ModelAttribute UserInfo userInfo,String newpwd){
		boolean result = userCardInfoService.validateOldPwd(userInfo);
		if(!result){
			return AncunApiResponse.create(XinHuResponseCode._120003.getCode(),XinHuResponseCode._120003.getMsg());
		}
		userInfo.setPwd(newpwd);
		userCardInfoService.updatePwd(userInfo);
		return AncunApiResponse.create();
	}
	
	/**
	 * 新名片接受
	* @param tokenid
	* @param applyId
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月9日 上午11:27:33
	 */
	@RequestMapping(value = "/acceptNewCard", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse acceptNewCard(int applyId,HttpServletRequest request){
		userCardInfoService.acceptNewCard(applyId,request);
		return AncunApiResponse.create();
	}
	
	/**
	 * 获取名片接口，如果searchType = exchange 则是返回已经交换的名片列表
	* @param cardInfo
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月8日 上午10:29:38
	 */
	@RequestMapping(value = "/getExchangeNewCardList", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse getExchangeNewCardList(@ModelAttribute CardInfoSnapshotQuery query,String tokenid,HttpServletRequest request){
		// 设置分页
		XinHuConfig.setQueryPage(query);
		
		int defaultCardId = xinHuUserAgentSession.get(tokenid).getDefaultCardId();
		query.setId(defaultCardId);
		PageResult<CardInfoSnapshot> pageResult = userCardInfoService.getExchangeNewCardList(query,request);
		
		return AncunApiResponse.create(pageResult);
	}
	
	/**
	 * 新名片内，已经交换的名片的详细获取接口
	* @param cardInfoSnapshot
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月8日 上午10:30:11
	 */
	@RequestMapping(value = "/getUserCardInfo", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse getUserCardInfo(@ModelAttribute CardInfoSnapshot cardInfoSnapshot,HttpServletRequest request,String tokenid){
		int defaultCardId = xinHuUserAgentSession.get(tokenid).getDefaultCardId();
		cardInfoSnapshot.setId(cardInfoSnapshot.getCardId());
		cardInfoSnapshot = userCardInfoService.getUserCardInfo(cardInfoSnapshot,request,defaultCardId);
		return AncunApiResponse.create(cardInfoSnapshot);
	}
	
	/**
	 * 二维码扫描交换名片接口（添加新名片）
	* @param cardInfo
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月8日 上午10:31:38
	 */
	@RequestMapping(value = "/qrcardExchangeCard", method = RequestMethod.GET)
	@ResponseBody
	public AncunApiResponse qrcardExchangeCard(@ModelAttribute UserCardApply userCardApply,String tokenid,HttpServletRequest request){
		int defaultCardId = xinHuUserAgentSession.get(tokenid).getDefaultCardId();
		userCardApply.setCardIdFrom(defaultCardId);
		
		UserCardLink userCardLink = new UserCardLink();
		userCardLink.setCardIdFrom(userCardApply.getCardIdFrom());
		userCardLink.setCardId(userCardApply.getCardId());
		boolean isExists = userCardInfoService.isExistsUserCardLink(userCardLink);
		if(!isExists){
			userCardInfoService.qrcardExchangeCard(userCardApply,request);
			
			CardInfoSnapshot cardInfoSnapshot = new CardInfoSnapshot();
			cardInfoSnapshot.setId(userCardApply.getCardId());
			cardInfoSnapshot = userCardInfoService.getUserCardInfo(cardInfoSnapshot,request,defaultCardId);
			
			CardInfoSnapshot res = new CardInfoSnapshot();
			res.setCardId(cardInfoSnapshot.getId());
			res.setTrueName(cardInfoSnapshot.getTrueName());
			res.setMobile(cardInfoSnapshot.getMobile());
			res.setIconUrl(cardInfoSnapshot.getIconUrl());
			res.setOrgName(cardInfoSnapshot.getOrgName());
			res.setGmtExchange(new Date());
			
			return AncunApiResponse.create(res);
		}else{
			return AncunApiResponse.create(XinHuResponseCode._120006.getCode(),XinHuResponseCode._120006.getMsg());
		}
	}
	
	/**
	 * 搜索新名片，分类，精确查找接口（分页)
	* @param cardInfo
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月8日 上午10:32:13
	 */
	@RequestMapping(value = "/searchNewCardList", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse searchNewCardList(@ModelAttribute XinHuQuery query,HttpServletRequest request){
		//企业搜索独立接口
		if("orgName".equals(query.getSearchType())){
			// 设置分页
			//XinHuConfig.setQueryPage(query);
			
			PageResult<EntInfo> pageResult = userCardInfoService.searchEntCardList(query);
			
			return AncunApiResponse.create(pageResult);
		}else{
			// 设置分页
			//XinHuConfig.setQueryPage(query);
			int defaultCardId = xinHuUserAgentSession.get(query.getTokenid()).getDefaultCardId();
			query.setCardId(defaultCardId);
			PageResult<CardInfoSnapshot> pageResult = userCardInfoService.searchNewCardList(query,request);
			
			return AncunApiResponse.create(pageResult);
		}
	}
	
	/**
	 * 递交新名片接口
	* @param cardInfo
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月8日 上午10:33:03
	 */
	@RequestMapping(value = "/deliverNewCard", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse deliverNewCard(@ModelAttribute UserCardApply userCardApply){
		XinHuResponseCode res = userCardInfoService.deliverNewCard(userCardApply);
		return AncunApiResponse.create(res.getCode(),res.getMsg());
	}
	
	/**
	 * 搜索结果为 企业 加 个人 接口
	* @param cardInfo
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月8日 上午10:34:07
	 */
	@RequestMapping(value = "/searchCardList", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse searchCardList(@ModelAttribute XinHuQuery query,HttpServletRequest request){
		// 分页查询
//		if (query.getPageSize() == 0) {
//			query.setPageSize(10);
//		}
		Map<String,Object> result = userCardInfoService.searchCardList(query,request);
		
		return AncunApiResponse.create(result);
	}
	
	/**
	 * 搜索结果为 企业 加 个人 接口——搜索企业
	* @param cardInfo
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月8日 上午10:34:46
	 */
	@RequestMapping(value = "/searchEntCardList", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse searchEntCardList(@ModelAttribute XinHuQuery query){
		// 设置分页
		XinHuConfig.setQueryPage(query);
		
		PageResult<EntInfo> pageResult = userCardInfoService.searchEntCardList(query);
		
		return AncunApiResponse.create(pageResult);
	}
	
	/**
	 * 搜索结果为 企业 加 个人 接口——搜索个人
	* @param cardInfo
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月8日 上午10:35:07
	 */
	@RequestMapping(value = "/searchClientCardList", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse searchClientCardList(@ModelAttribute XinHuQuery query){
		// 设置分页
		XinHuConfig.setQueryPage(query);
		
		PageResult<CardInfoSnapshot> pageResult = userCardInfoService.searchClientCardList(query);
		
		return AncunApiResponse.create(pageResult);
	}
	
	/**
	 * 企业名片详情获取接口
	* @param cardInfo
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月8日 上午10:35:43
	 */
	@RequestMapping(value = "/getEntCardInfo", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse getEntCardInfo(@ModelAttribute XinHuQuery xinHuQuery,HttpServletRequest request){
		int defaultCardId = xinHuUserAgentSession.get(xinHuQuery.getTokenid()).getDefaultCardId();
		xinHuQuery.setCardId(defaultCardId);
		EntInfo entInfo = userCardInfoService.getEntCardInfo(xinHuQuery,request);
		return AncunApiResponse.create(entInfo);
	}
	
	/**
	 * 企业下已绑定的名片获取接口
	* @param cardInfo
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月8日 上午10:41:34
	 */
	@RequestMapping(value = "/getEntExchangeCardList", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse getEntExchangeCardList(@ModelAttribute XinHuQuery xinHuQuery,HttpServletRequest request){
		int defaultCardId = xinHuUserAgentSession.get(xinHuQuery.getTokenid()).getDefaultCardId();
		xinHuQuery.setCardId(defaultCardId);
		List<CardInfoSnapshot> list = userCardInfoService.getEntExchangeCardList(xinHuQuery,request);
		return AncunApiResponse.create(list);
	}
	
	/**
	 * 个人信息获取接口
	* @param cardInfoSnapshot
	* @param request
	* @param tokenid
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年7月7日 下午8:05:14
	 */
	@RequestMapping(value = "/getSelfCardInfo", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse getSelfCardInfo(@ModelAttribute CardInfoSnapshot cardInfoSnapshot,HttpServletRequest request,String tokenid){
		int defaultCardId = xinHuUserAgentSession.get(tokenid).getDefaultCardId();
		cardInfoSnapshot.setId(defaultCardId);
		cardInfoSnapshot = userCardInfoService.getSelfCardInfo(cardInfoSnapshot,request);
		return AncunApiResponse.create(cardInfoSnapshot);
	}
	
	/**
	 * 个人信息修改接口
	* @param cardInfo
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月8日 上午10:48:24
	 */
	@RequestMapping(value = "/updateUserCardInfo", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse updateUserCardInfo(@ModelAttribute CardInfo cardInfo,String tokenid,HttpServletRequest request){
		int defaultCardId = xinHuUserAgentSession.get(tokenid).getDefaultCardId();
		String positionName = request.getParameter("positionName");
		if (positionName != null && !"".equals(positionName.trim())) {
			String[] positionNameArr = positionName.trim().split(",");
			if(positionNameArr.length>3){
				return AncunApiResponse.create(XinHuResponseCode._120010.getCode(), XinHuResponseCode._120010.getMsg()); 
			}
		}
		cardInfo.setId(defaultCardId);
		userCardInfoService.updateUserCardInfo(cardInfo,request);
		return AncunApiResponse.create();
	}
	
	/**
	 * 图片上传接口
	* @param cardInfo
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月8日 上午10:48:58
	 */
	@RequestMapping(value = "/uploadUserIcon", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse uploadUserIcon(String tokenid,HttpServletRequest request) throws Exception{
		XinHuUserAgent userAgent = xinHuUserAgentSession.get(tokenid);
		String fileUrl = userCardInfoService.uploadUserIcon(userAgent,request);
		return AncunApiResponse.create(fileUrl);
	}
	
	/**
	 * 搜索已经注册的企业接口
	* @param cardInfo
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月8日 上午10:49:56
	 */
	@RequestMapping(value = "/searchEntList", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse searchEntList(@ModelAttribute XinHuQuery xinHuQuery){
		List<EntInfo> list = userCardInfoService.searchEntList(xinHuQuery);
		return AncunApiResponse.create(list);
	}
	
	/**
	 * 获取搜索企业详情接口
	* @param cardInfo
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月8日 上午10:54:28
	 */
	@RequestMapping(value = "/getSearchEntCardInfo", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse getSearchEntCardInfo(@ModelAttribute XinHuQuery xinHuQuery){
		int defaultCardId = xinHuUserAgentSession.get(xinHuQuery.getTokenid()).getDefaultCardId();
		xinHuQuery.setCardId(defaultCardId);
		EntInfo entInfo = userCardInfoService.getSearchEntCardInfo(xinHuQuery);
		return AncunApiResponse.create(entInfo);
	}
	
	/**
	 * 申请绑定企业接口
	* @param cardInfo
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月8日 上午10:56:07
	 */
	@RequestMapping(value = "/applyBindEnt", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse applyBindEnt(@ModelAttribute CardApplyEnt cardApplyEnt){
		userCardInfoService.applyBindEnt(cardApplyEnt);
		return AncunApiResponse.create();
	}
	
	/**
	 * 人脉统计接口
	* @param cardInfo
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月8日 上午10:56:37
	 */
	@RequestMapping(value = "/getConnStatisticalInfo", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse getConnStatisticalInfo(String tokenid){
		int cardId = xinHuUserAgentSession.get(tokenid).getDefaultCardId();
		Map<String,Object> map = userCardInfoService.getConnStatisticalInfo(cardId);
		return AncunApiResponse.create(map);
	}
	
	/**
	 * 搜索企业部门的接口
	* @param cardInfo
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月8日 上午10:57:08
	 */
	@RequestMapping(value = "/getEntDeptList", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse getEntDeptList(@ModelAttribute XinHuQuery query){
		List<HashMap<String,Object>> list = userCardInfoService.getEntDeptList(query);
		return AncunApiResponse.create(list);
	}
	
	/**
	 * 搜索企业个人的接口
	* @param cardInfo
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月8日 上午10:57:40
	 */
	@RequestMapping(value = "/getEntStaffList", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse getEntStaffList(@ModelAttribute XinHuQuery query,HttpServletRequest request){
		int cardId = xinHuUserAgentSession.get(query.getTokenid()).getDefaultCardId();
		query.setId(cardId);
		// 设置分页
//		XinHuConfig.setQueryPage(query);
		//临时取消分页
		query.setPageSize(100000);
		query.setPageNo(1);
		
		if("trueName".equals(query.getSearchType())){
			PageResult<HashMap<String,Object>> pageResult = userCardInfoService.getEntStaffList(query,request);
			return AncunApiResponse.create(pageResult);
		}else if("deptName".equals(query.getSearchType())){
			PageResult<HashMap<String,Object>> pageResult = userCardInfoService.queryEntStaffListByDept(query,request);
			return AncunApiResponse.create(pageResult);
		}else{
			return AncunApiResponse.create();
		}
	}
	
	/**
	 * 主页 搜索结果个人加部门的接口
	* @param cardInfo
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月8日 上午10:58:10
	 */
	@RequestMapping(value = "/searchEntInnerCardList", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse searchEntInnerCardList(@ModelAttribute XinHuQuery query,HttpServletRequest request){
		int cardId = xinHuUserAgentSession.get(query.getTokenid()).getDefaultCardId();
		query.setId(cardId);
		
		Map<String,Object> result = userCardInfoService.searchEntInnerCardList(query,request);
		return AncunApiResponse.create(result);
	}
	
	/**
	 * 企业通讯录个人名片详情获取接口
	* @param cardInfo
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月8日 上午10:58:36
	 */
	@RequestMapping(value = "/getEntClientCardInfo", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse getEntClientCardInfo(@ModelAttribute CardInfoSnapshot cardInfoSnapshot,HttpServletRequest request,String tokenid){
		//修改注意事项,这个暂时是和上面getUserCardInfo方法公用相同的方法的，如下调整这个方法，需要复制再修改
		int defaultCardId = xinHuUserAgentSession.get(tokenid).getDefaultCardId();
		cardInfoSnapshot.setId(cardInfoSnapshot.getCardId());
		cardInfoSnapshot = userCardInfoService.getUserCardInfo(cardInfoSnapshot,request,defaultCardId);
		return AncunApiResponse.create(cardInfoSnapshot);
	}
	
	/**
	 * 删除名片接口
	* @param cardInfo
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月8日 上午10:59:12
	 */
	@RequestMapping(value = "/deleteCard", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse deleteCard(@ModelAttribute UserCardApply userCardApply,String tokenid){
		int defaultCardId = xinHuUserAgentSession.get(tokenid).getDefaultCardId();
		userCardApply.setCardIdFrom(defaultCardId);
		userCardApply.setCardIdDelete(defaultCardId);
		userCardInfoService.deleteCard(userCardApply);
		return AncunApiResponse.create();
	}
	
	/**
	 * 举报名片接口
	* @param cardInfo
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月8日 上午10:59:34
	 */
	@RequestMapping(value = "/reportCard", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse reportCard(@ModelAttribute CardReport cardReport,int cardId,String reportType,String tokenid){
		int defaultCardId = xinHuUserAgentSession.get(tokenid).getDefaultCardId();
		cardReport.setReportUseridFrom(defaultCardId);
		cardReport.setReportUserid(cardId);
		cardReport.setReportType(reportType);
		userCardInfoService.reportCard(cardReport);
		return AncunApiResponse.create();
	}
	
	/**
	 * 隐私接口（禁止通关手机号或名片号等搜索到）
	* @param cardInfo
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月8日 上午11:00:12
	 */
	@RequestMapping(value = "/setIsEnableSearch", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse setIsEnableSearch(@ModelAttribute XinHuQuery xinHuQuery,HttpServletRequest request){
		int defaultCardId = xinHuUserAgentSession.get(xinHuQuery.getTokenid()).getDefaultCardId();
		xinHuQuery.setCardId(defaultCardId);
		userCardInfoService.setIsEnableSearch(defaultCardId,request);
		return AncunApiResponse.create();
	}
//	public AncunApiResponse setIsEnableSearch(@ModelAttribute XinHuQuery xinHuQuery,String type){
//		int defaultCardId = xinHuUserAgentSession.get(xinHuQuery.getTokenid()).getDefaultCardId();
//		xinHuQuery.setCardId(defaultCardId);
//		userCardInfoService.setIsEnableSearch(xinHuQuery,type);
//		return AncunApiResponse.create();
//	}
	
	/**
	 * 绑定新手机接口
	* @param cardInfo
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月8日 上午11:00:38
	 */
	@RequestMapping(value = "/bindNewMobile", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse bindNewMobile(String mobile,String code,String tokenid){
		XinHuUserAgent userAgent=xinHuUserAgentSession.get(mobile);
		if(userAgent != null && code!=null && code.equals(userAgent.getCode())){
			XinHuUserAgent user = xinHuUserAgentSession.get(tokenid);
			int userId= ((Long)(user.getId())).intValue();
			int cardId=user.getDefaultCardId();
			userCardInfoService.bindNewMobile(userId,cardId,mobile);
			xinHuUserAgentSession.remove(mobile,false);
			return AncunApiResponse.create();
		}else{
			return AncunApiResponse.create(XinHuResponseCode._120001.getCode(), XinHuResponseCode._120001.getMsg());
		}
	}
	
	/**
	 * 消息已读接口
	* @param cardInfo
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月8日 上午11:01:12
	 */
	
	@RequestMapping(value = "/updateMessageReadTime", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse updateMessageReadTime(String tokenid){
		Long userId = xinHuUserAgentSession.get(tokenid).getId();
		userCardInfoService.updateMessageReadTime(userId.intValue());
		return AncunApiResponse.create();
	}
	
	/**
	 * 推荐名片接口（交换名片搜索没有内容时调用）
	* @param cardInfo
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月8日 上午11:01:38
	 */
	@RequestMapping(value = "/getRecommendCardList", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse getRecommendCardList(@ModelAttribute XinHuQuery xinHuQuery,HttpServletRequest request){
		int cardId = xinHuUserAgentSession.get(xinHuQuery.getTokenid()).getDefaultCardId();
		xinHuQuery.setCardId(cardId);
		List<HashMap<String,String>> list = userCardInfoService.getRecommendCardList(xinHuQuery,request); 
		return AncunApiResponse.create(list);
	}
	
	/**
	 * 所有交换名片信息更新动态列表接口
	* @param cardInfo
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月8日 上午11:02:09
	 */
	@RequestMapping(value = "/getCardUpdateDynamicList", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse getCardUpdateDynamicList(@ModelAttribute XinHuQuery query,HttpServletRequest request){
		XinHuUserAgent xinHuUserAgent = xinHuUserAgentSession.get(query.getTokenid());
		int cardId = xinHuUserAgent.getDefaultCardId();
		query.setCardId(cardId);
		query.setId(xinHuUserAgent.getId().intValue());
		
		// 设置分页
		XinHuConfig.setQueryPage(query);
		
		PageResult<HashMap<String,Object>> pageResult = userCardInfoService.getCardUpdateDynamicList(query,request);
		return AncunApiResponse.create(pageResult);
	}
	
	/**
	 * 单张名片信息更新动态列表接口
	* @param query
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月17日 下午4:49:11
	 */
	@RequestMapping(value = "/getCardUpdateDynamicInfo", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse getCardUpdateDynamicInfo(@ModelAttribute XinHuQuery query,HttpServletRequest request){
		XinHuUserAgent xinHuUserAgent = xinHuUserAgentSession.get(query.getTokenid());
		int defaultCardId = xinHuUserAgent.getDefaultCardId();
		query.setId(defaultCardId);

		Map<String,Object> pageResult = userCardInfoService.getCardUpdateDynamicInfo(query,request);
		return AncunApiResponse.create(pageResult);
	}
	
	/**
	 * 个推消息读取通知接口
	* @param tokenid
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年7月5日 上午10:13:13
	 */
	@RequestMapping(value = "/getuiMessageReadNotice", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse getuiMessageReadNotice(String messageId,String tokenid){
		Integer cardId = xinHuUserAgentSession.get(tokenid).getDefaultCardId();
		GetuiMessageLog getuiMessageLog = new  GetuiMessageLog();
		getuiMessageLog.setId(messageId);
//		getuiMessageLog.setUserId(userId.intValue());
		getuiMessageLog.setCardId(cardId);
		userCardInfoService.getuiMessageReadNotice(getuiMessageLog);
		return AncunApiResponse.create();
	}
	
	/**
	 * 获取企业职位列表
	* @param xinHuQuery
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年7月5日 上午11:08:43
	 */
	@RequestMapping(value = "/getEntPositionList", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse getEntPositionList(@ModelAttribute XinHuQuery xinHuQuery){
//		Long userId = xinHuUserAgentSession.get(xinHuQuery.getTokenid()).getId();
		String result = userCardInfoService.getEntPositionList(xinHuQuery.getEntId());
		return AncunApiResponse.create(result);
	}
	
	
	@RequestMapping(value = "/denyOrAffirmEntBand", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse denyOrAffirmEntBand(Integer cardId, Integer type){
		userCardInfoService.denyOrAffirmEntBand(cardId,type);
		return AncunApiResponse.create();
	}

}

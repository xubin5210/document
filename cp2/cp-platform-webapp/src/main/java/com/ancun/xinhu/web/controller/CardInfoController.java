package com.ancun.xinhu.web.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ancun.products.core.api.AncunApiResponse;
import com.ancun.products.core.config.SysConfig;
import com.ancun.products.core.model.PageResult;
import com.ancun.xinhu.biz.mappers.CardInfoMapper;
import com.ancun.xinhu.biz.mappers.EntDepartmentMapper;
import com.ancun.xinhu.biz.mappers.EntPositionMapper;
import com.ancun.xinhu.biz.mappers.SysOperationLogMapper;
import com.ancun.xinhu.biz.service.CardInfoService;
import com.ancun.xinhu.biz.service.EntInfoService;
import com.ancun.xinhu.domain.dto.XinHuQuery;
import com.ancun.xinhu.domain.dto.XinHuUserAgent;
import com.ancun.xinhu.domain.model.CardInfo;
import com.ancun.xinhu.domain.model.EntDepartment;
import com.ancun.xinhu.domain.model.EntInfo;
import com.ancun.xinhu.domain.model.EntPosition;
import com.ancun.xinhu.domain.query.CardInfoQuery;
import com.ancun.xinhu.domain.query.EntDepartmentQuery;
import com.ancun.xinhu.domain.query.EntPositionQuery;
import com.ancun.xinhu.web.session.XinHuUserAgentSession;

/**
 * 花名册管理
 * 
 * <p>
 * create at 2015年6月5日 下午2:29:33
 * 
 * @author <a href="mailto:qiande@ancun.com">QianDe</a>
 * @version %I%, %G%
 * @see
 */
@Controller
public class CardInfoController {

	@Autowired
	private SysConfig sysConfig;
	@Autowired
	private CardInfoService cardInfoService;

	@Autowired
	private EntInfoService entInfoService;

	@Autowired
	private EntDepartmentMapper entDepartmentMapper;

	@Autowired
	private EntPositionMapper entPositionMapper;

	@Autowired
	private XinHuUserAgentSession xinHuUserAgentSession;
	
	@Autowired
	private CardInfoMapper cardInfoMapper;
	@Autowired
	private SysOperationLogMapper sysOperationLogMapper;
	/**
	 * 跳转到花名册管理
	 * 
	 * @param entInfo
	 * @param request
	 * @return <p>
	 *         author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	 *         create at: 2015年6月16日 下午4:38:35
	 */
	@RequestMapping(value = "card-manage", method = RequestMethod.GET)
	public String cardManage(@ModelAttribute CardInfoQuery cardInfoQuery, HttpServletRequest request, Model model) {
		XinHuUserAgent xinhu = xinHuUserAgentSession.get(request);
		if(xinhu==null)return "xinhu/login/index";
		int entId = xinhu.getId().intValue();
		// 企业信息
		EntInfo entInfo = entInfoService.getEntInfo(entId);
		model.addAttribute("entInfo", entInfo);
		cardInfoQuery.setEntId(entId);
		// 获取APP端用户申请
		PageResult<CardInfo> page = getRegistUserList(cardInfoQuery);
		model.addAttribute("page", page);
		// 返回query
		model.addAttribute("query", cardInfoQuery);
		// 花名册统计
		Map<String, Integer> count = entInfoService.getCardsCount(entId);
		model.addAttribute("count", count);
		// 职位集合
		EntPositionQuery entPositionQuery = new EntPositionQuery();
		entPositionQuery.setEntId(entId);
		List<EntPosition> positionList = entPositionMapper.queryList(entPositionQuery);
		model.addAttribute("positionList", positionList);
		// 部门集合
		EntDepartmentQuery entDepartmentQuery = new EntDepartmentQuery();
		entDepartmentQuery.setEntId(entId);
		List<EntDepartment> deptList = entDepartmentMapper.queryList(entDepartmentQuery);
		model.addAttribute("deptList", deptList);
		String storeUrl = sysConfig.get(SysConfig.KEY_STORE_SERVER_URL);
		model.addAttribute("previewUrl", sysConfig.get("store.server.url")+"xinhu/web/temp/");
		model.addAttribute("storeUrl", storeUrl);
		return "xinhu/ent/card-manage";
	}

	/**
	 * 获取员工导入界面
	 * 
	 * @param cardInfoQuery
	 * @param request
	 * @param model
	 * @return <p>
	 *         author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	 *         create at: 2015年6月17日 下午2:01:08
	 */
	@RequestMapping(value = "card-export", method = RequestMethod.GET)
	public String cardExport(@ModelAttribute CardInfoQuery cardInfoQuery, HttpServletRequest request, Model model) {
		XinHuUserAgent xinhu = xinHuUserAgentSession.get(request);
		if(xinhu==null)return "xinhu/login/index";
		int entId = xinhu.getId().intValue();
		// 企业信息
		EntInfo entInfo = entInfoService.getEntInfo(entId);
		model.addAttribute("errorUrl", sysConfig.get("store.server.url")+"xinhu/web/temp/");
		model.addAttribute("entInfo", entInfo);
		return "xinhu/ent/card-export";
	}

	/**
	 * 获取员工单个增加界面
	 * 
	 * @param cardInfoQuery
	 * @param request
	 * @param model
	 * @return <p>
	 *         author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	 *         create at: 2015年6月17日 下午2:37:18
	 */
	@RequestMapping(value = "card-add", method = RequestMethod.GET)
	public String cardAdd(@ModelAttribute CardInfoQuery cardInfoQuery, HttpServletRequest request, Model model) {
		XinHuUserAgent xinhu = xinHuUserAgentSession.get(request);
		if(xinhu==null)return "xinhu/login/index";
		int entId = xinhu.getId().intValue();
		// 企业信息
		EntInfo entInfo = entInfoService.getEntInfo(entId);
		model.addAttribute("entInfo", entInfo);
		// 职位集合
		EntPositionQuery entPositionQuery = new EntPositionQuery();
		entPositionQuery.setEntId(entInfo.getId());
		List<EntPosition> positionList = entPositionMapper.queryList(entPositionQuery);
		model.addAttribute("positionList", positionList);
		// 部门集合
		EntDepartmentQuery entDepartmentQuery = new EntDepartmentQuery();
		entDepartmentQuery.setEntId(entInfo.getId());
		List<EntDepartment> deptList = entDepartmentMapper.queryList(entDepartmentQuery);
		model.addAttribute("deptList", deptList);
		// 花名册统计
		Map<String, Integer> count = entInfoService.getCardsCount(entInfo.getId());
		model.addAttribute("count", count);
		String storeUrl = sysConfig.get(SysConfig.KEY_STORE_SERVER_URL);
		model.addAttribute("previewUrl", sysConfig.get("store.server.url")+"xinhu/web/temp/");
		model.addAttribute("storeUrl", storeUrl);
		return "xinhu/ent/card-add";
	}

	/**
	 * 跳转到员工资料修改页面
	 * 
	 * @param cardInfoQuery
	 * @param request
	 * @param model
	 * @return <p>
	 *         author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	 *         create at: 2015年6月17日 下午5:46:20
	 */
	@RequestMapping(value = "card-edit", method = RequestMethod.GET)
	public String cardEdit(@ModelAttribute CardInfoQuery cardInfoQuery, HttpServletRequest request, Model model) {
		XinHuUserAgent xinhu = xinHuUserAgentSession.get(request);
		if(xinhu==null)return "xinhu/login/index";
		int entId = xinhu.getId().intValue();
		// 企业信息
		EntInfo entInfo = entInfoService.getEntInfo(entId);
		cardInfoQuery.setEntId(entId);
		model.addAttribute("entInfo", entInfo);
		// 职位集合
		EntPositionQuery entPositionQuery = new EntPositionQuery();
		entPositionQuery.setEntId(entInfo.getId());
		List<EntPosition> positionList = entPositionMapper.queryList(entPositionQuery);
		model.addAttribute("positionList", positionList);
		// 部门集合
		EntDepartmentQuery entDepartmentQuery = new EntDepartmentQuery();
		entDepartmentQuery.setEntId(entInfo.getId());
		List<EntDepartment> deptList = entDepartmentMapper.queryList(entDepartmentQuery);
		model.addAttribute("deptList", deptList);
		// 花名册统计
		Map<String, Integer> count = entInfoService.getCardsCount(entId);
		model.addAttribute("count", count);
		// 员工信息
		PageResult<CardInfo> cardResult = getEmployeeList(cardInfoQuery);
		List<CardInfo> cardList = cardResult.getData();
		if (cardList != null && cardList.size() != 0) {
			model.addAttribute("cardInfo", cardList.get(0));
		}

		String storeUrl = sysConfig.get(SysConfig.KEY_STORE_SERVER_URL);
		model.addAttribute("previewUrl", sysConfig.get("store.server.url")+"xinhu/web/temp/");
		model.addAttribute("storeUrl", storeUrl);
		return "xinhu/ent/card-edit";
	}

	/**
	 * 跳转到员工管理主页面
	 * 
	 * @param cardInfoQuery
	 * @param request
	 * @param model
	 * @return <p>
	 *         author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	 *         create at: 2015年6月17日 下午4:54:28
	 */
	@RequestMapping(value = "card-search", method = RequestMethod.GET)
	public String cardSearch(@ModelAttribute CardInfoQuery cardInfoQuery, HttpServletRequest request, Model model) {
		XinHuUserAgent xinhu = xinHuUserAgentSession.get(request);
		if(xinhu==null)return "xinhu/login/index";
		int entId = xinhu.getId().intValue();
		// 企业信息
		EntInfo entInfo = entInfoService.getEntInfo(entId);
		model.addAttribute("entInfo", entInfo);
		cardInfoQuery.setEntId(entId);
		// 花名册统计
		Map<String, Integer> count = entInfoService.getCardsCount(entInfo.getId());
		model.addAttribute("count", count);
		
		String storeUrl = sysConfig.get(SysConfig.KEY_STORE_SERVER_URL);
		model.addAttribute("storeUrl", storeUrl);
		model.addAttribute("errorUrl", sysConfig.get("store.server.url")+"xinhu/web/temp/");
		model.addAttribute("previewUrl", sysConfig.get("store.server.url")+"xinhu/web/temp/");
		
		if (count.get("flowerCount") == null || count.get("flowerCount") == 0) {
			return "xinhu/ent/card-export";
		}
		// 获取所有员工集合
		PageResult<CardInfo> cardInfoList = getEmployeeList(cardInfoQuery);
		model.addAttribute("page", cardInfoList);

		// 职位集合
		EntPositionQuery entPositionQuery = new EntPositionQuery();
		entPositionQuery.setEntId(entInfo.getId());
		List<EntPosition> positionList = entPositionMapper.queryList(entPositionQuery);
		model.addAttribute("positionList", positionList);
		// 部门集合
		EntDepartmentQuery entDepartmentQuery = new EntDepartmentQuery();
		entDepartmentQuery.setEntId(entInfo.getId());
		List<EntDepartment> deptList = entDepartmentMapper.queryList(entDepartmentQuery);
		model.addAttribute("deptList", deptList);
		
		model.addAttribute("queryModel",cardInfoQuery);

		return "xinhu/ent/card-search";
	}

	/**
	 * 新增企业员工
	 * 
	 * @param entInfo
	 * @param request
	 * @return <p>
	 *         author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	 *         create at: 2015年6月5日 上午11:18:16
	 */
	@RequestMapping(value = "/addNewEmployee", method = RequestMethod.POST)
	public void addNewEmployee(@ModelAttribute CardInfo cardInfo, HttpServletRequest request) {
		// String clientAddr = HttpUtils.getRemoteAddr(request);// 接入者ip地址
		cardInfoService.addNewEmployee(cardInfo, request);
	}
	
	
	/**
	 * 校验卡片手机号是否被注册过
	* @param cardInfo
	* @param request
	* @return
	* <p>
	* author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	* create at: 2015年7月22日 下午3:47:13
	 */
	@RequestMapping(value = "/checkcardMobile", method = RequestMethod.GET)
	@ResponseBody
	public boolean checkcardMobile(@RequestParam("mobile") String mobile) {
		// String clientAddr = HttpUtils.getRemoteAddr(request);// 接入者ip地址
		int count = cardInfoMapper.checkMobile(mobile);
		if(count != 0)
			return false;
		else
			return true;
	}
	

	/**
	 * 修改企业员工
	 * 
	 * @param entInfo
	 * @param request
	 * @return <p>
	 *         author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	 *         create at: 2015年6月5日 下午12:16:35
	 */
	@RequestMapping(value = "/updateEmployee", method = RequestMethod.POST)
	public void updateEmployee(@ModelAttribute CardInfo cardInfo, HttpServletRequest request) {
		cardInfoService.updateEmployee(cardInfo, request);
	}

	/**
	 * 删除企业员工
	 * 
	 * @param cardInfo
	 * @param request
	 * @return <p>
	 *         author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	 *         create at: 2015年6月5日 下午3:31:07
	 */
	@RequestMapping(value = "/deleteEmployee", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse deleteEmployee(@ModelAttribute CardInfo cardInfo, HttpServletRequest request) {
		cardInfoService.deleteEmployee(cardInfo);
		return AncunApiResponse.create(true);
	}

	/**
	 * 获取所有员工信息列表 带分页
	 * 
	 * @param cardInfoQuery
	 * @param request
	 * @return <p>
	 *         author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	 *         create at: 2015年6月5日 下午3:52:52
	 */
	public PageResult<CardInfo> getEmployeeList(CardInfoQuery cardInfoQuery) {
		// 分页查询
		if (cardInfoQuery.getPageSize() == 0) {
			cardInfoQuery.setPageSize(10);
		}
		return cardInfoService.getEmployeeList(cardInfoQuery);
	}

	/**
	 * 获取指定员工所有信息
	 * 
	 * @param id
	 * @param request
	 * @return <p>
	 *         author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	 *         create at: 2015年6月5日 下午4:05:30
	 */
	@RequestMapping(value = "/getEmployeeInfo", method = RequestMethod.GET)
	@ResponseBody
	public AncunApiResponse getEmployeeInfo(@RequestParam("id") int id, HttpServletRequest request) {
		CardInfo cardInfo = cardInfoService.getEmployeeInfo(id);
		if (cardInfo != null)
			return AncunApiResponse.create(cardInfo);
		else
			return AncunApiResponse.create(false);
	}

	/**
	 * 修改员工账号状态
	 * 
	 * @param id
	 * @param status
	 * @param request
	 * @return <p>
	 *         author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	 *         create at: 2015年6月5日 下午4:12:53
	 */
	@RequestMapping(value = "/updateEmployeeStatus", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse updateEmployeeStatus(@RequestParam("id") int id, @RequestParam("status") int status,
			HttpServletRequest request) {
		cardInfoService.updateEmployeeStatus(id, status);
		return AncunApiResponse.create(true);
	}

	/**
	 * 批量导入员工信息
	 * 
	 * @param entId
	 * @param fileName
	 * @param request
	 * @return <p>
	 *         author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	 *         create at: 2015年6月11日 上午9:05:27
	 * @throws IOException 
	 */
	@RequestMapping(value = "/exportEmployees", method = RequestMethod.POST)
	public void exportEmployees(HttpServletRequest request,HttpServletResponse response) throws IOException {
		JSONObject json =new JSONObject();
		response.setContentType("text/html;charset=UTF-8");
		Map<String,Object> result = cardInfoService.exportEmployees(request);
		json.put("data", result);
		response.getWriter().write(json.toJSONString());
	}

	/**
	 * 获取APP端注册的用户列表
	 * 
	 * @param cardInfoQuery
	 * @param request
	 * @return <p>
	 *         author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	 *         create at: 2015年6月12日 上午10:22:25
	 */
	private PageResult<CardInfo> getRegistUserList(CardInfoQuery cardInfoQuery) {
		// 分页查询
		if (cardInfoQuery.getPageSize() == 0) {
			cardInfoQuery.setPageSize(10);
		}
		return cardInfoService.getRegistUserList(cardInfoQuery);
	}

	/**
	 * 通过用户
	 * 
	 * @param cardInfoQuery
	 * @param request
	 * @return <p>
	 *         author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	 *         create at: 2015年6月12日 下午2:21:50
	 */
	@RequestMapping(value = "/passUser", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse passUser(@ModelAttribute CardInfo cardInfo, HttpServletRequest request) {
		cardInfoService.passUser(cardInfo);
		return AncunApiResponse.create(true);
	}

	/**
	 * 不通过用户申请
	 * 
	 * @param cardInfo
	 * @param request
	 * @return <p>
	 *         author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	 *         create at: 2015年6月12日 下午2:48:22
	 */
	@RequestMapping(value = "/unpassUser", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse unpassUser(@ModelAttribute CardInfo cardInfo, HttpServletRequest request) {
		cardInfoService.unpassUser(cardInfo);
		return AncunApiResponse.create(true);
	}

	/**
	 * 获取卡片交换记录
	 * 
	 * @param cardInfo
	 * @param request
	 * @return <p>
	 *         author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	 *         create at: 2015年6月23日 下午3:52:31
	 */
	@RequestMapping(value = "/card-exchanging-record", method = RequestMethod.GET)
	public String cardExchangingRecord(@ModelAttribute CardInfoQuery cardInfoQuery, HttpServletRequest request,
			Model model) {
		XinHuUserAgent xinhu = xinHuUserAgentSession.get(request);
		if(xinhu==null)return "xinhu/login/index";
		int entId = xinhu.getId().intValue();
		// 企业信息
		EntInfo entInfo = entInfoService.getEntInfo(entId);
		model.addAttribute("entInfo", entInfo);
		cardInfoQuery.setEntId(entId);
		if (cardInfoQuery.getPageSize() == 0) {
			cardInfoQuery.setPageSize(10);
		}
		PageResult<Map<String, Object>> result = cardInfoService.getCardExchangingRecord(cardInfoQuery);
		model.addAttribute("page", result);
		String storeUrl = sysConfig.get(SysConfig.KEY_STORE_SERVER_URL);
		model.addAttribute("storeUrl", storeUrl);
		model.addAttribute("previewUrl", sysConfig.get("store.server.url")+"xinhu/web/temp/");
		return "xinhu/ent/card-exchanging-record";
	}

	/**
	 * 获取单条卡片交换记录
	 * 
	 * @param cardId
	 * @param cardIdFrom
	 * @param request
	 * @return <p>
	 *         author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	 *         create at: 2015年6月23日 下午5:56:45
	 */
	@RequestMapping(value = "/getOneRecord", method = RequestMethod.GET)
	@ResponseBody
	public AncunApiResponse getOneRecord(@RequestParam("cardId") int cardId,
			@RequestParam("cardIdFrom") int cardIdFrom, HttpServletRequest request) {
		Map<String, Integer> paraMap = new HashMap<String, Integer>();
		paraMap.put("cardIdFrom", cardIdFrom);
		paraMap.put("cardId", cardId);
		Map<String, Object> map = cardInfoService.getOneRecord(paraMap);
		XinHuQuery xinHuQuery = new XinHuQuery();
		xinHuQuery.setCardId(cardIdFrom);
		xinHuQuery.setId(cardId);
		List<HashMap<String,Object>> changeMapList = sysOperationLogMapper.getCardUpdateDynamicInfo(xinHuQuery);
		map.put("changeMapList",changeMapList);
		return AncunApiResponse.create(map);
	}

	/**
	 * 获取卡片详细信息
	 * 
	 * @param request
	 * @return <p>
	 *         author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	 *         create at: 2015年6月24日 下午2:11:35
	 */
	@RequestMapping(value = "/getCardInfo", method = RequestMethod.GET)
	public String getCardInfo(@RequestParam("id") int id,  Model model, HttpServletRequest request) {
		XinHuUserAgent xinhu = xinHuUserAgentSession.get(request);
		if(xinhu==null)return "xinhu/login/index";
		int entId = xinhu.getId().intValue();
		// 企业信息
		EntInfo entInfo = entInfoService.getEntInfo(entId);
		model.addAttribute("entInfo", entInfo);
		CardInfo card = cardInfoService.getEmployeeInfo(id);
		model.addAttribute("card", card);
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("entId", entId);
		paraMap.put("id", id);
		Map<String, Object> counts = cardInfoService.getExchangeCardInfo(paraMap);
		model.addAttribute("counts", counts);
		String storeUrl = sysConfig.get(SysConfig.KEY_STORE_SERVER_URL);
		model.addAttribute("previewUrl", sysConfig.get("store.server.url")+"xinhu/web/temp/");
		model.addAttribute("storeUrl", storeUrl);
		return "xinhu/ent/personal-card";
	}

	/**
	 * 显示分享后的名片详情
	* @param id
	* @param model
	* @param request
	* @return
	* <p>
	* author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	* create at: 2015年7月17日 下午2:29:52
	 */
	@RequestMapping(value = "/shareCard", method = RequestMethod.GET)
	public String shareCard(@RequestParam("id") int id,  Model model, HttpServletRequest request) {
		CardInfo card = cardInfoService.getEmployeeInfo(id);
		model.addAttribute("card", card);
		EntInfo entInfo = null;
		if(card!=null && card.getEntId()!=null){
			entInfo = entInfoService.getEntInfo(card.getEntId());
		}
		String storeUrl = sysConfig.get(SysConfig.KEY_STORE_SERVER_URL);
		model.addAttribute("storeUrl", storeUrl);
		model.addAttribute("entInfo", entInfo);
		return "xinhu/ent/show-appcard";
	}
	/**
	 * 上传临时图片并返回图片地址
	* @param request
	* @param response
	* @return
	* <p>
	* author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	* create at: 2015年7月23日 下午2:04:28
	 * @throws FileNotFoundException 
	 */
	@RequestMapping(value = "/previewImage", method = RequestMethod.POST)
	public void previewImage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String value = cardInfoService.getImageTempUrl(request);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(value);
	}
}

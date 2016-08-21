package com.ancun.xinhu.web.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ancun.products.core.api.AncunApiResponse;
import com.ancun.products.core.config.SysConfig;
import com.ancun.products.core.model.PageResult;
import com.ancun.products.core.web.utils.CookieUtil;
import com.ancun.products.core.web.utils.HttpUtils;
import com.ancun.products.core.web.utils.WebContextUtils;
import com.ancun.xinhu.biz.mappers.EntDepartmentMapper;
import com.ancun.xinhu.biz.mappers.EntInfoMapper;
import com.ancun.xinhu.biz.mappers.EntPositionMapper;
import com.ancun.xinhu.biz.mappers.SysNoticeMapper;
import com.ancun.xinhu.biz.service.EntInfoService;
import com.ancun.xinhu.domain.dto.XinHuResponseCode;
import com.ancun.xinhu.domain.dto.XinHuUserAgent;
import com.ancun.xinhu.domain.model.EntDepartment;
import com.ancun.xinhu.domain.model.EntInfo;
import com.ancun.xinhu.domain.model.EntPosition;
import com.ancun.xinhu.domain.model.EntShield;
import com.ancun.xinhu.domain.model.SysNotice;
import com.ancun.xinhu.domain.query.EntDepartmentQuery;
import com.ancun.xinhu.domain.query.EntPositionQuery;
import com.ancun.xinhu.domain.query.EntShieldQuery;
import com.ancun.xinhu.domain.query.SysNoticeQuery;
import com.ancun.xinhu.web.session.XinHuUserAgentSession;

/**
 * 企业用户信息管理
 * 
 * <p>
 * create at 2015年6月5日 上午9:55:29
 * @author <a href="mailto:qiande@ancun.com">QianDe</a>
 * @version %I%, %G%
 * @see
 */
@Controller
public class EntInfoController {

	@Autowired
	private SysConfig sysConfig;
	@Autowired
	private EntInfoService entInfoService;
	@Autowired
	private EntPositionMapper entPositionMapper;
	@Autowired
	private EntDepartmentMapper entDepartmentMapper;
	@Autowired
	private XinHuUserAgentSession xinHuUserAgentSession;
	@Autowired
	private WebContextUtils webContextUtils;
	@Autowired
	private SysNoticeMapper sysNoticeMapper;
	@Autowired
	private EntInfoMapper entInfoMapper;
	
	private static final String COOKIE_KEY = "phone_code";
	/**
	 * 显示登录页面
	* @param entInfo
	* @param request
	* @return
	* <p>
	* author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	* create at: 2015年6月15日 上午10:20:16
	 */
	@RequestMapping(value = {"","/index"}, method = RequestMethod.GET)
	public String index(@ModelAttribute EntInfo entInfo,HttpServletRequest request,Model model) {
	//	xinHuUserAgentSession.remove(request.getSession().getId());
		XinHuUserAgent agent = xinHuUserAgentSession.get(request);
		if(agent != null && agent.isIfRemember()){
			entInfo.setMobile(agent.getMobile());
			getEntSystemInfo(entInfo,model);
			model.addAttribute("previewUrl", sysConfig.get("store.server.url")+"xinhu/web/temp/");
			return "xinhu/ent/system-notice";
		}else{
			return "xinhu/login/index";
		}
	}
	
	/**
	 * 跳到注册第一步
	* @param entInfo
	* @param request
	* @return
	* <p>
	* author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	* create at: 2015年6月15日 下午2:00:48
	 */
	@RequestMapping(value = "regist-basic", method = RequestMethod.GET)
	public String registBasic(@ModelAttribute EntInfo entInfo,HttpServletRequest request,Model model) {
		if(entInfo.getMobile()!=null){
			entInfo = entInfoService.getEntInfoByMobile(entInfo.getMobile());
			model.addAttribute("entInfo",entInfo);
			String oldCode = CookieUtil.read(COOKIE_KEY, request, "UTF-8");
			model.addAttribute("oldCode",oldCode);
		}
		return "xinhu/login/registration-basic-info";
	}
	
	/**
	 * 跳转到注册第二步
	* @param entInfo
	* @param request
	* @return
	* <p>
	* author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	* create at: 2015年6月15日 下午5:02:10
	 */
	@RequestMapping(value = "regist-second", method = RequestMethod.GET)
	public String registSecond(@ModelAttribute EntInfo entInfo,HttpServletRequest request,Model model) {
		entInfo = entInfoService.getEntInfoByMobile(entInfo.getMobile());
		model.addAttribute("entInfo",entInfo);
		if(entInfo.getOrgCode()==null || "".equals(entInfo.getOrgCode())){
			model.addAttribute("previewUrl", sysConfig.get("store.server.url")+"xinhu/web/temp/");
			return "xinhu/login/registration-shunt";
		}else{
			return "xinhu/login/index";
		}
	}
	
	/**
	 * 跳到第三步
	* @param entInfo
	* @param request
	* @param model
	* @return
	* <p>
	* author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	* create at: 2015年6月16日 下午12:42:21
	 */
	@RequestMapping(value = "regist-third", method = RequestMethod.GET)
	public String registThird(@ModelAttribute EntInfo entInfo,HttpServletRequest request,Model model) {
		entInfo = entInfoService.getEntInfoByMobile(entInfo.getMobile());
		model.addAttribute("entInfo",entInfo);
		entInfo.setIndustryList(entInfoService.getEntIndustryList(entInfo.getId()));
		//entInfo.setPaperList(entInfoService.getEntPaperList(entInfo.getId()));
		String storeUrl = sysConfig.get(SysConfig.KEY_STORE_SERVER_URL);
		model.addAttribute("storeUrl",storeUrl);
		return "xinhu/login/registration-final";
	}
	/**
	 * 跳转到找回密码页面
	* @param entInfo
	* @param request
	* @param model
	* @return
	* <p>
	* author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	* create at: 2015年7月1日 下午5:29:33
	 */
	@RequestMapping(value = "forget-password", method = RequestMethod.GET)
	public String forgetPassword(HttpServletRequest request) {
		return "xinhu/login/forget-password";
	}
	
	/**
	 * 发送密码到用户手机
	* @param mobile
	* @param request
	* @return
	* <p>
	* author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	* create at: 2015年7月1日 下午7:13:58
	 */
	@RequestMapping(value = "sendPwd", method = RequestMethod.GET)
	public String sendPwd(@RequestParam("mobile") String mobile ,HttpServletRequest request) {
		
		return "xinhu/login/forget-password";
	}
	
	
	
	/**
	 * 未登录跳转到系统公告
	* @param entInfo
	* @param request
	* @param model
	* @return
	* <p>
	* author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	* create at: 2015年6月16日 下午2:34:56
	 */
	@RequestMapping(value = "login_system-notice", method = RequestMethod.GET)
	public String logintoSystemNotice(@ModelAttribute EntInfo entInfo,HttpServletRequest request,Model model,HttpServletResponse response) {
		entInfo = getEntSystemInfo(entInfo,model);
		if(entInfo!=null){
			XinHuUserAgent xinHuUserAgent = new XinHuUserAgent();
			xinHuUserAgent.setId(entInfo.getId());
			xinHuUserAgent.setAccount(entInfo.getOrgName());
			xinHuUserAgent.setMobile(entInfo.getMobile());
			xinHuUserAgent.setIfRemember(false);
			xinHuUserAgentSession.save(request, xinHuUserAgent, response);
		}
		model.addAttribute("previewUrl", sysConfig.get("store.server.url")+"xinhu/web/temp/");
		return "xinhu/ent/system-notice";
	}
	
	/**
	 * 登录状态正常跳转到系统公告
	* @param entInfo
	* @param request
	* @param model
	* @return
	* <p>
	* author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	* create at: 2015年7月10日 下午4:42:11
	 */
	@RequestMapping(value = "system-notice", method = RequestMethod.GET)
	public String systemNotice(@ModelAttribute EntInfo entInfo,HttpServletRequest request,Model model) {
		XinHuUserAgent agent = xinHuUserAgentSession.get(request);
		if(agent==null)return "xinhu/login/index";
		entInfo.setMobile(agent.getMobile());
		getEntSystemInfo(entInfo,model);
		model.addAttribute("previewUrl", sysConfig.get("store.server.url")+"xinhu/web/temp/");
		return "xinhu/ent/system-notice";
	}
	
	//
	private EntInfo getEntSystemInfo(EntInfo entInfo, Model model){
		//企业信息
		entInfo = entInfoService.getEntInfoByMobile(entInfo.getMobile());
		model.addAttribute("entInfo",entInfo);
		//系统通知
		SysNoticeQuery sysNoticeQuery = new SysNoticeQuery();
		if (sysNoticeQuery.getPageSize() == 0) {
			sysNoticeQuery.setPageSize(10);
		}
		PageResult<SysNotice> sysNoticeList = entInfoService.getSysNoticeList(sysNoticeQuery);
		model.addAttribute("page",sysNoticeList);
		//花名册统计
		Map<String,Integer> count = entInfoService.getCardsCount(entInfo.getId());
		model.addAttribute("count",count);
		String storeUrl = sysConfig.get(SysConfig.KEY_STORE_SERVER_URL);
		model.addAttribute("storeUrl",storeUrl);
		return entInfo;
	}
	
	/**
	 * 跳转到标签管理页面
	* @param entInfo
	* @param request
	* @param model
	* @return
	* <p>
	* author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	* create at: 2015年6月17日 上午9:40:45
	 */
	@RequestMapping(value = "ent-tags", method = RequestMethod.GET)
	public String entTags(@ModelAttribute EntInfo entInfo,HttpServletRequest request,Model model) {
		int index = entInfo.getIndex();
		XinHuUserAgent xinhu = xinHuUserAgentSession.get(request);
		if(xinhu==null)return "xinhu/login/index";
		int id = xinhu.getId().intValue();
		//企业信息
		entInfo = entInfoService.getEntInfo(id);
		entInfo.setIndex(index);
		model.addAttribute("entInfo",entInfo);
		//职位集合
		EntPositionQuery entPositionQuery = new EntPositionQuery();
		entPositionQuery.setEntId(entInfo.getId());
		List<EntPosition> positionList = entPositionMapper.queryList(entPositionQuery);
		model.addAttribute("positionList",positionList);
		//部门集合
		EntDepartmentQuery entDepartmentQuery = new EntDepartmentQuery();
		entDepartmentQuery.setEntId(entInfo.getId());
		List<EntDepartment> deptList = entDepartmentMapper.queryList(entDepartmentQuery);
		model.addAttribute("deptList",deptList);
		//花名册统计
		Map<String,Integer> count = entInfoService.getCardsCount(entInfo.getId());
		model.addAttribute("count",count);
		String storeUrl = sysConfig.get(SysConfig.KEY_STORE_SERVER_URL);
		model.addAttribute("storeUrl",storeUrl);
		model.addAttribute("previewUrl", sysConfig.get("store.server.url")+"xinhu/web/temp/");
		return "xinhu/ent/ent-tags";
	}
	
	/**
	 * 跳转到公司基本信息
	* @param entInfo
	* @param request
	* @param model
	* @return
	* <p>
	* author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	* create at: 2015年6月18日 上午10:20:32
	 */
	@RequestMapping(value = "ent-basic-info", method = RequestMethod.GET)
	public String entBasicInfo(@ModelAttribute EntInfo entInfo,HttpServletRequest request,Model model) {
		XinHuUserAgent xinhu = xinHuUserAgentSession.get(request);
		if(xinhu==null)return "xinhu/login/index";
		int id = xinhu.getId().intValue();
		Integer index = entInfo.getIndex();
		boolean isclick = entInfo.isIfRemember();
		//企业信息
		entInfo = getEntInfo(id);
		model.addAttribute("entInfo",entInfo);
		String storeUrl = sysConfig.get(SysConfig.KEY_STORE_SERVER_URL);
		model.addAttribute("previewUrl", sysConfig.get("store.server.url")+"xinhu/web/temp/");
		model.addAttribute("storeUrl",storeUrl);
		model.addAttribute("index",index);
		model.addAttribute("isclick",isclick);
		return "xinhu/ent/ent-basic-info";
	}
	
	/**
	 * 注销
	* @param request
	* @param response
	* @return
	* <p>
	* author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	* create at: 2015年6月23日 下午2:23:52
	 */
	@RequestMapping(value = "logout", method = RequestMethod.POST)
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		// 设置退出之后系统跳转的地址
		//String url = webContextUtils.getSubSystemUrl(request, SubSystemService.KEY_LOGIN);
		xinHuUserAgentSession.remove(request,response);
		//ActionResult msg = ActionResult.create(url);
		return "xinhu/login/index";
	}
	
	
	
	/**
	 * 企业登录校验
	* @param entInfo
	* @param request
	* @return
	* <p>
	* author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	* create at: 2015年6月5日 上午11:18:16
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse login(@ModelAttribute EntInfo entInfo,HttpServletRequest request,HttpServletResponse response) {
		boolean ifRemember = entInfo.isIfRemember();
		if(null == entInfoMapper.checkMobile(entInfo)){
			return AncunApiResponse.create(XinHuResponseCode._120004.getCode(), XinHuResponseCode._120004.getMsg());
		}
		
		if(0 == entInfoMapper.accoutIfExist(entInfo)){
			return AncunApiResponse.create(XinHuResponseCode._120002.getCode(), XinHuResponseCode._120002.getMsg());
		}
		String clientAddr = HttpUtils.getRemoteAddr(request);// ip地址
		entInfo = entInfoService.getEntInfo(entInfo,clientAddr);
		if(entInfo!=null){
			XinHuUserAgent xinHuUserAgent = new XinHuUserAgent();
			xinHuUserAgent.setId(entInfo.getId());
			xinHuUserAgent.setAccount(entInfo.getOrgName());
			xinHuUserAgent.setMobile(entInfo.getMobile());
			xinHuUserAgent.setIfRemember(ifRemember);
			xinHuUserAgentSession.save(request, xinHuUserAgent, response);
		}
		return AncunApiResponse.create(entInfo);
	}
	
	/**
	 * 企业注册
	* @param entInfo
	* @param request
	* @return
	* <p>
	* author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	* create at: 2015年6月5日 下午12:16:35
	 */
	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	@ResponseBody
	public boolean regist(@Valid EntInfo entInfo,HttpServletRequest request) {
		entInfoService.registEnt(entInfo,request);
		if(entInfo!=null)
			return true;
		else
			return false;
		
	}
	
	/**
	 * 检验组织结构代码信息
	* @param entInfo
	* @param request
	* @return
	* <p>
	* author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	* create at: 2015年7月21日 下午3:17:12
	 */
	@RequestMapping(value = "/checkRegistInfo", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse checkRegistInfo(@ModelAttribute EntInfo entInfo,HttpServletRequest request) {
		int count = entInfoMapper.ifExistEnt(entInfo);
		if(count!=0)
			return AncunApiResponse.create(XinHuResponseCode._120009.getCode(), XinHuResponseCode._120009.getMsg());
		return AncunApiResponse.create(true);
	}
	
	
	
	/**
	 * 获取手机验证码
	* @param entInfo
	* @return
	* <p>
	* author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	* create at: 2015年6月5日 下午12:24:38
	 * @throws IOException 
	 */
	@RequestMapping(value = "/getCode", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse getCode(@ModelAttribute EntInfo entInfo, HttpServletResponse response) throws IOException{
		String code = entInfoService.getLoginCode(entInfo.getMobile(),response);
		return AncunApiResponse.create(code);
	}
	/**
	 * 找回密码
	* @param entInfo
	* @param response
	* @return
	* @throws IOException
	* <p>
	* author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	* create at: 2015年7月16日 上午9:36:19
	 */
	@RequestMapping(value = "/getUpdatePwdCode", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse getUpdatePwdCode(@ModelAttribute EntInfo entInfo, HttpServletResponse response) throws IOException{
		String code = entInfoService.getUpdatePwdCode(entInfo.getMobile(),response);
		return AncunApiResponse.create(code);
	}
	
	
	
	/**
	 * 验证手机号码唯一性
	* @param entInfo
	* @return
	* <p>
	* author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	* create at: 2015年6月5日 下午1:23:47
	 */
	@RequestMapping(value = "/checkMobile", method = RequestMethod.GET)
	@ResponseBody
	public AncunApiResponse checkMobile(@ModelAttribute EntInfo entInfo){
		Integer num = entInfoService.checkMobile(entInfo);
		return AncunApiResponse.create(num);//null则没有记录
	}
	
	/**
	 * 获取系统通知分页列表
	* @param sysNoticeQuery
	* @return
	* <p>
	* author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	* create at: 2015年6月5日 下午2:18:05
	 */
	@RequestMapping(value = "/getSysNoticeList", method = RequestMethod.GET)
	@ResponseBody
	public AncunApiResponse getSysNoticeList(@ModelAttribute SysNoticeQuery sysNoticeQuery){
		// 分页查询
		if (sysNoticeQuery.getPageSize() == 0) {
			sysNoticeQuery.setPageSize(10);
		}
		PageResult<SysNotice> sysNoticeList = entInfoService.getSysNoticeList(sysNoticeQuery);
		if(sysNoticeList != null){
			return AncunApiResponse.create(sysNoticeList);
		}else{
			return AncunApiResponse.create(false);
		}
		
	}
	
	/**
	 * 获取公司主体认证信息
	* @param entId
	* @return
	* <p>
	* author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	* create at: 2015年6月8日 下午5:49:36
	 */
	private EntInfo getEntInfo(int entId){
		EntInfo entInfo = entInfoService.getEntInfo(entId);
		entInfo.setIndustryList(entInfoService.getEntIndustryList(entId));
		//entInfo.setPaperList(entInfoService.getEntPaperList(entId));
		return entInfo;
		
	}
	
	/**
	 * 修改公司主体密码
	* @param entId
	* @param newPwd
	* @param oldPwd
	* @return
	* <p>
	* author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	* create at: 2015年6月9日 上午8:54:42
	 */
	@RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse updatePwd(HttpServletRequest request,@RequestParam("entId") int entId,@RequestParam("newPwd") String newPwd,
			@RequestParam("oldPwd") String oldPwd){
		EntInfo entInfo = new EntInfo();
		entInfo.setId(entId);
		entInfo.setPwd(oldPwd);
		int count = entInfoService.checkPwd(entInfo);
		if(count != 0){
			entInfo.setPwd(newPwd);
			entInfoService.updatePwd(entInfo);
			return AncunApiResponse.create(entInfo);
		}else{
			return AncunApiResponse.create(false);
		}
		
		
	}
	
	/**
	 * 修改手机号码
	* @param entInfo
	* @return
	* <p>
	* author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	* create at: 2015年6月9日 上午9:43:47
	 */
	@RequestMapping(value = "/updateMobile", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse updateMobile(@ModelAttribute EntInfo entInfo){
		entInfoService.updateMobile(entInfo);
		return AncunApiResponse.create(entInfo);
	}
	
	/**
	 * 获取修改手机号码时的验证码
	* @param entInfo
	* @return
	* <p>
	* author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	* create at: 2015年6月9日 上午9:48:56
	 * @throws IOException 
	 */
	@RequestMapping(value = "/getPhoneCode", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse getPhoneCode(@ModelAttribute EntInfo entInfo,HttpServletResponse response) throws IOException{
		String code = entInfoService.getCode(entInfo.getMobile(),response);
//		System.out.println("#########getCode:"+code);
		return AncunApiResponse.create(code);
	}
	
	/**
	 * 验证手机验证码
	* @param entInfo
	* @param response
	* @return
	* @throws IOException
	* <p>
	* author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	* create at: 2015年6月26日 下午4:59:35
	 */
	@RequestMapping(value = "/validatePhone", method = RequestMethod.GET)
	@ResponseBody
	public AncunApiResponse validatePhone(@RequestParam("code") String code, HttpServletRequest request) throws IOException{
		return AncunApiResponse.create(entInfoService.validatePhone(code,request));
	}
	
	
	
	/**
	 * 屏蔽公司或者员工
	* @param type
	* @param entId
	* @param name
	* @return
	* <p>
	* author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	* create at: 2015年6月9日 上午11:27:45
	 */
	@RequestMapping(value = "/addShieldUserOrEnt", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse addShieldUserOrEnt(@RequestParam("type") int type,@RequestParam("entId") int entId,@RequestParam("name") String name){
		Integer code = 0;
		if(type == 0)
			code = entInfoService.getEntIdByName(name);
		else
			code = entInfoService.getUserIdByName(name);
		if(code !=null){
			EntShield entShield = new EntShield();
			entShield.setEntId(entId);
			entShield.setShieldId(code);
			entShield.setShieldType(type+"");
			entInfoService.addShieldUserOrEnt(entShield);
			return AncunApiResponse.create(true);
		}else{
			return AncunApiResponse.create(false);
		}
	}
	
	/**
	 * 删除屏蔽了的公司或者员工
	* @param type
	* @param entId
	* @param id
	* @return
	* <p>
	* author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	* create at: 2015年6月9日 上午11:41:06
	 */
	@RequestMapping(value = "/deleteShieldUserOrEnt", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse deleteShieldUserOrEnt(@RequestParam("type") String type,@RequestParam("entId") int entId,@RequestParam("id") int id){
		EntShield entShield = new EntShield();
		entShield.setEntId(entId);
		entShield.setShieldId(id);
		entShield.setShieldType(type);
		entInfoService.deleteShieldUserOrEnt(entShield);
		return AncunApiResponse.create(true);
	}
	
	/**
	 * 获取屏蔽列表
	* @param entShieldQuery
	* @return
	* <p>
	* author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	* create at: 2015年6月12日 下午4:31:48
	 */
	@RequestMapping(value = "/getShieldList", method = RequestMethod.GET)
	@ResponseBody
	public AncunApiResponse getShieldList(@ModelAttribute EntShieldQuery entShieldQuery){
		// 分页查询
		if (entShieldQuery.getPageSize() == 0) {
			entShieldQuery.setPageSize(10);
		}
		PageResult<Map<String,Object>> shieldList = entInfoService.getShieldList(entShieldQuery);
		return AncunApiResponse.create(shieldList);
	}
	
	
	
	/**
	 * 增加公司职位标签
	* @param entPosition
	* @return
	* <p>
	* author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	* create at: 2015年6月9日 下午1:27:55
	 */
	@RequestMapping(value = "/addPositionLabel", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse addPositionLabel(@ModelAttribute EntPosition entPosition){
		boolean ifExist = entInfoService.addPositionLabel(entPosition);
		return AncunApiResponse.create(ifExist);
	}
	
	/**
	 * 删除职位标签
	* @param entPosition
	* @return
	* <p>
	* author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	* create at: 2015年6月9日 下午1:28:36
	 */
	@RequestMapping(value = "/deletePositionLabel", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse deletePositionLabel(@ModelAttribute EntPosition entPosition){
		boolean ifExist = entInfoService.deletePositionLabel(entPosition);
		return AncunApiResponse.create(ifExist);
	}
	
	/**
	 * 增加公司部门标签
	* @param entPosition
	* @return
	* <p>
	* author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	* create at: 2015年6月9日 下午1:30:06
	 */
	@RequestMapping(value = "/addDeptLabel", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse addDeptLabel(@ModelAttribute EntDepartment entDepartment){
		boolean ifExist = entInfoService.addDeptLabel(entDepartment);
		return AncunApiResponse.create(ifExist);
	}
	
	/**
	 * 删除部门标签
	* @param entDepartment
	* @return
	* <p>
	* author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	* create at: 2015年6月9日 下午1:32:47
	 */
	@RequestMapping(value = "/deleteDeptLabel", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse deleteDeptLabel(@ModelAttribute EntDepartment entDepartment){
		boolean ifExist = entInfoService.deleteDeptLabel(entDepartment);
		return AncunApiResponse.create(ifExist);
	}
	/**
	 * 获取通告详细信息
	* @param sysNotice
	* @param request
	* @param model
	* @return
	* <p>
	* author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	* create at: 2015年6月25日 下午7:38:06
	 */
	@RequestMapping(value = "news-info", method = RequestMethod.GET)
	public String newsInfo(@ModelAttribute SysNotice sysNotice,HttpServletRequest request,Model model) {
		//企业信息
		EntInfo entInfo = entInfoService.getEntInfo(sysNotice.getEntId());
		model.addAttribute("entInfo",entInfo);
		SysNotice result= sysNoticeMapper.load(sysNotice.getId());
		model.addAttribute("p",result);
		//花名册统计
		Map<String,Integer> count = entInfoService.getCardsCount(sysNotice.getEntId());
		model.addAttribute("count",count);
		String storeUrl = sysConfig.get(SysConfig.KEY_STORE_SERVER_URL);
		model.addAttribute("previewUrl", sysConfig.get("store.server.url")+"xinhu/web/temp/");
		model.addAttribute("storeUrl",storeUrl);
		return "xinhu/ent/news-info";
	}
	
	/**
	 * 修改部门标签
	* @param entDepartment
	* @return
	* <p>
	* author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	* create at: 2015年7月9日 下午6:44:17
	 */
	@RequestMapping(value = "/updateDeptLabel", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse updateDeptLabel(@ModelAttribute EntDepartment entDepartment){
		Integer code = entDepartmentMapper.queryCode(entDepartment);
		if(code!=null)return AncunApiResponse.create(false);
		entInfoService.updateDeptLabel(entDepartment);
		return AncunApiResponse.create(true);
	}
	
	/**
	 * 修改职位标签
	* @param entPosition
	* @return
	* <p>
	* author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	* create at: 2015年7月10日 上午8:40:07
	 */
	@RequestMapping(value = "/updatePositionLabel", method = RequestMethod.POST)
	@ResponseBody
	public AncunApiResponse updatePositionLabel(@ModelAttribute EntPosition entPosition){
		Integer code = entPositionMapper.queryCode(entPosition);
		if(code!=null)return AncunApiResponse.create(false);
		entInfoService.updatePositionLabel(entPosition);
		return AncunApiResponse.create(true);
	}
	
	/**
	 * 上传头像
	 * 
	 * @param cardInfo
	 * @param request
	 * @return <p>
	 *         author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	 *         create at: 2015年6月26日 上午9:01:18
	 */
	@RequestMapping(value = "/uploadIcon", method = RequestMethod.POST)
	@ResponseBody
	public String uploadIcon(@Valid EntInfo entInfo,HttpServletRequest request) {
		entInfoService.registEnt(entInfo,request);
		// sysConfig.get(SysConfig.KEY_STORE_DIR)
		return entInfo.getLogoUrl();
	}
	
	
}

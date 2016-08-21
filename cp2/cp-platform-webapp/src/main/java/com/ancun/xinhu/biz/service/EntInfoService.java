package com.ancun.xinhu.biz.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ancun.products.core.model.PageResult;
import com.ancun.xinhu.domain.model.EntDepartment;
import com.ancun.xinhu.domain.model.EntIndustryLink;
import com.ancun.xinhu.domain.model.EntInfo;
import com.ancun.xinhu.domain.model.EntPaper;
import com.ancun.xinhu.domain.model.EntPosition;
import com.ancun.xinhu.domain.model.EntShield;
import com.ancun.xinhu.domain.model.SysNotice;
import com.ancun.xinhu.domain.query.EntShieldQuery;
import com.ancun.xinhu.domain.query.SysNoticeQuery;

/**
 * 公司信息处理
 * 
 * <p>
 * create at 2015年6月5日 上午10:14:31
 * @author <a href="mailto:qiande@ancun.com">QianDe</a>
 * @version %I%, %G%
 * @see
 */
public interface EntInfoService {
	
	/**注册公司信息*/
	void registEnt(EntInfo entInfo,HttpServletRequest request);

	/**验证登录信息*/
	EntInfo getEntInfo(EntInfo entInfo, String clientAddr);
	
	/**公司注销*/
	void logoutEnt(EntInfo entInfo);

	/**获取更改手机绑定时的随机验证码*/
	String getCode(String mobile, HttpServletResponse response) throws IOException;
	
	/**获取登录手机随机验证码*/
	String getLoginCode(String mobile, HttpServletResponse response) throws IOException;

	/**验证手机号码是否已经存在*/
	Integer checkMobile(EntInfo entInfo);
	
	/**获取系统通知列表*/
	PageResult<SysNotice> getSysNoticeList(SysNoticeQuery sysNoticeQuery);
	
	/**获取基本信息*/
	EntInfo getEntInfo(int entId);
	
	/**获取行业信息*/
	List<EntIndustryLink> getEntIndustryList(int entId);
	
	/**获取证书信息*/
	List<EntPaper> getEntPaperList(int entId);

	/**验证公司密码*/
	int checkPwd(EntInfo entInfo);

	/**修改公司密码*/
	void updatePwd(EntInfo entInfo);

	/**修改手机号码*/
	void updateMobile(EntInfo entInfo);

	/**屏蔽用户或者企业*/
	void addShieldUserOrEnt(EntShield entShield);

	/**根据名称获取公司code*/
	Integer getEntIdByName(String name);
	
	/**根据名字获取员工code*/
	Integer getUserIdByName(String name);

	/**删除屏蔽了的公司或者员工*/
	void deleteShieldUserOrEnt(EntShield entShield);

	/**增加公司职位标签*/
	boolean addPositionLabel(EntPosition entPosition);

	/**删除公司职位标签*/
	boolean deletePositionLabel(EntPosition entPosition);

	/**增加公司部门标签*/
	boolean addDeptLabel(EntDepartment entDepartment);

	/**删除公司部门标签*/
	boolean deleteDeptLabel(EntDepartment entDepartment);

	/**获取公司屏蔽的公司或者用户*/
	PageResult<Map<String, Object>> getShieldList(EntShieldQuery entShieldQuery);

	/**根据手机号码获取公司信息*/
	EntInfo getEntInfoByMobile(String mobile);
	
	/**获得花名册总数和名片总数*/
	Map<String,Integer> getCardsCount(Integer entId);

	/**验证手机号码*/
	boolean validatePhone(String code, HttpServletRequest request);

	/**修改部门标签*/
	void updateDeptLabel(EntDepartment entDepartment);

	/**修改职位标签*/
	void updatePositionLabel(EntPosition entPosition);

	
	String getUpdatePwdCode(String mobile, HttpServletResponse response) throws IOException;
	
}

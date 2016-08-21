package com.ancun.xinhu.biz.service;

import java.io.FileNotFoundException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ancun.products.core.model.PageResult;
import com.ancun.xinhu.domain.model.CardInfo;
import com.ancun.xinhu.domain.query.CardInfoQuery;


/**
 * 花名管理
 * 
 * <p>
 * create at 2015年6月5日 下午2:35:09
 * @author <a href="mailto:qiande@ancun.com">QianDe</a>
 * @version %I%, %G%
 * @see
 */
public interface CardInfoService {
	
	/**新建员工
	 * @param request */
	void addNewEmployee(CardInfo cardInfo, HttpServletRequest request);
	
	/**更新员工*/
	void updateEmployee(CardInfo cardInfo,HttpServletRequest request);

	/**删除员工*/
	void deleteEmployee(CardInfo cardInfo);

	/**获取员工list 分页*/
	PageResult<CardInfo> getEmployeeList(CardInfoQuery cardInfoQuery);

	/**根据ID 获取员工所有信息*/
	CardInfo getEmployeeInfo(int id);

	/**根据ID 和 状态   修改员工账号状态*/
	void updateEmployeeStatus(int id, int status);

	/**批量插入员工信息*/
	Map<String,Object> exportEmployees(HttpServletRequest request);

	/**获取APP端注册的用户*/
	PageResult<CardInfo> getRegistUserList(CardInfoQuery cardInfoQuery);

	/**通过用户*/
	void passUser(CardInfo cardInfo);

	/**标记用户不通过*/
	void unpassUser(CardInfo cardInfo);

	/**获取名片交换记录*/
	PageResult<Map<String,Object>> getCardExchangingRecord(CardInfoQuery cardInfoQuery);

	/**获取单条交换记录*/
	Map<String, Object> getOneRecord(Map<String,Integer> paraMap);

	/**获取交换名片各种统计*/
	Map<String, Object> getExchangeCardInfo(Map<String,Object> paraMap);

	/**上传用户头像*/
	void uploadIcon(HttpServletRequest request);

	/**上传临时图片
	 * @throws FileNotFoundException 
	 * @throws Exception */
	String getImageTempUrl(HttpServletRequest request) throws  Exception;



}

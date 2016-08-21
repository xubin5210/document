package com.ancun.xinhu.biz.mappers;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;

import com.ancun.xinhu.domain.dto.XinHuQuery;
import com.ancun.xinhu.domain.model.SysOperationLog;
import com.ancun.xinhu.domain.query.SysOperationLogQuery;

public interface SysOperationLogMapper  {
	/***/
	SysOperationLog load(Integer id);

	/***/
	void insert(SysOperationLog sysOperationLog);

	/***/
	void update(SysOperationLog sysOperationLog);

	/***/
	void updateStatus(@Param("id") Integer id, @Param("status") Serializable status);

	/***/
	void delete(Integer id);

	/***/
	List<SysOperationLog> queryList(SysOperationLogQuery sysOperationLogQuery);

	/***/
	int queryCount(SysOperationLogQuery sysOperationLogQuery);
	
	/**所有交换名片信息更新动态列表接口*/
	List<HashMap<String,Object>> queryCardUpdateDynamicList(HashMap<String,Object> map);
	
	int queryCardUpdateDynamicCount(HashMap<String,Object> map);
	
	List<HashMap<String,Object>> getCardUpdateDynamicList(HashMap<String,Object> map); 
	
	/**单张名片信息更新动态列表接口*/
	HashMap<String,Object> getCardInfo(Integer cardId);
	
	/**批量插入*/
	void insertBatch(List<SysOperationLog> list);

	/**获取员工信息变动*/
	List<HashMap<String, Object>> getCardUpdateDynamicInfo(XinHuQuery xinHuQuery);
	
}
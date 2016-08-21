package com.ancun.xinhu.biz.mappers;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ancun.xinhu.domain.model.EntDepartment;
import com.ancun.xinhu.domain.query.EntDepartmentQuery;

public interface EntDepartmentMapper  {
	/***/
	EntDepartment load(Integer id);

	/***/
	void insert(EntDepartment entDepartment);

	/***/
	void update(EntDepartment entDepartment);

	/***/
	void updateStatus(@Param("id") Integer id, @Param("status") Serializable status);

	/***/
	void delete(Integer id);

	/***/
	List<EntDepartment> queryList(EntDepartmentQuery entDepartmentQuery);

	/***/
	int queryCount(EntDepartmentQuery entDepartmentQuery);

	/**根据职位名称获取职位编码*/
	Integer queryCode(EntDepartment entDepartment);
	
	/**获取用户名片部门列表*/
	List<EntDepartment> getUserCardDeptList(Integer cardId);
	
	/**获取用户名片职位列表*/
	List<EntDepartment> queryAllList(Integer id);

	/**查看部门是否被使用过*/
	Integer queryExistCount(Integer id);

}
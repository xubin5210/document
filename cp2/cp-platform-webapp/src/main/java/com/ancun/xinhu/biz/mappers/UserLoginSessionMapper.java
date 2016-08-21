package com.ancun.xinhu.biz.mappers;

import com.ancun.xinhu.domain.model.UserLoginSession;

public interface UserLoginSessionMapper {
	
	/***/
	UserLoginSession load(String tokenid);
	
	/***/
	void insert(UserLoginSession userLoginSession);

	/***/
	void update(UserLoginSession userLoginSession);

	/***/
	void delete(String tokenid);

}

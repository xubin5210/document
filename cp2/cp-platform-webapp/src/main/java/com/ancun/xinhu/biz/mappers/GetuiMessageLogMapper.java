package com.ancun.xinhu.biz.mappers;

import java.util.List;

import com.ancun.xinhu.domain.model.GetuiMessageLog;

public interface GetuiMessageLogMapper {
	
	/***/
	GetuiMessageLog load(String id);
	
	/***/
	void insert(GetuiMessageLog getuiMessageLog);

	/***/
	void update(GetuiMessageLog getuiMessageLog);
	
	/**修改消息读取*/
	void updateMessageRead(GetuiMessageLog getuiMessageLog);
	
	/**批量插入*/
	void insertBatch(List<GetuiMessageLog> getuiMessageLogList);
	
	/**获取名片个推消息列表*/
	List<GetuiMessageLog> getCardGetuiMessageList(Integer  cardId);
	
	/**修改名片个推消息列表状态*/
	void updateCardGetuiMessageListStatus(Integer cardId);


}

package com.ancun.xinhu.domain.dto;

import com.ancun.products.core.query.AbstractQueryParam;
import com.ancun.products.core.query.QueryParam;

/**
 * 信乎查询-专门供移动端查询相关使用
 * 
 * <p>
 * create at 2015年6月10日 下午3:47:18
 * @author <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a>
 * @version %I%, %G%
 * @see
 */
public class XinHuQuery extends AbstractQueryParam implements QueryParam{
	
	private String tokenid;//会话令牌
	
	private String content;//查询内容
	private String searchType;//查询类型
	private String searchTime;//查询时间
	private String controlSwitch;//查询控制位图
	
	private Integer id;//id
	private Integer cardId;//名片id
	private Integer entId;//企业id
	
	private String status;//状态
	
	
	public String getTokenid() {
		return tokenid;
	}
	public void setTokenid(String tokenid) {
		this.tokenid = tokenid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getSearchTime() {
		return searchTime;
	}
	public void setSearchTime(String searchTime) {
		this.searchTime = searchTime;
	}
	public String getControlSwitch() {
		return controlSwitch;
	}
	public void setControlSwitch(String controlSwitch) {
		this.controlSwitch = controlSwitch;
	}
	public Integer getCardId() {
		return cardId;
	}
	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}
	public Integer getEntId() {
		return entId;
	}
	public void setEntId(Integer entId) {
		this.entId = entId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
}

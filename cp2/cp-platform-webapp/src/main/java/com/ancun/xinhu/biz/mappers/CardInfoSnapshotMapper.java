package com.ancun.xinhu.biz.mappers;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ancun.xinhu.domain.dto.XinHuQuery;
import com.ancun.xinhu.domain.model.CardInfoSnapshot;
import com.ancun.xinhu.domain.query.CardInfoSnapshotQuery;

public interface CardInfoSnapshotMapper  {
	/***/
	CardInfoSnapshot load(Integer id);

	/***/
	void insert(CardInfoSnapshot cardInfoSnapshot);

	/***/
	void update(CardInfoSnapshot cardInfoSnapshot);

	/***/
	void updateStatus(@Param("id") Integer id, @Param("status") Serializable status);

	/***/
	void delete(Integer id);

	/***/
	List<CardInfoSnapshot> queryList(CardInfoSnapshotQuery cardInfoSnapshotQuery);

	/***/
	int queryCount(CardInfoSnapshotQuery cardInfoSnapshotQuery);
	
	/**新名片内，已经交换的名片数据获取接口*/
	List<CardInfoSnapshot> queryExchangeNewCardList(CardInfoSnapshotQuery cardInfoSnapshotQuery);
	
	int queryExchangeNewCardCount(CardInfoSnapshotQuery cardInfoSnapshotQuery);
	
	/**新名片内，已经交换的名片数据获取接口_单张名片*/
	CardInfoSnapshot queryExchangeNewCardOne(Integer id);
	
	/**新名片内，已经交换的名片的详细获取接口*/
	CardInfoSnapshot getUserCardInfo(Integer id);
	
	/**搜索新名片，分类，精确查找接口（分页)*/
	List<CardInfoSnapshot> queryNewCardList(XinHuQuery xinHuQuery);
	
	int queryNewCardCount(XinHuQuery xinHuQuery);
	
	/**搜索结果为 企业 加 个人 接口——搜索个人*/
	List<CardInfoSnapshot> queryClientCardList(XinHuQuery xinHuQuery);
	
	int queryClientCardCount(XinHuQuery xinHuQuery);
	
	/**获取企业名片上的部分人脉*/
	List<CardInfoSnapshot> getEntCardConnList(XinHuQuery xinHuQuery);
	
	/**企业下已绑定的名片获取接口*/
	List<CardInfoSnapshot> getEntExchangeCardList(XinHuQuery xinHuQuery);
	
	/**获取递交新名片接口*/
	CardInfoSnapshot getAcceptCardInfo(Integer id);
	
}
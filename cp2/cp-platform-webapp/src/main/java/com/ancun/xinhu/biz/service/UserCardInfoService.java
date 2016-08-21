package com.ancun.xinhu.biz.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ancun.products.core.model.PageResult;
import com.ancun.xinhu.domain.dto.XinHuQuery;
import com.ancun.xinhu.domain.dto.XinHuResponseCode;
import com.ancun.xinhu.domain.dto.XinHuUserAgent;
import com.ancun.xinhu.domain.model.CardApplyEnt;
import com.ancun.xinhu.domain.model.CardInfo;
import com.ancun.xinhu.domain.model.CardInfoSnapshot;
import com.ancun.xinhu.domain.model.CardReport;
import com.ancun.xinhu.domain.model.EntInfo;
import com.ancun.xinhu.domain.model.EntPosition;
import com.ancun.xinhu.domain.model.GetuiMessageLog;
import com.ancun.xinhu.domain.model.UserCardApply;
import com.ancun.xinhu.domain.model.UserCardLink;
import com.ancun.xinhu.domain.model.UserClientInfo;
import com.ancun.xinhu.domain.model.UserInfo;
import com.ancun.xinhu.domain.query.CardInfoSnapshotQuery;

/**
 * 用户名片service
 * 
 * <p>
 * create at 2015年6月8日 下午5:31:23
 * @author <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a>
 * @version %I%, %G%
 * @see
 */
public interface UserCardInfoService {
	
	/**
	 * 保存客户端信息
	* @param userClientInfo
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月8日 下午5:33:29
	 */
	boolean saveClientInfo(UserClientInfo userClientInfo);
	
	/**
	 * 个推消息推送——登录上报客户端信息后，查询当前用户是否有需要推送的消息
	* @param cardId
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年7月9日 上午9:04:15
	 */
	boolean geTuiPushMessage(Integer cardId);
	
	/**
	 * 验证旧密码
	* @param userInfo
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月8日 下午5:36:39
	 */
	boolean validateOldPwd(UserInfo userInfo);
	
	/**
	 * 修改密码
	* @param userInfo
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月8日 下午5:37:39
	 */
	boolean updatePwd(UserInfo userInfo);
	
	
	/**
	 * 新名片接受
	* @param applyId
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月9日 下午3:28:00
	 */
	boolean acceptNewCard(int applyId,HttpServletRequest request);
	
	/**
	 * 新名片内，已经交换的名片数据获取接口
	* @param query
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月9日 下午4:40:13
	 */
	PageResult<CardInfoSnapshot> getExchangeNewCardList(CardInfoSnapshotQuery query,HttpServletRequest request);
	
	/**
	 * 新名片内，已经交换的名片的详细获取接口
	* @param cardInfoSnapshot
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月10日 上午10:31:43
	 */
	CardInfoSnapshot getUserCardInfo(CardInfoSnapshot cardInfoSnapshot,HttpServletRequest request,Integer cardIdFrom);
	
	/**
	 * 名片关系是否已存在
	* @param userCardLink
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月10日 下午2:29:52
	 */
	boolean isExistsUserCardLink(UserCardLink userCardLink);
	
	/**
	 * 二维码扫描交换名片接口（添加新名片）
	* @param userCardApply
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月10日 下午2:21:10
	 */
	boolean qrcardExchangeCard(UserCardApply userCardApply,HttpServletRequest request);
	
	/**
	 * 搜索新名片，分类，精确查找接口（分页)
	* @param query
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月10日 下午3:55:33
	 */
	PageResult<CardInfoSnapshot> searchNewCardList(XinHuQuery query,HttpServletRequest request);
	
	/**
	 * 递交新名片接口
	* @param userCardApply
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月10日 下午5:55:21
	 */
	XinHuResponseCode deliverNewCard(UserCardApply userCardApply);
	
	/**
	 * 搜索结果为 企业 加 个人 接口
	* @param query
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月11日 上午8:57:18
	 */
	HashMap<String,Object> searchCardList(XinHuQuery query,HttpServletRequest request);
	
	/**
	 * 搜索结果为 企业 加 个人 接口——搜索企业
	* @param query
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月11日 上午8:57:25
	 */
	PageResult<EntInfo> searchEntCardList(XinHuQuery query);
	
	/**
	 * 搜索结果为 企业 加 个人 接口——搜索个人
	* @param query
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月11日 上午8:57:33
	 */
	PageResult<CardInfoSnapshot> searchClientCardList(XinHuQuery query);
	
	/**
	 * 企业名片详情获取接口
	* @param entInfo
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月11日 上午10:30:57
	 */
	EntInfo getEntCardInfo(XinHuQuery xinHuQuery,HttpServletRequest request);
	
	/**
	 * 企业下已绑定的名片获取接口
	* @param xinHuQuery
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月11日 上午11:15:38
	 */
	List<CardInfoSnapshot> getEntExchangeCardList(XinHuQuery xinHuQuery,HttpServletRequest request);
	
	/**
	 * 个人信息获取接口
	* @param cardInfoSnapshot
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月10日 上午10:31:43
	 */
	CardInfoSnapshot getSelfCardInfo(CardInfoSnapshot cardInfoSnapshot,HttpServletRequest request);
	
	/**
	 * 个人信息修改接口
	* @param cardInfo
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月11日 下午2:13:10
	 */
	boolean updateUserCardInfo(CardInfo cardInfo,HttpServletRequest request);
	
	/**
	 * 图片上传接口
	* @param userAgent
	* @param request
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月19日 上午9:52:43
	 */
	String uploadUserIcon(XinHuUserAgent userAgent,HttpServletRequest request);
	
	/**
	 * 搜索已经注册的企业接口
	* @param xinHuQuery
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月11日 下午2:59:29
	 */
	List<EntInfo> searchEntList(XinHuQuery xinHuQuery);
	
	/**
	 * 获取搜索企业详情接口
	* @param xinHuQuery
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月11日 下午3:31:29
	 */
	EntInfo getSearchEntCardInfo(XinHuQuery xinHuQuery);
	
	/**
	 * 申请绑定企业接口
	* @param cardApplyEnt
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月12日 下午3:43:58
	 */
	boolean applyBindEnt(CardApplyEnt cardApplyEnt);
	
	/**
	 * 人脉统计接口
	* @param cardId
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月12日 下午4:09:56
	 */
	HashMap<String,Object> getConnStatisticalInfo(Integer cardId);
	
	/**
	 * 搜索企业部门的接口
	* @param xinHuQuery
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月12日 下午4:59:13
	 */
	List<HashMap<String,Object>> getEntDeptList(XinHuQuery xinHuQuery);
	
	/**
	 *  搜索企业通讯录下员工信息的接口
	* @param xinHuQuery
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月12日 下午5:30:31
	 */
	PageResult<HashMap<String,Object>> getEntStaffList(XinHuQuery xinHuQuery,HttpServletRequest request);
	
	
	/**
	 * 根据部门号搜索企业个人的接口
	* @param xinHuQuery
	* @param request
	* @return
	* <p>
	* author: <a href="mailto:caozhenfei@ancun.com">Dim.Cao</a><br>
	* create at 2015年7月16日 上午10:39:10
	 */
	PageResult<HashMap<String,Object>> queryEntStaffListByDept(XinHuQuery xinHuQuery,HttpServletRequest request);
	
	/**
	 * 主页 搜索结果个人加部门的接口
	* @param query
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月15日 上午10:39:18
	 */
	public HashMap<String,Object> searchEntInnerCardList(XinHuQuery query,HttpServletRequest request);
	
	/**
	 * 删除名片接口
	* @param userCardApply
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月15日 上午11:01:55
	 */
	boolean deleteCard(UserCardApply userCardApply);
	
	/**
	 * 举报名片接口
	* @param cardReport
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月15日 下午1:52:00
	 */
	boolean reportCard(CardReport cardReport);
	
	/**
	 * 隐私接口（禁止通关手机号或名片号等搜索到）
	* @param xinHuQuery
	* @param type
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月15日 下午3:34:17
	 */
	boolean setIsEnableSearch(int cardId,HttpServletRequest request);
//	boolean setIsEnableSearch(XinHuQuery xinHuQuery,String type);
	
	/**
	 * 绑定新手机接口
	* @param userId
	* @param cardId
	* @param mobile
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月15日 下午4:02:55
	 */
	boolean bindNewMobile(int userId,int cardId,String mobile);
	
	/**
	 * 消息已读接口
	* @param userId
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月15日 下午4:57:36
	 */
	boolean updateMessageReadTime(int userId);
	
	/**
	 * 推荐名片接口（交换名片搜索没有内容时调用）
	* @param cardId
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月15日 下午5:07:41
	 */
	List<HashMap<String,String>> getRecommendCardList(XinHuQuery xinHuQuery,HttpServletRequest request);
	
	/**
	 * 所有交换名片信息更新动态列表接口
	* @param xinHuQuery
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月16日 下午4:43:46
	 */
	PageResult<HashMap<String,Object>> getCardUpdateDynamicList(XinHuQuery xinHuQuery,HttpServletRequest request);
	
	/**
	 * 单张名片信息更新动态列表接口
	* @param xinHuQuery
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月17日 下午4:50:52
	 */
	HashMap<String,Object> getCardUpdateDynamicInfo(XinHuQuery xinHuQuery,HttpServletRequest request);
	
	/**
	 * 个推消息读取通知接口
	* @param id
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年7月5日 上午10:48:20
	 */
	boolean getuiMessageReadNotice(GetuiMessageLog getuiMessageLog);
	
	/**
	 * 获取企业职位列表
	* @param entId
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年7月5日 上午11:12:59
	 */
	String getEntPositionList(Integer entId);
	
	/**
	 * 用户首次注册登录时，如果该用户的名片信息，已经被企业维护，那么他可以取消，从而重新绑定企业
	* @param cardId
	* <p>
	* author: xyy
	* create at: 2014年4月16日上午12:34:54
	 */
	
	
	/**
	 * 用户首次注册登录时，如果该用户的名片信息，已经被企业维护，那么他可以取消，从而重新绑定企业,或者确认
	* @param cardId 名片ID
	* @param type   0 取消  1 确认
	* <p>
	* author: xyy
	* create at: 2014年4月16日上午12:34:54
	 */
	void denyOrAffirmEntBand(Integer cardId, Integer type);

}

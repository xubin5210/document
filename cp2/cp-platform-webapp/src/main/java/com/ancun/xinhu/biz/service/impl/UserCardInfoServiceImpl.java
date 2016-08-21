package com.ancun.xinhu.biz.service.impl;

import java.io.File;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ancun.products.basebiz.service.StoreService;
import com.ancun.products.core.exception.ServiceException;
import com.ancun.products.core.model.PageResult;
import com.ancun.products.core.utils.PathUtils;
import com.ancun.products.core.utils.StringUtils;
import com.ancun.products.core.web.utils.WebContextUtils;
import com.ancun.xinhu.biz.mappers.CardApplyEntMapper;
import com.ancun.xinhu.biz.mappers.CardDeptLinkMapper;
import com.ancun.xinhu.biz.mappers.CardInfoMapper;
import com.ancun.xinhu.biz.mappers.CardInfoSnapshotMapper;
import com.ancun.xinhu.biz.mappers.CardPositionLinkMapper;
import com.ancun.xinhu.biz.mappers.CardReportMapper;
import com.ancun.xinhu.biz.mappers.EntDepartmentMapper;
import com.ancun.xinhu.biz.mappers.EntInfoMapper;
import com.ancun.xinhu.biz.mappers.EntPositionMapper;
import com.ancun.xinhu.biz.mappers.GetuiMessageLogMapper;
import com.ancun.xinhu.biz.mappers.SysOperationLogMapper;
import com.ancun.xinhu.biz.mappers.UserCardApplyMapper;
import com.ancun.xinhu.biz.mappers.UserCardLinkMapper;
import com.ancun.xinhu.biz.mappers.UserClientInfoMapper;
import com.ancun.xinhu.biz.mappers.UserInfoMapper;
import com.ancun.xinhu.biz.service.GeTuiService;
import com.ancun.xinhu.biz.service.UserCardInfoService;
import com.ancun.xinhu.config.XinHuConfig;
import com.ancun.xinhu.domain.dto.XinHuQuery;
import com.ancun.xinhu.domain.dto.XinHuResponseCode;
import com.ancun.xinhu.domain.dto.XinHuUserAgent;
import com.ancun.xinhu.domain.model.CardApplyEnt;
import com.ancun.xinhu.domain.model.CardDeptLink;
import com.ancun.xinhu.domain.model.CardInfo;
import com.ancun.xinhu.domain.model.CardInfoSnapshot;
import com.ancun.xinhu.domain.model.CardPositionLink;
import com.ancun.xinhu.domain.model.CardReport;
import com.ancun.xinhu.domain.model.EntDepartment;
import com.ancun.xinhu.domain.model.EntInfo;
import com.ancun.xinhu.domain.model.EntPosition;
import com.ancun.xinhu.domain.model.GetuiMessageLog;
import com.ancun.xinhu.domain.model.SysOperationLog;
import com.ancun.xinhu.domain.model.UserCardApply;
import com.ancun.xinhu.domain.model.UserCardLink;
import com.ancun.xinhu.domain.model.UserClientInfo;
import com.ancun.xinhu.domain.model.UserInfo;
import com.ancun.xinhu.domain.query.CardInfoSnapshotQuery;
import com.ancun.xinhu.domain.query.UserCardApplyQuery;
import com.ancun.xinhu.util.GeTuiUtils;
import com.ancun.xinhu.util.PinyinUtils;
import com.ancun.xinhu.web.session.XinHuUserAgentSession;

/**
 * 用户名片service实现
 * 
 * <p>
 * create at 2015年6月8日 下午5:31:59
 * 
 * @author <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a>
 * @version %I%, %G%
 * @see
 */
@Service
public class UserCardInfoServiceImpl implements UserCardInfoService {

	private static final String PREFIX = "xinhu";

	@Autowired
	private XinHuUserAgentSession xinHuUserAgentSession;

	@Autowired
	private UserClientInfoMapper userClientInfoMapper;

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Autowired
	private UserCardApplyMapper userCardApplyMapper;

	@Autowired
	private UserCardLinkMapper userCardLinkMapper;

	@Autowired
	private CardInfoMapper cardInfoMapper;

	@Autowired
	private CardInfoSnapshotMapper cardInfoSnapshotMapper;

	@Autowired
	private EntPositionMapper entPositionMapper;

	@Autowired
	private EntDepartmentMapper entDepartmentMapper;

	@Autowired
	private EntInfoMapper entInfoMapper;

	@Autowired
	private CardApplyEntMapper cardApplyEntMapper;

	@Autowired
	private CardDeptLinkMapper cardDeptLinkMapper;

	@Autowired
	private CardReportMapper cardReportMapper;

	@Autowired
	private SysOperationLogMapper sysOperationLogMapper;

	@Autowired
	private CardPositionLinkMapper cardPositionLinkMapper;

	@Autowired
	private StoreService storeService;

	@Autowired
	private WebContextUtils webContextUtils;

	@Autowired
	private GeTuiService geTuiService;

	@Autowired
	private GetuiMessageLogMapper getuiMessageLogMapper;

	/**
	 * 保存客户端信息
	 */
	public boolean saveClientInfo(UserClientInfo userClientInfo) {
		if (userClientInfoMapper.load(userClientInfo) == null) {
			userClientInfoMapper.insert(userClientInfo);
		} else {
			//通知原手机下线
			UserInfo userInfo = new UserInfo();
			userInfo.setId(userClientInfo.getUserId());
			userInfo.setClientId(userClientInfo.getClientId());
			geTuiService.transmissionTemplate(userInfo, GeTuiUtils.CODE_LoginOtherMobile, "{userId:"+userClientInfo.getUserId()+"}");
			
			userClientInfoMapper.update(userClientInfo);
		}
		UserInfo userInfo = new UserInfo();
		userInfo.setClientId(userClientInfo.getClientId());
		userInfo.setId(userClientInfo.getUserId());
		userInfoMapper.update(userInfo);
		return true;
	}

	/**
	 * 个推消息推送——登录上报客户端信息后，查询当前用户是否有需要推送的消息
	 * 
	 * @param cardId
	 * @return <p>
	 *         author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	 *         create at: 2015年7月9日 上午9:04:15
	 */
	public boolean geTuiPushMessage(Integer cardId) {
		List<GetuiMessageLog> list = getuiMessageLogMapper.getCardGetuiMessageList(cardId);
		if (list != null && list.size() > 0) {
			getuiMessageLogMapper.updateCardGetuiMessageListStatus(cardId);

			for (int i = 0; i < list.size(); i++) {
				geTuiService.transmissionTemplateOnlyPush(list.get(i).getClientId(), list.get(i).getMessage());
			}

		}
		return true;
	}

	/**
	 * 验证旧密码
	 */
	public boolean validateOldPwd(UserInfo userInfo) {
		Long id = xinHuUserAgentSession.get(userInfo.getTokenid()).getId();
		userInfo.setId(id.intValue());

		if (userInfoMapper.getUserInfoById(userInfo).getPwd().equals(userInfo.getPwd())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 修改密码
	 */
	public boolean updatePwd(UserInfo userInfo) {
		userInfoMapper.updatePwd(userInfo);
		return true;
	}

	/**
	 * 新名片接受
	 */
	public boolean acceptNewCard(int applyId, HttpServletRequest request) {
		UserCardApply uca = userCardApplyMapper.load(applyId);
		UserCardLink ucl = new UserCardLink();
		ucl.setCardId(uca.getCardId());
		ucl.setCardIdFrom(uca.getCardIdFrom());

		boolean b = isExistsUserCardLink(ucl);
		UserCardApply userCardApply = new UserCardApply();

		if (!b) {
			userCardApply = uca;
			UserCardLink userCardLink = new UserCardLink();
			userCardLink.setApplyId(applyId);
			userCardLink.setCardIdFrom(userCardApply.getCardIdFrom());
			userCardLink.setCardId(userCardApply.getCardId());
			userCardLinkMapper.insert(userCardLink);

		} else {
			userCardApply.setCardId(uca.getCardId());
			userCardApply.setCardIdFrom(uca.getCardIdFrom());
		}

		userCardApply.setIsExchange("1");
		// 修改状态
		userCardApplyMapper.updateExchangeStatus(userCardApply);

		// 给名片递交人发送名片接收消息
		// 递交新名片通知对方：code:2 data{} 姓名
		UserInfo userInfo = userInfoMapper.getClientIdByCardId(userCardApply.getCardIdFrom());
		CardInfoSnapshot cardInfoSnapshot = cardInfoSnapshotMapper.getAcceptCardInfo(userCardApply.getCardId());

		// 添加交换时间
		UserCardApplyQuery userCardApplyQuery = new UserCardApplyQuery();
		userCardApplyQuery.setCardId(cardInfoSnapshot.getCardId());
		userCardApplyQuery.setCardIdFrom(uca.getCardIdFrom());
		userCardApplyQuery.setIsDelete(0);
		List<UserCardApply> list = userCardApplyMapper.queryList(userCardApplyQuery);
		if (list.size() > 0) {
			cardInfoSnapshot.setGmtExchange(list.get(0).getGmtExchange());
		}

		cardInfoSnapshot.setIconUrl(getFileUrl(cardInfoSnapshot.getIconUrl(), request));
		geTuiService.transmissionTemplate(userInfo, GeTuiUtils.CODE_AcceptNewCard, cardInfoSnapshot);

		return true;
	}

	/**
	 * 新名片内，已经交换的名片数据获取接口
	 */
	public PageResult<CardInfoSnapshot> getExchangeNewCardList(CardInfoSnapshotQuery query, HttpServletRequest request) {
		int count = cardInfoSnapshotMapper.queryExchangeNewCardCount(query);
		List<CardInfoSnapshot> list = cardInfoSnapshotMapper.queryExchangeNewCardList(query);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setIconUrl(getFileUrl(list.get(i).getIconUrl(), request));
		}
		return PageResult.create(query, list, count);
	}

	/**
	 * 新名片内，已经交换的名片的详细获取接口
	 */
	public CardInfoSnapshot getUserCardInfo(CardInfoSnapshot cardInfoSnapshot, HttpServletRequest request,
			Integer cardIdFrom) {
		cardInfoSnapshot = cardInfoSnapshotMapper.getUserCardInfo(cardInfoSnapshot.getId());
		int cardId = cardInfoSnapshot.getId();
		cardInfoSnapshot.setDepartmentList(entDepartmentMapper.getUserCardDeptList(cardId));
		cardInfoSnapshot.setPositionList(entPositionMapper.getUserCardPositionList(cardId));
		cardInfoSnapshot.setIconUrl(getFileUrl(cardInfoSnapshot.getIconUrl(), request));

		if (cardIdFrom != null && cardIdFrom > 0) {
			UserCardLink userCardLink = new UserCardLink();
			userCardLink.setCardId(cardId);
			userCardLink.setCardIdFrom(cardIdFrom);
			cardInfoSnapshot.setIsExchange(isExistsUserCardLink(userCardLink) ? "1" : "0");
		}
		return cardInfoSnapshot;
	}

	/**
	 * 名片关系是否已存在
	 */
	public boolean isExistsUserCardLink(UserCardLink userCardLink) {
		int num = userCardLinkMapper.getTwoCardIsExistsLink(userCardLink);
		return num == 0 ? false : true;
	}

	/**
	 * 二维码扫描交换名片接口（添加新名片）
	 */
	public boolean qrcardExchangeCard(UserCardApply userCardApply, HttpServletRequest request) {
		// 插入名片申请表
		userCardApply.setIsExchange("1");
		userCardApplyMapper.insert(userCardApply);

		userCardApply = userCardApplyMapper.getUserCardApplyByTwoCardId(userCardApply);

		// 插入到用户名片关系表中
		UserCardLink userCardLink = new UserCardLink();
		userCardLink.setApplyId(userCardApply.getId());
		userCardLink.setCardIdFrom(userCardApply.getCardIdFrom());
		userCardLink.setCardId(userCardApply.getCardId());
		userCardLinkMapper.insert(userCardLink);

		// 递交新名片通知对方：code:2 data{} 姓名
		UserInfo userInfo = userInfoMapper.getClientIdByCardId(userCardApply.getCardId());
		CardInfoSnapshot cardInfoSnapshot = cardInfoSnapshotMapper.getAcceptCardInfo(userCardApply.getCardId());
		cardInfoSnapshot.setIconUrl(getFileUrl(cardInfoSnapshot.getIconUrl(), request));
		// String content =
		// GeTuiUtils.getJson(GeTuiUtils.CODE_AcceptNewCard,cardInfoSnapshot);
		geTuiService.transmissionTemplate(userInfo, GeTuiUtils.CODE_AcceptNewCard, cardInfoSnapshot);
		return true;
	}

	/**
	 * 搜索新名片，分类，精确查找接口（分页)
	 */
	public PageResult<CardInfoSnapshot> searchNewCardList(XinHuQuery query, HttpServletRequest request) {
		// 查询控制开关索引
		int scsIndex = XinHuConfig.getSearchControlSwitchIndex(query.getSearchType());
		query.setControlSwitch(Integer.toString(scsIndex));

		List<CardInfoSnapshot> list = cardInfoSnapshotMapper.queryNewCardList(query);

		for (int i = 0; i < list.size(); i++) {
			list.get(i).setIconUrl(getFileUrl(list.get(i).getIconUrl(), request));
		}

		int count = cardInfoSnapshotMapper.queryNewCardCount(query);

		return PageResult.create(query, list, count);
	}

	/**
	 * 递交新名片接口
	 */
	public XinHuResponseCode deliverNewCard(UserCardApply userCardApply) {
		// 不可以递交给自己
		if (userCardApply.getCardId().equals(userCardApply.getCardIdFrom())) {
			return XinHuResponseCode._120005;
		}

		// 已经是好友关系
		UserCardLink userCardLink = new UserCardLink();
		userCardLink.setCardId(userCardApply.getCardId());
		userCardLink.setCardIdFrom(userCardApply.getCardIdFrom());
		int count = userCardLinkMapper.getTwoCardIsExistsLink(userCardLink);
		if (count > 0) {
			return XinHuResponseCode._120006;
		}

		// 已经递交名片（还未通过）
		userCardApply.setIsExchange("0");
		UserCardApply temp = userCardApplyMapper.getUserCardApplyByTwoCardId(userCardApply);
		if (temp != null) {
			if (temp.getCardId() == userCardApply.getCardId()) {
				return XinHuResponseCode._120007;
			} else {
				return XinHuResponseCode._120008;
			}
		}
		
		//2015/7/20 XYY 逻辑变更， 如果已经该条数据被删除过，那么更新该条数据
		UserCardApplyQuery userCardApplyQuery = new UserCardApplyQuery();
		userCardApplyQuery.setCardId(userCardApply.getCardId());
		userCardApplyQuery.setCardIdFrom(userCardApply.getCardIdFrom());
		userCardApplyQuery.setIsExchange("1");
		List<UserCardApply> list = userCardApplyMapper.queryList(userCardApplyQuery);
		if(list.size()>0){//更新
			UserCardApply ucaUpdate = new UserCardApply();
			ucaUpdate.setId(list.get(0).getId());
			ucaUpdate.setIsExchange("0");
			ucaUpdate.setIsDelete(0);
			ucaUpdate.setGmtApply(new Date());
			userCardApplyMapper.update(ucaUpdate);
		}else{ //插入数据
			userCardApply.setIsExchange("0");
			userCardApplyMapper.insert(userCardApply);
		}
		

		// 递交新名片通知对方：code:1 data{} 姓名

		UserInfo userInfo = userInfoMapper.getClientIdByCardId(userCardApply.getCardId());
		// CardInfo cardInfo = cardInfoMapper.load(userCardApply.getCardId());
		CardInfoSnapshot cardInfoSnapshot = cardInfoSnapshotMapper.getUserCardInfo(userCardApply.getCardIdFrom());
		// 要用自己的名字 XYY
		geTuiService.transmissionTemplate(userInfo, GeTuiUtils.CODE_DeliverNewCard, cardInfoSnapshot.getTrueName());

		return XinHuResponseCode._1000000;
	}

	/**
	 * 搜索结果为 企业 加 个人 接口
	 */
	public HashMap<String, Object> searchCardList(XinHuQuery query, HttpServletRequest request) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		int recordNum = 4;
		query.setPageSize(recordNum + 1);
		List<EntInfo> entList = entInfoMapper.queryEntCardList(query);
		if (entList.size() > recordNum) {
			map.put("isMoreEnt", 1);
			entList.remove(recordNum);
		} else {
			map.put("isMoreEnt", 0);
		}
		map.put("entList", entList);

		List<CardInfoSnapshot> clientList = cardInfoSnapshotMapper.queryClientCardList(query);
		if (clientList.size() > recordNum) {
			map.put("isMoreClient", 1);
			clientList.remove(recordNum);
		} else {
			map.put("isMoreClient", 0);
		}

		for (int i = 0; i < clientList.size(); i++) {
			clientList.get(i).setIconUrl(getFileUrl(clientList.get(i).getIconUrl(), request));
		}

		map.put("clientList", clientList);
		return map;
	}

	/**
	 * 搜索结果为 企业 加 个人 接口——搜索企业
	 */
	public PageResult<EntInfo> searchEntCardList(XinHuQuery query) {
		int count = entInfoMapper.queryEntCardCount(query);
		List<EntInfo> list = entInfoMapper.queryEntCardList(query);
		return PageResult.create(query, list, count);
	}

	/**
	 * 搜索结果为 企业 加 个人 接口——搜索个人
	 */
	public PageResult<CardInfoSnapshot> searchClientCardList(XinHuQuery query) {
		int count = cardInfoSnapshotMapper.queryClientCardCount(query);
		List<CardInfoSnapshot> list = cardInfoSnapshotMapper.queryClientCardList(query);
		return PageResult.create(query, list, count);
	}

	/**
	 * 企业名片详情获取接口
	 */
	public EntInfo getEntCardInfo(XinHuQuery xinHuQuery, HttpServletRequest request) {
		EntInfo entInfo = entInfoMapper.getEntCardInfo(xinHuQuery);
		String weiXinUrl = entInfo.getWeiXinUrl();
		if (StringUtils.isNotBlank(weiXinUrl)) {
			entInfo.setWeiXinUrl(getFileUrl(weiXinUrl, request));
		}

		String logoUrl = entInfo.getLogoUrl();
		if (StringUtils.isNotBlank(logoUrl)) {
			entInfo.setLogoUrl(getFileUrl(logoUrl, request));
		}

		if (entInfo != null) {
			List<CardInfoSnapshot> connList = cardInfoSnapshotMapper.getEntCardConnList(xinHuQuery);
			for (int i = 0; i < connList.size(); i++) {
				if (connList.get(i) != null && connList.get(i).getIconUrl() != null
						&& !"".equals(connList.get(i).getIconUrl().trim())) {
					connList.get(i).setIconUrl(getFileUrl(connList.get(i).getIconUrl(), request));
				} else {
					if (connList.get(i) == null) {
						connList.set(i, new CardInfoSnapshot());
					}
					// XYY 设置默认头像图片
					connList.get(i).setIconUrl(getFileUrl("xinhu/client/toux_default.png", request));
				}
			}
			entInfo.setConnList(connList);
		}
		return entInfo;
	}

	/**
	 * 企业下已绑定的名片获取接口
	 */
	public List<CardInfoSnapshot> getEntExchangeCardList(XinHuQuery xinHuQuery, HttpServletRequest request) {
		List<CardInfoSnapshot> list = cardInfoSnapshotMapper.getEntExchangeCardList(xinHuQuery);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setIconUrl(getFileUrl(list.get(i).getIconUrl(), request));
		}
		return list;
	}

	/**
	 * 个人信息获取接口
	 */
	public CardInfoSnapshot getSelfCardInfo(CardInfoSnapshot cardInfoSnapshot, HttpServletRequest request) {
		cardInfoSnapshot = cardInfoSnapshotMapper.getUserCardInfo(cardInfoSnapshot.getId());
		int cardId = cardInfoSnapshot.getId();
		cardInfoSnapshot.setDepartmentList(entDepartmentMapper.getUserCardDeptList(cardId));
		cardInfoSnapshot.setPositionList(entPositionMapper.getUserCardPositionList(cardId));
		cardInfoSnapshot.setIconUrl(getFileUrl(cardInfoSnapshot.getIconUrl(), request));

		HashMap<String, Object> caeMap = cardApplyEntMapper.getCardApplyEntNoAudit(cardId);
		if (caeMap != null) {
			cardInfoSnapshot.setOrgName((String) caeMap.get("org_name"));
			// cardInfoSnapshot.setEntId((Integer)caeMap.get("ent_id"));
		}

		return cardInfoSnapshot;
	}

	/**
	 * 个人信息修改接口
	 */
	public boolean updateUserCardInfo(CardInfo cardInfo, HttpServletRequest request) {

		// XYY 获取该名片所在的企业ID， 因为部门和职位 都是挂在 企业下的。
		String entIdStr = cardApplyEntMapper.getCardApplyEntNoOrYesAudit(cardInfo.getId());
		int entId = 0;
		if (entIdStr != null && !"".equals(entIdStr)) {
			entId = Integer.parseInt(entIdStr);
		}

		// 获取信息用于修改比对日志记录
		CardInfoSnapshot cardInfoSnapshot = new CardInfoSnapshot();
		cardInfoSnapshot.setId(cardInfo.getId());
		cardInfoSnapshot = getUserCardInfo(cardInfoSnapshot, request, null);

		String deptName = request.getParameter("deptName");
		String positionName = request.getParameter("positionName");
		cardInfo.setEntDepartmentStr(deptName);
		cardInfo.setEntPositionStr(positionName);

		boolean departmentChange = false;
		boolean positionChange = false;

		// 部门
		// xyy 如果部门不存在，那么就插入部门，
		if (deptName != null && !"".equals(deptName.trim())) {

			// XYY 获取原先的部门名称集合
			List<EntDepartment> dsold = cardInfoSnapshot.getDepartmentList();
			Set<String> dnold = new HashSet<String>(); // 原先部门名称列表
			for (EntDepartment edp : dsold) {
				dnold.add(edp.getDeptName());
			}

			// XYY 获取现在的部门名称集合
			Set<String> dnnew = new HashSet<String>(); // 原先部门名称列表
			String[] deptNameArr = deptName.trim().split(",");
			for (String ss : deptNameArr) {
				dnnew.add(ss);
			}

			// XYY比较新老部门名称集合是否相同，如果不同则更新
			departmentChange = !org.apache.commons.collections.CollectionUtils.isEqualCollection(dnold, dnnew);
			if (departmentChange) {
				// XYY直接删除原先的部门关联数据
				cardDeptLinkMapper.delete(cardInfo.getId());
				for (String ss : dnnew) {
					EntDepartment entDepartment = new EntDepartment();
					entDepartment.setEntId(entId);
					entDepartment.setDeptName(ss);
					Integer deptId = entDepartmentMapper.queryCode(entDepartment);
					// xyy 如果没有该部门，则插入一条新纪录
					if (deptId == null || deptId < 0) {
						entDepartmentMapper.insert(entDepartment);
						deptId = entDepartmentMapper.queryCode(entDepartment);
					}
					CardDeptLink cardDeptLink = new CardDeptLink();
					cardDeptLink.setCardId(cardInfo.getId());
					cardDeptLink.setDeptId(deptId);
					cardDeptLinkMapper.insert(cardDeptLink);
				}
			}
		}

		// 职位
		if (positionName != null && !"".equals(positionName.trim())) {

			// XYY 获取原先的职位名称集合
			List<EntPosition> oldPositionList = cardInfoSnapshot.getPositionList();
			Set<String> oldPositionNames = new HashSet<String>(); // 原先部门名称列表
			for (EntPosition opl : oldPositionList) {
				oldPositionNames.add(opl.getPositionName());
			}

			// XYY 获取现在的职位名称集合
			Set<String> newPositionNames = new HashSet<String>(); // 原先部门名称列表
			String[] positionNameArr = positionName.trim().split(",");
			for (String ss : positionNameArr) {
				newPositionNames.add(ss);
			}

			positionChange = !org.apache.commons.collections.CollectionUtils.isEqualCollection(oldPositionNames,
					newPositionNames);

			if (positionChange) {
				cardPositionLinkMapper.delete(cardInfo.getId());
				EntPosition entPosition = new EntPosition();
				entPosition.setEntId(entId);

				CardPositionLink cardPositionLink = new CardPositionLink();
				cardPositionLink.setCardId(cardInfo.getId());

				for (int i = 0; i < positionNameArr.length; i++) {
					entPosition.setPositionName(positionNameArr[i].trim());
					Integer positionId = entPositionMapper.queryCode(entPosition);
					if (positionId == null || positionId < 0) {
						entPositionMapper.insert(entPosition);
						positionId = entPositionMapper.queryCode(entPosition);
					}

					cardPositionLink.setPositionId(positionId);
					cardPositionLinkMapper.insert(cardPositionLink);
				}
			}

		}
		// 基本信息
		// 处理真实姓名拼音
		String pinyin = PinyinUtils.converterToFirstSpell(cardInfo.getTrueName());
		if (pinyin != null && pinyin.length() > 0) {
			cardInfo.setTnFirstSpell(pinyin.substring(0, 1).toUpperCase());
		}
		cardInfoMapper.update(cardInfo);
		// 如果是修改的身份证，那么也将用户表中的身份证号码修改掉
		if (cardInfo.getUserIdcard() != null && !"".equals(cardInfo.getUserIdcard().trim())) {
			UserInfo userInfo = new UserInfo();
			userInfo.setId(cardInfo.getUserId());
			userInfo.setIdCard(cardInfo.getUserIdcard());
			userInfoMapper.update(userInfo);
		}

		// 动态记录
		boolean ischange = saveCardUpdateDynamic(cardInfo, cardInfoSnapshot, departmentChange, positionChange);
		
		if(ischange){
			// 获取用户名片
			CardInfoSnapshot cis = cardInfoSnapshotMapper.queryExchangeNewCardOne(cardInfo.getId());
			cis.setIconUrl(getFileUrl(cis.getIconUrl(), request));
			// 动态消息推送给好友：code:3 data{} 姓名
			List<UserInfo> userInfoList = userInfoMapper.getFriendClientIdListByCardId(cardInfo.getId());
			geTuiService.transmissionTemplate(userInfoList, GeTuiUtils.CODE_CardDynamic, cis);
		}

		return true;
	}
	
	/**
	 * 保存名片修改动态 
	* @param ci
	* @param cis
	* @param departmentChange
	* @param positionChange
	* @return  true  职位，部门，或者手机号码有改动
	* <p>
	* author: xyy
	* create at: 2014年4月16日上午12:34:54
	 */
	private boolean saveCardUpdateDynamic(CardInfo ci, CardInfoSnapshot cis, boolean departmentChange,
			boolean positionChange) {
		boolean flag = false;
		List<SysOperationLog> dyList = new ArrayList<SysOperationLog>();
		String pkValue = ci.getId().toString();
		// 手机号
		if (ci.getMobile() != null && !ci.getMobile().equals(cis.getMobile())) {
			dyList.add(new SysOperationLog(pkValue, XinHuConfig.cardDynamicTable, XinHuConfig.cardDynamic_mobile, ci
					.getMobile()));
		}
		// 部门
		/*
		 * boolean modifyMark = false; StringBuffer deptStr = new
		 * StringBuffer(); List<EntDepartment> deptList =
		 * cis.getDepartmentList(); if (ci.getEntDepartmentStr() != null &&
		 * !ci.getEntDepartmentStr().trim().equals("")) { if (deptList == null)
		 * { deptList = new ArrayList<EntDepartment>(); } String[] deptArr =
		 * ci.getEntDepartmentStr().trim().split(","); // 判断修改的 for (int i = 0;
		 * i < deptArr.length; i++) { boolean mark = true; for (int j = 0; j <
		 * deptList.size(); j++) { if
		 * (deptArr[i].equals(deptList.get(j).getDeptName())) { mark = false;
		 * break; } } if (mark) { // dyList.add(new //
		 * SysOperationLog(pkValue,XinHuConfig
		 * .cardDynamicTable,XinHuConfig.cardDynamic_department,deptArr[i]));
		 * modifyMark = true; } } if (deptList != null && deptList.size() !=
		 * deptArr.length) { modifyMark = true; } if (modifyMark) { for (int i =
		 * 0; i < deptArr.length; i++) { if (i > 0) { deptStr.append(" | "); }
		 * deptStr.append(deptArr[i]); } } } else { if (deptList != null &&
		 * deptList.size() > 0) { modifyMark = true; } }
		 */

		if (departmentChange) {
			StringBuffer deptStr = new StringBuffer();
			String[] deptArr = ci.getEntDepartmentStr().trim().split(",");
			for (int i = 0; i < deptArr.length; i++) {
				if (i > 0) {
					deptStr.append(" | ");
				}
				deptStr.append(deptArr[i]);
			}
			dyList.add(new SysOperationLog(pkValue, XinHuConfig.cardDynamicTable, XinHuConfig.cardDynamic_department,
					deptStr.toString()));
		}

		/*
		 * // 职位 modifyMark = false; StringBuffer positionStr = new
		 * StringBuffer(); List<EntPosition> positionList =
		 * cis.getPositionList(); if (ci.getEntPositionStr() != null &&
		 * !ci.getEntPositionStr().trim().equals("")) { if (positionList ==
		 * null) { positionList = new ArrayList<EntPosition>(); } String[]
		 * positionArr = ci.getEntPositionStr().trim().split(","); for (int i =
		 * 0; i < positionArr.length; i++) { boolean mark = true; for (int j =
		 * 0; j < positionList.size(); j++) { if
		 * (positionArr[i].equals(positionList.get(j).getPositionName())) { mark
		 * = false; break; } } if (mark) { // dyList.add(new //
		 * SysOperationLog(pkValue
		 * ,XinHuConfig.cardDynamicTable,XinHuConfig.cardDynamic_position
		 * ,positionArr[i])); modifyMark = true; } }
		 * 
		 * if (positionList != null && positionList.size() !=
		 * positionArr.length) { modifyMark = true; } if (modifyMark) { for (int
		 * i = 0; i < positionArr.length; i++) { if (i > 0) {
		 * positionStr.append(" | "); } positionStr.append(positionArr[i]); } }
		 * 
		 * } else { if (positionList != null && positionList.size() > 0) {
		 * modifyMark = true; } }
		 */

		if (positionChange) {
			StringBuffer positionStr = new StringBuffer();
			String[] positionArr = ci.getEntPositionStr().trim().split(",");
			for (int i = 0; i < positionArr.length; i++) {
				if (i > 0) {
					positionStr.append(" | ");
				}
				positionStr.append(positionArr[i]);
			}
			dyList.add(new SysOperationLog(pkValue, XinHuConfig.cardDynamicTable, XinHuConfig.cardDynamic_position,
					positionStr.toString()));
		}

		if (dyList.size() > 0) {
			sysOperationLogMapper.insertBatch(dyList);
			flag = true;
		}
		
		return flag;
	}

	/** 保存名片修改动态_因部门和职位的展示需求修改问题需要对保存数据的方式进行处理，该方法暂时废弃 */
	@Deprecated
	private void saveCardUpdateDynamic_20150706(CardInfo ci, CardInfoSnapshot cis) {
		List<SysOperationLog> dyList = new ArrayList<SysOperationLog>();
		String pkValue = ci.getId().toString();
		// 手机号
		if (ci.getMobile() != null && !ci.getMobile().equals(cis.getMobile())) {
			dyList.add(new SysOperationLog(pkValue, XinHuConfig.cardDynamicTable, XinHuConfig.cardDynamic_mobile, ci
					.getMobile()));
		}
		// 部门
		if (ci.getEntDepartmentStr() != null && !ci.getEntDepartmentStr().trim().equals("")) {
			List<EntDepartment> deptList = cis.getDepartmentList();
			if (deptList == null) {
				deptList = new ArrayList<EntDepartment>();
			}
			String[] deptArr = ci.getEntDepartmentStr().trim().split(",");
			for (int i = 0; i < deptArr.length; i++) {
				boolean mark = true;
				for (int j = 0; j < deptList.size(); j++) {
					if (deptArr[i].equals(deptList.get(j).getDeptName())) {
						mark = false;
						break;
					}
				}
				if (mark) {
					dyList.add(new SysOperationLog(pkValue, XinHuConfig.cardDynamicTable,
							XinHuConfig.cardDynamic_department, deptArr[i]));
				}
			}
		}
		// 职位
		if (ci.getEntPositionStr() != null && !ci.getEntPositionStr().trim().equals("")) {
			List<EntPosition> positionList = cis.getPositionList();
			if (positionList == null) {
				positionList = new ArrayList<EntPosition>();
			}
			String[] positionArr = ci.getEntPositionStr().trim().split(",");
			for (int i = 0; i < positionArr.length; i++) {
				boolean mark = true;
				for (int j = 0; j < positionList.size(); j++) {
					if (positionArr[i].equals(positionList.get(j).getPositionName())) {
						mark = false;
						break;
					}
				}
				if (mark) {
					dyList.add(new SysOperationLog(pkValue, XinHuConfig.cardDynamicTable,
							XinHuConfig.cardDynamic_position, positionArr[i]));
				}
			}
		}

		if (dyList.size() > 0) {
			sysOperationLogMapper.insertBatch(dyList);
		}

	}

	/**
	 * 图片上传接口
	 */
	public String uploadUserIcon(XinHuUserAgent userAgent, HttpServletRequest request) {
		String filePath = null;
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			List<FileItem> items = upload.parseRequest(request);
			// 得到所有的文件
			Iterator<FileItem> it = items.iterator();
			while (it.hasNext()) {
				FileItem fItem = (FileItem) it.next();
				if (!fItem.isFormField()) {
					String fileName = URLDecoder.decode(fItem.getName(), "utf-8");

					if (StringUtils.isNotBlank(fileName)) {
						fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
						InputStream is = fItem.getInputStream();
						String attachType = fileName.substring(fileName.lastIndexOf("."));
						// 拼装存储路径 格式:名片ID%100/名片ID
						String storePath = this.getStorePath(userAgent.getDefaultCardId());
						filePath = storeService.saveObject(is, System.currentTimeMillis() + attachType, storePath);
					}
				}
			}

		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}

		if (filePath != null) {
			CardInfo cardInfo = new CardInfo();
			cardInfo.setId(userAgent.getDefaultCardId());
			cardInfo.setIconUrl(filePath);
			cardInfoMapper.updateIconUrl(cardInfo);

			filePath = getFileUrl(filePath, request);

			// 测试下载代码
			/*
			 * try { InputStream is = storeService.getObject(filePath);
			 * OutputStream outputStream = new FileOutputStream("D:/yjx.jpg");
			 * byte[] bytes = new byte[1024]; while (is.read(bytes) != -1) {
			 * outputStream.write(bytes); } if(null !=is){ is.close(); }
			 * outputStream.close(); } catch (Exception e) {
			 * e.printStackTrace(); }
			 */
		}

		return filePath;
	}

	/**
	 * 搜索已经注册的企业接口
	 */
	public List<EntInfo> searchEntList(XinHuQuery xinHuQuery) {
		return entInfoMapper.searchEntList(xinHuQuery);
	}

	/**
	 * 获取搜索企业详情接口
	 */
	public EntInfo getSearchEntCardInfo(XinHuQuery xinHuQuery) {
		return entInfoMapper.getSearchEntCardInfo(xinHuQuery);
	}

	/**
	 * 申请绑定企业接口
	 */
	public boolean applyBindEnt(CardApplyEnt cardApplyEnt) {
		// XYY 根据 名片号 和 企业 id 查询 名片绑定企业记录， 如果 存在， 则更新为 申请状态， 如果不存在则插入
		CardApplyEnt cardApplyEnt1 = cardApplyEntMapper.load(cardApplyEnt);
		if (cardApplyEnt1 == null) { // 没有记录，则插入
			cardApplyEnt.setBindStatus(0);
			cardApplyEntMapper.insert(cardApplyEnt);
		} else { // 有记录， 更新
			cardApplyEnt.setUnpassMes("");
			cardApplyEnt.setBindStatus(0);
			cardApplyEnt.setGmtApply(new Date());
			cardApplyEntMapper.update2(cardApplyEnt);
		}

		return true;

		/*
		 * HashMap<String, Object> map =
		 * cardApplyEntMapper.getCardApplyEntNoAudit(cardApplyEnt.getCardId());
		 * if (map == null || map.isEmpty()) { cardApplyEnt.setBindStatus(0);
		 * cardApplyEntMapper.insert(cardApplyEnt); return true; } else {
		 * Integer entIdOld = (Integer) map.get("ent_id"); if
		 * (cardApplyEnt.getEntId() != entIdOld) { // 先删除申请未审核的数据
		 * cardApplyEntMapper
		 * .deleteCardApplyEntNoAudit(cardApplyEnt.getCardId());
		 * 
		 * // 插入新数据 cardApplyEnt.setBindStatus(0);
		 * cardApplyEntMapper.insert(cardApplyEnt); } return false; }
		 */
	}

	/**
	 * 人脉统计接口
	 */
	public HashMap<String, Object> getConnStatisticalInfo(Integer cardId) {
		return userCardLinkMapper.getConnStatisticalInfo(cardId);
	}

	/**
	 * 搜索企业部门的接口
	 */
	public List<HashMap<String, Object>> getEntDeptList(XinHuQuery xinHuQuery) {
		return cardDeptLinkMapper.getEntDeptList(xinHuQuery);
	}

	/**
	 * 搜索企业通讯录下员工信息的接口
	 */
	public PageResult<HashMap<String, Object>> getEntStaffList(XinHuQuery query, HttpServletRequest request) {
		// int count = cardInfoMapper.queryEntStaffCount(query);
		List<HashMap<String, Object>> list = cardInfoMapper.queryEntStaffList(query);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).put("iconUrl", getFileUrl((String) list.get(i).get("iconUrl"), request));
		}
		// 分页暂时去掉
		return PageResult.create(query, list, 1);
	}

	/**
	 * 根据部门号搜索企业个人的接口
	 */
	@Override
	public PageResult<HashMap<String, Object>> queryEntStaffListByDept(XinHuQuery query, HttpServletRequest request) {
		// int count = cardInfoMapper.queryEntStaffListByDeptCount(query);
		List<HashMap<String, Object>> list = cardInfoMapper.queryEntStaffListByDept(query);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).put("iconUrl", getFileUrl((String) list.get(i).get("iconUrl"), request));
		}
		// 分页暂时去掉
		return PageResult.create(query, list, 1);
	}

	/**
	 * 主页 搜索结果个人加部门的接口
	 */
	public HashMap<String, Object> searchEntInnerCardList(XinHuQuery query, HttpServletRequest request) {
		HashMap<String, Object> map = new HashMap<String, Object>();

		int recordNum = 4;
		query.setPageSize(10000);
		List<HashMap<String, Object>> deptList = cardDeptLinkMapper.getEntDeptList(query);
		List<HashMap<String, Object>> resList = new ArrayList<HashMap<String, Object>>();
		if (deptList.size() > recordNum) {
			for (int i = 0; i < recordNum; i++) {
				resList.add(deptList.get(i));
			}
			map.put("deptList", resList);
			map.put("isMoreDept", 1);
		} else {
			map.put("deptList", deptList);
			map.put("isMoreDept", 0);
		}

		query.setSearchType("trueName");
		List<HashMap<String, Object>> clientList = cardInfoMapper.queryEntStaffList(query);
		List<HashMap<String, Object>> resList2 = new ArrayList<HashMap<String, Object>>();
		if (clientList.size() > recordNum) {
			for (int i = 0; i < recordNum; i++) {
				resList2.add(clientList.get(i));
			}
			map.put("clientList", resList2);
			map.put("isMoreClient", 1);
		} else {
			map.put("clientList", clientList);
			map.put("isMoreClient", 0);
		}

		for (int i = 0; i < clientList.size(); i++) {
			clientList.get(i).put("iconUrl", getFileUrl((String) clientList.get(i).get("iconUrl"), request));
		}

		// 增加部门总数和人总数 xyy
		/*
		 * CardDeptLinkQuery cardDeptLinkQuery = new CardDeptLinkQuery();
		 * cardDeptLinkQuery.setCardId(query.getCardId()); int cardDeptNum =
		 * cardDeptLinkMapper.queryCount(cardDeptLinkQuery);
		 * 
		 * int entStaffCount = cardInfoMapper.queryEntStaffCount(query);
		 */
		map.put("cardDeptCount", deptList.size());
		map.put("entStaffCount", clientList.size());

		return map;
	}

	/**
	 * 删除名片接口
	 */
	public boolean deleteCard(UserCardApply userCardApply) {
		userCardApplyMapper.updateDeleteInfo(userCardApply);
		UserCardLink userCardLink = new UserCardLink();
		userCardLink.setCardId(userCardApply.getCardId());
		userCardLink.setCardIdFrom(userCardApply.getCardIdFrom());
		userCardLinkMapper.delete(userCardLink);

		// 删除名片通知对方后台删除掉对方本地名片：code:1 data{} 姓名
		int cardId;
		int cardId2;// 被删除对象的card_id;
		if (userCardApply.getCardId() == userCardApply.getCardIdDelete()) {
			cardId = userCardApply.getCardIdFrom();
			cardId2 = userCardApply.getCardId();
		} else {
			cardId = userCardApply.getCardId();
			cardId2 = userCardApply.getCardIdFrom();
		}
		UserInfo userInfo = userInfoMapper.getClientIdByCardId(cardId);
		geTuiService.transmissionTemplate(userInfo, GeTuiUtils.CODE_CardDelete, cardId2);

		return true;
	}

	/**
	 * 举报名片接口
	 */
	public boolean reportCard(CardReport cardReport) {
		cardReportMapper.insert(cardReport);
		return true;
	}

	/**
	 * 隐私接口（禁止通关手机号或名片号等搜索到）
	 */
	public boolean setIsEnableSearch(int cardId, HttpServletRequest request) {
		// card_id:名片号，mobile:手机号，email：邮箱，position：职位，nickName：昵称
		String searchControlSwitch = "";

		String[] typeArr = XinHuConfig.searchControlSwitch;
		for (int i = 0; i < typeArr.length; i++) {
			String b = request.getParameter(typeArr[i]);
			if ("0".equals(b) && "1".equals(b)) {
				b = "1";
			}
			searchControlSwitch += b;
		}

		CardInfo cardInfo = new CardInfo();
		cardInfo.setId(cardId);
		cardInfo.setSearchControlSwitch(searchControlSwitch);
		cardInfoMapper.update(cardInfo);
		return true;
	}

	// public boolean setIsEnableSearch(XinHuQuery xinHuQuery,String type){
	// //card_id:名片号，mobile:手机号，email：邮箱，position：职位，nickName：昵称
	// int index=XinHuConfig.getSearchControlSwitchIndex(type);
	// if(index==-1){
	// return false;
	// }
	// HashMap<String,Object> map = new HashMap<String, Object>();
	// map.put("id", xinHuQuery.getCardId());
	// map.put("status", xinHuQuery.getStatus());
	// map.put("index", index);
	// cardInfoMapper.setIsEnableSearch(map);
	// return true;
	// }

	/**
	 * 绑定新手机接口
	 */
	public boolean bindNewMobile(int userId, int cardId, String mobile) {
		UserInfo userInfo = new UserInfo();
		userInfo.setId(userId);
		userInfo.setMobile(mobile);
		userInfoMapper.updateMobile(userInfo);

		CardInfo cardInfo = new CardInfo();
		cardInfo.setId(cardId);
		cardInfo.setMobile(mobile);
		cardInfoMapper.updateMobile(cardInfo);

		return true;
	}

	/**
	 * 消息已读接口
	 */
	public boolean updateMessageReadTime(int userId) {
		userInfoMapper.updateMessageReadTime(userId);
		return true;
	}

	/**
	 * 推荐名片接口（交换名片搜索没有内容时调用）
	 */
	public List<HashMap<String, String>> getRecommendCardList(XinHuQuery xinHuQuery, HttpServletRequest request) {
		int pageSize = 10;
		xinHuQuery.setPageSize(pageSize);
		List<HashMap<String, String>> list = userCardLinkMapper.getRecommendCardList_colleague(xinHuQuery);
		/*
		 * List<HashMap<String, String>> list =
		 * userCardLinkMapper.getRecommendCardList_one(xinHuQuery); if (list ==
		 * null || list.size() < pageSize) { CardInfo cardInfo =
		 * cardInfoMapper.load(xinHuQuery.getCardId()); Integer entId =
		 * cardInfo.getEntId(); if (entId != null && entId > 0) {
		 * xinHuQuery.setEntId(entId); xinHuQuery.setPageSize(pageSize -
		 * list.size());
		 * userCardLinkMapper.getRecommendCardList_colleague(xinHuQuery); } }
		 */

		for (int i = 0; i < list.size(); i++) {
			list.get(i).put("iconUrl", getFileUrl(list.get(i).get("iconUrl"), request));
		}

		return list;
	}

	/**
	 * 所有交换名片信息更新动态列表接口
	 */
	public PageResult<HashMap<String, Object>> getCardUpdateDynamicList(XinHuQuery xinHuQuery,
			HttpServletRequest request) {
		// UserInfo userInfo = userInfoMapper.load(xinHuQuery.getId());
		// userInfo.getGmtMessageRead()
		Date gmtDynamicLastRead = cardInfoMapper.getGmtDynamicLastRead(xinHuQuery.getCardId());

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("gmtModify", gmtDynamicLastRead);
		map.put("cardId", xinHuQuery.getCardId());

		// int count = sysOperationLogMapper.queryCardUpdateDynamicCount(map);
		List<HashMap<String, Object>> list = sysOperationLogMapper.queryCardUpdateDynamicList(map);

		if (list != null && list.size() > 0) {
			Integer[] idArray = new Integer[list.size()];
			for (int i = 0; i < list.size(); i++) {
				idArray[i] = (Integer) (list.get(i).get("cardId"));
			}
			map.put("idArray", idArray);

			// 处理动态列表
			List<HashMap<String, Object>> dyList_log = sysOperationLogMapper.getCardUpdateDynamicList(map);
			for (int i = 0; i < list.size(); i++) {
				HashMap<String, Object> cardInfoMap = list.get(i);
				Integer cardId = (Integer) cardInfoMap.get("cardId");
				HashMap<String, String> dyMap = new HashMap<String, String>();
				for (int j = 0; j < dyList_log.size(); j++) {
					if (cardId == Integer.parseInt(dyList_log.get(j).get("pkValue").toString())) {
						setCardUpdateDynamic(dyMap, dyList_log.get(j));
					}
				}

				// cardInfoMap.putAll(dyMap);
				// 标记动态
				String[] cardDynamic = XinHuConfig.cardDynamic;
				for (int j = 0; j < cardDynamic.length; j++) {
					if (dyMap.get(cardDynamic[j]) != null) {
						list.get(i).put(cardDynamic[j], list.get(i).get(cardDynamic[j]) + XinHuConfig.cardDynamicCss);
					}
				}

			}
		}

		for (int i = 0; i < list.size(); i++) {
			list.get(i).put("iconUrl", getFileUrl((String) list.get(i).get("iconUrl"), request));
		}

		cardInfoMapper.updateGmtDynamicLastRead(xinHuQuery.getCardId());
		// 暂时不分页
		return PageResult.create(xinHuQuery, list, 1);
	}

	/** 处理名片动态 */
	public HashMap<String, String> setCardUpdateDynamic(HashMap<String, String> dyMap, HashMap<String, Object> map) {
		if (dyMap.get("dynamicTime") == null) {
			dyMap.put("dynamicTime", map.get("gmtModify").toString());
		}
		String columnName = map.get("columnName").toString();
		String[] cardDynamic = XinHuConfig.cardDynamic;
		for (int i = 0; i < cardDynamic.length; i++) {
			if (cardDynamic[i].equals(columnName)) {
				if (dyMap.get(cardDynamic[i]) == null) {
					dyMap.put(cardDynamic[i], map.get("columnValue").toString());
				} else {
					if (XinHuConfig.cardDynamic_mobile.contains(columnName)) {// 手机号只取最后一次
						// dyMap.put(cardDynamic[i],
						// dyMap.get(cardDynamic[i])+" | "+map.get("columnValue").toString());
					} else {
						dyMap.put(cardDynamic[i], dyMap.get(cardDynamic[i]) + " | " + map.get("columnValue").toString());
					}
				}
			}
		}

		return dyMap;
	}

	/**
	 * 单张名片信息更新动态列表接口
	 */
	public HashMap<String, Object> getCardUpdateDynamicInfo(XinHuQuery xinHuQuery, HttpServletRequest request) {
		// UserInfo userInfo = userInfoMapper.load(xinHuQuery.getId());
		UserCardApply userCardApply = new UserCardApply();
		userCardApply.setCardId(xinHuQuery.getCardId());
		userCardApply.setCardIdFrom(xinHuQuery.getId());
		userCardApply.setIsExchange("1");
		userCardApply = userCardApplyMapper.getUserCardApplyByTwoCardId(userCardApply);

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("gmtModify", userCardApply.getGmtExchange());
		map.put("cardId", xinHuQuery.getCardId());

		HashMap<String, Object> cardInfo = sysOperationLogMapper.getCardInfo(xinHuQuery.getCardId());

		Integer[] idArray = new Integer[] { (Integer) cardInfo.get("cardId") };
		map.put("idArray", idArray);

		// XYY 处理动态列表
		List<HashMap<String, Object>> dyList_log = sysOperationLogMapper.getCardUpdateDynamicList(map);

		// XYY 对dyList_log进行分组， 按时间分组， 如果时间相同，则是同一次提交的修改
		List<HashMap<String, Object>> dyList = new ArrayList<HashMap<String, Object>>();

		HashMap<String, Object> cardInfoMap = cardInfo;
		HashMap<String, Integer> dyMap_date = new HashMap<String, Integer>();
		for (int j = 0; j < dyList_log.size(); j++) {
			String gmtModify = (String) dyList_log.get(j).get("gmtModify");
			if (dyMap_date.get(gmtModify) == null) {
				dyMap_date.put(gmtModify, dyList.size());
				dyList.add(new HashMap<String, Object>());
				dyList.get(dyMap_date.get(gmtModify)).put("dynamicTime", dyList_log.get(j).get("gmtModify"));
			}
			dyList.get(dyMap_date.get(gmtModify)).put((String) dyList_log.get(j).get("columnName"),
					dyList_log.get(j).get("columnValue"));
		}
		/**
		 * XYY 对dyList 进行处理， dyList [{dynamicTime:xx, dynamicDepartment:xx,
		 * dynamicPosition:xx, dynamicMobile:xx}...] 有四个字段， 其中 dynamicTime:xx
		 * 是肯定有的 dynamicDepartment 部门跟新信息 dynamicPosition:xx 职位跟新信息
		 * dynamicMobile:xx 手机号码更新信息 如果上面三个字段有修改过，则对象中有 如果对象中没有，那么要取上一次的信息，补充完整
		 */

		// TODO XYY 第一次更新的数据还是有问题的，这个最好重新设计过

		for (int i = dyList.size() - 1; i >= 0; i--) {
			if (i == dyList.size() - 1) { // 最开始的跟新动态信息
				HashMap<String, Object> firstlist = dyList.get(dyList.size() - 1);
				// 第一次更新的信息，吧手机号码放进去,吧所有字段都放进去
				// 获取当前的数据
				CardInfoSnapshot cardInfoSnapshot = new CardInfoSnapshot();
				cardInfoSnapshot.setId(xinHuQuery.getCardId());
				cardInfoSnapshot = getUserCardInfo(cardInfoSnapshot, request, null);

				if (!firstlist.containsKey("dynamicMobile")) {
					firstlist.put("dynamicMobile", cardInfoSnapshot.getMobile());
				}
				if (!firstlist.containsKey("dynamicDepartment")) {
					// 动态获取，组装部门信息
					List<EntDepartment> departmentList = cardInfoSnapshot.getDepartmentList();
					String departmentStr = "";
					for (EntDepartment ed : departmentList) {
						departmentStr = departmentStr + ed.getDeptName().trim() + ",";
					}
					departmentStr = departmentStr.substring(0, departmentStr.length() - 1);
					firstlist.put("dynamicDepartment", departmentStr);
				}
				if (!firstlist.containsKey("dynamicPosition")) {
					// 动态获取，组装职位信息
					List<EntPosition> positionList = cardInfoSnapshot.getPositionList();
					String positionStr = "";
					for (EntPosition ep : positionList) {
						positionStr = positionStr + ep.getPositionName().trim() + ",";
					}
					positionStr.substring(0, positionStr.length() - 1);
					firstlist.put("dynamicPosition", positionStr);
				}
			}
			// 按时间从早到晚 取出 对象
			HashMap<String, Object> list = dyList.get(i);
			if (!list.containsKey("dynamicMobile")) {
				list.put("dynamicMobile", dyList.get(i + 1).get("dynamicMobile"));
			}
			if (!list.containsKey("dynamicDepartment")) {
				list.put("dynamicDepartment", dyList.get(i + 1).get("dynamicDepartment"));
			}
			if (!list.containsKey("dynamicPosition")) {
				list.put("dynamicPosition", dyList.get(i + 1).get("dynamicPosition"));
			}
		}

		/*
		 * // 处理动态列表 List<HashMap<String, Object>> dyList_log =
		 * sysOperationLogMapper.getCardUpdateDynamicList(map); HashMap<String,
		 * Object> cardInfoMap = cardInfo; List<HashMap<String, Object>> dyList
		 * = new ArrayList<HashMap<String, Object>>(); HashMap<String, Integer>
		 * dyMap_date = new HashMap<String, Integer>(); for (int j = 0; j <
		 * dyList_log.size(); j++) { String gmtModify = (String)
		 * dyList_log.get(j).get("gmtModify"); if (dyMap_date.get(gmtModify) ==
		 * null) { dyMap_date.put(gmtModify, dyList.size()); dyList.add(new
		 * HashMap<String, Object>()); }
		 * dyList.get(dyMap_date.get(gmtModify)).put((String)
		 * dyList_log.get(j).get("columnName"),
		 * dyList_log.get(j).get("columnValue")); }
		 */

		// 添加缺省值及动态标记
		/*
		 * for (int j = 1; j < dyList.size(); j++) { String[] cardDynamic =
		 * XinHuConfig.cardDynamic; for (int k = 0; k < cardDynamic.length; k++)
		 * { if (dyList.get(j).get(cardDynamic[k]) != null) {
		 * dyList.get(j).put(cardDynamic[k],
		 * dyList.get(j).get(cardDynamic[k]).toString() +
		 * XinHuConfig.cardDynamicCss); } else { if (dyList.get(j -
		 * 1).get(cardDynamic[k]) != null) { dyList.get(j).put( cardDynamic[k],
		 * dyList.get(j - 1).get(cardDynamic[k]).toString()
		 * .replace(XinHuConfig.cardDynamicCss, "")); } } } }
		 */

		cardInfoMap.put("dyList", dyList);

		cardInfo.put("iconUrl", getFileUrl((String) cardInfo.get("iconUrl"), request));

		return cardInfo;
	}

	/** 个推消息读取通知接口 */
	public boolean getuiMessageReadNotice(GetuiMessageLog getuiMessageLog) {
		getuiMessageLogMapper.updateMessageRead(getuiMessageLog);
		return true;
	}

	/** 获取企业职位列表 */
	public String getEntPositionList(Integer entId) {
		List<EntPosition> list = entPositionMapper.getEntPositionList(entId);
		StringBuffer posStr = new StringBuffer();
		if (list == null || list.size() == 0) {
			posStr.append("产品经理|主管,项目经理,开发工程师,测试工程师,运维工程师,UI设计师,运营专员,市场推广,人事/HR,行政");
		} else {
			for (int i = 0; i < list.size(); i++) {
				if (i > 0) {
					posStr.append(",");
				}
				posStr.append(list.get(i).getPositionName());
			}
		}
		return posStr.toString();
	}

	/**
	 * 获取文件存储路径
	 * 
	 * @param cardId
	 * @return <p>
	 *         author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	 *         create at: 2015年6月23日 下午2:14:17
	 */
	private String getStorePath(Integer cardId) {
		String storePath = "/" + PREFIX + "/client/" + (cardId % 100) + "/" + cardId + "/";
		return PathUtils.concatDeleteRepeat(storePath);
	}

	/**
	 * 获取文件url
	 * 
	 * @param fileUrl
	 * @return <p>
	 *         author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	 *         create at: 2015年6月24日 下午2:30:07
	 */
	private String getFileUrl(String fileUrl, HttpServletRequest request) {
		if (fileUrl == null || "".equals(fileUrl.trim())) {
			return fileUrl;
		}
		String root = webContextUtils.getStoreUrl(request);

		if (!storeService.isExistOnDisk(fileUrl)) {
			fileUrl = storeService.getAgentUrl() + fileUrl; // 如果磁盘上的文件不存在，则路径指向OOS上
		} else {
			fileUrl = root + fileUrl;
		}
		return fileUrl;
	}

	@Override
	public void denyOrAffirmEntBand(Integer cardId, Integer type) {
		
		if(Integer.valueOf(0).equals(type)){//取消
			//更新表  card_info
			CardInfo cardInfo = new CardInfo();
			cardInfo.setId(cardId);
			cardInfo.setEntId(-1);
			cardInfo.setUserIdcard("");
			cardInfo.setTrueName("");
			cardInfo.setEmail("");
			cardInfoMapper.update(cardInfo);
			
			//删除部门和职业表
			cardPositionLinkMapper.delete(cardId);
			cardDeptLinkMapper.delete(cardId);
		}else{  //确认， 插入 card_apply_ent 表
			CardApplyEnt cardApplyEnt = new CardApplyEnt();
			cardApplyEnt.setCardId(cardId);
			CardInfo ci = cardInfoMapper.load(cardId);
			cardApplyEnt.setEntId(ci.getEntId());
			cardApplyEnt.setBindStatus(1);
			cardApplyEnt.setGmtBind(new Date());
			cardApplyEntMapper.insert(cardApplyEnt);
		}
		
		
	}

}

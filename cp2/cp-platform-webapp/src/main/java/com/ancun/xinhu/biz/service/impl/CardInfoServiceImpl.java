package com.ancun.xinhu.biz.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ancun.products.basebiz.service.StoreService;
import com.ancun.products.core.config.SysConfig;
import com.ancun.products.core.exception.ServiceException;
import com.ancun.products.core.model.PageResult;
import com.ancun.products.core.utils.PathUtils;
import com.ancun.products.core.utils.StringUtils;
import com.ancun.xinhu.biz.mappers.CardApplyEntMapper;
import com.ancun.xinhu.biz.mappers.CardDeptLinkLogMapper;
import com.ancun.xinhu.biz.mappers.CardDeptLinkMapper;
import com.ancun.xinhu.biz.mappers.CardInfoMapper;
import com.ancun.xinhu.biz.mappers.CardInfoSnapshotMapper;
import com.ancun.xinhu.biz.mappers.CardPositionLinkLogMapper;
import com.ancun.xinhu.biz.mappers.CardPositionLinkMapper;
import com.ancun.xinhu.biz.mappers.EntDepartmentMapper;
import com.ancun.xinhu.biz.mappers.EntInfoMapper;
import com.ancun.xinhu.biz.mappers.EntPositionMapper;
import com.ancun.xinhu.biz.mappers.SysLoginLogMapper;
import com.ancun.xinhu.biz.mappers.UserCardApplyMapper;
import com.ancun.xinhu.biz.mappers.UserInfoMapper;
import com.ancun.xinhu.biz.service.CardInfoService;
import com.ancun.xinhu.biz.service.GeTuiService;
import com.ancun.xinhu.domain.model.CardApplyEnt;
import com.ancun.xinhu.domain.model.CardDeptLink;
import com.ancun.xinhu.domain.model.CardInfo;
import com.ancun.xinhu.domain.model.CardInfoSnapshot;
import com.ancun.xinhu.domain.model.CardPositionLink;
import com.ancun.xinhu.domain.model.EntDepartment;
import com.ancun.xinhu.domain.model.EntInfo;
import com.ancun.xinhu.domain.model.EntPosition;
import com.ancun.xinhu.domain.model.UserInfo;
import com.ancun.xinhu.domain.query.CardInfoQuery;
import com.ancun.xinhu.util.GeTuiUtils;
import com.ancun.xinhu.util.MSExcelHelper;
import com.ancun.xinhu.util.PinyinUtils;
import com.ancun.xinhu.util.ReflectUtils;

@Service
public class CardInfoServiceImpl implements CardInfoService {
	private static final String PREFIX = "xinhu";
	@Autowired
	private CardInfoMapper cardInfoMapper;
	@Autowired
	private StoreService storeService;
	@Autowired
	private SysLoginLogMapper sysLoginLogMapper;
	@Autowired
	private CardPositionLinkMapper cardPositionLinkMapper;
	@Autowired
	private EntPositionMapper entPositionMapper;
	@Autowired
	private EntDepartmentMapper entDepartmentMapper;
	@Autowired
	private CardInfoSnapshotMapper cardInfoSnapshotMapper;
	@Autowired
	private CardDeptLinkMapper cardDeptLinkMapper;
	@Autowired
	private CardDeptLinkLogMapper cardDeptLinkLogMapper;
	@Autowired
	private CardPositionLinkLogMapper cardPositionLinkLogMapper;
	@Autowired
	private CardApplyEntMapper cardApplyEntMapper;
	@Autowired
	private EntInfoMapper entInfoMapper;

	@Autowired
	private UserCardApplyMapper userCardApplyMapper;
	@Autowired
	private SysConfig sysConfig;
	@Autowired
	private UserInfoMapper userInfoMapper;
	@Autowired
	private GeTuiService geTuiService;

	@Override
	public void addNewEmployee(CardInfo cardInfo, HttpServletRequest request) {
		setCardInfo(cardInfo, request);
		// 处理真实姓名拼音
		String pinyin = PinyinUtils.converterToFirstSpell(cardInfo.getTrueName());
		if (pinyin != null && pinyin.length() > 0) {
			cardInfo.setTnFirstSpell(pinyin.substring(0, 1).toUpperCase());
		}
		cardInfoMapper.insert(cardInfo);
		int cardId = cardInfo.getId();
		int entId = cardInfo.getEntId();
		savePosition(cardInfo.getEntPositionStr(), cardId, entId, 0);
		saveDept(cardInfo.getEntDepartmentStr(), cardId, entId, 0);
	}

	/* 设置卡片属性 */
	private void setCardInfo(CardInfo cardInfo, HttpServletRequest request) {
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> items = null;
		String industryId = null;
		try {
			items = upload.parseRequest(request);
			// 得到所有的文件
			Iterator<FileItem> it = items.iterator();
			while (it.hasNext()) {
				FileItem fItem = (FileItem) it.next();
				if (fItem.isFormField()) {
					ReflectUtils.reflect(cardInfo, fItem);
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		uploadUserIcon(cardInfo, items);
	}

	/**
	 * 头像图片上传
	 */
	public void uploadUserIcon(CardInfo cardInfo, List<FileItem> items) {
		String filePath = null;
		String storePath = null;
		try {
			Iterator<FileItem> it = items.iterator();
			while (it.hasNext()) {
				FileItem fItem = (FileItem) it.next();
				if (!fItem.isFormField()) {
					String fileName = URLDecoder.decode(fItem.getName(), "utf-8");
					if (StringUtils.isNotBlank(fileName)) {
						fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
						InputStream is = fItem.getInputStream();
						String attachType = fileName.substring(fileName.lastIndexOf("."));
						// 保存头像url
						storePath = "/" + PREFIX + "/web/iconUrl/" + cardInfo.getId() + "/";
						storePath = PathUtils.concatDeleteRepeat(storePath);
						filePath = storeService.saveObject(is, System.currentTimeMillis() + attachType, storePath);
						cardInfo.setIconUrl(filePath);
					}
				}
			}

		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public void updateEmployee(CardInfo cardInfo, HttpServletRequest request) {
		if (cardInfo.getId() == null)
			setCardInfo(cardInfo, request);
		int cardId = cardInfo.getId();
		int entId = cardInfo.getEntId();
		Integer status = cardInfo.getIfOnJob();
		if (status == null) {
		} else if (1 == status) {// 离职状态
			cardInfoSnapshotMapper.insert(getSnapShot(entInfoMapper.load(entId), cardInfoMapper.load(cardId)));// 组装快照对象
		} else if (status == 0) {// 恢复在职
			cardPositionLinkLogMapper.delete(cardId);
			cardDeptLinkLogMapper.delete(cardId);
			cardInfoSnapshotMapper.delete(cardId);
			cardInfo.setIfOnJob(0);
		} else {
			cardInfo.setIfOnJob(null);
		}
		String pinyin = PinyinUtils.converterToFirstSpell(cardInfo.getTrueName());
		if (pinyin != null && pinyin.length() > 0) {
			cardInfo.setTnFirstSpell(pinyin.substring(0, 1).toUpperCase());
		}
		cardInfoMapper.update(cardInfo);
		cardPositionLinkMapper.delete(cardId);
		cardDeptLinkMapper.delete(cardId);
		savePosition(cardInfo.getEntPositionStr(), cardId, entId, status);
		saveDept(cardInfo.getEntDepartmentStr(), cardId, entId, status);
	}

	// 获取快照表数据
	private CardInfoSnapshot getSnapShot(EntInfo e, CardInfo c) {
		CardInfoSnapshot shot = new CardInfoSnapshot();
		shot.setCardId(c.getId());
		shot.setUserId(c.getUserId());
		shot.setEntEntryDate(c.getEntEntryDate());
		shot.setCardType(c.getCardType());
		shot.setMobile(c.getMobile());
		shot.setActivationStatus(c.getActivationStatus());
		shot.setUserIdcard(c.getUserIdcard());
		shot.setTrueName(c.getTrueName());
		shot.setNickName(c.getNickName());
		shot.setSex(c.getSex());
		shot.setIconUrl(c.getIconUrl());
		shot.setMotto(c.getMotto());
		shot.setEname(c.getEname());
		shot.setPhone(c.getPhone());
		shot.setPhoneShort(c.getPhoneShort());
		shot.setFax(c.getFax());
		shot.setQq(c.getQq());
		shot.setWeiXin(c.getWeiXin());
		shot.setEmail(c.getEmail());
		shot.setEmail2(c.getEmail2());
		shot.setQrcodeUrl(c.getQrcodeUrl());
		shot.setSearchControlSwitch(c.getSearchControlSwitch());
		shot.setTnFirstSpell(c.getTnFirstSpell());
		shot.setGmtDynamicLastRead(null);
		shot.setGmtCreateCard(c.getGmtCreate());
		shot.setGmtModifyCard(c.getGmtModify());
		shot.setEntId(e.getId());
		shot.setOrgName(e.getOrgName());
		shot.setOrgEname(e.getOrgEname());
		shot.setOfficialWebsite(e.getOfficialWebsite());
		shot.setEntAddress(e.getEntAddress());
		shot.setCertificationStatus(e.getCertificationStatus());
		shot.setLogoUrl(e.getLogoUrl());
		return shot;
	}

	@Override
	public void deleteEmployee(CardInfo cardInfo) {
		cardInfoMapper.delete(cardInfo.getId());
	}

	@Override
	public PageResult<CardInfo> getEmployeeList(CardInfoQuery cardInfoQuery) {
		List<CardInfo> list = cardInfoMapper.queryList(cardInfoQuery);
		for (int i = 0; i < list.size(); i++) {
			CardInfo cardInfo = list.get(i);
			setPositionAndDept(cardInfo, cardInfo.getId());
		}
		int count = cardInfoMapper.queryCount(cardInfoQuery);
		return PageResult.create(cardInfoQuery, list, count);
	}

	@Override
	public CardInfo getEmployeeInfo(int id) {
		CardInfo cardInfo = cardInfoMapper.load(id);
		setPositionAndDept(cardInfo, id);
		return cardInfo;
	}

	/** 为用户设置职位和部门 */
	private void setPositionAndDept(CardInfo cardInfo, int id) {
		List<EntPosition> positionList = entPositionMapper.queryAllList(id);
		List<EntDepartment> deptLinkList = entDepartmentMapper.queryAllList(id);
		cardInfo.setEntPositionList(positionList);
		cardInfo.setEntDepartmentList(deptLinkList);
	}

	@Override
	public void updateEmployeeStatus(int id, int status) {
		cardInfoMapper.updateStatus(id, status);
	}

	@Override
	public Map<String, Object> exportEmployees(HttpServletRequest request) {
		Map<String, Object> map = getFileSteam(request);
		List<List<String>> dataList = null;
		Map<String, Object> turnMap = null;
		List<Map<String, String>> errorList = null;
		Map<String, String> errorMap = null;
		int blankRowSize = 0;
		try {
			if (map == null || map.get("fileName") == null) {
				turnMap = new HashMap<String, Object>();
				turnMap.put("iftrue", false);
				return turnMap;
			}
			int entId = Integer.parseInt(map.get("entId").toString());
			InputStream is = (InputStream) map.get("is");
			Map<String, Object> result = MSExcelHelper.readExcel(map.get("fileName").toString(), is);
			if (result == null || result.get("error") != null) {
				turnMap = new HashMap<String, Object>();
				turnMap.put("iftrue", false);
				return turnMap;
			}
			dataList = (List<List<String>>) result.get("rows");
			errorList = (List<Map<String, String>>) result.get("errorList");
			if (errorList != null && errorList.size() != 0) {
				turnMap = new HashMap<String, Object>();
				turnMap.put("errorList", errorList);
				turnMap.put("errorSize", errorList.size());
				turnMap.put("errorPath", downLoadError(errorList));
				return turnMap;
			}

			// 校验Excel中是否存在相同的手机号
			for (int i = 0; i < dataList.size(); i++) {
				if (dataList.get(i) == null)
					continue;
				String phone1 = dataList.get(i).get(2);// 手机号码
				for (int j = 0; j < dataList.size(); j++) {
					if (i == j || dataList.get(j) == null)
						continue;
					String phone2 = dataList.get(j).get(2);// 手机号码
					if (phone1.equals(phone2)) {
						errorMap = new HashMap<String, String>();
						errorMap.put("row", i + 2 + "");
						errorMap.put("error", "Excel中存在相同手机号！请修改！");
						errorList.add(errorMap);
						break;
					}
				}
			}

			if (errorList != null && errorList.size() != 0) {
				turnMap = new HashMap<String, Object>();
				turnMap.put("errorList", errorList);
				turnMap.put("errorSize", errorList.size());
				turnMap.put("errorPath", downLoadError(errorList));
				return turnMap;
			}

			// 校验该人物是否存在系统中
			for (int i = 0; i < dataList.size(); i++) {
				if (dataList.get(i) == null)
					continue;
				int count = cardInfoMapper.checkMobile(dataList.get(i).get(2));// 手机号码
				if (count != 0) {
					errorMap = new HashMap<String, String>();
					errorMap.put("row", i + 2 + "");
					errorMap.put("error", "该员工已经存在！请确认！");
					errorList.add(errorMap);
				}
			}

			if (errorList != null && errorList.size() != 0) {
				turnMap = new HashMap<String, Object>();
				turnMap.put("errorList", errorList);
				turnMap.put("errorSize", errorList.size());
				turnMap.put("errorPath", downLoadError(errorList));
				return turnMap;
			}

			CardInfo cardInfo = null;
			List<String> data = null;
			for (int i = 0; i < dataList.size(); i++) {
				data = dataList.get(i);
				if (data == null) {
					blankRowSize++;
					continue;
				}
				cardInfo = new CardInfo();
				cardInfo.setEntId(entId);
				for (int j = 0; j < data.size(); j++) {
					String value = data.get(j);
					buildModel(j, cardInfo, value);
				}
				String pinyin = PinyinUtils.converterToFirstSpell(cardInfo.getTrueName());
				if (pinyin != null && pinyin.length() > 0) {
					cardInfo.setTnFirstSpell(pinyin.substring(0, 1).toUpperCase());
				}
				cardInfoMapper.insert(cardInfo);
				CardInfo resultCard = cardInfoMapper.queryCardInfo(cardInfo);
				int userId = resultCard.getId();// 获取生成的用户ID
				cardPositionLinkMapper.delete(userId);
				cardDeptLinkMapper.delete(userId);
				savePosition(cardInfo.getEntPositionStr(), userId, entId, 0);// 处理职位
				saveDept(cardInfo.getEntDepartmentStr(), userId, entId, 0);// 处理部门
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		turnMap = new HashMap<String, Object>();
		turnMap.put("iftrue", true);
		turnMap.put("successSize", dataList.size() - blankRowSize);
		return turnMap;

	}

	/**
	 * 获取文件对象
	 * 
	 * @param request
	 * @return <p>
	 *         author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	 *         create at: 2015年6月25日 下午4:35:26
	 */
	private Map<String, Object> getFileSteam(HttpServletRequest request) {
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		Map<String, Object> resultMap = null;
		List<FileItem> items = null;
		try {
			items = upload.parseRequest(request);
			// 得到所有的文件
			Iterator<FileItem> it = items.iterator();
			resultMap = new HashMap<String, Object>();
			while (it.hasNext()) {
				FileItem fItem = (FileItem) it.next();
				if ("entId".equals(fItem.getFieldName())) {
					resultMap.put("entId", fItem.getString());
				}
				if (!fItem.isFormField()) {
					String fileName = URLDecoder.decode(fItem.getName(), "utf-8");
					if (StringUtils.isNotBlank(fileName)) {
						fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
						InputStream is = fItem.getInputStream();
						resultMap.put("is", is);
						resultMap.put("fileName", fileName);
					}
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return resultMap;
	}

	/** 获取员工基本信息，职位信息，部门信息 */
	private void buildModel(int i, CardInfo cardInfo, String value) {
		if (i == 0)
			cardInfo.setTrueName(value);// 真实姓名
		else if (i == 1)
			cardInfo.setSex(value);// 性别
		else if (i == 2)
			cardInfo.setMobile(value);// 手机
		else if (i == 3)
			cardInfo.setEmail(value);// 邮箱地址
		else if (i == 4)
			cardInfo.setUserIdcard(value);// 身份证
		else if (i == 5)
			cardInfo.setEntPositionStr(value);// 职位码表
		else if (i == 6)
			cardInfo.setEntDepartmentStr(value);// 部门码表
	}

	/** 保存职位 */
	private void savePosition(String positions, int userId, int entId, Integer islastlink) {
		EntPosition entPosition = null;
		if (positions != null && positions.indexOf(",") != -1) {
			String[] arrays = positions.split(",");
			for (String value : arrays) {
				entPosition = new EntPosition();
				entPosition.setPositionName(value);
				entPosition.setEntId(entId);
				checkAndAddPosition(entPosition, userId, islastlink);
			}
		} else if (positions != null && !"".equals(positions)) {
			entPosition = new EntPosition();
			entPosition.setPositionName(positions);
			entPosition.setEntId(entId);
			checkAndAddPosition(entPosition, userId, islastlink);
		}
	}

	/** 保存部门 */
	private void saveDept(String positions, int userId, int entId, Integer islastlink) {
		EntDepartment entDepartment = null;
		if (positions != null && positions.indexOf(",") != -1) {
			String[] arrays = positions.split(",");
			for (String value : arrays) {
				entDepartment = new EntDepartment();
				entDepartment.setDeptName(value);
				entDepartment.setEntId(entId);
				checkAndAddDepartment(entDepartment, userId, islastlink);
			}
		} else if (positions != null && !"".equals(positions)) {
			entDepartment = new EntDepartment();
			entDepartment.setDeptName(positions);
			entDepartment.setEntId(entId);
			checkAndAddDepartment(entDepartment, userId, islastlink);
		}
	}

	/**
	 * 通过码表校验职位（没有的话码表新增一条，有的话直接返回码表 码值）
	 * 
	 * @param userId
	 * @param islastlink
	 */
	private void checkAndAddPosition(EntPosition entPosition, int userId, Integer islastlink) {
		Integer code = entPositionMapper.queryCode(entPosition);
		CardPositionLink cardPositionLink = new CardPositionLink();
		if (code != null) {
			cardPositionLink.setPositionId(code);
			cardPositionLink.setCardId(userId);
		} else {
			entPositionMapper.insert(entPosition);
			cardPositionLink.setPositionId(entPosition.getId());
			cardPositionLink.setCardId(userId);
		}
		cardPositionLinkMapper.insert(cardPositionLink);
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("cardId", userId);
		paraMap.put("positionId", cardPositionLink.getPositionId());
		paraMap.put("islastlink", islastlink);
		cardPositionLinkLogMapper.savePositionLog(paraMap);
	}

	/**
	 * 通过码表校验部门（没有的话码表新增一条，有的话直接返回码表 码值）
	 * 
	 * @param userId
	 * @param islastlink
	 */
	private void checkAndAddDepartment(EntDepartment entDepartment, int userId, Integer islastlink) {
		Integer code = entDepartmentMapper.queryCode(entDepartment);
		CardDeptLink cardDeptLink = new CardDeptLink();
		if (code != null) {
			cardDeptLink.setDeptId(code);
			cardDeptLink.setCardId(userId);
		} else {
			entDepartmentMapper.insert(entDepartment);
			cardDeptLink.setDeptId(entDepartment.getId());
			cardDeptLink.setCardId(userId);
		}
		cardDeptLinkMapper.insert(cardDeptLink);
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("cardId", userId);
		paraMap.put("deptId", cardDeptLink.getDeptId());
		paraMap.put("islastlink", islastlink);
		cardDeptLinkLogMapper.saveDeptLog(paraMap);// 插入到日志表
	}

	@Override
	public PageResult<CardInfo> getRegistUserList(CardInfoQuery cardInfoQuery) {
		List<CardInfo> list = cardInfoMapper.queryAppList(cardInfoQuery);
		int count = cardInfoMapper.queryAppCount(cardInfoQuery);
		return PageResult.create(cardInfoQuery, list, count);
	}

	@Override
	public void passUser(CardInfo cardInfo) {
		cardInfoMapper.update(cardInfo);
		updateUserStatus(cardInfo.getId(), 1, cardInfo.getUnpassMes());

		// 推送给用户消息
		UserInfo userInfo = userInfoMapper.getClientIdByCardId(cardInfo.getId());
		geTuiService.transmissionTemplate(userInfo, GeTuiUtils.CODE_ApplyBindEntPass, "通过审核");

	}

	@Override
	public void unpassUser(CardInfo cardInfo) {
		updateUserStatus(cardInfo.getId(), 2, cardInfo.getUnpassMes());
		// 直接删除记录
		// cardApplyEntMapper.delete(cardInfo.getId());
		// 删除名片通知对方后台删除掉对方本地名片：code:1 data{} 姓名
		UserInfo userInfo = userInfoMapper.getClientIdByCardId(cardInfo.getId());
		geTuiService.transmissionTemplate(userInfo, GeTuiUtils.CODE_ApplyBindEntUnpass,
				"审核不通过：" + cardInfo.getUnpassMes());
	}

	/**
	 * 修改申请用户的状态
	 * 
	 * @param mes
	 */
	private void updateUserStatus(int id, int status, String mes) {
		CardApplyEnt cardApplyEnt = new CardApplyEnt();
		cardApplyEnt.setCardId(id);
		cardApplyEnt.setBindStatus(status);
		cardApplyEnt.setGmtBind(new Date());
		cardApplyEnt.setUnpassMes(mes);
		cardApplyEntMapper.update(cardApplyEnt);
	}

	@Override
	public PageResult<Map<String, Object>> getCardExchangingRecord(CardInfoQuery cardInfoQuery) {
		List<Map<String, Object>> list = cardInfoMapper.getCardExchangingRecord(cardInfoQuery);
		int count = cardInfoMapper.queryExchangRecordCount(cardInfoQuery);
		return PageResult.create(cardInfoQuery, list, count);
	}

	@Override
	public Map<String, Object> getOneRecord(Map<String, Integer> paraMap) {
		return cardInfoMapper.getOneRecord(paraMap);
	}

	@Override
	public Map<String, Object> getExchangeCardInfo(Map<String, Object> paraMap) {
		Map<String, Object> counts = new HashMap<String, Object>();
		String id = paraMap.get("id").toString();
		int cardExchangeCount = userCardApplyMapper.getCardExchangeCount(id);// 名片数量
		counts.put("cardExchangeCount", cardExchangeCount);
		int cardExchangePositionCount = userCardApplyMapper.getCardExchangePositionCount(id);// 职位数量
		counts.put("cardExchangePositionCount", cardExchangePositionCount);
		int cardExchangeEntCount = userCardApplyMapper.getCardExchangeEntCount(id);// 企业数量
		counts.put("cardExchangeEntCount", cardExchangeEntCount);
		Integer cardExchangeEntOrder = userCardApplyMapper.getCardExchangeEntOrder(paraMap);// 公司排名
		counts.put("cardExchangeEntOrder", cardExchangeEntOrder == null ? 0 : cardExchangeEntOrder);
		// Integer cardExchangeDeptOrder =
		// userCardApplyMapper.getCardExchangeDeptOrder(paraMap);//部门排名
		// counts.put("cardExchangeDeptOrder",
		// cardExchangeDeptOrder==null?0:cardExchangeDeptOrder);
		Integer cardExchangeIndustryOrder = userCardApplyMapper.getCardExchangeIndustryOrder(paraMap);// 行业排名
		counts.put("cardExchangeIndustryOrder", cardExchangeIndustryOrder == null ? 0 : cardExchangeIndustryOrder);
		Integer cardExchangeXinhuOrder = userCardApplyMapper.getCardExchangeXinhuOrder(paraMap);// 信乎排名
		counts.put("cardExchangeXinhuOrder", cardExchangeXinhuOrder == null ? 0 : cardExchangeXinhuOrder);

		return counts;
	}

	@Override
	public void uploadIcon(HttpServletRequest request) {
		// TODO Auto-generated method stub

	}

	private String downLoadError(List<Map<String, String>> errorList) {
		List<List<Object>> errors = new ArrayList<List<Object>>();
		List<Object> list = null;
		list = new ArrayList<Object>();
		list.add("错误行");
		list.add("错误信息");
		errors.add(list);
		for (Map<String, String> map : errorList) {
			list = new ArrayList<Object>();
			list.add(map.get("row"));
			list.add(map.get("error"));
			errors.add(list);
		}
		String path = sysConfig.get("store.dir") + "xinhu/web/temp/";
		return MSExcelHelper.writeXlsText(path, errors);
	}

	@Override
	public String getImageTempUrl(HttpServletRequest request) throws Exception {
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		String type = null;
		List<FileItem> items = upload.parseRequest(request);
		InputStream is = null;
		int imageSize = 2000;
		String sizeError = "附件不能大于2M";
		List<InputStream> lists = new ArrayList<InputStream>();
		String attachType = null;
		try {
			Iterator<FileItem> it = items.iterator();
			while (it.hasNext()) {
				FileItem fItem = (FileItem) it.next();
				String fieldName = fItem.getFieldName();
				if("imageType".equals(fieldName))
					type = fItem.getString();
				if("entImage".equals(fieldName)){
					imageSize = 20480;
					sizeError = "附件不能大于20M";
				}
				if (!fItem.isFormField()) {
					String fileName = URLDecoder.decode(fItem.getName(), "utf-8");
					attachType = fileName.substring(fileName.lastIndexOf("."));
					if (StringUtils.isNotBlank(fileName)) {
						fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
						is = fItem.getInputStream();
						lists.add(is);
					}
				}
			}

		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		if(lists.size()==2){
			if("2".equals(type))
				is = lists.get(1);
			else
				is = lists.get(0);
		}else{
			is = lists.get(0);
		}
		
		int size = is.available();
		
		if(size/1024>imageSize){
			return sizeError;
		}
		
		String name = System.currentTimeMillis() + attachType;
		String path = sysConfig.get("store.dir") + "xinhu/web/temp/" + name;
		FileOutputStream out = new FileOutputStream(path);
		byte buffer[] = new byte[1024];
		int len = 0;
		while ((len = is.read(buffer)) > 0) {
			out.write(buffer, 0, len);
		}
		is.close();
		out.close();
		return name;
	}

}

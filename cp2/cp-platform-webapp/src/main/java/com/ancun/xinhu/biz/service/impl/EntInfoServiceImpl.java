package com.ancun.xinhu.biz.service.impl;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ancun.products.basebiz.service.SMSService;
import com.ancun.products.basebiz.service.StoreService;
import com.ancun.products.core.exception.ServiceException;
import com.ancun.products.core.model.PageResult;
import com.ancun.products.core.utils.PathUtils;
import com.ancun.products.core.utils.StringUtils;
import com.ancun.products.core.web.utils.CookieUtil;
import com.ancun.xinhu.biz.mappers.CardInfoMapper;
import com.ancun.xinhu.biz.mappers.EntDepartmentMapper;
import com.ancun.xinhu.biz.mappers.EntIndustryLinkMapper;
import com.ancun.xinhu.biz.mappers.EntInfoMapper;
import com.ancun.xinhu.biz.mappers.EntPaperMapper;
import com.ancun.xinhu.biz.mappers.EntPositionMapper;
import com.ancun.xinhu.biz.mappers.EntShieldMapper;
import com.ancun.xinhu.biz.mappers.SysLoginLogMapper;
import com.ancun.xinhu.biz.mappers.SysNoticeMapper;
import com.ancun.xinhu.biz.service.EntInfoService;
import com.ancun.xinhu.domain.model.EntDepartment;
import com.ancun.xinhu.domain.model.EntIndustryLink;
import com.ancun.xinhu.domain.model.EntInfo;
import com.ancun.xinhu.domain.model.EntPaper;
import com.ancun.xinhu.domain.model.EntPosition;
import com.ancun.xinhu.domain.model.EntShield;
import com.ancun.xinhu.domain.model.SysLoginLog;
import com.ancun.xinhu.domain.model.SysNotice;
import com.ancun.xinhu.domain.query.CardInfoQuery;
import com.ancun.xinhu.domain.query.EntIndustryLinkQuery;
import com.ancun.xinhu.domain.query.EntPaperQuery;
import com.ancun.xinhu.domain.query.EntShieldQuery;
import com.ancun.xinhu.domain.query.SysNoticeQuery;
import com.ancun.xinhu.util.ReflectUtils;

@Service
public class EntInfoServiceImpl implements EntInfoService {
	private static final String PREFIX = "xinhu";
	
	@Autowired
	private EntInfoMapper entInfoMapper;
	
	@Autowired
	private SysLoginLogMapper sysLoginLogMapper;
	@Autowired
	private SMSService smsService;
	@Autowired
	private SysNoticeMapper sysNoticeMapper;
	@Autowired
	private EntIndustryLinkMapper entIndustryLinkMapper;
	@Autowired
	private EntPaperMapper entPaperMapper;
	@Autowired
	private EntShieldMapper entShieldMapper;
	@Autowired
	private CardInfoMapper cardInfoMapper;
	
	@Autowired
	private StoreService storeService;
	
	@Autowired
	private EntPositionMapper entPositionMapper;
	@Autowired
	private EntDepartmentMapper entDepartmentMapper;
	@Override
	public void registEnt(EntInfo entInfo,HttpServletRequest request) {
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> items = null;
		String industryId = null;
		if(entInfo.getId() == null && entInfo.getMobile() == null){
			try {
				items = upload.parseRequest(request);
				// 得到所有的文件
				Iterator<FileItem> it = items.iterator();
				while (it.hasNext()) {
					FileItem fItem = (FileItem) it.next();
					if (fItem.isFormField()) {
						if("industryId".equals(fItem.getFieldName())){
							industryId = entInfo.getIndustryId();
						}
						ReflectUtils.reflect(entInfo, fItem);
						if("industryId".equals(fItem.getFieldName())){
							if(industryId!=null){
								String ids = industryId + ";" + new String(fItem.getString().getBytes("ISO-8859-1"),"UTF-8");
								entInfo.setIndustryId(ids);
							}
						}
					}
				}
			} catch (Exception e) {
				throw new ServiceException(e.getMessage());
			}
		}
		
		if(entInfo.getId() == null && entInfo.getMobile() != null){//注册第一步
			entInfoMapper.insert(entInfo);
		}else{
			int id = entInfo.getId();
			if(items != null)
				uploadUserIcon(id,entInfo,items);
			entInfoMapper.update(entInfo);
			//saveZsFile(entInfo.getPaperList(),id);
			saveIndustryLink(entInfo.getIndustryId(),id);
		}
	}
	
	/**保存证书
	 * @param entId 
	 * @param list */
	private void saveZsFile(List<EntPaper> list, int entId){
		entPaperMapper.delete(entId);
		for (EntPaper entPaper : list) {
			entPaperMapper.insert(entPaper);
		}
	}
	
	/**保存行业类别*/
	private void saveIndustryLink(String industrys,int entId){
		EntIndustryLink entIndustryLink = null;
		if(industrys!=null && industrys.indexOf(";")!=-1){//以;分割行业集合
			String [] array = industrys.split(";");
			for (String value : array) {
				entIndustryLink = new EntIndustryLink();
				entIndustryLink.setEntId(entId);
				//entIndustryLink.setIndustryId(Integer.parseInt(value));
				entIndustryLink.setIndustryId(value);
				entIndustryLinkMapper.insert(entIndustryLink);
			}
		}else if(industrys!=null){
			entIndustryLink = new EntIndustryLink();
			entIndustryLink.setEntId(entId);
			entIndustryLink.setIndustryId(industrys);
			entIndustryLinkMapper.insert(entIndustryLink);
		}
		
	}

	@Override
	public EntInfo getEntInfo(EntInfo entInfo, String clientAddr) {
		EntInfo resultEnt = entInfoMapper.checkLogin(entInfo);
		SysLoginLog sysLoginLog = new SysLoginLog();
		sysLoginLog.setUserType(1);
		
		if(resultEnt == null){//验证失败
			sysLoginLog.setLoginStatus(0);
			sysLoginLog.setUserName(entInfo.getMobile());
			sysLoginLog.setIp(clientAddr);
		}else{//验证成功
			sysLoginLog.setLoginStatus(1);
			sysLoginLog.setUserName(resultEnt.getMobile());
			sysLoginLog.setIp(clientAddr);
		}
		sysLoginLogMapper.insert(sysLoginLog);
		return resultEnt;
	}

	@Override
	public void logoutEnt(EntInfo entInfo) {
		// TODO Auto-generated method stub
		
	}
	
	private static final String COOKIE_KEY = "phone_code";
	/**
	 * 手机注册随机验证码
	 * @param mobile
	 * @return
	 * @throws IOException 
	 */
	public String getLoginCode(String mobile, HttpServletResponse response) throws IOException{
		String code=getFixLenthString(4);
		smsService.send(mobile, "您正在注册信乎个人账号，验证码为："+code+",信乎将与您一起打造诚信的信息世界。");
		CookieUtil.save(COOKIE_KEY, code, -1, response, "UTF-8");
		response.flushBuffer();
		return code;
	}
	
	/**
	 * 手机找回密码
	 * @param mobile
	 * @return
	 * @throws IOException 
	 */
	public String getUpdatePwdCode(String mobile, HttpServletResponse response) throws IOException{
		String code=getFixLenthString(4);
		smsService.send(mobile, "您正在找回信乎密码，验证码为："+code+",信乎将与您一起打造诚信的信息世界。");
		CookieUtil.save(COOKIE_KEY, code, -1, response, "UTF-8");
		response.flushBuffer();
		return code;
	}
	
	/**
	 * 获取更改手机绑定时的随机验证码
	 * @param mobile
	 * @return
	 * @throws IOException 
	 */
	public String getCode(String mobile, HttpServletResponse response) throws IOException{
		String code=getFixLenthString(4);
		smsService.send(mobile, "您正在更换信乎绑定手机号码，验证码："+code+",信乎将与您一起打造诚信的信息世界。");
		CookieUtil.save(COOKIE_KEY, code, -1, response, "UTF-8");
		response.flushBuffer();
		return code;
	}
	
	
	/**
	 * 返回长度为【strLength】的随机数，在前面补0  
	 * @param strLength
	 * @return
	 */
	private static String getFixLenthString(int strLength) {  
	    Random rm = new Random();  
	    // 获得随机数  
	    double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);  
	    // 将获得的获得随机数转化为字符串  
	    String fixLenthString = String.valueOf(pross);  
	    // 返回固定的长度的随机数  
	    return fixLenthString.substring(1, strLength + 1);  
	}

	@Override
	public Integer checkMobile(EntInfo entInfo) {
		return entInfoMapper.checkMobile(entInfo);
	}

	@Override
	public PageResult<SysNotice> getSysNoticeList(SysNoticeQuery sysNoticeQuery) {
		List<SysNotice> list = sysNoticeMapper.queryList(sysNoticeQuery);
		int count = sysNoticeMapper.queryCount(sysNoticeQuery);
		return PageResult.create(sysNoticeQuery, list, count);
	}

	@Override
	public EntInfo getEntInfo(int entId) {
		return entInfoMapper.load(entId);
	}

	@Override
	public List<EntIndustryLink> getEntIndustryList(int entId) {
		EntIndustryLinkQuery entIndustryLinkQuery = new EntIndustryLinkQuery();
		entIndustryLinkQuery.setEntId(entId);
		return entIndustryLinkMapper.queryList(entIndustryLinkQuery);
	}

	@Override
	public List<EntPaper> getEntPaperList(int entId) {
		EntPaperQuery entPaperQuery = new EntPaperQuery();
		entPaperQuery.setEntId(entId);
		return entPaperMapper.queryList(entPaperQuery);
	}

	@Override
	public int checkPwd(EntInfo entInfo) {
		return entInfoMapper.checkPwd(entInfo);
	}

	@Override
	public void updatePwd(EntInfo entInfo) {
		entInfoMapper.update(entInfo);
	}

	@Override
	public void updateMobile(EntInfo entInfo) {
		entInfoMapper.update(entInfo);
	}

	@Override
	public void addShieldUserOrEnt(EntShield entShield) {
		entShieldMapper.insert(entShield);
	}

	@Override
	public Integer getEntIdByName(String name) {
		return entInfoMapper.getEntCodeByName(name);
	}

	@Override
	public Integer getUserIdByName(String name) {
		return cardInfoMapper.getUserCodeByName(name);
	}

	@Override
	public void deleteShieldUserOrEnt(EntShield entShield) {
		entShieldMapper.deleteBySelect(entShield);
	}

	@Override
	public boolean addPositionLabel(EntPosition entPosition) {
		Integer code = entPositionMapper.queryCode(entPosition);
		if(code != null){
			return false;
		}else{
			entPositionMapper.insert(entPosition);
			return true;
		}
	}

	@Override
	public boolean deletePositionLabel(EntPosition entPosition) {
		Integer count = entPositionMapper.queryExistCount(entPosition.getId());
		if(count==0){
			entPositionMapper.delete(entPosition.getId());
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean addDeptLabel(EntDepartment entDepartment) {
		Integer code = entDepartmentMapper.queryCode(entDepartment);
		if(code != null){
			return false;
		}else{
			entDepartmentMapper.insert(entDepartment);
			return true;
		}
		
	}

	@Override
	public boolean deleteDeptLabel(EntDepartment entDepartment) {
		Integer count = entDepartmentMapper.queryExistCount(entDepartment.getId());
		if(count==0){
			entDepartmentMapper.delete(entDepartment.getId());
			return true;
		}else{
			return false;
		}
		
	}

	@Override
	public PageResult<Map<String, Object>> getShieldList(EntShieldQuery entShieldQuery) {
		List<Map<String,Object>> list = null;
		if("1".equals(entShieldQuery.getShieldType()))
			list = entInfoMapper.getUserShieldList(entShieldQuery);
		else if("2".equals(entShieldQuery.getShieldType()))
			list = entInfoMapper.getEntShieldList(entShieldQuery);
		int count = entInfoMapper.queryShieldCount(entShieldQuery);
		return PageResult.create(entShieldQuery, list, count);
	}

	@Override
	public EntInfo getEntInfoByMobile(String mobile) {
		return entInfoMapper.getEntInfoByMobile(mobile);
	}

	@Override
	public Map<String, Integer> getCardsCount(Integer entId) {
		Map<String,Integer> resultMap = new HashMap<String, Integer>();
		CardInfoQuery cardInfoQuery = new CardInfoQuery();
		cardInfoQuery.setEntId(entId);
		Integer cardCount = cardInfoMapper.querychangeCount(cardInfoQuery);
		Integer flowerCount = cardInfoMapper.queryCount(cardInfoQuery);
		resultMap.put("cardCount", cardCount);
		resultMap.put("flowerCount", flowerCount);
		return resultMap;
	}
	
	
	/**
	 * 图片上传接口
	 */
	public void uploadUserIcon(int entId,EntInfo entInfo,List<FileItem> items){
		String filePath=null;
		String storePath = null;
		String ewm = "weiXinUrl";
		String logo = "logoUrl";
		String zs = "zsfile";
		List<EntPaper> paperList = null;
		try {
			Iterator<FileItem> it = items.iterator();
			paperList = new ArrayList<EntPaper>();
			entInfo.setPaperList(paperList);
			while (it.hasNext()) {
				FileItem fItem = (FileItem) it.next();
				if (!fItem.isFormField()) {
					 String fileName = URLDecoder.decode(fItem.getName(), "utf-8");
					 String fieldName = fItem.getFieldName();
					if (StringUtils.isNotBlank(fileName)) {
						fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
						InputStream is = fItem.getInputStream();
						String attachType = fileName.substring(fileName.lastIndexOf("."));
						if(ewm.equals(fieldName)){//保存二维码图片
							storePath = "/" + PREFIX + "/web/" + ewm + "/" + entId + "/";
							storePath = PathUtils.concatDeleteRepeat(storePath);
							filePath = storeService.saveObject(is, System.currentTimeMillis() + attachType,storePath);
							entInfo.setWeiXinUrl(filePath);
						}else if(logo.equals(fieldName)){//保存公司logo图片
							is = dealLogo(is,entInfo);
							storePath = "/" + PREFIX + "/web/" + logo + "/" + entId + "/";
							storePath = PathUtils.concatDeleteRepeat(storePath);
							filePath = storeService.saveObject(is, System.currentTimeMillis() + attachType,storePath);
							entInfo.setLogoUrl(filePath);
						}else if(zs.equals(fieldName)){//上传证书
							storePath = "/" + PREFIX + "/web/" + zs + "/" + entId + "/";
							storePath = PathUtils.concatDeleteRepeat(storePath);
							filePath = storeService.saveObject(is, System.currentTimeMillis() + attachType,storePath);
							entInfo.setEntImage(filePath);
						}
					}
				}
			}
						
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
	//处理logo裁剪后的效果
	private InputStream dealLogo(InputStream is, EntInfo entInfo) throws IOException{
		if(entInfo!=null && entInfo.getX()!=null){
	        ImageInputStream iis = null;  
	        try {  
	            Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName("jpg");  
	            ImageReader reader = it.next();  
	            // 获取图片流  
	            iis = ImageIO.createImageInputStream(is);  
	            reader.setInput(iis, true);  
	            ImageReadParam param = reader.getDefaultReadParam();  
	            Rectangle rect = new Rectangle(entInfo.getX(), entInfo.getY(), entInfo.getW(), entInfo.getH());  
	            // 提供一个 BufferedImage，将其用作解码像素数据的目标。  
	            param.setSourceRegion(rect);  
	            BufferedImage bi = reader.read(0, param); 
	            bi.flush(); 
	             
	            ByteArrayOutputStream bs = new ByteArrayOutputStream();  
	             
	            ImageOutputStream imOut; 
	            try { 
	                imOut = ImageIO.createImageOutputStream(bs); 
	                ImageIO.write(bi, "png",imOut); 
	                is= new ByteArrayInputStream(bs.toByteArray()); 
	            } catch (IOException e) { 
	                e.printStackTrace(); 
	            }  
	            
	            // 保存新图片  
	        } finally {  
	            if (is != null)  
	                is.close();  
	            if (iis != null)  
	                iis.close();  
	        }  
	        return is;
		}else{
			return is;
		}
	}

	@Override
	public boolean validatePhone(String code, HttpServletRequest request) {
		String validatePhoneCode = CookieUtil.read(COOKIE_KEY, request, "UTF-8");

		if (validatePhoneCode == null) {
			return false;
		}

		return StringUtils.equalsIgnoreCase(validatePhoneCode, code);
	}

	@Override
	public void updateDeptLabel(EntDepartment entDepartment) {
		entDepartmentMapper.update(entDepartment);
	}

	@Override
	public void updatePositionLabel(EntPosition entPosition) {
		entPositionMapper.update(entPosition);
	}
	
}

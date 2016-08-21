/**    
 * @author zhuwt 
 * @Description: TODO(用一句话描述该文件做什么)   
 * @Package com.master.util.common   
 * @Title: StringUtils.java   
 * @date 2015年5月26日 下午1:10:30   
 * @version V1.0 
 * @说明  代码版权归 杭州反盗版中心有限公司 所有
 */
package com.master.util.common;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhuwt
 * @Description: 处理各种字符串的工具类
 * @ClassName: StringUtils.java
 * @date 2015年5月26日 下午1:10:30
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */

public class StringUtils {

	/**
	 * @Auther: maomy
	 * @Description: 判断是不是非空 是空返回 true
	 * @Date:2013-10-17下午4:33:04
	 * @param json
	 * @return
	 * @return boolean
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static boolean isNotEmpty(String value) {
		if (value == null || value.equals("") || "null".equals(value)) {
			return false;
		}
		return true;
	}

	/**
	 * @Auther: maomy
	 * @Description: 验证手机号
	 * @Date:2013-10-28下午2:55:23
	 * @param mobiles
	 * @return
	 * @return boolean
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static boolean isMobileNO(String mobiles) {
		if (null == mobiles || "".equals(mobiles))
			return false;
		Pattern p = Pattern.compile("^(([0-9][0-9][0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * @Auther: maomy
	 * @Description: 密码长度 6-20位
	 * @Date:2013-10-30下午4:51:30
	 * @param str
	 * @return
	 * @return boolean
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static boolean lenghtVerify(String str, int min, int max) {
		if (null == str || "".equals(str))
			return false;
		if (min <= str.length() && str.length() <= max)
			return true;
		return false;
	}

	/**
	 * @Auther: maomy
	 * @Description: 只能是中文或者数字或者英文
	 * @Date:2013-11-8下午4:24:36
	 * @param str
	 * @return
	 * @return boolean
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static boolean onlyNumAndCH(String str) {
		if (null == str || "".equals(str))
			return false;
		if (onlyNum(str) || onlyCH(str) || onlyAZ(str))
			return true;
		return false;
	}

	/**
	 * @Auther: maomy
	 * @Description: 只能是数字
	 * @Date:2013-11-8下午4:26:37
	 * @param str
	 * @return
	 * @return boolean
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static boolean onlyNum(String str) {
		if (null == str || "".equals(str))
			return false;
		Pattern p = Pattern.compile("^[0-9]*$");
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * @Auther: maomy
	 * @Description: 只能是中文
	 * @Date:2013-11-8下午4:26:37
	 * @param str
	 * @return
	 * @return boolean
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static boolean onlyCH(String str) {
		if (null == str || "".equals(str))
			return false;
		Pattern p = Pattern.compile("^[\u4e00-\u9fa5]{0,}$");
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * @Auther: maomy
	 * @Description: 只能是英文
	 * @Date:2013-11-8下午4:26:37
	 * @param str
	 * @return
	 * @return boolean
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static boolean onlyAZ(String str) {
		if (null == str || "".equals(str))
			return false;
		Pattern p = Pattern.compile("^[A-Za-z]+$");
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * @Auther: xuxg
	 * @Description: TODO(去除号码前的字符串)
	 * @Date:2013-12-13上午9:49:41
	 * @param con
	 * @return
	 * @return String
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static String deletePnonePart(String inPhone) {
		if (null == inPhone || "".equals(inPhone))
			return "000000";

		String phone = null;
		if (null != inPhone && inPhone.indexOf("+86") != -1) {
			phone = inPhone.substring(3, inPhone.length()).replace(" ", "");
		} else if (null != inPhone && inPhone.indexOf("-") != -1) {
			phone = inPhone.replace("-", "").replace(" ", "");
		} else
			phone = inPhone.replace(" ", "");
		return phone;
	}
	
	/**
	 * @Auther: wudd  
	 * @Description: 去除空格 
	 * @Date:2015年3月26日下午2:18:00
	 * @param str
	 * @return  
	 * @return String 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
	
	/**
	 * @Auther: zhuwt 
	 * @Description: 计算 并保留两位小数
	 * @Date:2015年5月14日下午10:03:39
	 * @param num
	 * @return  
	 * @return String 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static String saveTwoDecimals(Float num){
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(num);
	}
	
	/**
	 * @Auther: zhuwt 
	 * @Description:验证邮箱
	 * @Date:2015年6月20日下午1:27:22
	 * @param email
	 * @return  
	 * @return boolean 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static boolean checkEmail(String email) {
		Pattern pattern = Pattern
				.compile("[_a-zA-Z0-9\\-]+(\\.[_a-zA-Z0-9\\-]*)*@[a-zA-Z0-9\\-]+([\\.][a-zA-Z0-9\\-]+)+$");
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	
	/**
	 * @Auther: zhuwt 
	 * @Description: 字母和数字 
	 * @Date:2015年7月11日下午1:32:27
	 * @param str
	 * @return  
	 * @return boolean 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static boolean checkNumAndAz(String str) {
		return str.matches("[0-9A-Za-z_]*");
	}
}

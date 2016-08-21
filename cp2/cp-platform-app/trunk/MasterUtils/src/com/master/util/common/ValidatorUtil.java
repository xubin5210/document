package com.master.util.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtil {
	
	
	/**
	 * 判断手机是否符合规则
	 * 
	 * @author chenjh
	 * @version Jun 21, 2010
	 * @param phone
	 * @return
	 */
	public static boolean checkPhone(String phone) {
		Pattern pattern = Pattern
				.compile("^13\\d{9}||15\\d{9}||14\\d{9}||18\\d{9}$");
		Matcher matcher = pattern.matcher(phone);
		return matcher.matches();
	}
	
	/**
	 * 判断邮箱名是否符合规则
	 * 
	 * @author chenjh
	 * @version Jun 21, 2010
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		Pattern pattern = Pattern
				.compile("[_a-zA-Z0-9\\-]+(\\.[_a-zA-Z0-9\\-]*)*@[a-zA-Z0-9\\-]+([\\.][a-zA-Z0-9\\-]+)+$");
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	
	/**
	 * 是否是纯数字 首位是正整数
	 * 
	 * @author chenjh
	 * @version Jun 15, 2011
	 * @param str
	 * @return 纯数字 true，非数字 false；
	 */
	public static boolean isNum(String str) {
		Pattern pattern1 = Pattern.compile("^[1-9][0-9]*$");
		Matcher matcher1 = pattern1.matcher(str);
		if (matcher1.matches()) {
			return true;
		}
		return false;
	}
	
	/**
	 * 验证密码长度
	 * 
	 * @param pwd
	 */
	public static boolean validatPwd(String pwd) {
		if (pwd.length() < 6 || pwd.length() > 16) {
			return false;
		}
		return true;
	}
	public static boolean isBlank(String str) {
		return str == null || str.trim().length() == 0;
	}
}

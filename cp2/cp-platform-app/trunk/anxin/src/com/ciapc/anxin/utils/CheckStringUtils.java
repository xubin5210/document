package com.ciapc.anxin.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import com.ciapc.anxin.common.pojo.JobPojo;
import com.ciapc.anxin.common.view.CustomEditText;
import com.master.util.common.DialogUtil;
import com.master.util.common.StringUtils;

/**
 * 
 * @author zhuwt
 * @Description: 校验账号密码工具类
 * @ClassName: CheckStringUtils.java
 * @date 2015年6月2日 下午1:49:02
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */
public class CheckStringUtils {

	/**
	 * @Auther: zhuwt
	 * @Description: 校验密码 长度 规则
	 * @Date:2015年6月2日下午1:51:48
	 * @param pwd
	 * @return
	 * @return boolean
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static boolean checkPwd(Context context, String pwd) {
		// 不为空
		if (!StringUtils.isNotEmpty(pwd)) {
			DialogUtil.showSystemToast(context, "密码不能为空");
			return false;
		}
		if (!StringUtils.lenghtVerify(pwd, 6, 12)) {
			DialogUtil.showSystemToast(context, "密码不符合长度");
			return false;
		}
		if (StringUtils.onlyAZ(pwd) || StringUtils.onlyNum(pwd)) {
			DialogUtil.showSystemToast(context, "不能为全是字母或数字");
			return false;
		}
		if (pwd.indexOf("") > 0) {
			DialogUtil.showSystemToast(context, "不能有空格");
			return false;
		}
		if ((checkPattern("[~!@#$%^&*()_+.;'<>,^]", pwd)
				&& checkPattern("[A-Za-z]", pwd) && checkPattern("[0-9]", pwd))
				|| (checkPattern("[A-Za-z]", pwd) && checkPattern("[0-9]", pwd))
				|| (checkPattern("[~!@#$%^&*()_+.;'<>,^]", pwd) && checkPattern(
						"[0-9]", pwd))) {
			DialogUtil
					.showSystemToast(context, "字母、数字或者英文符号任意两种组合，最短6位，区分大小写！");
			return false;
		}
		return true;
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 验证密码
	 * @Date:2015年7月7日下午4:07:05
	 * @param str
	 * @param pwd
	 * @return
	 * @return boolean
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static boolean checkPattern(String str, String pwd) {
		Pattern p = Pattern.compile(str);

		Matcher m = p.matcher(pwd);
		return m.matches();
	}

	/**
	 * @Auther: zhoumc
	 * @Description: 身份证验证
	 * @Date:2015-7-10上午10:31:30
	 * @param nameId
	 * @return
	 * @return boolean
	 * @说明 代码版权归 杭州安存网络科技有限公司 所有
	 */
	public static boolean checkUserIdCrad(String nameId) {
		Pattern pattern = Pattern.compile("(^\\d{15}$)|(^\\d{17}([0-9]|X)$)");
		Matcher matcher = pattern.matcher(nameId);
		return matcher.matches();
	}

	/**
	 * @Auther: zhoumc
	 * @Description: QQ号验证
	 * @Date:2015-7-10上午10:35:02
	 * @param qq
	 * @return
	 * @return boolean
	 * @说明 代码版权归 杭州安存网络科技有限公司 所有
	 */
	public static boolean checkQq(String qq) {
		Pattern pattern = Pattern.compile("[1-9][0-9]{4,14}");
		Matcher matcher = pattern.matcher(qq);
		return matcher.matches();
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 比较list
	 * @Date:2015年7月9日下午11:03:12
	 * @param a
	 * @param b
	 * @return
	 * @return boolean
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static boolean compare(List<JobPojo> a, String b) {
		int count = 0;
		if (!StringUtils.isNotEmpty(b) && null != a) {
			return false;
		}
		if (a.size() <= 0 && StringUtils.isNotEmpty(b)) {
			return false;
		}
		String[] strs = b.split(",");
		if (a.size() != strs.length) {
			return false;
		}
		for (int i = 0; i < a.size(); i++) {
			if (b.indexOf(a.get(i).getPositionName()) >= 0) {
				count++;
			}
		}

		if (count >= strs.length) {
			return true;
		} else {
			return false;
		}
	}

	public static String getDate(String month, String day) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 24小时制
		java.util.Date d = new java.util.Date();
		;
		String str = sdf.format(d);
		String nowmonth = str.substring(5, 7);
		String nowday = str.substring(8, 10);
		String result = null;

		int temp = Integer.parseInt(nowday) - Integer.parseInt(day);
		switch (temp) {
		case 0:
			result = "今天";
			break;
		case 1:
			result = "昨天";
			break;
		case 2:
			result = "前天";
			break;
		default:
			StringBuilder sb = new StringBuilder();
			sb.append(Integer.parseInt(month) + "月");
			sb.append(Integer.parseInt(day) + "日");
			result = sb.toString();
			break;
		}
		return result;
	}

	public static String getTime(long timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = null;
		try {
			java.util.Date currentdate = new java.util.Date();// 当前时间

			long i = (currentdate.getTime() / 1000 - timestamp) / (60);
			// System.out.println(currentdate.getTime());
			// System.out.println(i);
			Timestamp now = new Timestamp(System.currentTimeMillis());// 获取系统当前时间
			// System.out.println("now-->" + now);// 返回结果精确到毫秒。

			String str = sdf.format(new Timestamp(timestamp));
			time = str.substring(11, 16);

			String month = str.substring(5, 7);
			String day = str.substring(8, 10);
			// System.out.println(str);
			// System.out.println(time);
			// System.out.println(getDate(month, day));
			time = getDate(month, day) + time;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return time;
	}

	// java Timestamp构造函数需传入Long型
	public static long IntToLong(int i) {
		long result = (long) i;
		result *= 1000;
		return result;
	}

	/**
	 * 判断是否为相同字符
	 */
	public static boolean isSameChars(String str) {
		if (str.length() < 2) {
			return true;
		}
		char first = str.charAt(0);
		for (int i = 1; i < str.length(); i++) {
			if (str.charAt(i) != first) {
				return false;
			}
		}
		return true;
	}

	public static void openKeybord(CustomEditText mEditText, Context mContext) {
		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
				InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	public static void closeKeybord(CustomEditText mEditText, Context mContext) {
		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
	}

}

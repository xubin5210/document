/**    
 * @author zhuwt  
 * @Description: TODO(用一句话描述该文件做什么)   
 * @Package com.master.util.db.annotation   
 * @Title: Types.java   
 * @date 2014年8月5日 下午2:47:41   
 * @version V1.0 
 * @说明  代码版权归 杭州反盗版中心有限公司 所有
 */
package com.master.util.db;

import java.util.HashMap;
import java.util.Map;

import com.master.util.exception.MasterException;

/**
 * @author zhuwt
 * @Description:获取类型
 * @ClassName: Types.java
 * @date 2014年8月5日 下午2:47:41
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */

public class Types {
	// 自定义的基本类型，和数据对应,最后组成字符串
	public static final String VARCHAR = "VARCHAR";
	public static final String INTEGER = "INTEGER";
	public static final String BOOLEAN = "BOOLEAN";
	public static final String LONG = "BIGINT";
	public static final String TEXT = "TEXT";
	public static final String BLOB = "BLOB";
	public static final String FLOAT = "FLOAT";
	public static final String DOUBLE = "DOUBLE";
	public static final String DATE = "DATE";
	public static final String TIME = "TIME";

	// 默认长度
	public static final String VARCHAR_LENGTH = "50";
	public static final String INTEGER_LENGTH = "50";
	public static final String LONG_LENGTH = "200";
	public static final String TEXT_LENGTH = "null";
	public static final String BLOB_LENGTH = "null";
	public static final String FLOAT_LENGTH = "200";
	public static final String DOUBLE_LENGTH = "200";
	public static final String DATE_LENGTH = "null";
	public static final String TIME_LENGTH = "null";

	// 将类型 默认长度，放入集合
	public static Map<String, String> map = new HashMap<String, String>();
	static {
		map.put(VARCHAR, VARCHAR_LENGTH);
		map.put(INTEGER, INTEGER_LENGTH);
		map.put(BOOLEAN, "0");
		map.put(LONG, LONG_LENGTH);
		map.put(TEXT, TEXT_LENGTH);
		map.put(BLOB, BLOB_LENGTH);
		map.put(FLOAT, FLOAT_LENGTH);
		map.put(DOUBLE, DOUBLE_LENGTH);
		map.put(DATE, DATE_LENGTH);
		map.put(TIME, TIME_LENGTH);
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 获取字段类型 拼接成sql语句
	 * @Date:2014年8月5日下午4:10:49
	 * @param type
	 * @param length
	 * @return
	 * @return String
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static String getTypeSql(String type, String length) {
		if (type == null) {
			throw new RuntimeException("类型错误  :" + type);
		}
		// 防止boolean 这类型
		if ((!length.equals("0") && !length.equals("1"))
				&& !"null".equals(length)) {
			return type + "(" + length + ")";
		}
		return type;
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 讲对象中的类型匹配到数据库中相应的类型
	 * @Date:2014年8月8日下午2:12:32
	 * @param type
	 *            对象中的类型
	 * @param isBig
	 *            是否text格式
	 * @return
	 * @return String
	 * @throws MasterException
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static String getType(String type, boolean isBig)
			throws MasterException {
		if ("".equals(type)) {
			return null;
		}
		try {
			// String 转成 VARCHAR
			if (type.indexOf("String") != -1 && !isBig) {
				type = Types.VARCHAR;
			}
			// Integer int 转成INTEGER
			if (type.indexOf("Integer") != -1 || type.equals("int")
					|| type.indexOf("byte") != -1) {
				type = Types.INTEGER;
			}
			// boolean 转成 BOOLEAN
			if (type.indexOf("boolean") != -1) {
				type = Types.BOOLEAN;
			}
			// byte[] 转成 BLOB
			if (type.indexOf("[B") != -1) {
				type = Types.BLOB;
			}
			// long 转成 BIGINT
			if (type.indexOf("long") != -1 || type.indexOf("LONG") != -1) {
				type = Types.LONG;
			}
			// float 转成 FLOAT
			if (type.indexOf("float") != -1 || type.indexOf("Float") != -1) {
				type = Types.FLOAT;
			}
			// double 转成 DOUBLE
			if (type.indexOf("double") != -1 || type.indexOf("Double") != -1) {
				type = Types.DOUBLE;
			}
			// date 转成 DATE
			if (type.indexOf("date") != -1 || type.indexOf("Date") != -1) {
				type = Types.DATE;
			}
			// time 转成 TIME
			if (type.indexOf("time") != -1 || type.indexOf("Time") != -1) {
				type = Types.TIME;
			}
			// 大长度字符串需求
			if (isBig) {
				type = Types.TEXT;
			}
		} catch (Exception e) {
			throw new MasterException("转换错误 type = " + type, e);
		}
		return type;
	}

	private static String getStirng(String str, String length) {
		return str + "(" + length + ")";
	}
}

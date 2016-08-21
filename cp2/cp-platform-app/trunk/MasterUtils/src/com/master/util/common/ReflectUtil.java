package com.master.util.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.util.Log;

import com.master.util.db.Types;
import com.master.util.db.annotation.Column;
import com.master.util.db.annotation.Id;
import com.master.util.db.annotation.Table;
import com.master.util.exception.MasterException;

/**
 * @author zhuwt
 * @Description: 获取注解的各种工具
 * @ClassName: ReflectUtil.java
 * @date 2014年8月7日 上午10:43:20
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */

@SuppressLint("DefaultLocale")
public class ReflectUtil {

	public ReflectUtil mReflectUtil = null;

	public static final String TAG = "ReflectUtil";

	/**
	 * @Auther: zhuwt
	 * @Description: 获取表名
	 * @Date:2014年8月5日上午11:26:47
	 * @param mObject
	 * @return
	 * @return String
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public String getTableName(Object mObject) throws MasterException {
		String tableName = null;
		try {
			if (mObject.getClass().isAnnotationPresent(Table.class)) {
				// 提取表名
				tableName = mObject.getClass().getAnnotation(Table.class)
						.tableName();
				if (MasterConstants.isDebug()) {
					Log.e(TAG, "表名=" + tableName);
				}
			}
		} catch (Exception e) {
			throw new MasterException("没有设置表名注解", new Exception());
		}

		return tableName;
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 获取ID的别名 类型 拼接成sql语句格式
	 * @Date:2014年8月6日下午2:24:50
	 * @param obj
	 * @return
	 * @return String
	 * @throws MasterException
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public String getIdSql(Object obj) throws MasterException {
		StringBuilder sql = new StringBuilder();
		try {
			Class<? extends Object> c = obj.getClass();
			// 获取所有带注释的字段
			Field[] fileds = c.getDeclaredFields();
			if (null != fileds && fileds.length > 0) {
				for (Field mField : fileds) {
					// 找出ID的别名
					if (mField.isAnnotationPresent(Id.class)) {
						// 生成别名
						String id = mField.getAnnotation(Id.class).idName();
						sql.append(id);
						// 获取别名的类型 是否为INTEGER
						if ((java.lang.Integer.class.getName()).equals(mField
								.getType().getName())
								|| ("int").equals(mField.getType().getName())) {
							// 加入类型
							sql.append(" INTEGER ");
						}
						// 是否为String 是的话 加上长度
						if ((java.lang.String.class.getName()).equals(mField
								.getType().getName())) {
							sql.append(" " + Types.VARCHAR);
							// 长度
							String length = obj.getClass()
									.getAnnotation(Id.class).length();
							if (!"125".equals(length) && null != length) {
								// 加入长度
								sql.append(" (" + length + ")");
							}
						}
						// 加入主键约束
						sql.append("PRIMARY KEY");
						// 判断是否是自增的
						if (mField.getAnnotation(Id.class).increment()) {
							// 加入自增
							sql.append(" AUTOINCREMENT ");
						}
						sql.append(",");
						if (MasterConstants.isDebug()) {
							Log.e(TAG, "id = " + sql.toString());
						}
						break;
					}
				}
			}
		} catch (Exception e) {
			throw new MasterException("拼接成sql语句错误", e);
		}
		return sql.toString();
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 获取别名coiumn字段
	 * @Date:2014年8月4日下午5:47:09
	 * @param obj
	 * @return void
	 * @throws MasterException
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public ArrayList<Field> getColumnList(Object obj) throws MasterException {
		ArrayList<Field> list = new ArrayList<Field>();
		try {
			Class<? extends Object> c = obj.getClass();
			Field[] fileds = c.getDeclaredFields();
			// 获取带注释的字段
			if (null != fileds && fileds.length > 0) {
				for (Field mField : fileds) {
					if (mField.isAnnotationPresent(Column.class)) {
						list.add(mField);
					}
				}
			}
		} catch (Exception e) {
			throw new MasterException("获取别名coiumn字段错误", e);
		}
		return list;
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 获取所有带注解的字段（表名除外）
	 * @Date:2014年8月6日下午4:06:33
	 * @param obj
	 * @return
	 * @return ArrayList<Field>
	 * @throws MasterException
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public ArrayList<Field> getFieldList(Object obj) throws MasterException {
		ArrayList<Field> list = new ArrayList<Field>();
		try {
			Class<? extends Object> c = obj.getClass();
			Field[] fileds = c.getDeclaredFields();
			// 获取带注释的字段 包括id
			if (null != fileds && fileds.length > 0) {
				for (Field mField : fileds) {
					if (mField.isAnnotationPresent(Column.class)
							|| mField.isAnnotationPresent(Id.class)) {
						list.add(mField);
					}
				}
			}
		} catch (Exception e) {
			throw new MasterException("获取所有带注解的字段错误", e);
		}
		return list;
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 获取字段的值
	 * @Date:2014年8月6日下午4:00:35
	 * @param obj
	 * @param field
	 * @return
	 * @return String
	 * @throws MasterException
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	@SuppressLint("DefaultLocale")
	public String getFieldValue(Object obj, Field field) throws MasterException {
		String value = null;
		String name = field.getName();
		String type = field.getType().getName();
		// 获得当前字段的get方法名
		String methodName = null;
		if (type.indexOf("boolean") != -1) {
			methodName = "is" + name.substring(0, 1).toUpperCase()
					+ name.substring(1);
		} else if (type.indexOf("byte") != -1) {
			methodName = "get" + name.substring(0, 1).toString().toUpperCase()
					+ name.substring(1);
		} else {
			methodName = "get" + name.substring(0, 1).toUpperCase()
					+ name.substring(1);
		}
		Method method = null;
		Object methodValue = null;
		try {
			// 获得方法
			method = obj.getClass().getMethod(methodName);
		} catch (Exception e1) {
			throw new MasterException("获取字段的值错误", e1);
		}
		if (method != null) {
			try {
				// 获得相对应的字段的值
				methodValue = method.invoke(obj);
				if (methodValue != null) {
					value = methodValue.toString();
				}
				if (MasterConstants.isDebug()) {
					Log.e(TAG, field.getType().getName() + "=" + value);
				}
			} catch (Exception e) {
				throw new MasterException("获取字段的值错误"
						+ field.getType().getName() + "=", e);
			}
		}
		return value;
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 获取字段跟别名 想对应的map
	 * @Date:2014年8月7日下午3:24:42
	 * @param mObject
	 * @return
	 * @return HashMap<String,String>
	 * @throws MasterException
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public HashMap<String, String> getColumnMap(Object mObject)
			throws MasterException {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			ArrayList<Field> listFields = getFieldList(mObject);
			for (Field mField : listFields) {
				// 对象里的字段名
				String name = mField.getName();
				// 数据库中相对应的别名
				String column = null;
				if (mField.isAnnotationPresent(Id.class)) {
					column = mField.getAnnotation(Id.class).idName();
				}
				if (mField.isAnnotationPresent(Column.class)) {
					column = mField.getAnnotation(Column.class).columnName();
				}
				map.put(name, column);
			}
		} catch (Exception e) {
			throw new MasterException("转换错误", e);
		}
		return map;
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 将查询结果 转换成对象
	 * @Date:2014年8月7日下午3:20:16
	 * @param mCursor
	 * @param mObject
	 * @return
	 * @return ArrayList<Object>
	 * @throws MasterException
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	@SuppressLint("SimpleDateFormat")
	public ArrayList<Object> cursorToList(Cursor mCursor, Object mObject)
			throws MasterException {
		ArrayList<Object> list = new ArrayList<Object>();
		Class<? extends Object> c = mObject.getClass();
		// 获取所有方法
		Method methods[] = c.getDeclaredMethods();
		ArrayList<Field> listFields = getFieldList(mObject);
		HashMap<String, String> map = getColumnMap(mObject);
		String type = null;
		try {
			if (mCursor != null) {
				while (mCursor.moveToNext()) {
					// 新建一个该类型的对象
					mObject = mObject.getClass().newInstance();
					// 遍历方法
					for (Method method : methods) {
						// 获取方法的小写名字
						String mName = method.getName().toLowerCase();
						for (Field mField : listFields) {
							// 对象里的字段名
							String name = mField.getName();
							// 如果是byte[] 则要去掉[]
							if (name.indexOf("[]") != -1) {
								name.replaceAll("[]", "");
							}
							if (mName.equals("set" + name.toLowerCase())) {
								// 如果方法名跟字段名匹配
								// 先找出该字段的类型
								// 根据类型 和 别名 设置值
								type = mField.getType().getName();
								if (type.indexOf("String") != -1) {
									method.invoke(mObject, mCursor
											.getString(mCursor
													.getColumnIndexOrThrow(map
															.get(name))));
								}
								// int类型
								if (type.indexOf("int") != -1
										|| type.indexOf("Integer") != -1) {
									method.invoke(mObject, mCursor
											.getInt(mCursor
													.getColumnIndexOrThrow(map
															.get(name))));
								}
								// lang类型
								if (type.indexOf("Long") != -1
										|| type.indexOf("long") != -1) {
									method.invoke(mObject, mCursor
											.getLong(mCursor
													.getColumnIndexOrThrow(map
															.get(name))));
								}
								// boolean 类型
								if (type.indexOf("boolean") != -1) {
									method.invoke(
											mObject,
											mCursor.getLong(mCursor
													.getColumnIndexOrThrow(map
															.get(name))) == 0 ? false
													: true);
								}
								// byte 类型
								if (type.indexOf("byte") != -1
										|| type.indexOf("Byte") != -1) {
									method.invoke(mObject, (byte) (mCursor
											.getInt(mCursor
													.getColumnIndexOrThrow(map
															.get(name)))));
								}
								// byte[]类型
								if (type.indexOf("[B") != -1) {
									method.invoke(mObject, (mCursor
											.getBlob(mCursor
													.getColumnIndexOrThrow(map
															.get(name)))));
								}
								// double 类型
								if (type.indexOf("double") != -1
										|| type.indexOf("Double") != -1) {
									method.invoke(mObject, mCursor
											.getDouble(mCursor
													.getColumnIndexOrThrow(map
															.get(name))));
								}
								// float 类型
								if (type.indexOf("float") != -1
										|| type.indexOf("Float") != -1) {
									method.invoke(mObject, mCursor
											.getFloat(mCursor
													.getColumnIndexOrThrow(map
															.get(name))));
								}
								// 时间类型
								if (type.indexOf("Date") != -1) {
									String value = mCursor.getString(mCursor
											.getColumnIndex(map.get(name)));
									SimpleDateFormat formatter = new SimpleDateFormat(
											"yyyy-MM-dd");
									ParsePosition pos = new ParsePosition(0);
									Date strtodate = formatter
											.parse(value, pos);
									method.invoke(mObject, strtodate);
								}
							}
						}
					}
					list.add(mObject);
				}
			}
		} catch (Exception e) {
			throw new MasterException("转换错误" + type, e);
		} finally {
			mCursor.close();
		}
		return list;
	}
}

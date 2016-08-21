/**    
 * @author maomy  
 * @Description: SQL语句生成类
 * @Package com.master.util.db   
 * @Title: MakeSqlUtil.java   
 * @date 2014年8月4日 上午10:14:16   
 * @version V2.0 
 * @说明  代码版权归 杭州反盗版中心有限公司 所有
 */
package com.master.util.db;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.master.util.common.MasterConstants;
import com.master.util.common.ReflectUtil;
import com.master.util.db.annotation.Column;
import com.master.util.db.annotation.Id;
import com.master.util.exception.MasterException;

public class SqlFactory {

	protected static final String TAG = "MakeSqlUtil";

	protected static SqlFactory factory = null;

	protected static ReflectUtil mReflectUtil = null;

	/**
	 * @Auther: zhuwt
	 * @Description: 单例模式
	 * @Date:2014年8月7日上午11:08:57
	 * @return
	 * @return SqlFactory
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static SqlFactory getInstance() {
		if (null == mReflectUtil) {
			mReflectUtil = new ReflectUtil();
		}
		if (null == factory) {
			factory = new SqlFactory();
		}
		return factory;
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 创建建表语句
	 * @Date:2014年8月5日下午3:17:20
	 * @param mObject
	 * @return
	 * @return String
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	protected String createSql(Object mObject) throws MasterException {
		// 表名
		String tableName = mReflectUtil.getTableName(mObject);
		// 创建建表语句
		StringBuilder sql = new StringBuilder("CREATE TABLE " + tableName
				+ " (");
		// 字段集合
		ArrayList<String> columns = new ArrayList<String>();
		// 带注释的Field集合
		ArrayList<Field> list = mReflectUtil.getColumnList(mObject);
		try {
			for (Field mField : list) {
				// 列名
				String name = null;
				// 类型
				String type = null;
				Annotation[] mAnnotations = mField.getDeclaredAnnotations();
				if (mAnnotations.length == 0) {
					continue;
				} else {
					// 获取注解信息
					Column mColumn = (Column) mAnnotations[0];
					// 获得sql需要的别名
					String Cname = mColumn.columnName();
					// 类型
					String Ctype = mField.getType().getName();
					// 长度
					String Clength = mColumn.lengtn();
					// 是否是TEXT格式
					boolean isBig = mColumn.isBigString();
					if (Cname.length() == 0) {
						// 获得默认的字段名
						name = mField.getName();
					} else {
						name = Cname;
					}
					if (Ctype.length() == 0) {
						// 获得默认的类型
						type = mField.getType().toString();
					} else {
						type = Types.getType(Ctype, isBig);
					}
					// 没有设置长度 则获取默认的长度
					if (Clength.length() < 0) {
						Clength = Types.map.get(type).toString();
					}
					// 对于boolean 特殊处理 0代表false
					if ("0".equals(Clength)) {
						// 获取默认的长度
						Clength = Types.map.get(type).toString();
					}
					// 生成字段类型和长度sql语句
					type = Types.getTypeSql(type, Clength);
					// 拼接成sql语句格式 加入集合
					columns.add(name + " " + type);
				}
			}
			// 加入ID
			sql.append(mReflectUtil.getIdSql(mObject));
			// 遍历集合 生成sql语句
			for (String str : columns) {
				sql.append(" " + str + " ,");
			}
			// 最终的创建表语句
			String finalSql = sql.substring(0, sql.length() - 1) + " );";
			if (MasterConstants.isDebug()) {
				Log.i(TAG, "建表语句 = " + finalSql);
			}
			return finalSql;
		} catch (Exception e) {
			throw new MasterException("建表出错 sql = " + sql.toString(), e);
		}
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 插入语句
	 * @Date:2014年8月6日下午4:23:54
	 * @param obj
	 *            要插入的对象
	 * @return
	 * @return String
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	protected String insertObj(Object obj) throws MasterException {
		String insertSql = null;
		// 获取表名
		String tableName = mReflectUtil.getTableName(obj);
		if (tableName != null) {
			// 要插入的别名
			StringBuffer sqlStr = new StringBuffer("INSERT INTO ");
			// 要插入的别名的值
			StringBuffer valueStr = new StringBuffer(" VALUES (");
			// 所有带注解的字段
			List<Field> list = mReflectUtil.getFieldList(obj);
			if (list != null && list.size() > 0) {
				// 开始拼接字符串
				sqlStr.append(tableName + " (");
				// 开始遍历所有带注释的字段
				// 别名
				String str = null;
				// 字段的值
				Object fieldValue = null;
				boolean isBig = false;
				try {
					for (Field field : list) {
						if (field.isAnnotationPresent(Id.class)) {
							Id mId = field.getAnnotation(Id.class);
							str = mId.idName();
						}
						if (field.isAnnotationPresent(Column.class)) {
							Column anno = field.getAnnotation(Column.class);
							str = anno.columnName();
							// 是否是text类型
							if (anno.isBigString()) {
								isBig = true;
							}
						}
						// 获取字段的值
						fieldValue = mReflectUtil.getFieldValue(obj, field);
						// boleann 类型对应数据库的值 false =0 ture = 1
						if ("false".equals(fieldValue)) {
							fieldValue = "0";
						}
						if ("true".equals(fieldValue)) {
							fieldValue = "1";
						}
						String type = field.getType().getName();
						if (null == fieldValue || "".equals(fieldValue)) {
							// 根据对象的类型 获取数据库里匹配类型的默认值
							fieldValue = null;
						}
						// 拼接要插入的别名的sql语句
						sqlStr.append(str + ",");
						// 根据类型 设置sql语句中 不同的格式
						if (field.getType().getName().indexOf("String") != -1
								|| field.getType().getName().indexOf("Date") != -1
								|| field.getType().getName().indexOf("Time") != -1) {
							// varchar 要加''
							valueStr.append("'" + fieldValue + "',");
						} else {
							// 其余直接拼接
							valueStr.append(fieldValue + ",");
						}
					}
				} catch (Exception e) {
					throw new MasterException("插入错误 ", e);
				}
				// 添加括号
				insertSql = sqlStr.toString().substring(0, sqlStr.length() - 1)
						+ ")"
						+ valueStr.toString().substring(0,
								valueStr.length() - 1) + ");";
				if (MasterConstants.isDebug()) {
					Log.i(TAG, "插入语句 = " + insertSql);
				}
			}
		}
		return insertSql;
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 删除方法 根据主键删除
	 * @Date:2014年8月6日下午4:42:09
	 * @param mObject
	 *            对象
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	protected String delObj(Object mObject) throws MasterException {
		StringBuilder sql = new StringBuilder("DELETE FROM ");
		sql.append(mReflectUtil.getTableName(mObject) + " where ");
		try {
			ArrayList<Field> list = mReflectUtil.getFieldList(mObject);
			String idName = null;
			for (Field field : list) {
				if (field.isAnnotationPresent(Id.class)) {
					// 获取id的别名
					idName = field.getAnnotation(Id.class).idName();
					// 获取id的值
					String value = mReflectUtil.getFieldValue(mObject, field);
					if (null != value && !"".equals(value))
						sql.append(idName + " = " + value + ";");
					break;
				}
			}
			if ("".equals(idName) || null == idName) {
				throw new MasterException("没有主键 删除失败", new Exception());
			}
			if (MasterConstants.isDebug()) {
				Log.i(TAG, "删除语句=" + sql.toString());
			}
			return sql.toString();
		} catch (Exception e) {
			throw new MasterException("插入错误 " + sql.toString(), e);
		}
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 查询语句
	 * @Date:2014年8月6日下午5:01:18
	 * @return String
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	protected String selectObj(Object mObject,int id) throws MasterException {
		String tableName = mReflectUtil.getTableName(mObject);
		StringBuffer selectBuffer = new StringBuffer("SELECT ");
		if (tableName != null) {
			List<Field> list = mReflectUtil.getFieldList(mObject);
			Id aId = null;
			if (list != null && list.size() > 0) {
				try {
					for (Field field : list) {
						if (field.isAnnotationPresent(Id.class)) {
							// 获取id在表中的别名
							aId = field.getAnnotation(Id.class);
//							// 获取对象id的值
//							String value = mReflectUtil.getFieldValue(mObject,
//									field);
							selectBuffer.append(" * from " + tableName
									+ " where " + aId.idName() + " = " + id
									+ ";");
							break;
						}
					}
					if ("".equals(aId.idName()) || null == aId.idName()) {
						throw new MasterException("没有主键 查询失败"
								+ selectBuffer.toString(), new Exception());
					}
				} catch (Exception e) {
					throw new MasterException("没有主键 查询失败"
							+ selectBuffer.toString(), new Exception());
				}
			}
		}
		return selectBuffer.toString();
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 自定义sql语句的查询
	 * @Date:2014年8月7日下午2:52:13
	 * @param mData
	 *            数据库
	 * @param sql
	 *            sql语句
	 * @param mObject
	 *            对象类
	 * @return
	 * @return ArrayList<Object> 集合
	 * @throws MasterException
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	protected ArrayList<Object> getListBySql(SQLiteDatabase mData, String sql,
			Object mObject) throws MasterException {
		ArrayList<Object> list = new ArrayList<Object>();
		try {
			Cursor mCursor = mData.rawQuery(sql, null);
			list = mReflectUtil.cursorToList(mCursor, mObject);
		} catch (Exception e) {
			throw new MasterException("自定义sql语句的查询", e);

		}
		return list;
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 增 删 改的方法
	 * @Date:2014年8月7日下午4:18:10
	 * @param mData
	 * @param sql
	 * @return true 返回成功
	 * @return boolean
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	protected boolean getSqlResult(SQLiteDatabase mData, String sql) {
		try {
			mData.execSQL(sql);
		} catch (Exception e) {
			e.printStackTrace();
			if (MasterConstants.isDebug()) {
				Log.i(TAG, "错误 sql = " + sql);
			}
			return false;
		}
		return true;
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 更新
	 * @Date:2014年8月6日下午5:23:10
	 * @param obj
	 *            对象
	 * @param isId
	 *            主键的值
	 * @param map
	 *            条件
	 * @return
	 * @return String
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	protected String updateObj(Object obj) throws MasterException {
		String updateSql = null;
		// 表名
		String tableName = mReflectUtil.getTableName(obj);
		try {
			if (tableName != null) {
				List<Field> list = mReflectUtil.getFieldList(obj);
				if (list != null && list.size() > 0) {
					StringBuffer sqlStr = new StringBuffer("UPDATE "
							+ tableName + " ");
					StringBuffer valueStr = new StringBuffer(" SET ");
					String whereStr = " WHERE ";
					// 别名
					String str = null;
					// 是否是text格式
					boolean isBig = false;
					try {
						for (Field field : list) {
							if (field.isAnnotationPresent(Id.class)) {
								// 获取id在表中的别名
								Id mId = field.getAnnotation(Id.class);
								// 获取对象id的值
								String value = mReflectUtil.getFieldValue(obj,
										field);
								// 拼接条件语句
								str = mId.idName();
								whereStr += mId.idName() + " = " + value;
							}
							if (field.isAnnotationPresent(Column.class)) {
								Column anno = field.getAnnotation(Column.class);
								str = anno.columnName();
								if (anno.isBigString()) {
									isBig = true;
								}
							}
							// 该字段在对象对应的值
							String value = mReflectUtil.getFieldValue(obj,
									field);
							// boleann 类型对应数据库的值 false =0 ture = 1
							if ("false".equals(value)) {
								value = "0";
							}
							if ("true".equals(value)) {
								value = "1";
							}
							// 根据类型 设置sql语句中 不同的格式
							String type = field.getType().getName();
							if (null == type || "".equals(type)) {
								// 根据对象的类型 获取数据库里匹配类型的默认值
								value = Types.map.get(Types
										.getType(type, isBig));
							}
							if (field.getType().getName().indexOf("String") != -1
									|| field.getType().getName()
											.indexOf("Date") != -1
									|| field.getType().getName()
											.indexOf("Time") != -1) {
								// varchar 要加''
								valueStr.append(str + " = " + "'" + value
										+ "',");
							} else {
								// 其余直接拼接
								valueStr.append(str + " = " + value + ",");
							}
						}
					} catch (Exception e) {
						throw new MasterException("更新值错误", e);
					}
					updateSql = sqlStr.toString()
							+ valueStr.toString().substring(0,
									valueStr.length() - 1) + whereStr + ";";
					if (MasterConstants.isDebug()) {
						Log.i(TAG, "修改语句 = " + updateSql);
					}
				}
			}
		} catch (Exception e) {
			throw new MasterException("更新值错误错误" + updateSql, e);
		}
		return updateSql;
	}

}

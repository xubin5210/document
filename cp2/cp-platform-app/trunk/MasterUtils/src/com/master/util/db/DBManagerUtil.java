/**    
 * @author maomy  
 * @Description: 数据库管理工具类   
 * @Package com.master.util.db   
 * @Title: DBManagerUtil.java   
 * @date 2014年8月4日 上午10:03:17   
 * @version V2.0 
 * @说明  代码版权归 杭州反盗版中心有限公司 所有
 */
package com.master.util.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.master.util.common.MasterConstants;
import com.master.util.exception.MasterException;

public class DBManagerUtil {

	private static DBManagerUtil managerUtil = null;

	private static SQLiteDatabase mDatabase = null;
	
	private final static String TAG = "DBManagerUtil";
	
	/**
	 * @Auther: zhuwt
	 * @Description:单例
	 * @Date:2014年8月7日上午11:43:00
	 * @return
	 * @return DBManagerUtil
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static DBManagerUtil getInstance(SQLiteDatabase mLiteDatabase) {
		if (null == managerUtil) {
			managerUtil = new DBManagerUtil();
		}
		if (null == mDatabase) {
			mDatabase = mLiteDatabase;
		}
		return managerUtil;
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 根据对象 创建相应的建表语句
	 * @Date:2014年8月7日上午11:42:32
	 * @param mObject
	 * @return
	 * @return String
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public boolean createTableSql(Object mObject) {
		try {
			if (MasterConstants.isDebug()) {
				Log.i("====", "" + SqlFactory.getInstance().createSql(mObject));
			}
			mDatabase.execSQL(SqlFactory.getInstance().createSql(mObject));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 插入传入的对象到数据库相应的表
	 * @Date:2014年8月7日下午1:38:13
	 * @param obj
	 *            要插入的对象
	 * @return
	 * @return String
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public boolean insertSql(Object obj) {
		try {
			String sql = SqlFactory.getInstance().insertObj(obj);
			mDatabase.execSQL(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 调用系统的插入方法
	 * @Date:2014年8月8日上午10:30:14
	 * @param tableName
	 *            表名
	 * @return values 插入的数据
	 * @return long 返回插入的数据在表中的id
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public long insert(String tableName, ContentValues values) {
		long id = -1;
		try {
			id = mDatabase.insert(tableName, null, values);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return id;
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 根据传入的对象id删除该对象
	 * @Date:2014年8月7日下午12:40:50
	 * @param mObject
	 *            要删除的对象
	 * @return
	 * @return String
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public boolean delSql(Object mObject) {
		try {
			String sql = SqlFactory.getInstance().delObj(mObject);
			mDatabase.execSQL(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 调用系统的删除方法
	 * @Date:2014年8月8日上午10:50:58
	 * @param tableName
	 * @param whereSql
	 *            删除的条件语句
	 * @param strs
	 *            删除的数组
	 * @return
	 * @return id
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public long del(String tableName, String whereSql, String[] strs) {
		long id = -1;
		try {
			id = mDatabase.delete(tableName, whereSql, strs);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return id;
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 根据传入的对象id查询该对象
	 * @Date:2014年8月7日下午1:39:46
	 * @param mObject
	 *            要查找的对象
	 * @return list 返回该类型的集合
	 * @return String
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public Object selectSql(Object mObject,int id) {
		List<Object> list = new ArrayList<Object>();
		try {
			String sql = SqlFactory.getInstance().selectObj(mObject,id);
			list = SqlFactory.getInstance().getListBySql(mDatabase, sql,
					mObject);
			if(MasterConstants.isDebug()){
				Log.i(TAG, list.size()+sql);
			}
			if(list.size() <= 0){
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return list.get(0);
	}

	/**
	 * @Auther: zhuwt
	 * @Description:更具传入的对象 跟新相应的数据
	 * @Date:2014年8月7日下午1:47:33
	 * @param obj
	 *            要跟新的对象
	 * @return
	 * @return ｂｏｏｌｅａｎ
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public boolean updateSql(Object obj) {
		try {
			String sql = SqlFactory.getInstance().updateObj(obj);
			mDatabase.execSQL(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 系统的更新方法
	 * @Date:2014年8月8日上午11:36:17
	 * @return
	 * @return long
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public long update(String tableName, ContentValues values, String whereSql,
			String[] strs) {
		long id = -1;
		try {
			id = mDatabase.update(tableName, values, whereSql, strs);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return id;
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 根据sql语句查询 返回指定对象集合
	 * @Date:2014年8月7日下午4:21:01
	 * @param mData
	 * @param sql
	 *            查询语句
	 * @param mObject
	 *            返回的对象类型
	 * @return
	 * @return ArrayList<Object>
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public ArrayList<Object> querryListBySql(String sql, Object mObject)
			throws MasterException {
		return SqlFactory.getInstance().getListBySql(mDatabase, sql, mObject);
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 通过sql语句操作数据库 包括自定义的增 删 该操作
	 * @Date:2014年8月7日下午4:22:54
	 * @param mData
	 * @param sql
	 * @return
	 * @return boolean
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public boolean execSql(String sql) {
		return SqlFactory.getInstance().getSqlResult(mDatabase, sql);
	}
}

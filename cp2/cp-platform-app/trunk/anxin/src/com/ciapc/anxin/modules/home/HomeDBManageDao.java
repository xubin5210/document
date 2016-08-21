package com.ciapc.anxin.modules.home;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ciapc.anxin.common.AXSharedPreferences;
import com.ciapc.anxin.common.BaseApplication;
import com.ciapc.anxin.common.BaseDBManageDao;
import com.ciapc.anxin.common.GlobalConstants;
import com.ciapc.anxin.common.pojo.ExchangePojo;
import com.master.util.common.ObjectUtil;
import com.master.util.common.StringUtils;
import com.master.util.db.DBManagerUtil;

public class HomeDBManageDao extends BaseDBManageDao {

	private static final String TAG = "HomeDBManageDao";

	private static HomeDBManageDao manageDao;

	public static HomeDBManageDao getInstance() {
		if (null == manageDao) {
			manageDao = new HomeDBManageDao();
		}
		return manageDao;
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 批量插入数据库
	 * @Date:2015年6月9日下午3:24:56
	 * @param list
	 * @return
	 * @return boolean
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	protected boolean batchInsertCard(List<ExchangePojo> list) {
		try {
			liteDatabase.beginTransaction();
			long retId;
			List<ContentValues> data = convertContentValues(list);
			for (ContentValues cv : data) {
				retId = liteDatabase.insert("cardInfo", null, cv);
				if (GlobalConstants.isDebug)
					Log.i(TAG, "batchInsertCard  retId = " + retId);
			}
			liteDatabase.setTransactionSuccessful();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 处理完成
			liteDatabase.endTransaction();
		}
		return false;
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 插入单条数据
	 * @Date:2015年7月1日下午5:05:22
	 * @param pojo
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public boolean insertCard(ExchangePojo pojo) {
		return DBManagerUtil.getInstance(liteDatabase).insertSql(pojo);
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 按搜索条件查询
	 * @Date:2015年6月9日下午5:01:21
	 * @param condition
	 * @return
	 * @return List<ExchangePojo>
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public List<ExchangePojo> queryByCriteria(String condition) {
		List<ExchangePojo> list = new ArrayList<ExchangePojo>();
		// 查询条件
		String sql = "select * from cardInfo where " + " userId = "
				+ AXSharedPreferences.getCardId(mContext)
				+ " and (orgName like " + "'%" + condition + "%'"
				+ " or trueName like " + "'%" + condition + "%'"
				+ " or mobile like " + "'%" + condition + "%')";
		if (GlobalConstants.isDebug) {
			Log.i(TAG, "sql = " + sql);
		}
		try {
			list = ObjectUtil.convertObjToBeanList(DBManagerUtil.getInstance(
					liteDatabase).querryListBySql(sql, new ExchangePojo()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 分页查询数据
	 * @Date:2015年6月9日下午5:18:51
	 * @param start
	 *            开始位置
	 * @param pageSize
	 *            每页个数
	 * @return
	 * @return List<ExchangePojo>
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public List<ExchangePojo> queryCard(int start, int pageSize) {
		List<ExchangePojo> list = new ArrayList<ExchangePojo>();
		String limit = " limit " + start + "," + pageSize;
		// 查询条件
		String sql = "select * from (select * from cardInfo order by gmtExchange desc) where userId =  "
				+ AXSharedPreferences.getCardId(mContext) + limit;
		if (GlobalConstants.isDebug) {
			Log.i(TAG, "sql = " + sql);
		}
		try {
			list = ObjectUtil.convertObjToBeanList(DBManagerUtil.getInstance(
					liteDatabase).querryListBySql(sql, new ExchangePojo()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	/**
	 * @Auther: zhuwt
	 * @Description: 类型转换 用于批量插入
	 * @Date:2015年6月9日下午3:35:45
	 * @param list
	 * @return
	 * @return List<ContentValues>
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public List<ContentValues> convertContentValues(List<ExchangePojo> list) {
		List<ContentValues> data = new ArrayList<ContentValues>();
		ContentValues cv;
		try {
			for (ExchangePojo pojo : list) {
				cv = new ContentValues();
				cv.put("id", pojo.getId());
				cv.put("trueName", pojo.getTrueName());
				cv.put("orgName", pojo.getOrgName());
				cv.put("mobile", pojo.getMobile());
				cv.put("iconUrl", pojo.getIconUrl());
				cv.put("cardId", pojo.getCardId());
				cv.put("gmtExchange", pojo.getGmtExchange());
				cv.put("userId", AXSharedPreferences.getCardId(mContext));
				data.add(cv);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 删除名片
	 * @Date:2015年7月1日下午4:02:04
	 * @param pojo
	 * @return
	 * @return boolean
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void delCard(String cardId) {
		String sql = "delete from cardInfo " + "where cardId = " + cardId
				+ " and userId = " + AXSharedPreferences.getCardId(mContext);
		try {
			liteDatabase.execSQL(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 更新名片
	 * @Date:2015年7月8日下午4:56:46
	 * @param pojo
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void updateCard(ExchangePojo pojo) {
		String sql = "UPDATE cardInfo  SET trueName = '" + pojo.getTrueName()
				+ "',iconUrl = '" + pojo.getIconUrl() + "',mobile = '"
				+ pojo.getMobile() + "',orgName = '" + pojo.getOrgName()
				+ "' WHERE userId = " + AXSharedPreferences.getCardId(mContext)
				+ " and cardId = " + pojo.getCardId();
		liteDatabase.execSQL(sql);
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 搜索本地是都已经存在该数据
	 * @Date:2015年7月14日下午7:40:29
	 * @param cardId
	 * @return
	 * @return boolean
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public boolean searchCardId(String cardId) {
		String sql = "select * from cardInfo " + "where cardId = " + cardId
				+ " and userId = " + AXSharedPreferences.getCardId(mContext);
		Cursor mCursor = null;
		try {
			mCursor = liteDatabase.rawQuery(sql, null);
			if (null != mCursor) {
				while (mCursor.moveToNext()) {
					String id = mCursor.getString(mCursor
							.getColumnIndexOrThrow("cardId"));
					if (StringUtils.isNotEmpty(id)) {
						return false;
					} else {
						return true;
					}
				}
			} else {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}

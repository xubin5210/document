/**    
 * @author maomy  
 * @Description: DAO 父类所有的dao 都要继承
 * @Package com.ciapc.anxin.common   
 * @Title: BaseDBManageDao.java   
 * @date 2015年5月25日 上午10:04:26   
 * @version V1.0 
 * @说明  代码版权归 杭州安存网络科技有限公司 所有
 */
package com.ciapc.anxin.common;

import com.ciapc.anxin.common.db.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class BaseDBManageDao {
	
	protected SQLiteDatabase liteDatabase;
	protected Context mContext;
	
	//static类型，保证全局唯一
	private static SQLiteDatabase mSQLiteDatabase = null;
	
	public BaseDBManageDao() {
		// TODO Auto-generated constructor stub
		mContext=BaseApplication.getInstance();
		liteDatabase = getSqLiteDatabase();
	}
	
	 /**
     * @Auther: maomy  
     * @Description: 获取数据库操作类 
     * @Date:2015年5月25日上午9:57:59
     * @return  
     * @return SQLiteDatabase 
     * @说明  代码版权归 杭州安存网络科技有限公司 所有
     */
    private SQLiteDatabase getSqLiteDatabase() {
    	if(null == mSQLiteDatabase){
    		mSQLiteDatabase = new DBHelper(mContext, GlobalConstants.DB_NAME, GlobalConstants.DB_VERSION).
    				getReadableDatabase();
    	}
    	return mSQLiteDatabase;
	}
}

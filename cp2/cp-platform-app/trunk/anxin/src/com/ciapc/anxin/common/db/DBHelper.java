/**    
 * @author maomy  
 * @Description: TODO(用一句话描述该文件做什么)   
 * @Package com.ciapc.anxin.common.db   
 * @Title: DBHelper.java   
 * @date 2015年5月25日 上午9:51:33   
 * @version V1.0 
 * @说明  代码版权归 杭州安存网络科技有限公司 所有
 */
package com.ciapc.anxin.common.db;

import com.ciapc.anxin.common.GlobalConstants;
import com.ciapc.anxin.common.pojo.CardPojo;
import com.ciapc.anxin.common.pojo.ExchangePojo;
import com.master.util.db.DBManagerUtil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author maomy
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @ClassName: DBHelper.java
 * @date 2015年5月25日 上午9:51:33
 * @说明 代码版权归 杭州安存网络科技有限公司 所有
 */

public class DBHelper extends SQLiteOpenHelper {

	/**
	 * 日志tag
	 */
	private String TAG = "DBHelper";

	public DBHelper(Context context, String name, int version) {
		super(context, name, null, version);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			DBManagerUtil.getInstance(db).createTableSql(new ExchangePojo());
			if (GlobalConstants.isDebug)
				Log.i(TAG,
						" onCreate   database tabel create success .........");
		} catch (Exception e) {
			if (GlobalConstants.isDebug)
				Log.i(TAG,
						" onCreate   database tabel create faild .........");
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}

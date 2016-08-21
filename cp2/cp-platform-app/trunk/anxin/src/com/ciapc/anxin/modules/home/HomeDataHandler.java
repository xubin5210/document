package com.ciapc.anxin.modules.home;

import java.util.List;

import org.apache.http.Header;

import com.ciapc.anxin.common.AXSharedPreferences;
import com.ciapc.anxin.common.BaseApplication;
import com.ciapc.anxin.common.GlobalConstants;
import com.ciapc.anxin.common.InterfaceConstants;
import com.ciapc.anxin.common.callback.AXCallBack;
import com.ciapc.anxin.common.pojo.ExchangePojo;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.master.util.common.StringUtils;
import com.master.util.http.MasterHttpClient;

import android.content.Context;
import android.util.Log;

/**
 * @author zhuwt
 * @Description: 数据库操作工具类
 * @ClassName: HomeDataHandler.java
 * @date 2015年6月9日 下午3:45:58
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */

public class HomeDataHandler {

	private final String TAG = "HomeDataHandler";

	private static HomeDataHandler mDataHandler;

	/**
	 * @Auther: zhuwt
	 * @Description: 单例模式
	 * @Date:2015年6月9日下午3:53:31
	 * @param context
	 * @return
	 * @return HomeDBManageDao
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static HomeDataHandler getInstance() {
		if (null == mDataHandler) {
			mDataHandler = new HomeDataHandler();
		}
		return mDataHandler;
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 批量插入
	 * @Date:2015年6月9日下午3:56:49
	 * @param list
	 * @return
	 * @return boolean
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public boolean batchInsertCard(List<ExchangePojo> list) {
		// 线程锁
		synchronized (this) {
			return HomeDBManageDao.getInstance().batchInsertCard(list);
		}
	}

	/**
	 * @Auther: zhuwt
	 * @Description:插入单挑数据
	 * @Date:2015年7月1日下午5:04:26
	 * @param list
	 * @return
	 * @return boolean
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */ 
	public boolean insertCard(ExchangePojo pojo) {
		boolean flag = HomeDBManageDao.getInstance().insertCard(pojo);
		if (GlobalConstants.isDebug) {
			Log.i(TAG, "插入"+flag);
		}
		return flag;
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 按条件查询
	 * @Date:2015年6月9日下午5:14:28
	 * @param condition
	 * @return
	 * @return List<CardPojo>
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public List<ExchangePojo> queryByCondition(String condition) {
		return HomeDBManageDao.getInstance().queryByCriteria(condition);
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 分页查询数据
	 * @Date:2015年6月9日下午5:20:59
	 * @param start
	 *            开始位置
	 * @param pageSize
	 *            每页个数
	 * @return
	 * @return List<CardPojo>
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public List<ExchangePojo> queryCard(int start, int pageSize) {
		return HomeDBManageDao.getInstance().queryCard(start, pageSize);
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
		HomeDBManageDao.getInstance().delCard(cardId);
	}
	
	/**
	 * @Auther: zhuwt 
	 * @Description:更新名片信息
	 * @Date:2015年7月8日下午4:57:48
	 * @param pojo  
	 * @return void 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void updateCard(ExchangePojo pojo){
		HomeDBManageDao.getInstance().updateCard(pojo);
	}
	
	/**
	 * @Auther: zhuwt 
	 * @Description: 搜索本地数据库是否已经存在改数据
	 * @Date:2015年7月14日下午7:49:10
	 * @param cardId
	 * @return  
	 * @return boolean 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public boolean searchCardId(String cardId){
		return HomeDBManageDao.getInstance().searchCardId(cardId);
	}
	
	/**
	 * @Auther: zhuwt 
	 * @Description: 企业注册名片后 登录
	 * @Date:2015年7月27日下午4:13:02
	 * @param type
	 * @param back  
	 * @return void 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void perfectFlag(String type,final AXCallBack back){
		RequestParams map = new RequestParams();
		map.add("cardId", AXSharedPreferences.getCardId(BaseApplication.getInstance()));
		map.add("type", type);	
		map.add(InterfaceConstants.INTERFACE_NAME,
				InterfaceConstants.PERFECT_TYPE);
		MasterHttpClient.get(map, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				String result = new String(arg2);
				if (StringUtils.isNotEmpty(result)) {
					if (GlobalConstants.isDebug) {
						Log.i(TAG, "code = " + arg0 + " , msg = "
								+ new String(arg2));
					}
					back.onCallBackString(result);
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				if (GlobalConstants.isDebug) {
					Log.i(TAG, "code = " + arg0);
				}
				back.onCallBackString(null);
			}
		});

	}
}

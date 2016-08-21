/**    
 * @author maomy  
 * @Description: 全局参数配置 放这里
 * @Package com.ciapc.nono.common   
 * @Title: GlobalConstants.java   
 * @date 2015-5-25 下午3:55:42   
 * @version V1.0  
   @说明  代码版权归  杭州安存网络科技有限公司 所有
 */
package com.ciapc.anxin.common;

import android.os.Environment;

public class GlobalConstants {

	/****
	 * 数据库参数定义 ，注意：每次数据库升级都要在这里添加添备注 修改记录: 1、第一版本，创建，时间2015-05-25
	 * 
	 * 
	 */
	public final static int DB_VERSION = 1;

	public final static String DB_NAME = "ciapc_anxin";

	// public final static String URL =
	// "http://121.199.24.78:9016/api/xinhuapi";
	public final static String URL = "http://xinhuapi.ancun.com";
	// public final static String URL = "http://112.124.52.105:9016/api/appApi";
	// public final static String URL = "http://10.0.17.57:8081/api/appApi?";
//	 public final static String URL = "http://10.0.17.57:8081/api/appApi?";
	// public final static String URL = "http://10.13.17.169:8081/api/appApi?";
	// public final static String URL = "http://10.0.17.8:8081/api/appApi?";

	public final static String SHARE = "http://xinhu.ancun.com/shareCard?id=";
	/**
	 * 日志开关
	 */
	public static boolean isDebug = true;

	// 超时时间，单位毫秒
	public final static int HTTP_TIME_OUT = 30000;

	/**
	 * post 请求方式
	 */
	public final static String HTTP_METHOD_POST = "POST";
	/**
	 * get 请求方式
	 */
	public final static String HTTP_METHOD_GET = "GET";
	/**
	 * 返回错误标识
	 */
	public static final int RESULT_ERROR = 0;
	/**
	 * 返回成功标识
	 */
	public static final int RESULT_OK = 1;
	/**
	 * 默认编码
	 */
	public static final String DEFAULT_CHARSET = "utf-8";

	// 是否第一次启动
	public static final String INITIALIZE = "initialize";

	// 头像SD卡保存地址
	public static final String PHOTO_LOCAL_PATH = Environment
			.getExternalStorageDirectory() + "/anxin/head";

	// 头像 内存保存地址
	public static final String PHOTO_SDK_PATH = Environment.getRootDirectory()
			+ "/anxin/head";

	// http请求成功返回code
	public static final String HTTP_SUCCESS_CODE = "1000000";

	// http请求返回数据
	public static final String HTTP_DATA = "data";

	// http请求返回信息
	public static final String HTTP_MSG = "msg";

	// http请求返回code
	public static final String HTTP_CODE = "code";

	// 手机号
	public static final String PHONE = "phone";

	// 密码
	public static final String PWD = "pwd";

	// 登录令牌
	public static final String TOKEN_ID = "tokenId";

	// 登录状态
	public static final String LOGIN_TYPE = "loginType";

	// 名片ID
	public static final String CARD_ID = "cardId";

	// 企业ID
	public static final String ENT_ID = "entId";

	// 企业验证状态 1 已审核 0 未审核
	public static final String ENT_STATUS = "entStatus";

	// 名片状态
	public static final String USER_STATUS = "userStatus";

	// 新消息提醒
	public static final String NEWINFO_REMIND = "newinforemind";

	// 声音
	public static final String SOUND = "sound";

	// 震动
	public static final String SHAKE = "shake";

	// 是否已经绑定企业
	public static final String IS_BIND = "isBind";

	// 新名片提醒
	public static final String NEW_REMIND = "newRemind";

	// 是否已经绑定企业
	public static final String UPDATE_REMIND = "updateRemind";

}

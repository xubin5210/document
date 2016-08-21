//package com.ciapc.anxin.wxapi;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.widget.Toast;
//
//import com.ciapc.share.common.IShareCallBack;
//import com.ciapc.share.common.ShareConstants;
//import com.ciapc.share.common.utils.ShareUtil;
//import com.ciapc.share.controller.ShareServiceFactory;
//
///**
// * 
// * @author wudd
// * @Description: 微信
// * @ClassName: WXEntryActivity.java
// * @date 2015年2月10日 上午10:00:17
// * @说明 代码版权归 杭州反盗版中心有限公司 所有
// */
//public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
//	private IWXAPI api;
//	private static IShareCallBack iWShareCallBack;
//	/**
//	 * 类型(朋友圈或者微信)
//	 */
//	private static String type;
//	/**
//	 * 分享应用来源（果冻录音还是其他应用）
//	 */
//	private static String comeFrom = "";
//	private static final int GET_INTEGRAL = 0;
//	private static final int SHARE_SOFT = 1;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//
//		// // 通过WXAPIFactory工厂，获取IWXAPI的实例
//		api = WXAPIFactory.createWXAPI(this,
//				ShareUtil.getMetaData(this, "SHARE_WX_APP_ID"), true);
//
//		api.handleIntent(getIntent(), this);
//	}
//
//	@Override
//	protected void onNewIntent(Intent intent) {
//		super.onNewIntent(intent);
//
//		setIntent(intent);
//		api.handleIntent(intent, this);
//	}
//
//	// 微信发送请求到第三方应用时，会回调到该方法
//	@Override
//	public void onReq(BaseReq req) {
//		switch (req.getType()) {
//		case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
//			break;
//		case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
//			break;
//		default:
//			break;
//		}
//	}
//
//	// 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
//	@Override
//	public void onResp(BaseResp resp) {
//		switch (resp.errCode) {
//		case BaseResp.ErrCode.ERR_OK:
//			// if(null == iWShareCallBack){
//			Toast.makeText(this, "分享成功", Toast.LENGTH_SHORT).show();
//			// }else{
//			// iWShareCallBack.onShare(ShareConstants.SHARE_SUCCESS, "分享成功");
//			// }
//			String fid = SharePanelPreferencesUtil.getFid(getBaseContext());
//			if (null != fid && !"".equals(fid) && !"null".equals(fid))// 分享录音
//				mHandler.sendEmptyMessage(GET_INTEGRAL);
//			if (SharePanelPreferencesUtil.getShareSoft(WXEntryActivity.this))// 分享软件
//				mHandler.sendEmptyMessage(SHARE_SOFT);
//			ShareServiceFactory.reportToNet(
//					SharePanelPreferencesUtil.getType(getBaseContext()),
//					SharePanelPreferencesUtil.getMsg(getBaseContext()),
//					SharedPreferencesUtil.getUid(getBaseContext()),
//					SharePanelPreferencesUtil.getFid(getBaseContext()),
//					SharePanelPreferencesUtil.getAppVersion(getBaseContext()),
//					SharePanelPreferencesUtil.getAppFrom(getBaseContext()),
//					SharePanelPreferencesUtil.getRecordType(getBaseContext()),
//					SharePanelPreferencesUtil.getComeFrom(getBaseContext()));
//			finish();
//			break;
//		case BaseResp.ErrCode.ERR_USER_CANCEL:
//			Toast.makeText(this, "分享取消", Toast.LENGTH_SHORT).show();
//			finish();
//			break;
//		case BaseResp.ErrCode.ERR_AUTH_DENIED:
//			if (null == iWShareCallBack) {
//				Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
//			} else {
//				iWShareCallBack.onShare(ShareConstants.OAUTH_ERROR, "授权失败");
//			}
//			finish();
//			break;
//		case BaseResp.ErrCode.ERR_SENT_FAILED:
//			if (null == iWShareCallBack) {
//				Toast.makeText(this, "分享失败", Toast.LENGTH_SHORT).show();
//			} else {
//				iWShareCallBack.onShare(ShareConstants.SHARE_ERROR, "分享失败");
//			}
//			finish();
//			break;
//		default:
//			break;
//		}
//
//	}
//
//	/**
//	 * @Auther: wudd
//	 * @Description: 设置回调
//	 * @Date:2015年2月10日下午6:03:25
//	 * @param iShareCallBack
//	 * @return void
//	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
//	 */
//	public static void setCallBack(IShareCallBack iShareCallBack) {
//		iWShareCallBack = iShareCallBack;
//	}
//
//	public static String getMsg() {
//		return msg;
//	}
//
//	public static void setMsg(String msg) {
//		WXEntryActivity.msg = msg;
//	}
//
//	public static String getUid() {
//		return uid;
//	}
//
//	public static void setUid(String uid) {
//		WXEntryActivity.uid = uid;
//	}
//
//	public static String getFid() {
//		return fid;
//	}
//
//	public static void setFid(String fid) {
//		WXEntryActivity.fid = fid;
//	}
//
//	public static String getAppFrom() {
//		return appFrom;
//	}
//
//	public static void setAppFrom(String appFrom) {
//		WXEntryActivity.appFrom = appFrom;
//	}
//
//	public static String getRecordType() {
//		return recordType;
//	}
//
//	public static void setRecordType(String recordType) {
//		WXEntryActivity.recordType = recordType;
//	}
//
//	public static String getAppVer() {
//		return appVer;
//	}
//
//	public static void setAppVer(String appVer) {
//		WXEntryActivity.appVer = appVer;
//	}
//
//	public static String getType() {
//		return type;
//	}
//
//	public static void setType(String type) {
//		WXEntryActivity.type = type;
//	}
//
//	public static String getComeFrom() {
//		return comeFrom;
//	}
//
//	public static void setComeFrom(String comeFrom) {
//		WXEntryActivity.comeFrom = comeFrom;
//	}
//
//	/**
//	 * 获取积分
//	 */
//	private Handler mHandler = new Handler() {
//		public void handleMessage(Message msg) {
//			switch (msg.what) {
//			case GET_INTEGRAL:
//				if (SystemUtils.isNetWorkConnected(WXEntryActivity.this)) {
//					IntegralDataHandler
//							.getInstance(WXEntryActivity.this)
//							.updateIntegral(
//									String.valueOf(5 * LoginSharedPreferencesUtil
//											.getUserLevelTimes(WXEntryActivity.this)),
//									"2", new IHttpCallBackString() {
//
//										@Override
//										public String httpCallBackString(
//												String retStr) {
//											if (!"1".equals(retStr)) {
//												SharePanelPreferencesUtil
//														.setShareSoft(
//																WXEntryActivity.this,
//																false);
//												DialogUtil
//														.showSystemToast(
//																WXEntryActivity.this,
//																"恭喜您获得了"
//																		+ 5
//																		* LoginSharedPreferencesUtil
//																				.getUserLevelTimes(WXEntryActivity.this)
//																		+ "积分");
//											} else {
//
//											}
//											return null;
//										}
//									});
//				} else {
//					DialogUtil.showSystemToast(WXEntryActivity.this, "网络出问题啦");
//				}
//				break;
//
//			case SHARE_SOFT:
//				if (SystemUtils.isNetWorkConnected(WXEntryActivity.this)) {
//					IntegralDataHandler
//							.getInstance(WXEntryActivity.this)
//							.updateIntegral(
//									String.valueOf(5 * LoginSharedPreferencesUtil
//											.getUserLevelTimes(WXEntryActivity.this)),
//									"5", new IHttpCallBackString() {
//
//										@Override
//										public String httpCallBackString(
//												String retStr) {
//											if (!"1".equals(retStr)) {
//												DialogUtil
//														.showSystemToast(
//																WXEntryActivity.this,
//																"恭喜您获得了"
//																		+ 5
//																		* LoginSharedPreferencesUtil
//																				.getUserLevelTimes(WXEntryActivity.this)
//																		+ "积分");
//											} else {
//
//											}
//											return null;
//										}
//									});
//				} else {
//					DialogUtil.showSystemToast(WXEntryActivity.this, "网络出问题啦");
//				}
//				break;
//			default:
//				break;
//			}
//		};
//	};
//
//}

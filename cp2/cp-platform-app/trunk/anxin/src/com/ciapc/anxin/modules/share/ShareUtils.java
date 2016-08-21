package com.ciapc.anxin.modules.share;

import android.content.Context;
import android.graphics.Bitmap;

import com.androidquery.AQuery;
import com.ciapc.share.common.utils.ShareUtil;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class ShareUtils {

	public static void initWx(Context mContext, String content, String url,String title) {
		String appid = ShareUtil.getMetaData(mContext, "SHARE_WX_APP_ID");
		IWXAPI api = WXAPIFactory.createWXAPI(mContext, appid, true);
		api.registerApp(appid);
		WXWebpageObject msgs = new WXWebpageObject();
		msgs.webpageUrl = url;

		WXMediaMessage media = new WXMediaMessage(msgs);
		media.title = title;
		media.description = content;

		AQuery aQuery = new AQuery(mContext);
		Bitmap bit = aQuery.getCachedImage(url);
		media.setThumbImage(bit);
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("webpage");
		req.message = media;
		req.scene = SendMessageToWX.Req.WXSceneSession;
		api.sendReq(req);
	}

	public static String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis())
				: type + System.currentTimeMillis();
	}
}

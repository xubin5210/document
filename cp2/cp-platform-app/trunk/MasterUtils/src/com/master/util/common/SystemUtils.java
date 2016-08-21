/**    
 * @author maomy  
 * @Description: 系统工具类
 * @Package com.ciapc.tzd.modules.common.utils   
 * @Title: DateUtil.java   
 * @date 2013-10-29 下午3:05:16   
 * @version V1.0 
 * @说明  代码版权归 杭州反盗版中心有限公司 所有
 */ 
package com.master.util.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Display;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class SystemUtils {

	private static  String  TAG ="SystemTools";
	
	/**
	 * @Auther: maomy  
	 * @Description: 手机是否网  true 连网 false 不连网
	 * @Date:2014-1-16上午10:48:39
	 * @param c
	 * @return  
	 * @return boolean 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static boolean isNetWorkConnected(Context c) {
		ConnectivityManager mConnectivity = (ConnectivityManager) c
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (mConnectivity != null) {
			NetworkInfo info = mConnectivity.getActiveNetworkInfo();
			if (info == null || !info.isConnected()) {
				return false;
			}
		} else {
		}
		return true;
	}

	
	/**
	 * @Auther: maomy  
	 * @Description: 是否是wifi
	 * @Date:2014-1-22下午3:22:51
	 * @param c
	 * @return  
	 * @return boolean 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static boolean isWifiNetWork(Context c) {
		ConnectivityManager conMan = (ConnectivityManager) c
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		// Network
		State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.getState();
		if (wifi == State.CONNECTED) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * @Auther: maomy  
	 * @Description: 是否是蜂窝数据 
	 * @Date:2014-1-22下午3:22:30
	 * @param c
	 * @return  
	 * @return boolean 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static boolean isMobileNetWork(Context c) {
		ConnectivityManager conMan = (ConnectivityManager) c
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		// Network
		State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.getState();
		if (wifi == State.CONNECTED) {
			return true;
		}
		return false;
	}

	/**
	 * ��ȡip��ַ
	 * 
	 * @return
	 */
	public static String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
		}
		return "127.0.0.1";

	}

	public static String getWIFIIpAddress(Context c) {
		WifiManager wifiManager = (WifiManager) c
				.getSystemService(Context.WIFI_SERVICE);
		if (!wifiManager.isWifiEnabled()) {
			wifiManager.setWifiEnabled(true);
		}
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		int ipAddress = wifiInfo.getIpAddress();
		return intToIp(ipAddress);
	}

	private static String intToIp(int i) {
		return ((i >> 24) & 0xFF) + "." + ((i >> 16) & 0xFF) + "."
				+ ((i >> 8) & 0xFF) + "." + (i & 0xFF);
	}

	public static boolean sdCardIsExsit() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}

	public static String getExternalStoragePath() {
		String state = android.os.Environment.getExternalStorageState();
		if (android.os.Environment.MEDIA_MOUNTED.equals(state)) {
			if (android.os.Environment.getExternalStorageDirectory().canWrite()) {
				return android.os.Environment.getExternalStorageDirectory()
						.getPath();
			}
		}
		return null;
	}

	public static boolean sendCommonTextMsg(Context context,
			String destinationAddress, String msg) {
		if (destinationAddress == null || destinationAddress.length() <= 0
				|| msg == null || msg.length() <= 0) {
			return false;
		}
		SmsManager smsManager = SmsManager.getDefault();
		PendingIntent sentIntent = PendingIntent.getBroadcast(context, 0,
				new Intent(), 0);
		PendingIntent recIntent = PendingIntent.getBroadcast(context, 1,
				new Intent(), 0);
		try {
			if (msg.length() > 70) {
				List<String> msgs = smsManager.divideMessage(msg);
				for (String subMsg : msgs) {
					smsManager.sendTextMessage(destinationAddress, null,
							subMsg, sentIntent, recIntent);
				}
			} else {
				smsManager.sendTextMessage(destinationAddress, null, msg,
						sentIntent, recIntent);
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	public static Drawable getLocalDrawable(String pathName) {
		if (getExternalStoragePath() == null)
			return null;
		if (pathName == null || pathName.equals("")) {
			return null;
		}
		BitmapDrawable db = new BitmapDrawable(pathName);
		return (Drawable) db;
	}

	public static Bitmap getLocalBitmap(String pathName) {
		if (getExternalStoragePath() == null)
			return null;
		if (pathName == null || pathName.equals("")) {
			return null;
		}
		Bitmap bitmap = null;
		bitmap = BitmapFactory.decodeFile(pathName);
		return bitmap;
	}

	public static Bitmap decodeFile(String path) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 2;
		return BitmapFactory.decodeFile(path, options);
	}

	public static Bitmap decodeFileInSampleSize(String filePath) {
		BitmapFactory.Options op = new BitmapFactory.Options();
		op.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, op);
		int screenWidth = 400;
		int screenHeight = 400;
		int wRatio = (int) Math.ceil(op.outWidth / (float) screenWidth); // �߻���ȱ���
		int hRatio = (int) Math.ceil(op.outHeight / (float) screenHeight); // �߻��߶ȱ���
		if (wRatio > 1 && hRatio > 1) {
			if (wRatio > hRatio) {
				op.inSampleSize = wRatio;
			} else {
				op.inSampleSize = hRatio;
			}
		}
		op.inJustDecodeBounds = false;
		Bitmap mBitmap = null;
		try {
			mBitmap = BitmapFactory.decodeFile(filePath, op);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mBitmap;

	}

	@SuppressWarnings("deprecation")
	public static Bitmap decodeFileInSampleSize(Activity activity,
			String filePath) {
		BitmapFactory.Options op = new BitmapFactory.Options();
		op.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, op);
		int screenWidth = 480;
		int screenHeight = 800;
		Display display = activity.getWindowManager().getDefaultDisplay();
		if (display.getWidth() > 0 && display.getHeight() > 0) {
			screenWidth = display.getWidth();
			screenHeight = display.getHeight();
		}
		int wRatio = (int) Math.ceil(op.outWidth / (float) screenWidth); // �߻���ȱ���
		int hRatio = (int) Math.ceil(op.outHeight / (float) screenHeight); // �߻��߶ȱ���
		if (wRatio > 1 && hRatio > 1) {
			if (wRatio > hRatio) {
				op.inSampleSize = wRatio;
			} else {
				op.inSampleSize = hRatio;
			}
		}
		op.inJustDecodeBounds = false;
		Bitmap mBitmap = null;
		try {
			mBitmap = BitmapFactory.decodeFile(filePath, op);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mBitmap;

	}
	
	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5',
		'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	
	public static String toHexString(byte[] b) { // String to byte
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
			sb.append(HEX_DIGITS[b[i] & 0x0f]);
		}
		return sb.toString();
	}
	public static Bitmap getHttpBitmap(String urlStr) {
		if (urlStr == null || urlStr.equals("")) {
			return null;
		}
		Bitmap bitmap = null;
		URL url;
		try {
			url = new URL(urlStr);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			InputStream in = con.getInputStream();
			bitmap = BitmapFactory.decodeStream(in);
			in.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bitmap;
	}

	public static HashMap<String, String> getDeviceCode(Context context) {
		HashMap<String, String> deviceCode = new HashMap<String, String>();
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = telephonyManager.getDeviceId();
		String sim = telephonyManager.getSimSerialNumber();
		String imsi = telephonyManager.getSubscriberId();
		String phoneNumber = telephonyManager.getLine1Number();

		deviceCode.put("imei", imei);
		deviceCode.put("sim", sim);
		deviceCode.put("imsi", imsi);
		deviceCode.put("phoneNumber", phoneNumber);
		return deviceCode;
	}
	
	public static String getPhoneNumber(Context context){
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getLine1Number(); 
	}

	public static String getDeviceIMEI(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = telephonyManager.getDeviceId();
		if(null == imei || "".equals(imei))
			return "0";
		else
			return imei;
	}

	public static String getDeviceIMSI(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String imsi = telephonyManager.getSubscriberId();
		if(null == imsi || "".equals(imsi))
			return "0";
		else 
			return imsi;
	}

	public static String getSimNumber(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getSimSerialNumber();
	}

	public static String getResoureString(Context c, int resId) {
		return c.getResources().getString(resId);
	}

	
	//手机品牌
	public static String getTelType() {
		String type =Build.BRAND;
		if(type.indexOf("htc")!=-1){
			return "HTC";
		}
		return Build.BRAND ;
	}
	
	//手机型号
		public static String getTelModel() {
			return Build.MODEL;
		}
		
		//系统版本号
		public static String getTelOSVer() {
			return Build.VERSION.RELEASE ;
		}
		
		//软件版本号
		@SuppressWarnings("unused")
		public static String getTelAppVer(Context context) {
			PackageInfo  info;
			 int versionCode = 0;	
			  String versionName = null;
				 try {
					info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
					  // 当前应用的版本名称  
				     versionName = info.versionName;  
				    // 当前版本的版本号  
				     versionCode = info.versionCode;  
				    // 当前版本的包名  
				    String packageNames = info.packageName;  		
	
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
				  
			return versionName ;
		}
	
		
		/**@author chenjh
		 * 获取手机内核版本号 
		 * @return
		 */
		public static String getLinuxKernelVer(){
			Process process = null;
			String kernelVer = "";
			try {

			process = Runtime.getRuntime().exec("cat /proc/version");

			} catch (IOException e) {
				Log.v("SystemTools", "获取内核出错...");

					}
		InputStream outs = process.getInputStream();
		InputStreamReader isrout = new InputStreamReader(outs);
		BufferedReader brout = new BufferedReader(isrout,8*1024);
		String result = "";
		String line;
		try {
			while ( (line = brout.readLine()) != null) {
					result += line;
				}
			} catch (IOException e) {

				Log.v("SystemTools", "获取内核出错...");
			}
	if( result != "" ) {
		String Keyword = "version ";
		int index = result.indexOf(Keyword);
			Log.v("SystemTools", result);
		line = result.substring(index + Keyword.length());
		index = line.indexOf(" ");
				  kernelVer = line.substring(0,index);
//		 kernelVer = line;
		}
			
			return  kernelVer;
		}
		
		
	
	/**
	 * ��ͼƬ���Բ��
	 * 
	 * @param bitmap
	 *            ��Ҫ�޸ĵ�ͼƬ
	 * @param pixels
	 *            Բ�ǵĻ���
	 * @return Բ��ͼƬ
	 */
	public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
		if (bitmap == null)
			return null;
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9.]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	public static Animation getAnim(Context context, int animID) {
		return AnimationUtils.loadAnimation(context, animID);
	}

	/**
	 * Resize a bitmap's size
	 * 
	 * @param b
	 * @param w
	 *            the target width
	 * @param h
	 *            the target height
	 */
	public static Bitmap resizeBitmap(Bitmap b, int w, int h) {
		Bitmap newB = null;
		int ow = b.getWidth();
		int oh = b.getHeight();
		final Matrix m = new Matrix();
		m.postScale(w / ow, h / oh);
		newB = Bitmap.createBitmap(b, 0, 0, ow, oh, m, true);
		return newB;
	}

	public static int getInterzoneRandom() {
		Random rand = new Random();
		return rand.nextInt(15) + 84;
	}

	@SuppressWarnings("deprecation")
	public static Drawable resizeImage(Bitmap bitmap, int w, int h) {
		// load the origial Bitmap
		Bitmap BitmapOrg = bitmap;
		int width = BitmapOrg.getWidth();
		int height = BitmapOrg.getHeight();
		int newWidth = w;
		int newHeight = h;
		// calculate the scale
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// create a matrix for the manipulation
		Matrix matrix = new Matrix();
		// resize the Bitmap
		matrix.postScale(scaleWidth, scaleHeight);
		// if you want to rotate the Bitmap
		// matrix.postRotate(45);
		// recreate the new Bitmap
		Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
				height, matrix, true);
		// make a Drawable from Bitmap to allow to set the Bitmap
		// to the ImageView, ImageButton or what ever
		return new BitmapDrawable(resizedBitmap);
	}

	public static Bitmap cutBitmap(String pathName) {
		Bitmap b = BitmapFactory.decodeFile(pathName);
		if (b == null) {
			return null;
		}
		b = Bitmap.createBitmap(b, 0, 0, 48, 18);
		return b;
	}

	
	public static void PlayMusic(Context c, File file) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file), "audio");
		try {
			c.startActivity(intent);
		} catch (Exception e) {
			Log.i(TAG, "试听出错................................");
		}
		
	}
	
	/**
	 * 播放网络音乐  chenjh
	 * @param ctx
	 * @param filePath
	 */
	public static void playMusicFormNet(Context ctx, String videoUri){		
		MediaPlayer mediaPlayer;
//		FileInputStream fis = null;
		  try {  
//			  fis = new FileInputStream(videoUri);
	            mediaPlayer = new MediaPlayer();  
//	            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);  
	            mediaPlayer.reset();  
	            mediaPlayer.setDataSource(videoUri);  
	            mediaPlayer.prepare();//prepare之后自动播放  
	        } catch (Exception e) {  
	            Log.e("mediaPlayer", "error", e);  
	        }  
	}
	

	public static Boolean deleteFile(String path) {
		if (null == path) {
			return false;
		}
		File file = new File(path);
		if (file.exists()) {
			file.delete();
			return true;
		}
		return false;
	}

	public static Boolean renameFile(String oldPath, String name) {
		if (null == oldPath || null == name || name.equals("")) {
			return false;
		}
		File file = new File(oldPath);
		if (file.renameTo(new File(file.getParent() + "/" + name))) {
			return true;
		}
		return false;
	}
	
	/**
	 * 获取应用程序版本号  如 1
	 * @return 返回应用程序的版本号,如果错误则返回-1
	 */
	public static int getAppVersionCode(Context ctx) {
		final String pkgName = ctx.getPackageName();
		try {
			PackageInfo pinfo = ctx.getPackageManager().getPackageInfo(pkgName, 0);
			return pinfo.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * 获取应用程序版本号 名 如 1.0
	 * @return 返回应用程序的版本号名,如果错误则返回-1
	 */
	public static String getAppVersionName(Context ctx) {
		final String pkgName = ctx.getPackageName();
		try {
			PackageInfo pinfo = ctx.getPackageManager().getPackageInfo(pkgName, 0);
			return pinfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
	/**
	 * @Auther: maomy  
	 * @Description: 获取meta data 里的数据
	 * @Date:2014年6月23日上午9:48:30
	 * @param context
	 * @param metaKey
	 * @return  
	 * @return String 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static String getMetaData(Context context,String metaKey){
		String retStr = "";
		try {
			PackageManager packageInfo = context.getPackageManager();
			String packageName = context.getPackageName();
			retStr = packageInfo.getApplicationInfo(packageName,
					PackageManager.GET_META_DATA).metaData
					.getString(metaKey);
		}catch(NameNotFoundException e){
			e.printStackTrace();
		}
		return retStr;
	}
	
	/**
	 * @Auther: wudd  
	 * @Description: 获取手机型号 
	 * @Date:2014年10月22日下午3:40:48
	 * @return  
	 * @return String 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static String getPhoneModel(){
		String model = SystemUtils.getTelModel().toLowerCase();
		if("".equals(model) || null == model)
			return "";
		if(model.indexOf("htc") != -1){
			return "htc";
		}else if(model.indexOf("sumsung") != -1){
			return "sumsung";
		}else if(model.indexOf("coolpad") != -1){
			return "coolpad";
		}else if(model.indexOf("xiaomi") != -1)
			return "xiaomi";
		return model;
	}
	
}
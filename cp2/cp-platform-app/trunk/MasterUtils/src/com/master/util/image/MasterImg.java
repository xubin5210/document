package com.master.util.image;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.androidquery.util.AQUtility;

/**
 * @Auther: zhuwt 
 * @Description: 图片处理
 * @Date:2014-7-16下午4:35:04
 * @return
 * @return AQuery
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */
public class MasterImg {

	private String TAG = "MasterImg";

	private static AQuery aq;

	private static Context mContext;

	private static String path;
	
	private static MasterImg masterImg;
	
	public MasterImg(Context context) {
		mContext = context;
	}

	public static MasterImg getInstance(Context context) {
		if(null == masterImg){
			masterImg = new MasterImg(context);
		}
		return masterImg;
	}

	/**
	 * @Auther: zhuwt 
	 * @Description: 设计图片缓存
	 * @Date:2014-7-16下午4:35:04
	 * @return
	 * @return AQuery
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void setCachePath(String paths) {
		path = paths;
		// 设置缓存路径 存在本地
		File cacheDir = new File(path);
		if (!cacheDir.exists()) {
			cacheDir.mkdir();
			AQUtility.setDebug(true);
			AQUtility.setCacheDir(cacheDir);
		}
	}

	/**
	 * @Auther: zhuwt
	 * @Description:
	 * @Date:2014-7-16下午4:35:04
	 * @return
	 * @return AQuery
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private static AQuery getAQueryInstance() {
		if (null == aq)
			return new AQuery(mContext);
		return aq;
	}

	/**
	 * 图片加载方法
	 * 
	 * @param url
	 *            圖片網絡地址
	 * @param view
	 *            控件
	 * @param memory
	 *            記憶緩存
	 * @param local
	 *            本地緩存
	 * @param yasuo
	 *            壓縮率 建議200~300
	 * @param resources
	 *            加載失敗后 顯示的圖片
	 */
	public void loadImg(String url, ImageView view, boolean memory,
			boolean local, int yasuo, int resources) {
		aq = getAQueryInstance();
		aq.id(view).image(url, memory, local, yasuo, resources,
				new BitmapAjaxCallback() {
					@Override
					protected void callback(String url, ImageView iv,
							Bitmap bm, AjaxStatus status) {
						if (iv.getTag().equals(url)) {
							iv.setImageBitmap(bm);
						}
					}
				});
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 过大时 优化处理 计算缩放尺寸
	 * @Date:2014-7-3下午3:55:58
	 * @param options
	 * @param minSideLength
	 * @param maxNumOfPixels
	 * @return
	 * @return int
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 处理图片
	 * @Date:2014-7-3下午3:56:38
	 * @param options
	 * @param minSideLength
	 * @param maxNumOfPixels
	 * @return
	 * @return int
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		// 计算合适的范围
		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			return lowerBound;
		}
		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	/**
	 * 
	 * 
	 * @param imgFile
	 *            文件地址
	 * @param minSideLength
	 *            最小显示区 一般赋值为 -1
	 * @param maxNumOfPixels
	 *            像素 displayX*displayY
	 * @return
	 */
	public static Bitmap getBitmap(String imgFile, int minSideLength,
			int maxNumOfPixels) {
		if (imgFile == null || imgFile.length() == 0)
			return null;
		try {
			FileDescriptor fd = new FileInputStream(imgFile).getFD();
			// 返回imgFile的标识连接到正在使用此文件输入流文件系统的实际文件的对象。
			// 不分配内存 获取图片的宽高
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			// BitmapFactory.decodeFile(imgFile, options);
			// 通过底层生成图片 与decodeFile不同 decodeFile是以流的形式 相比较而言 更省内存
			BitmapFactory.decodeFileDescriptor(fd, null, options);
			// 动态计算出合适的尺寸
			options.inSampleSize = computeSampleSize(options, minSideLength,
					maxNumOfPixels);
			try {
				// 这里一定要将其设置回false，因为之前我们将其设置成了true
				// 设置inJustDecodeBounds为true后，decodeFile并不分配空间，即，BitmapFactory解码出来的Bitmap为Null,但可计算出原始图片的长度和宽度
				options.inJustDecodeBounds = false;
				Bitmap bmp = BitmapFactory.decodeFile(imgFile, options);
				return bmp == null ? null : bmp;
			} catch (OutOfMemoryError err) {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 调用系统拍照
	 * 
	 * @param mUri
	 *            图片保存路径
	 */
	public Intent takePhoto(Uri mUri) {
		Intent mIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		mIntent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
		return mIntent;
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 手机相册裁剪图片
	 * @Date:2014-2-21下午4:35:12
	 * @param imageUri
	 *            保存地址
	 * @param d
	 *            输出长度
	 * @param y
	 *            输出宽度
	 * @return
	 * @return Intent
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public Intent takeAlbum(Uri imageUri, int x, int y) {
		// 调用选择相册裁剪功能
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType("image/*");
		intent.putExtra("crop", "true");
		double w = x;
		double h = y;
		intent.putExtra("aspectX", (int) Math.rint((w / h))); // 设置长和宽的比例
//		intent.putExtra("aspectX", 1); 
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", x); // 设置输出的长
		intent.putExtra("outputY", y); // 设置输出的宽
		intent.putExtra("scale", true);
		intent.putExtra("scaleUpIfNeeded", true);
		intent.putExtra("return-data", false);
		// 设置是否在给定的Uri 中获取图片
		// ture: 在给定的URL中获取图片 false: 相反
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString()); // 设置输出格式
		return intent;
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 拍照裁剪图片
	 * @Date:2014-2-21下午4:37:26
	 * @param uri
	 *            保存地址
	 * @param outputX
	 *            输出宽度
	 * @param outputY
	 *            输出长度
	 * @return
	 * @return Intent
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public Intent cropBytakeP(Uri uri, int outputX, int outputY) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		double w = outputX;
		double h = outputY;
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", (int) Math.rint((w / h))); // 设置长和宽的比例
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", outputX); // 设置输出的长
		intent.putExtra("outputY", outputY); // 设置输出的宽
		intent.putExtra("scale", true);
		intent.putExtra("scaleUpIfNeeded", true);
		intent.putExtra("return-data", false);
		// 设置是否在给定的Uri 中获取图片
		// ture: 在给定的URL中获取图片 false: 相反
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());// 设置输出格式
		return intent;
	};

	/**
	 * @Auther: zhuwt
	 * @Description: 根据图片地址 返回Bitmap
	 * @Date:2014-2-21下午4:39:28
	 * @param mUri
	 * @return
	 * @return Bitmap
	 * @throws IOException
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public Bitmap getBitmapByUri(Uri mUri) throws IOException {
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(mContext.getContentResolver()
					.openInputStream(mUri));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}
	
	/**
	 * @Auther: zhuwt
	 * @Description: 网络下载保存在本地
	 * @Date:2014-2-21下午4:39:28
	 * @param mUri
	 * @throws IOException
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void downLoad(String url,File mFile,final IAqueryFileCallBack mBack){
		aq = getAQueryInstance();
		aq.download(url, mFile, new AjaxCallback<File>(){
			public void callback(String url, File file, AjaxStatus status) {
                if(file != null){
                	mBack.aqueryFileCallBack(file);
                }else{
                	mBack.aqueryFileCallBack(null);
                }
		 }
		});
	}
	
}

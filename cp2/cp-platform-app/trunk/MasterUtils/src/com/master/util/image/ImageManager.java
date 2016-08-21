package com.master.util.image;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

/**
 * @author ageng
 * @Description: imageview 管理类
 * @ClassName: ImageManager.java
 * @date 2015-5-15 下午2:39:57
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */
public class ImageManager {

	/**
	 * @Auther: ageng
	 * @Description: 这个 图片 是否 可读
	 * @Date:2015-5-15下午2:40:47
	 * @param path
	 * @return
	 * @return boolean
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static boolean isThisBitmapCanRead(File path) {
		if (TextUtils.isEmpty(path.toString())) {
			return false;
		}
		if (!path.exists()) {
			return false;
		}
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path.toString(), options);
		int width = options.outWidth;
		int height = options.outHeight;
		if (width == -1 || height == -1) {
			return false;
		}
		return true;
	}

	/**
	 * @Auther: ageng
	 * @Description:读取 正常 的 图片
	 * @Date:2015-1-24下午5:10:42
	 * @param filePath
	 *            图片 的地址
	 * @param reqWidth
	 *            控件 的宽度
	 * @param reqHeight
	 *            控件 的高度
	 * @return
	 * @return Bitmap
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static Bitmap readNormalPic(File file, int reqWidth,
			int reqHeight) {
		try {
			if (TextUtils.isEmpty(file.toString())) {
				return null;
			}
			
			boolean fileExist = file.exists();

			// 图片 不存在 直接返回
			if (!fileExist) {
				return null;
			}

			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(file.toString(), options);

			// 计算出 地址 filePath -> bitmap 与控件的 的比例
			if (reqHeight > 0 && reqWidth > 0) {
				options.inSampleSize = calculateInSampleSize(options, reqWidth,
						reqHeight);
			}
			options.inJustDecodeBounds = false;
			options.inPurgeable = true;
			options.inInputShareable = true;
			Bitmap bitmap = BitmapFactory.decodeFile(file.toString(), options);
			if (bitmap == null) {
				// 本地图片 已被损坏 ，直接删除
				file.delete();
				return null;
			}
			// 控件的大小 必不为0
			if (reqHeight > 0 && reqWidth > 0) {
				// 得到 控件 和 bitmap 大小 的 比例
				int[] size = calcResize(bitmap.getWidth(), bitmap.getHeight(),
						reqWidth, reqHeight);
				if (size[0] > 0 && size[1] > 0) {
					// 生成 新的bitmap
					Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap,
							size[0], size[1], true);
					if (scaledBitmap != bitmap) {
						bitmap.recycle();
						bitmap = scaledBitmap;
					}
				}
			}
			return bitmap;
		} catch (OutOfMemoryError ignored) {
			throw ignored;
		}
	}

	/**
	 * @Auther: ageng
	 * @Description: 动态 计算 控件 和 图片 大小
	 * @Date:2015-1-24下午5:16:02
	 * @param options
	 * @param reqWidth
	 *            控件 的 宽度
	 * @param reqHeight
	 *            控件 的 高度
	 * @return
	 * @return int
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {

		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			if (height > reqHeight && reqHeight != 0) {
				inSampleSize = (int) Math.floor((double) height
						/ (double) reqHeight);
			}
			int tmp = 0;
			if (width > reqWidth && reqWidth != 0) {
				tmp = (int) Math.floor((double) width / (double) reqWidth);
			}
			inSampleSize = Math.max(inSampleSize, tmp);
		}
		int roundedSize;
		if (inSampleSize <= 8) {
			roundedSize = 1;
			while (roundedSize < inSampleSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (inSampleSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	/**
	 * @Auther: ageng
	 * @Description:得到 图片 和 】控件 大小 比例 参数
	 * @Date:2015-1-24下午5:28:40
	 * @param actualWidth
	 * @param actualHeight
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 * @return int[]
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private static int[] calcResize(int actualWidth, int actualHeight,
			int reqWidth, int reqHeight) {

		int height = actualHeight;
		int width = actualWidth;

		float betweenWidth = ((float) reqWidth) / (float) actualWidth;
		float betweenHeight = ((float) reqHeight) / (float) actualHeight;

		float min = Math.min(betweenHeight, betweenWidth);

		height = (int) (min * actualHeight);
		width = (int) (min * actualWidth);

		return new int[] { width, height };
	}

}

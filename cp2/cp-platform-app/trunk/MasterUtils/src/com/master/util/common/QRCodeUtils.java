package com.master.util.common;

import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * 
 * @author zhuwt
 * @Description: 二维码工具
 * @ClassName: QRCodeUtils.java
 * @date 2015年5月28日 上午10:55:38
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */
public class QRCodeUtils {

	/**
	 * @Auther: zhuwt
	 * @Description: 二维码生成工具
	 * @Date:2015年5月28日上午11:06:47
	 * @param url
	 *            地址
	 * @param bitmap
	 *            二维码中间的图片
	 * @param width
	 *            二维码长
	 * @param higth
	 *            二维码 宽
	 * @param imgW
	 *            中间图片的宽度
	 * @return
	 * @return boolean
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static Bitmap createQRCode(String url, Bitmap bitmap, int width,
			int higth, int imgW) {
		if (!StringUtils.isNotEmpty(url)) {
			return null;
		}
		Bitmap result = null;
		try {
			Matrix matrix = new Matrix();
			float sx = (float) 2 * imgW / bitmap.getWidth();
			float sy = (float) 2 * imgW / bitmap.getHeight();
			matrix.setScale(sx, sy);// 设置缩放信息
			bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
					bitmap.getHeight(), matrix, false);
			MultiFormatWriter writer = new MultiFormatWriter();
			Hashtable<EncodeHintType, String> hst = new Hashtable<EncodeHintType, String>();
			hst.put(EncodeHintType.CHARACTER_SET, "UTF-8");// 设置字符编码
			BitMatrix mBitMatrix = writer.encode(url, BarcodeFormat.QR_CODE,
					width, higth, hst);// 生成二维码矩阵信息
			int w = mBitMatrix.getWidth();// 矩阵高度
			int h = mBitMatrix.getHeight();// 矩阵宽度
			int halfW = w / 2;
			int halfH = h / 2;
			int[] pixels = new int[w * h];// 定义数组长度为矩阵高度*矩阵宽度，用于记录矩阵中像素信息
			for (int y = 0; y < h; y++) {// 从行开始迭代矩阵
				for (int x = 0; x < w; x++) {// 迭代列
					if (x > halfW - imgW && x < halfW + imgW
							&& y > halfH - imgW && y < halfH + imgW) {// 该位置用于存放图片信息
						// 记录图片每个像素信息
						pixels[y * width + x] = bitmap.getPixel(x - halfW
								+ imgW, y - halfH + imgW);
					} else {
						if (mBitMatrix.get(x, y)) {// 如果有黑块点，记录信息
							pixels[y * width + x] = 0xff000000;// 记录黑块信息
						}
					}
				}
			}
			result = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
			// 通过像素数组生成bitmap
			result.setPixels(pixels, 0, w, 0, 0, width, higth);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
}

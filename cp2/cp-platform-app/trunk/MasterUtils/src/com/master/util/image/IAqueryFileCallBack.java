package com.master.util.image;

import java.io.File;

import android.graphics.Bitmap;

public  interface IAqueryFileCallBack {
	
	/**
	 * @Auther: Zhuwt  
	 * @Description: 回调File方法
	 * @Date:2014-7-31下午5:52:48
	 * @param retStr  请求返回结果
	 * @return  
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void aqueryFileCallBack(File file);
	/**
	 * @Auther: ageng  回调   bitmap
	 * @Description: h
	 * @Date:2015-5-16下午4:06:10
	 * @param bitmap  
	 * @return void 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void callBackBitmap(Bitmap bitmap);
	
	/**
	 * @Auther: ageng  
	 * @Description: 回调  失败 原因
	 * @Date:2015-5-16下午4:06:30
	 * @param reason  
	 * @return void 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void callBackString(String arg1);
	
	
	
}

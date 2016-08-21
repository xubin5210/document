/**    
 * @author maomy  
 * @Description: sdcard 工具类  
 * @Package com.ciapc.tzd.modules.common.utils   
 * @Title: SDCardUtil.java   
 * @date 2013-12-17 上午9:47:35   
 * @version V1.0 
 * @说明  代码版权归 杭州反盗版中心有限公司 所有
 */ 
package com.master.util.common;

import java.io.File;

import android.os.Environment;
import android.os.StatFs;



public class SDCardUtil {

	
	/**
	 * @Auther: maomy  
	 * @Description:  sdcard是否存在
	 * @Date:2013-12-17上午9:48:02
	 * @return  
	 * @return boolean 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static boolean ExistSDCard() {  
		  if (android.os.Environment.getExternalStorageState().equals(  
		    android.os.Environment.MEDIA_MOUNTED)) {  
		   return true;  
		  } else  
		   return false;  
		 } 
	
	
	/**
	 * @Auther: maomy  
	 * @Description:  SD卡剩余空间 
	 * @Date:2013-12-17上午9:48:34
	 * @return  
	 * @return long 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static String getSDFreeSize(){  
		 String s="";
	     //取得SD卡文件路径  
	     File path = Environment.getExternalStorageDirectory();   
	     StatFs sf = new StatFs(path.getPath());   
	     //获取单个数据块的大小(Byte)  
	     long blockSize = sf.getBlockSize();   
	     //空闲的数据块的数量  
	     long freeBlocks = sf.getAvailableBlocks();  
	     //返回SD卡空闲大小  
	     //return freeBlocks * blockSize;  //单位Byte  
	     //return (freeBlocks * blockSize)/1024;   //单位KB  
	     long size= (freeBlocks * blockSize)/1024/1024; //单位MB  
	     if(size>1000){
	    	  s+=String.valueOf(size/1024)+"GB";
	     }else{
	    	 s+=String.valueOf(size)+"MB";
	    	 
	     }
	     return s;
	   }   
	
	
	/**
	 * @Auther: maomy  
	 * @Description: SD卡总容量 
	 * @Date:2013-12-17上午9:49:07
	 * @return  
	 * @return long 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static  long getSDAllSize(){  
	     //取得SD卡文件路径  
	     File path = Environment.getExternalStorageDirectory();   
	     StatFs sf = new StatFs(path.getPath());   
	     //获取单个数据块的大小(Byte)  
	     long blockSize = sf.getBlockSize();   
	     //获取所有数据块数  
	     long allBlocks = sf.getBlockCount();  
	     //返回SD卡大小  
	     //return allBlocks * blockSize; //单位Byte  
	     //return (allBlocks * blockSize)/1024; //单位KB  
	     return (allBlocks * blockSize)/1024/1024; //单位MB  
	   }      
	
}

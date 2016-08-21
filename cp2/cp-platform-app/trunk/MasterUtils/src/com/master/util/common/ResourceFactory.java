/**    
 * @author wudd  
 * @Description: 获取资源Id  
 * @Package com.ciapc.share.common   
 * @Title: ResourceFactory.java   
 * @date 2014-3-5 下午12:20:16   
 * @version V1.0 
 * @说明  代码版权归 杭州反盗版中心有限公司 所有
 */
package com.master.util.common;


import android.content.Context;

public class ResourceFactory {
	/**
	 * @Auther: wudd
	 * @Description: 获取资源Id
	 * @Date:2014-3-5下午12:22:22
	 * @param context
	 * @param className
	 * @param name
	 * @return
	 * @return int
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	@SuppressWarnings("rawtypes")
	public static int getIdByName(Context context, String className, String name) {
		String packageName = context.getPackageName();
		Class r = null;
		int id = 0;
		try {
			r = Class.forName(packageName + ".R");

			Class[] classes = r.getClasses();
			Class desireClass = null;

			for (int i = 0; i < classes.length; ++i) {
				if (classes[i].getName().split("\\$")[1].equals(className)) {
					desireClass = classes[i];
					break;
				}
			}

			if (desireClass != null)
				id = desireClass.getField(name).getInt(desireClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}

		return id;
	}
}

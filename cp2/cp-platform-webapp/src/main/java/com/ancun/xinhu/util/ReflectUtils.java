package com.ancun.xinhu.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

import org.apache.commons.fileupload.FileItem;

import com.ancun.products.core.utils.DateUtils;

/**
 * 通过反射设置对象属性值
 * 
 * <p>
 * create at 2015年6月25日 下午12:42:30
 * 
 * @author <a href="mailto:qiande@ancun.com">QianDe</a>
 * @version %I%, %G%
 * @see
 */
public class ReflectUtils {

	public static void reflect(Object obj, FileItem fItem) throws Exception {
		if (obj == null)
			return;
		// 字段名
		String fieldName = fItem.getFieldName();
		Field field = null;
		try {
			field = obj.getClass().getDeclaredField(fieldName);
		} catch (NoSuchFieldException e1) {
			return;
		}

		field.setAccessible(true);

		String setXXX = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		Method method = null;
		String value = new String(fItem.getString().getBytes("ISO-8859-1"),"UTF-8");
		// 字段值
		if (field.getType().getName().equals(java.lang.String.class.getName())) {
			// String type
			try {
				method = obj.getClass().getDeclaredMethod(setXXX, String.class);
				method.invoke(obj, new Object[] { value });
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} else if (field.getType().getName().equals(java.lang.Integer.class.getName())
				|| field.getType().getName().equals("int")) {
			// Integer type
			try {
				method = obj.getClass().getDeclaredMethod(setXXX, Integer.class);
				method.invoke(obj, new Object[] { Integer.parseInt(value) });
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} else if (field.getType().getName().equals(java.util.Date.class.getName())) {
			// Integer type
			try {
				method = obj.getClass().getDeclaredMethod(setXXX, Date.class);
				String dateStr = value;
				method.invoke(obj, DateUtils.convertStringToDate("yyyy-MM-dd", dateStr));
			}  catch (Exception e) {
				throw new Exception(e);
			}
		}
	}
}

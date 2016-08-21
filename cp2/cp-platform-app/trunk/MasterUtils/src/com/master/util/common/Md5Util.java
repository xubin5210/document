package com.master.util.common;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 名称：可以指定字符编码的MD5加密
 * */
public class Md5Util {
	/**
	 * Used building output as Hex
	 */
	private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * 对字符串进行MD5加密
	 * @param text 明文
	 * @param charSet 字符编码
	 * @return 密文
	 */
	public static String encrypt(String text, String charSet) {
		MessageDigest msgDigest = null;
		try {
			msgDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("System doesn't support MD5 algorithm.");
		}
		//charSet为null时，不重新编码
		if(charSet != null){
			try {
				msgDigest.update(text.getBytes(charSet)); //注意该接口是按照utf-8编码形式加密
			} catch (UnsupportedEncodingException e) {
				throw new IllegalStateException("System doesn't support your  EncodingException.");
			}
		}
		
		byte[] bytes = msgDigest.digest();
		return new String(encodeHex(bytes));
	}
	
	/**
	 * 对字符串进行MD5加密
	 * @param text 明文
	 * @return 密文
	 */
	public static String encrypt(String text) {
		return encrypt(text, null);
	}

	public static char[] encodeHex(byte[] data) {
		int l = data.length;
		char[] out = new char[l << 1];
		// two characters form the hex value.
		for (int i = 0, j = 0; i < l; i++) {
			out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
			out[j++] = DIGITS[0x0F & data[i]];
		}
		return out;
	}
	
	
	public static byte[] hex2byte(String strhex) {
		if (strhex == null) {
			return null;
		}
		int l = strhex.length();
		if (l % 2 == 1) {
			return null;
		}
		byte[] b = new byte[l / 2];
		for (int i = 0; i != l / 2; i++) {
			b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2),
					16);
		}
		return b;
	}

	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}
	
	/*
	 * 16进制数字字符集
	 */
	private static String hexString = "0123456789ABCDEF";

	/*
	 * 将字符串编码成16进制数字,适用于所有字符（包括中文）
	 */
	public static String encode(String str) {
		// 根据默认编码获取字节数组
		byte[] bytes = str.getBytes();
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		// 将字节数组中每个字节拆解成2位16进制整数
		for (int i = 0; i < bytes.length; i++) {
			sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
			sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
		}
		return sb.toString();
	}

	/*
	 * 将16进制数字解码成字符串,适用于所有字符（包括中文）
	 */
	public static String decode(String bytes) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(
				bytes.length() / 2);
		// 将每2位16进制整数组装成一个字节
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString
					.indexOf(bytes.charAt(i + 1))));
		return new String(baos.toByteArray());
	}
	
	/*
	 * 将16进制数字解码成字符串,适用于所有字符（包括中文）
	 */
	public static byte[] decodeToByte(String bytes) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(
				bytes.length() / 2);
		// 将每2位16进制整数组装成一个字节
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString
					.indexOf(bytes.charAt(i + 1))));
//		byte[] result = baos.toByteArray();
//		for(int i=0;i<result.length;i++){
//			if(result[i]<0){
//				result[i] += 128;
//			}
//		}
//		return result;
		return baos.toByteArray();
	}

	/**
	 * �ַ��Ƿ���ֵ(null��շ���false)
	 * 
	 * @param content
	 * @return �Ƿ���ֵ
	 */
	public static boolean hasContent(String content) {
		return (content != null && content.length() > 0);
	}

	/**
	 * �ַ�����ת��
	 * 
	 * @param str
	 * @return -1��ʾʧ��
	 */
	public static int strToInt(String str) {
		try {
			return Integer.valueOf(str).intValue();
		} catch (NumberFormatException ex) {
			return -1;
		}
	}

	/**
	 * ���ֽ���string��ʾ
	 * 
	 * @param b
	 *            �ֽ�����
	 * @return ����
	 */
	public static String byteToString(byte[] b) {
		if (null == b)
			return "";
		StringBuilder hs = new StringBuilder("");
		String tmp = "";
//		Log.i("TcpClient", "b.length:" + b.length);
		for (int n = 0; n < b.length; n++) {
			tmp = (java.lang.Integer.toString(b[n]));
			hs.append(tmp);
		}
		tmp = null;
		return hs.toString();
	}

	/**
	 * ���ֽ���16������ʾ
	 * 
	 * @param b
	 *            �ֽ�����
	 * @return ����
	 */
	public static String byteToHexString(byte[] b) {
		if (null == b)
			return "";
		// ת��16�����ַ�
		StringBuilder hs = new StringBuilder("");
		String tmp = "";
		for (int n = 0; n < b.length; n++) {
			tmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (tmp.length() == 1) {
				hs.append("0" + tmp);
			} else {
				hs.append(tmp);
			}
			if (n != b.length - 1)
				hs.append(" ");
		}
		tmp = null;
		return hs.toString().toUpperCase(); // ת�ɴ�д
	}

	/**
	 * �ֽ����鵽�ַ�����ת��
	 * 
	 * @param buffer
	 * @return 16���Ʊ�ʾ
	 */
	public static String byteToHexString(ByteBuffer buffer) {
		ByteBuffer buf = buffer.duplicate();
		byte[] byteBuffer = new byte[buf.limit() - buf.position()];
		buf.get(byteBuffer);
		return byteToHexString(byteBuffer);
	}

	public static String MD5(String str) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		char[] charArray = str.toCharArray();
		byte[] byteArray = new byte[charArray.length];
		for (int i = 0; i < charArray.length; i++) {
			byteArray[i] = (byte) charArray[i];
		}
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString().substring(8, 24);
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
	
	public static String MD5EncodeToHex(byte[] b) {
		try {
			// Create MD5 Hash
			MessageDigest digest = java.security.MessageDigest
					.getInstance("MD5");
			digest.update(b);
			byte messageDigest[] = digest.digest();
			return toHexString(messageDigest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String MD5EncodeToHex(String s) {
		try {
			// Create MD5 Hash
			MessageDigest digest = java.security.MessageDigest
					.getInstance("MD5");
			digest.update(s.getBytes());
			byte messageDigest[] = digest.digest();
			return toHexString(messageDigest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String MD5EncodeToHex(String strSrc, String key) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(strSrc.getBytes("UTF8"));
			String result = "";
			byte[] temp;
			temp = md5.digest(key.getBytes("UTF8"));
			for (int i = 0; i < temp.length; i++) {
				result += Integer.toHexString(
						(0x000000ff & temp[i]) | 0xffffff00).substring(6);
			}
			return result;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
		}

		return null;
	}
	
	/**
	* Md5 32位 or 16位 加密
	* @param plainText 
	* @return 32位加密
	*/
	public static String Md5(String plainText,String charset) {
		StringBuffer buf = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5"); 
			md.update(plainText.getBytes(charset));
			byte b[] = md.digest(); 
			int i; 
			buf = new StringBuffer(""); 
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if(i<0) i+= 256;
				if(i<16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			// Log.e("555","result: " + buf.toString());//32位的加密 
			//Log.e("555","result: " + buf.toString().substring(8,24));//16位的加密

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buf.toString(); 
	}
}
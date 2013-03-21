package com.test.security.util;

import org.apache.commons.codec.binary.Base64;


/**
 * 基于apache的base64编码
 * @author huozhicheng@gmail.com
 * @date 2013-3-20下午4:49:52
 * @version 1.0
 */
public class Base64SecurityUtil{
	/**
	 * base64加密encode
	 * @param originalString 原文
	 * @return 编码后的字符串
	 */
	public static String getEncryptString(String originalString){
		byte[] arr = Base64.encodeBase64(originalString.getBytes(), true);
		return new String(arr);
	}
	
	/**
	 * base64解密decode
	 * @param encryptString 加密的密文
	 * @return 解密后的明文
	 */
	public static String getDecryptString(String encryptString){
		byte[] arr = Base64.decodeBase64(encryptString.getBytes());
		return new String(arr);
	}
	
	public static void main(String[] args){
		String str="我爱北京！";
		
		String str1=Base64SecurityUtil.getEncryptString(str);
		System.out.println("Base64Encode = "+str1);
		
		String str2=Base64SecurityUtil.getDecryptString(str1);
		System.out.println("Base64Decode = "+str2);
		
	}
}
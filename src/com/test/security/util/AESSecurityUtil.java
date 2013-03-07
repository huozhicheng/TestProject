package com.test.security.util;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;


/**
 * AES算法加密解密实用工具类
 * 说明：
 * 作者：何杨(heyang78@gmail.com)
 * 创建时间：2010-11-29 上午11:19:11
 * 修改时间：2010-11-29 上午11:19:11
 */
public class AESSecurityUtil{
	// 加密方法
	private static final String Algorithm="AES";
	
	// 进行加密解密的密钥
	private static final String Key="03a53dfc257fe1b0996626a5e2e2210692936bd16cc60f37211cbeef9353e268";
	
	/**
	 * 取得解密后的字符串
	 * 
	 * 说明：
	 * @param encryptArr
	 * @return
	 * 创建时间：2010-12-1 下午03:33:31
	 */
	public static String getDecryptString(byte[] encryptArr){
		try{
			Cipher cp=Cipher.getInstance(Algorithm);
			cp.init(Cipher.DECRYPT_MODE, getKey());
			byte[] arr=cp.doFinal(encryptArr);
			
			return new String(arr);
		}
		catch(Exception ex){
			System.out.println("无法进行解密，原因是"+ex.getMessage());
			return null;
		}
	}
	
	/**
	 * 取得加密后的字节数组
	 * 
	 * 说明：
	 * @param originalString
	 * @return
	 * 创建时间：2010-12-1 下午03:33:49
	 */
	public static byte[] getEncryptByteArray(String originalString){
		try{
			Cipher cp=Cipher.getInstance(Algorithm);
			cp.init(Cipher.ENCRYPT_MODE, getKey());
			return cp.doFinal(originalString.getBytes());
		}
		catch(Exception ex){
			System.out.println("无法进行加密，原因是"+ex.getMessage());
			return null;
		}
	}
	
	/**
	 * 取得密钥数组
	 * 
	 * 说明：
	 * @return
	 * @throws Exception
	 * 创建时间：2010-12-1 下午03:31:08
	 */
	private static byte[] initKey() throws Exception{
		KeyGenerator kg=KeyGenerator.getInstance(Algorithm);
		kg.init(256);
		
		SecretKey sk=kg.generateKey();
		
		return sk.getEncoded();
	}
	
	/**
	 * 取得字符串形式的密钥
	 * 
	 * 说明：
	 * @return
	 * @throws Exception
	 * 创建时间：2010-12-1 下午03:31:36
	 */
	public static String initKeyHex() throws Exception{
		return new String(Hex.encodeHex(initKey()));
	}
	
	/**
	 * 取得密钥
	 * 
	 * 说明：
	 * @return
	 * @throws Exception
	 * 创建时间：2010-12-1 下午03:33:17
	 */
	private static Key getKey() throws Exception{
		byte[] arr=Hex.decodeHex(Key.toCharArray());
		
		return new SecretKeySpec(arr,Algorithm);
	}
	
	public static void main(String[] args)  throws Exception{
		//System.out.println(initKeyHex());
		
		String str="Hello!World 你好！世界。";
		
		byte[] arr=AESSecurityUtil.getEncryptByteArray(str);
		System.out.print("AES加密后的结果为:");
		for(byte b:arr){
			System.out.print(b);
		}
		System.out.println();
		
		String str1=AESSecurityUtil.getDecryptString(arr);
		System.out.println("AES解密后的字符串为:"+str1);
	}
}
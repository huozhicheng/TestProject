package com.test.security.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
/**
 * RSA签名验签
 * @author huozhicheng@gmail.com
 * @date 2012-12-24下午2:19:18
 * @version 1.0
 */
public class RSAUtility {

	/**
	 * 商户私钥签名： 商户的签名方法如下：BASE64(RSA(MD5(src),privatekey))，其中src为需要签名的字符串，privatekey是商户的CFCA证书私钥。
	 * @param plainText 待签名字符串
	 * @param path 签名私钥路径
	 * @param password  签名私钥密码
	 * @return 返回签名后的字符串
	 * @throws Exception
	 */
	public static String sign(String plainText,String path,String password) throws Exception  {
		/*
		 * MD5加密
		 */
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(plainText.getBytes("utf-8"));
		byte[] digestBytes = md5.digest();
		/*
		 * 用商户私钥进行签名 RSA
		 */
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, RSAUtil.getPrivateKey(path, password));
		byte[] rsaBytes = cipher.doFinal(digestBytes);
		return Base64.byteArrayToBase64(rsaBytes);
	}
	/**
	 * 用前置公钥证书进行验签
	 * @param message  签名之前的原文
	 * @param cipherText  签名
	 * @param pubKeyn 公钥n串
	 * @param pubKeye 公钥e串
	 * @return boolean 验签成功为true,失败为false
	 * @throws Exception
	 */
	public static boolean verify(String message, String cipherText,String pubKeyn,String pubKeye) throws Exception {
		Cipher c4 = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		// 根据密钥，对Cipher对象进行初始化,DECRYPT_MODE表示解密模式
		c4.init(Cipher.DECRYPT_MODE, RSAUtil.getPublicKey(pubKeyn,pubKeye));
		// 解密
		byte[] desDecTextBytes = c4.doFinal(Base64.base64ToByteArray(cipherText));
		// 得到前置对原文进行的MD5
		String md5Digest1 = Base64.byteArrayToBase64(desDecTextBytes);
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(message.getBytes("utf-8"));
		byte[] digestBytes = md5.digest();
		// 得到商户对原文进行的MD5
		String md5Digest2 = Base64.byteArrayToBase64(digestBytes);
		// 验证签名
		if (md5Digest1.equals(md5Digest2)) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 3DES解密
	 * 
	 * @param desKeyBytes
	 *            DESKEY 3DES密钥
	 * @param desEncTextString
	 *            经过BASE64编码的3DES加密数据
	 * @return 返回通过3DESKEY解密desEncTextString的数据
	 */
	public static String decrypt3DES(byte[] desKeyBytes, String desEncTextString)
			throws Exception {
		StringBuffer strBuf = new StringBuffer();
		SecretKey desKey = new SecretKeySpec(desKeyBytes, "DESede");
		byte[] desEncTextBytes = Base64.base64ToByteArray(desEncTextString);
		//
		Cipher c4 = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		// 根据密钥，对Cipher对象进行初始化,DECRYPT_MODE表示解密模式
		c4.init(Cipher.DECRYPT_MODE, desKey);
		// 解密
		byte[] desDecTextBytes = c4.doFinal(desEncTextBytes);
		strBuf.append(new String(desDecTextBytes, "utf-8"));
		return strBuf.toString();
	}

	/**
	 * 3DES加密
	 * 
	 * @param desKeyBytes
	 *            3DES key
	 * @param plainText
	 *            待加密字符串
	 * @return 返回加密后的字符串 并用BASE64编码 BASE64(3DES(src))
	 * @throws Exception
	 */
	public static String encrypt3DES(byte[] desKeyBytes, String plainText)
			throws Exception {
		// 用对称密钥加密原文
		// 生成Cipher对象，指定其支持3DES算法
		Cipher c2 = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		// 根据密钥，对Cipher对象进行初始化,ENCRYPT_MODE表示加密模式
		SecretKey desKey = new SecretKeySpec(desKeyBytes, "DESede");
		c2.init(Cipher.ENCRYPT_MODE, desKey);
		byte[] srcBytes = plainText.getBytes("utf-8");
		// 加密
		byte[] encBytes = c2.doFinal(srcBytes);
		return Base64.byteArrayToBase64(encBytes);
	}

	/**
	 * MD5加密
	 * 
	 * @param plainText
	 *            待加密字符串
	 * @return 返回加密后的字符串 并用BASE64编码 BASE64(MD5(src))
	 * @throws Exception
	 */
	public static String encryptMD5(String plainText) throws Exception {// MD5加密
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(plainText.getBytes("utf-8"));
		byte[] digestBytes = md5.digest();
		String md5Digest = Base64.byteArrayToBase64(digestBytes);
		return md5Digest;
	}

	/**
	 * MD5验证
	 * 
	 * @param message
	 *            明文
	 * @param MD5Code
	 *            MD5加密的数据
	 * @return true为验证通过，false为未通过
	 * @throws Exception
	 */
	public static boolean verify(String message, String MD5Code)// MD5验证
			throws Exception {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		//
		md5.update(message.getBytes("utf-8"));

		byte[] digestBytes = md5.digest();
		String md5Digest = Base64.byteArrayToBase64(digestBytes);
		if (!md5Digest.trim().equals(MD5Code.trim())) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 商户私钥签名： 商户的签名方法如下：BASE64(RSA(MD5(src),privatekey))，其中src为需要签名的字符串，
	 * privatekey是商户的CFCA证书私钥。
	 * 
	 * @param plainText
	 *            待签名字符串
	 * @return
	 * @throws Exception
	 */
	public String sign(PrivateKey privateKey, String plainText)
			throws Exception {
		/*
		 * MD5加密
		 */
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(plainText.getBytes("utf-8"));
		byte[] digestBytes = md5.digest();
		/*
		 * 用商户私钥进行签名 RSA
		 */
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		byte[] rsaBytes = cipher.doFinal(digestBytes);
		return Base64.byteArrayToBase64(rsaBytes);
	}

	/**
	 * 产生对称密钥3DES
	 * 
	 * @return
	 */
	public static byte[] generate3DESKey() {
		// 产生对称密钥3DES
		KeyGenerator gen = null;
		try {
			gen = KeyGenerator.getInstance("DESede");
		} catch (NoSuchAlgorithmException e) {
		}
		//
		SecretKey desKey = gen.generateKey();
		//
		byte[] rawBytes = desKey.getEncoded();
		return rawBytes;
	}
}

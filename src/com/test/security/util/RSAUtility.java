package com.test.security.util;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
/**
 * RSA,3DES加减密类
 * @author huozhicheng@gmail.com
 * @date 2012-12-24下午2:19:18
 * @version 1.0
 */
public class RSAUtility {

	/**
	 * RSA解密 RSA/ECB/PKCS1Padding
	 * 
	 * @param rsaEncString
	 *            经过BASE64编码的待解密数据
	 * @return 解密后数据
	 */
	public static byte[] decryptRSA(PrivateKey privateKey, String rsaEncString)
			throws Exception {
		// 解密数据
		byte[] decryptByte = null;
		byte[] rsaBytes = Base64.base64ToByteArray(rsaEncString);
		Cipher cp = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cp.init(Cipher.DECRYPT_MODE, privateKey);
		decryptByte = cp.doFinal(rsaBytes);
		return decryptByte;
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
	 * 根据modulus和public exponent得到公钥
	 * 
	 * @param modulus 十进制
	 * @param publicExponent 十进制
	 * @return
	 * @throws Exception
	 */
	public static PublicKey getPublickKey(String modulus, String publicExponent)
			throws Exception {
		KeySpec publicKeySpec = new RSAPublicKeySpec(
				new BigInteger(decimalTo16(modulus), 16), new BigInteger(decimalTo16(publicExponent), 16));
		KeyFactory factory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = factory.generatePublic(publicKeySpec);
		return publicKey;
	}
	
	/** 
	 * @return 返回私钥privateKey
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey(String modulus, String privateExponent)
			throws Exception {
		KeySpec privateKeySpec = new RSAPrivateKeySpec(
				new BigInteger(decimalTo16(modulus), 16), new BigInteger(decimalTo16(privateExponent), 16));
		KeyFactory factory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = factory.generatePrivate(privateKeySpec);
		return privateKey;
	}
	
    
	/**
	 * 将10进制转换为16进制
	 * 公钥的n、e转换为16进制，默认为10进制
	 * @param str 十进制数据
	 * @return
	 */
	public static String decimalTo16(String str){
		BigInteger bi = new BigInteger(str);
		return bi.toString(16);
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
	 * RSA加密
	 * 
	 * @param modulus
	 * @param publicExponent
	 * @param plainText
	 * @param certVersion
	 * @return
	 * @throws Exception
	 */
	public static String encryptRSA(PublicKey publicKey, byte[] rawBytes)
			throws Exception {
		StringBuffer strBuf = new StringBuffer();
		// 用公钥加密密码
		// Cipher负责完成加密或解密工作，基于RSA
		Cipher c1 = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		// 根据公钥，对Cipher对象进行初始化
		c1.init(Cipher.ENCRYPT_MODE, publicKey);
		// 加密
		//
		byte[] rsaEncBytes = c1.doFinal(rawBytes);

		// 用RSA公钥加密后的对称密钥
		strBuf.append(Base64.byteArrayToBase64(rsaEncBytes));
		return strBuf.toString();
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

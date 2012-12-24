package com.test.security.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.KeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Enumeration;

import javax.crypto.Cipher;

/**
 * 安全类，包括MD5加密、签名、读私钥公钥等方法
 * @author huozhicheng@gmail.com
 * @date 2012-12-24下午2:16:27
 * @version 1.0
 */
public class SecurityUtil {

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
		cipher.init(Cipher.ENCRYPT_MODE, getPrivateKey(path, password));
		byte[] rsaBytes = cipher.doFinal(digestBytes);
		return Base64.byteArrayToBase64(rsaBytes);
	}
	/**
	 * 读取私钥  返回PrivateKey
	 * @param path  包含私钥的证书路径
	 * @param password  私钥证书密码
	 * @return 返回私钥PrivateKey
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 * @throws UnrecoverableKeyException
	 */
	public static PrivateKey getPrivateKey(String path,String password) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException {
		KeyStore ks = KeyStore.getInstance("PKCS12");
		FileInputStream fis = new FileInputStream(path);
		char[] nPassword = null;
		if ((password == null) || password.trim().equals("")) {
			nPassword = null;
		} else {
			nPassword = password.toCharArray();
		}
		ks.load(fis, nPassword);
		fis.close();
		Enumeration<String> en = ks.aliases();
		String keyAlias = null;
		if (en.hasMoreElements()) {
			keyAlias = (String) en.nextElement();
		}
		
		//Certificate cert = ks.getCertificate(keyAlias);
		//System.out.println("商户取公钥   --- 公钥："+Base64.byteArrayToBase64(cert.getEncoded()));
		//System.out.println("收银台公钥   --- 公钥："+Base64.byteArrayToBase64(cert.getPublicKey().getEncoded()));
		
		return (PrivateKey) ks.getKey(keyAlias, nPassword);
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
		c4.init(Cipher.DECRYPT_MODE, getPublickKey(pubKeyn,pubKeye));
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
	 * 读取商户公钥cer
	 * @param path .cer文件的路径  如：c:/abc.cer
	 * @return  base64后的公钥串
	 * @throws IOException
	 * @throws CertificateException
	 */
	public static String getPublicKey(String path) throws IOException, CertificateException{
		InputStream inStream = new FileInputStream(path);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int ch;
		String res = "";
		while ((ch = inStream.read()) != -1) {
			out.write(ch);
		}
		byte[] result = out.toByteArray();
		res = Base64.byteArrayToBase64(result);
		return res;
	}
	/** 
	 * 根据公钥modulus、publicExponent生成公钥
	 * @param modulus   公钥publicExponent串    16进制
	 * @param publicExponent  公钥publicExponent串    16进制
	 * @return 返回公钥PublicKey
	 * @throws Exception
	 */
	public static PublicKey getPublickKey(String modulus, String publicExponent)
			throws Exception {
		KeySpec publicKeySpec = new RSAPublicKeySpec(
				new BigInteger(modulus, 16), new BigInteger(publicExponent, 16));
		KeyFactory factory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = factory.generatePublic(publicKeySpec);
		return publicKey;
	}
}

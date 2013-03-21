package com.test.security.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.spec.KeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Enumeration;

import javax.crypto.Cipher;

/**
 * RSA加密解密、公钥私钥的读取
 * @author huozhicheng@gmail.com
 * @date 2013-3-21下午3:34:05
 * @version 1.0
 */
public class RSAUtil {
	
	/**
	 * 将10进制数据转换为16进制
	 * @param str 十进制的字符串数据
	 * @return 返回16进制的字符串
	 */
	public static String decimalTo16(String str){
		BigInteger bi = new BigInteger(str);
		return bi.toString(16);
	}
	
	/**
	 * 根据modulus,public exponent得到PublicKey
	 * @param modulus 十进制的modulus串
	 * @param publicExponent 十进制的publicExponent串
	 * @return PublicKey类型的公钥
	 * @throws Exception
	 */
	public static PublicKey getPublicKey(String modulus, String publicExponent) throws Exception {
		//将字符串转换为BigInteger类型
		BigInteger modulusBigInt = new BigInteger(decimalTo16(modulus), 16);
		BigInteger publicExponentBigInt = new BigInteger(decimalTo16(publicExponent), 16);
		
		KeySpec publicKeySpec = new RSAPublicKeySpec(modulusBigInt, publicExponentBigInt);
		KeyFactory factory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = factory.generatePublic(publicKeySpec);
		return publicKey;
	}
	/**
	 * 通过指定的证书cer文件读取公钥
	 * @param path .cer文件的路径  如：c:/abc.cer
	 * @return 经过Base64后的字符串
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
	 * 通过path地址，读取私钥数据，并返回PrivateKey
	 * @param path 私钥文件的路径
	 * @param password 私钥的密码
	 * @return PrivateKey类型的公钥
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKeyByPath(String path,String password) throws Exception {
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
	 * 根据modulus,private exponent得到PrivateKey
	 * @param modulus 十进制的modulus串
	 * @param privateExponent 十进制的privateExponent串
	 * @return PrivateKey类型的公钥
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey(String modulus, String privateExponent) throws Exception {
		//将字符串转换为BigInteger类型
		BigInteger modulusBigInt = new BigInteger(decimalTo16(modulus), 16);
		BigInteger privateExponentBigInt = new BigInteger(decimalTo16(privateExponent), 16);
		
		KeySpec privateKeySpec = new RSAPrivateKeySpec(modulusBigInt, privateExponentBigInt);
		KeyFactory factory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = factory.generatePrivate(privateKeySpec);
		return privateKey;
	}
	/**
	 * 使用公钥进行RSA加密
	 * @param publicKey 公钥
	 * @param originalString  待加密的字符串
	 * @param charset 待加密字符串的编码 如：utf-8
	 * @return 加密后的byte数组
	 * @throws Exception
	 */
	public static byte[] encryptRSA(PublicKey publicKey, String originalString, String charset) throws Exception {
		//Cipher负责完成加密或解密工作，基于RSA
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		//根据公钥，对Cipher对象进行初始化 ENCRYPT_MODE为加密模式
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		//加密
		byte[] rsaEncBytes = cipher.doFinal(originalString.getBytes(charset));
		return rsaEncBytes;
	}
	/**
	 * 使用私钥进行RSA解密
	 * @param privateKey 私钥
	 * @param rsaBytes 待解密的byte数组
	 * @param charset 待加密字符串的编码 如：utf-8
	 * @return
	 * @throws Exception
	 */
	public static String decryptRSA(PrivateKey privateKey, byte[] rsaBytes, String charset)	throws Exception {
		//解密数据
		Cipher cp = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		//DECRYPT_MODE为解密模式
		cp.init(Cipher.DECRYPT_MODE, privateKey);
		byte [] decryptByte = cp.doFinal(rsaBytes);
		return new String(decryptByte, charset);
	}
	public static void main(String args[]) throws Exception{
		
		String modulus = "119998508602861008568312975045511451291403975889080868426458977554528216609303772285435297463246718205900021203461243440729135051019880487590699503748761873464892699853338577418434719764844871443654482976510134285830378107188162498670997967619820569804868320852662549631922412379254550991027727514318348371107";
		String publicExponent = "65537";
		String privateExponent = "83872799846975818537447738650071609156741106909854392785919501729408944843712374644535677112149509755674844305765432935433106325923793666098038850850962572694732104875772516489035538670174375861554324401910240963898828512640827573260307654180428283244645610356881872518943286155812526437448322006352181615049";
		String str = "你好，这里是北京！";
		PublicKey publicKey = RSAUtil.getPublicKey(modulus, publicExponent);
		byte [] encryptArray = RSAUtil.encryptRSA(publicKey, str, "utf-8");
		for(byte b:encryptArray){
			System.out.print(b);
		}
		System.out.println("");
		PrivateKey privateKey = RSAUtil.getPrivateKey(modulus, privateExponent);
		String plainText = RSAUtil.decryptRSA(privateKey, encryptArray, "utf-8");
		System.out.println("解密后明文为："+plainText);
		
	}
}

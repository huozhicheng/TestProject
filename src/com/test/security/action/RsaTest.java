/**
 * 
 */
package com.test.security.action;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

import org.junit.Test;

import com.test.security.util.Base64;
import com.test.security.util.RSAUtility;


/**
 * RSA加减密测试
 * @author huozhicheng@gmail.com
 * @date 2012-11-24下午3:38:00
 * @version 1.0
 */
public class RsaTest {

	static PublicKey publicKey;
	static PrivateKey privateKey;
	@Test
	public void rsaTestDemo() {
		try {
			String s = "北京市海淀区";
			RsaTest rsaTest = new RsaTest();
			byte b[] = rsaTest.encryptRSA(s.getBytes("utf-8"), publicKey);
			StringBuffer sb = new StringBuffer();
			for(byte a:b){
				sb.append(a);
			}
			System.out.println("密文："+sb);
			byte c[] = rsaTest.decryptRSA(b, privateKey);
			System.out.println("原文："+new String(c,"utf-8"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public RsaTest() throws Exception{
		KeyPairGenerator kpg=KeyPairGenerator.getInstance("RSA");
        kpg.initialize(1024);
        
        // 得到密钥对
        KeyPair kp=kpg.generateKeyPair();
        
        // 得到公钥
        publicKey = kp.getPublic();
        //publicKey=keyPublic.getEncoded();
        
        // 得到私钥
        privateKey = kp.getPrivate();
        //privateKey=keyPrivate.getEncoded();
	}
	/**
	 * RSA加密方法
	 * @param originalByte 原文，类型为byte[]
	 * @param publicKey 公钥
	 * @return 加密后的byte[]
	 * @throws Exception
	 */
	private byte[] encryptRSA(byte[] originalByte,PublicKey publicKey) throws Exception{
		//用公钥加密
		//Cipher负责完成加密或解密工作，基于RSA
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		//根据公钥，对Cipher对象进行初始化，ENCRYPT_MODE为加密模式
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		//加密
		return cipher.doFinal(originalByte);
	}
	/**
	 * RSA解密方法
	 * @param rsaEncByte 密文，类型为byte[]
	 * @param privateKey 私钥
	 * @return 解密后的byte[]
	 * @throws Exception
	 */
	public byte[] decryptRSA(byte[] rsaEncByte, PrivateKey privateKey)	throws Exception {
		//Cipher负责完成加密或解密工作，基于RSA
		Cipher cp = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		//DECRYPT_MODE为解密模式
		cp.init(Cipher.DECRYPT_MODE, privateKey);
		//解密数据
		return cp.doFinal(rsaEncByte);
	}
	@Test
	public void getPublicKey(){
		try {
			PublicKey pkey = RSAUtility.getPublickKey("97827999251931786011331257101039860019161246984328605110974093176037976837685111866678569994714635833629150686846743147242205382135488118004199599475305747442718127089447426155254804363623754145696366788413510844608513267611881763397053621480300534323240653468042208805075424959583222589406658867330717941509", "65537");
			byte b[] = new RsaTest().encryptRSA("北京".getBytes("utf-8"), pkey);
			StringBuffer sb = new StringBuffer();
			for(byte a:b){
				sb.append(a);
			}
			System.out.println("密文："+sb);
			System.out.println(Base64.byteArrayToBase64(pkey.getEncoded()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

/**
 * 
 */
package com.test.security.action;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.junit.Test;

import com.test.security.util.SecurityUtil;

/**
 * 从文件中得到公私钥
 * @author huozhicheng@gmail.com
 * @date 2012-12-8下午3:55:04
 * @version 1.0
 */
public class GetKeyFromFile {

	@Test
	public void test() throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		PrivateKey pk = SecurityUtil.getPrivateKey("c:/898000000000002.pfx","898000000000002");
		System.out.println(pk);
	}

}

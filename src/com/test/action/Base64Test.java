/**
 * 
 */
package com.test.action;

import org.junit.Test;

import com.test.util.Base64;

/**
 * @author huozhicheng@gmail.com
 * @date 2012-11-26下午4:19:27
 * @version 1.0
 */
public class Base64Test {
	@Test
	public void base64Test(){
		StringBuffer configFile = new StringBuffer();
		configFile.append("<?xml version='1.0' encoding='UTF-8'?>");
		configFile.append("<argorse>");
		configFile.append("<netTimeout>10000</netTimeout>");
		configFile.append("<modulus>97827999251931786011331257101039860019161246984328605110974093176037976837685111866678569994714635833629150686846743147242205382135488118004199599475305747442718127089447426155254804363623754145696366788413510844608513267611881763397053621480300534323240653468042208805075424959583222589406658867330717941509</modulus>");
		configFile.append("<publicExponent>65537</publicExponent>");
		configFile.append("</argorse>");
		System.out.println(Base64.byteArrayToBase64(configFile.toString().getBytes()));
	}

}

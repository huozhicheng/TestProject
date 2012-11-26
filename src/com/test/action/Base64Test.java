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
		configFile.append("<n>105010148924345691105674570882725096529974561961982710014549335291319631717970727735364995120681480785804960354430755705966131423691951114378177292726736830458368572552319478864345662809404494414446476486579649441616831845136544612049913951751567843956406356127072061245486291274421152480835217264280418582287</n>");
		configFile.append("<e>65537</e>");
		configFile.append("</argorse>");
		System.out.println(Base64.byteArrayToBase64(configFile.toString().getBytes()));
	}

}

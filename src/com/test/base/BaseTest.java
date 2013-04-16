/**
 * 
 */
package com.test.base;

import org.junit.Test;

/**
 * @author huozhicheng@gmail.com
 * @date 2012-11-27下午5:09:35
 * @version 1.0
 */
public class BaseTest {
	@Test
	public void strTest(){
		//System.out.println("1.0".compareTo("1.3"));
		BaseTest obj = new BaseTest();
		System.out.println(obj.getClass().getSimpleName());
		System.out.println(obj.getClass().getSimpleName());
		System.out.println(BaseTest.class.getSimpleName());
		
	}
}

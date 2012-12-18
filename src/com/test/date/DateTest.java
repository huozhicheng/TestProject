/**
 * 
 */
package com.test.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

/**
 * @author huozhicheng@gmail.com
 * @date 2012-12-4上午10:54:23
 * @version 1.0
 */
public class DateTest {

	public void test(){
		/*Date d = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		System.out.println(d);
		System.out.println(c.get(Calendar.YEAR)+"m:"+c.get(Calendar.MONTH)+"d:"+c.get(Calendar.DATE));
		System.out.println(Integer.valueOf("01"));*/
		String s = "0";
		//DecimalFormat formater = new DecimalFormat("#.00");
		//Double d = null;
		//System.out.println(d/100);
		//System.out.println(formater.format(d/100));
		System.out.println(String.format("%.2f",Double.parseDouble(s)/100));

		//几天后的时间
		Date d=new Date();   
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");   
		System.out.println("今天的日期："+df.format(d));   
		System.out.println("两天前的日期：" + df.format(new Date(d.getTime() - 2 * 24 * 60 * 60 * 1000)));  
		System.out.println("一天后的日期：" + df.format(new Date(d.getTime() + 1*24*60*60*1000)));
		System.out.println("三天后的日期：" + df.format(new Date(d.getTime() + 3 * 24 * 60 * 60 * 1000)));
		
		Calendar calendar=Calendar.getInstance();   
		calendar.setTime(new Date());
		System.out.println(calendar.get(Calendar.DAY_OF_MONTH));//今天的日期
		calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)+1);//让日期加1  
		System.out.println(calendar.get(Calendar.DAY_OF_MONTH));//加1之后的日期
	}
	/**
	 * 7天前
	 */
	//@Test
	public void sevenDay(){
		Calendar calendar=Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_MONTH, Calendar.DAY_OF_MONTH-5);
		String d = df.format(calendar.getTime());
		System.out.println("2012113009242".substring(0,8).compareTo(d));
		
	}
	@Test
	public  void timeAdd(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.HOUR_OF_DAY, 3);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		System.out.println(calendar.getTimeInMillis()+" - "+new Date().getTime());
	}
}

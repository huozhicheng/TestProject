package com.test.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionFactory {		
	
    private static  ConnectionFactory  conn=new ConnectionFactory();//自行定义ConnectionFactory对
                                                                 
	 private  ConnectionFactory() {
		 try{
			 Class.forName("com.mysql.jdbc.Driver");//注册
			 
		 }catch(ClassNotFoundException e){
			 System.out.println("加载失败");
			 
		 }
	 }
	 //连接数据库connection  DriverManager
	 public static Connection getConnection() throws SQLException{
	   String url="jdbc:mysql://202.206.84.58:3306/chargedb?useUnicode=true&amp&characterEncoding=gbk";
	   String username="root";
	   String psw="123";	  
	   return DriverManager.getConnection(url,username,psw);
	    }
	 //关闭
	 public static void close(ResultSet rs) throws SQLException{
		 rs.close();
	 }
	 public static void close(PreparedStatement pst){
		 try {
			pst.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 public static void close(Connection conn){
		 try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	 }
	 

}